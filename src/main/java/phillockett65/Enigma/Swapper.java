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
 * Swapper is a class that captures a directional mapping.
 */
package phillockett65.Enigma;

import java.util.Arrays;

public class Swapper {

    public final static int RIGHT_TO_LEFT = 1;
    public final static int LEFT_TO_RIGHT = 2;

    public final int R2L = RIGHT_TO_LEFT;
    public final int L2R = LEFT_TO_RIGHT;

    protected final String id;
    protected final int[] map;
    protected final boolean reflect;

    protected int[] leftMap;
    protected int[] rightMap;

    public static final int CASE_DELTA = 'a' - 'A';


    /************************************************************************
     * General support code.
     */

    public static int charToUpper(int v) { return ((v >= 'A') && (v <= 'Z')) ? v : v - CASE_DELTA; }
    public static int charToIndex(int v) { return charToUpper(v) - 'A'; }
    public static int indexToChar(int v) { return v + 'A'; }
    public static String indexToString(int v) { return "" + (char)indexToChar(v); }

    public static int charToInt(int v) { return charToIndex(v) + 1; }
    public static int intTochar(int v) { return indexToChar(v-1); }
    public static String intToString(int v) { return "" + (char)intTochar(v); }


    /************************************************************************
     * Initialization support code.
     */

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

    public Swapper(String id, int[] map) {
        this.id = id;
        this.map = map;
        reflect = setReflect();

        rightMap = new int[26];
        leftMap = new int[26];

        for (int i = 0; i < map.length; ++i) {
            rightMap[i] = map[i];
            leftMap[map[i]] = i;
        }
    }


    /************************************************************************
     * Getters support code.
     */

    public boolean is(String target) { return target.equals(id); }

    public String getId() { return id; }
    public int[] getMap() { return map; }
    public boolean isReflector() { return reflect; }

	public int[] getLeftMap()	{ return leftMap; }
    public int[] getRightMap()	{ return rightMap; }

    public int leftToRight(int index) { return leftMap[index]; }
    public int rightToLeft(int index) { return rightMap[index]; }


    /************************************************************************
     * Setters support code.
     */

    public void setOffset(int value) {}

    public int swap(int direction, int index) {
        final int output = map[index];

        System.out.print(id + "(" + Rotor.indexToString(index) + "->" + Rotor.indexToString(output) + ")  ");

        return output;
    }


    /************************************************************************
     * Debug support code.
     */

    @Override
    public String toString() {
        return "Rotor [" + 
            "id=" + id + 
            ", map=" + Arrays.toString(map) + 
            ", reflect=" + reflect + 
            "]";
    }

    protected void dumpMapping(int[] map) {
        for (int i = 0; i < map.length; ++i)
            System.out.print(Rotor.indexToString(map[i]));

        System.out.println();
    }

    public void dumpMap() { dumpMapping(map); }
    public void dumpLeftMap() { dumpMapping(map); }
    public void dumpRightMap() { dumpMapping(map); }

}