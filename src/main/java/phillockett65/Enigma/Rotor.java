/*  Enigma - a JavaFX based playing card image generator.
 *
 *  Copyright 2022 Philip Lockett.
 *
 *  This file is part of BaseFXML.
 *
 *  Enigma is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Enigma is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Enigma.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * Rotor is a class that is responsible for capturing the details of a rotor.
 */
package phillockett65.Enigma;

import java.util.Arrays;

public class Rotor {
    private final String id;
    private final String cipher;
    private final String date;
    private final String name;
    private final int[] map;
    private final boolean[] turnover;
    private final boolean reflect;
	
	private int ringSetting;
    private int[] leftMap;
    private int[] rightMap;

    public static final int CASE_DELTA = 'a' - 'A';

    public static int charToUpper(int v) { return ((v >= 'A') && (v <= 'Z')) ? v : v - CASE_DELTA; }
    public static int charToIndex(int v) { return charToUpper(v) - 'A'; }
    public static int indexToChar(int v) { return v + 'A'; }
    public static String indexToString(int v) { return "" + (char)indexToChar(v); }

    public static int charToInt(int v) { return charToIndex(v) + 1; }
    public static int intTochar(int v) { return indexToChar(v-1); }
    public static String intToString(int v) { return "" + (char)intTochar(v); }

    public boolean is(String target) {
        return target.equals(id);
    }

    private int[] buildIndices() {
        int [] alan = new int[26];

        for (int i = 0; i < cipher.length(); ++i) {
            final int c = charToIndex(cipher.charAt(i));
            alan[i] = c;
        }

        return alan;
    }

    private boolean[] buildTurnover(String map) {
        boolean [] turing = new boolean[26];

        for (int i = 0; i < turing.length; ++i)
            turing[i] = false;

        for (int i = 0; i < map.length(); ++i) {
            final int c = charToIndex(map.charAt(i));
            turing[c] = true;
        }

        return turing;
    }

    private boolean setReflect() {

        for (int i = 0; i < map.length; ++i) {
            final int c = map[i];
            if (c == i)
                return false;
            if (map[c] != i)
                return false;
        }

        return true;
    }

    public Rotor(String id, String cipher, String date, String name, String turnover) {
        this.id = id;
        this.cipher = cipher;
        this.date = date;
        this.name = name;
        map = buildIndices();
        this.turnover = buildTurnover(turnover);
        reflect = setReflect();
    }

    public String getId()		{ return id; }
    public String getCipher()	{ return cipher; }
    public String getDate()		{ return date; }
    public String getName()		{ return name; }
    public int[] getMap()		{ return map; }
    public boolean isReflector() { return reflect; }
    public boolean isTurnoverPoint(int index) { return turnover[index]; }

    public int getRingSetting()	{ return ringSetting; }
	public int[] getLeftMap()	{ return leftMap; }
    public int[] getRightMap()	{ return rightMap; }

    public int[] getMap(int index) {
        // System.out.println("getMap(" + index + ")");

        int [] alan = new int[26];

        int a = 0;
        for (int i = index; i < map.length; ++i)
            alan[a++] = map[i];
        for (int i = 0; i < index; ++i)
            alan[a++] = map[i];

        return alan;
    }

	public void setRingSetting(int index) {
        // System.out.println("setRingSetting(" + index + ")");

		ringSetting = index;
        rightMap = new int[26];
        leftMap = new int[26];

        int a = 0;
		final int max = map.length + index;
        for (int i = index; i < max; ++i)
			rightMap[a++] = map[i % 26];

		for (int i = 0; i < map.length; ++i)
			leftMap[rightMap[i]] = i;
    }

    @Override
    public String toString() {
        return "Rotor [" + 
            "id=" + id + 
            ", map=" + Arrays.toString(map) + 
            // ", cipher=" + cipher + 
            ", name=" + name + 
            ", reflect=" + reflect + 
            // ", date=" + date + 
            "]";
    }

}
