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
    private final boolean[] notches;
    private final boolean reflect;
    
    private int ringSetting;
    private int[] leftMap;
    private int[] rightMap;


    private void dumpMapping(int[] map) {
        for (int i = 0; i < map.length; ++i)
            System.out.print(Rotor.indexToString(map[i]));

        System.out.println();
    }
    public void dumpFlags(boolean[] flags) {
        for (int i = 0; i < flags.length; ++i)
            System.out.print(flags[i] ? "1" : "0");

        System.out.println();
    }

    public void dumpCipher() { System.out.println(cipher); }
    public void dumpMap() { dumpMapping(map); }
    public void dumpLeftMap() { dumpMapping(leftMap); }
    public void dumpRightMap() { dumpMapping(rightMap); }
    public void dumpTurnover() { dumpFlags(turnover); }
    public void dumpNotches() { dumpFlags(notches); }

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

    private boolean[] buildTurnover(String turnovers) {
        boolean [] turing = new boolean[26];

        for (int i = 0; i < turing.length; ++i)
            turing[i] = false;

        for (int i = 0; i < turnovers.length(); ++i) {
            final int c = charToIndex(turnovers.charAt(i));
            turing[c] = true;
        }

        return turing;
    }

    private boolean[] buildNotches() {
        boolean [] turing = new boolean[26];

        for (int i = 1; i < turnover.length; ++i)
            turing[i - 1] = turnover[i];

        turing[turnover.length - 1] = turnover[0];

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
        this.notches = buildNotches();
        reflect = setReflect();

        rightMap = new int[26];
        leftMap = new int[26];
    }

    public String getId()		{ return id; }
    public String getCipher()	{ return cipher; }
    public String getDate()		{ return date; }
    public String getName()		{ return name; }
    public int[] getMap()		{ return map; }
    public boolean isReflector() { return reflect; }
    public boolean isTurnoverPoint(int index) { return turnover[index]; }
    public boolean isNotchPoint(int index) { return notches[index]; }

    public int getRingSetting()	{ return ringSetting; }
    public int[] getLeftMap()	{ return leftMap; }
    public int[] getRightMap()	{ return rightMap; }


    /**
     * Set the ring setting and fill in the left and right mappings using the
     * map and ring setting.
     * @param index of the required ring setting.
     */
    public void setRingSetting(int index) {
        // System.out.println("setRingSetting(" + index + ")");

        ringSetting = index;

        for (int i = 0; i < map.length; ++i)
            rightMap[(i + index) % 26] = (map[i] + index) % 26;

        for (int i = 0; i < map.length; ++i)
            leftMap[rightMap[i]] = i;

        // System.out.print("rightMap = ");
        // dumpRightMap();
        // System.out.print("leftMap  = ");
        // dumpLeftMap();
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
