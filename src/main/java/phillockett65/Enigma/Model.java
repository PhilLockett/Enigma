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
 * Model is the class that captures the dynamic shared data plus some 
 * supporting constants and provides access via getters and setters.
 */
package phillockett65.Enigma;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;

public class Model {

    private static final int ROTOR_COUNT = 4;
    /**
     * Responsible for constructing the Model and any local objects. Called by 
     * the controller.
     */
    public Model() {
        initRotorWiring();
    }


    /**
     * Called by the controller after the constructor to initialise any 
     * objects after the controls have been initialised.
     */
    public void initialize() {
        // System.out.println("Model initialized.");

        initializeReflector();
        initializeWheelOrder();
        initializeRingSettings();
        initializeRotorOffsets();
        initializePlugboardConnections();
        initializeEncipher();
    }

    /**
     * Called by the controller after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     */
    public void init() {
        // System.out.println("Model init.");
    }



    /************************************************************************
     * Support code for Rotor definitions.
     */

    ObservableList<Rotor> commercial = FXCollections.observableArrayList();
    ObservableList<Rotor> rocket = FXCollections.observableArrayList();
    ObservableList<Rotor> swissK = FXCollections.observableArrayList();
    ObservableList<Rotor> m3 = FXCollections.observableArrayList();
    ObservableList<Rotor> m4 = FXCollections.observableArrayList();

    private void initRotorWiring() {

        commercial.add(new Rotor("IC",	"DMTWSILRUYQNKFEJCAZBPGXOHV",	"1924",	"Commercial Enigma A, B", "R"));
        commercial.add(new Rotor("IIC",	"HQZGPJTMOBLNCIFDYAWVEUSRKX",	"1924",	"Commercial Enigma A, B", "F"));
        commercial.add(new Rotor("IIIC","UQNTLSZFMREHDPXKIBVYGJCWOA",	"1924",	"Commercial Enigma A, B", "W"));

        rocket.add(new Rotor("I",	"JGDQOXUSCAMIFRVTPNEWKBLZYH",	"7 February 1941",	"German Railway (Rocket)", "R"));
        rocket.add(new Rotor("II",	"NTZPSFBOKMWRCJDIVLAEYUXHGQ",	"7 February 1941",	"German Railway (Rocket)", "F"));
        rocket.add(new Rotor("III",	"JVIUBHTCDYAKEQZPOSGXNRMWFL",	"7 February 1941",	"German Railway (Rocket)", "W"));
        rocket.add(new Rotor("UKW",	"QYHOGNECVPUZTFDJAXWMKISRBL",	"7 February 1941",	"German Railway (Rocket)", ""));
        rocket.add(new Rotor("ETW",	"QWERTZUIOASDFGHJKPYXCVBNML",	"7 February 1941",	"German Railway (Rocket)", ""));

        swissK.add(new Rotor("I-K",		"PEZUOHXSCVFMTBGLRINQJWAYDK",	"February 1939",	"Swiss K", "R"));
        swissK.add(new Rotor("II-K",	"ZOUESYDKFWPCIQXHMVBLGNJRAT",	"February 1939",	"Swiss K", "F"));
        swissK.add(new Rotor("III-K",	"EHRVXGAOBQUSIMZFLYNWKTPDJC",	"February 1939",	"Swiss K", "W"));
        swissK.add(new Rotor("UKW-K",	"IMETCGFRAYSQBZXWLHKDVUPOJN",	"February 1939",	"Swiss K", ""));
        swissK.add(new Rotor("ETW-K",	"QWERTZUIOASDFGHJKPYXCVBNML",	"February 1939",	"Swiss K", ""));

        m3.add(new Rotor("I",		"EKMFLGDQVZNTOWYHXUSPAIBRCJ",	"1930",	"Enigma I", "R"));
        m3.add(new Rotor("II",		"AJDKSIRUXBLHWTMCQGZNPYFVOE",	"1930",	"Enigma I", "F"));
        m3.add(new Rotor("III",		"BDFHJLCPRTXVZNYEIWGAKMUSQO",	"1930",	"Enigma I", "W"));
        m3.add(new Rotor("IV",		"ESOVPZJAYQUIRHXLNFTGKDCMWB",	"December 1938",	"M3 Army", "K"));
        m3.add(new Rotor("V",		"VZBRGITYUPSDNHLXAWMJQOFECK",	"December 1938",	"M3 Army", "A"));
        m3.add(new Rotor("VI",		"JPGVOUMFYQBENHZRDKASXLICTW",	"1939",	"M3 & M4 Naval (FEB 1942)", "AN"));
        m3.add(new Rotor("VII",		"NZJHGRCXMYSWBOUFAIVLPEKQDT",	"1939",	"M3 & M4 Naval (FEB 1942)", "AN"));
        m3.add(new Rotor("VIII",	"FKQHTLXOCBJSPDZRAMEWNIUYGV",	"1939",	"M3 & M4 Naval (FEB 1942)", "AN"));

        m4.add(new Rotor("Beta",				"LEYJVCNIXWPBQMDRTAKZGFUHOS",	"Spring 1941",	"M4 R2", ""));
        m4.add(new Rotor("Gamma",				"FSOKANUERHMBTIYCWLQPZXVGJD",	"Spring 1942",	"M4 R2", ""));
        m4.add(new Rotor("Reflector A",			"EJMZALYXVBWFCRQUONTSPIKHGD",	"",	"", ""));
        m4.add(new Rotor("Reflector B",			"YRUHQSLDPXNGOKMIEBFZCWVJAT",	"",	"", ""));
        m4.add(new Rotor("Reflector C",			"FVPJIAOYEDRZXWGCTKUQSBNMHL",	"",	"", ""));
        m4.add(new Rotor("Reflector B Thin",	"ENKQAUYWJICOPBLMDXZVFTHRGS",	"1940",	"M4 R1 (M3 + Thin)", ""));
        m4.add(new Rotor("Reflector C Thin",	"RDOBJNTKVEHMLFCWZAXGYIPSUQ",	"1940",	"M4 R1 (M3 + Thin)", ""));
        m4.add(new Rotor("ETW",					"ABCDEFGHIJKLMNOPQRSTUVWXYZ",	"",	"Enigma I", ""));
    }

    public void dumpRotorWiring() {
        for (Rotor rotor : commercial)
            System.out.println(rotor.toString());
        System.out.println();
        for (Rotor rotor : rocket)
            System.out.println(rotor.toString());
        System.out.println();
        for (Rotor rotor : swissK)
            System.out.println(rotor.toString());
        System.out.println();
        for (Rotor rotor : m3)
            System.out.println(rotor.toString());
        System.out.println();
        for (Rotor rotor : m4)
            System.out.println(rotor.toString());
        System.out.println();
    }



    /************************************************************************
     * Support code for "Reflector" panel.
     */

    ObservableList<String> reflectorList = FXCollections.observableArrayList();
    private String reflectorChoice;
    private boolean reconfigurable = false;

    private int[] reflectorLetterCounts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private int[] reconfigurableReflectorMap = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    private int[] reflectorMap;

    private ArrayList<Pair> pairs = new ArrayList<Pair>(PrimaryController.PAIR_COUNT);


    public ObservableList<String> getReflectorList()   { return reflectorList; }
    public String getReflectorChoice()   { return reflectorChoice; }
    public void setReflectorChoice(String choice)   { reflectorChoice = choice; }

    public void setReconfigurable(boolean state) { reconfigurable = state; }
    public boolean isReconfigurable() { return reconfigurable; }

    public void setPairText(int index, String text) {
        pairs.get(index).set(text);
        countLetterUsage(reflectorLetterCounts, pairs);
    }

    public void sanitizePair(int index) { pairs.get(index).sanitize(); }

    public String getPairText(int index)	{ return pairs.get(index).get(); }
    public int getPairCount(int index)		{ return pairs.get(index).count(); }

    public boolean isPairValid(int index) {
        Pair pair = pairs.get(index);

        if (pair.isEmpty())
            return true;

        if (!pair.isValid())
            return false;

        final int[] counts = getReflectorLetterCounts();
        final String text = pair.get();
        for (int i = 0; i < text.length(); ++i)
            if (counts[Rotor.charToIndex(text.charAt(i))] > 1)
                return false;

        System.out.println("pair " + index + " is valid");
        return true;
    }

    public void setPairText(String id, String text) { setPairText(idToIndex(id), text); }
    public void sanitizePair(String id) { sanitizePair(idToIndex(id)); }

    public String getPairText(String id)	{ return getPairText(idToIndex(id)); }
    public int getPairCount(String id)		{ return getPairCount(idToIndex(id)); }
    public boolean isPairValid(String id)	{ return isPairValid(idToIndex(id)); }

    public boolean isReflectorValid() {
        for (Pair pair : pairs)
            if (!pair.isValid())
                return false;

        int[] counts = getReflectorLetterCounts();
        for (int i = 0; i < counts.length; ++i)
            if (counts[i] > 1)
                return false;

        return true;
    }

    public int[] getReflectorLetterCounts() {
        return reflectorLetterCounts;
    }

    private void setReflectorMap() {
        int j = Rotor.charToIndex('j');
        int y = Rotor.charToIndex('y');
        reconfigurableReflectorMap[j] = y;
        reconfigurableReflectorMap[y] = j;

        for (Pair pair : pairs) {
            if (pair.isEmpty())
                continue;

            final String text = pair.get();
            int a = Rotor.charToIndex(text.charAt(0));
            int b = Rotor.charToIndex(text.charAt(1));
            reconfigurableReflectorMap[a] = b;
            reconfigurableReflectorMap[b] = a;
        }
    }

    private void fillReflectorList() {
        reflectorList.clear();

        for (Rotor rotor : m4)
            if (rotor.isReflector())
                reflectorList.add(rotor.getId());

        for (Rotor rotor : rocket)
            if (rotor.isReflector())
                reflectorList.add(rotor.getId());

        for (Rotor rotor : swissK)
            if (rotor.isReflector())
                reflectorList.add(rotor.getId());

        reflectorChoice = reflectorList.get(0);
    }

    /**
     * Initialize "Reflector" panel.
     */
    private void initializeReflector() {
        fillReflectorList();

        for (int i = 0; i < PrimaryController.PAIR_COUNT; ++i)
            pairs.add(new Pair());
    }



    /************************************************************************
     * Support code for "Wheel Order" panel.
     */

    ObservableList<String> wheelList = FXCollections.observableArrayList();
    private ArrayList<String> wheelChoices = new ArrayList<String>(ROTOR_COUNT);

    public ObservableList<String> getWheelList()   { return wheelList; }
    public String getWheelChoice(int index)   { return wheelChoices.get(index); }
    public void setWheelChoice(int index, String choice)   { wheelChoices.set(index, choice); }

    private void fillWheelList() {
        wheelList.clear();

        for (Rotor rotor : m3)
            wheelList.add(rotor.getId());
    }
    private void fillWheelChoices() {
        final String first = wheelList.get(0);

		for (int i = 0; i < ROTOR_COUNT; ++i)
			wheelChoices.add(first);
    }

    /**
     * Initialize "Wheel Order" panel.
     */
    private void initializeWheelOrder() {
        fillWheelList();
		fillWheelChoices();
    }




    /************************************************************************
     * Support code for "Ring Settings" panel.
     */

    ObservableList<String> ringSettingsList = FXCollections.observableArrayList();
    private ArrayList<ListSpinner> ringSettingSpinners = new ArrayList<ListSpinner>(ROTOR_COUNT);

    public SpinnerValueFactory<String> getRingSettingSVF(int index) { return ringSettingSpinners.get(index).getSVF(); }
    public String getRingSetting(int index) { return ringSettingSpinners.get(index).getCurrent(); }
    public int getRingIndex(int index) { return ringSettingSpinners.get(index).getIndex(); }
    public void setRingSetting(int index, String value) { ringSettingSpinners.get(index).setCurrent(value); }
    public void incrementRingSetting(int index, int step) { ringSettingSpinners.get(index).increment(step); }


    private void fillRingSettingsList() {
        ringSettingsList.clear();

        if (useLetters) {
            for (int i = 0; i < 26; ++i) {
                // final String item = String.valueOf(i + 'A');
                final String item = String.valueOf(Rotor.indexToString(i));
                // System.out.println(item);
                ringSettingsList.add(item);
            }
        } else {
            for (int i = 0; i < 26; ++i) {
                // final String item = String.valueOf(i + 1);
                final String item = String.valueOf(i + 1);
                // System.out.println(item);
                ringSettingsList.add(item);
            }
        }
    }
    private void fillRingSettingSpinners() {
        for (int i = 0; i < ROTOR_COUNT; ++i)
            ringSettingSpinners.add(new ListSpinner(ringSettingsList));
    }

    /**
     * Initialize "Ring Settings" panel.
     */
    private void initializeRingSettings() {
        fillRingSettingsList();
        fillRingSettingSpinners();
    }



    /************************************************************************
     * Support code for "Rotor Offsets" panel.
     */

    ObservableList<String> rotorOffsetsList = FXCollections.observableArrayList();
    private ArrayList<ListSpinner> rotorOffsetSpinners = new ArrayList<ListSpinner>(ROTOR_COUNT);

    public SpinnerValueFactory<String> getRotorOffsetSVF(int index) { return rotorOffsetSpinners.get(index).getSVF(); }
    public String getRotorOffset(int index) { return rotorOffsetSpinners.get(index).getCurrent(); }
    public int getRotorIndex(int index) { return rotorOffsetSpinners.get(index).getIndex(); }
    public void setRotorOffset(int index, String value) { rotorOffsetSpinners.get(index).setCurrent(value); }
    public void incrementRotorOffset(int index, int step) { rotorOffsetSpinners.get(index).increment(step); }


    private boolean useLetters = false;

    public void setUseLetters(boolean state) { 
        useLetters = state; 
        fillRingSettingsList();
        fillRotorOffsetsList();
    }

    public boolean getUseLetters() { return useLetters; }

    private void fillRotorOffsetsList() {
        rotorOffsetsList.clear();

        if (useLetters) {
            for (int i = 0; i < 26; ++i) {
                // final String item = String.valueOf(i + 'A');
                final String item = String.valueOf(Rotor.indexToString(i));
                // System.out.println(item);
                rotorOffsetsList.add(item);
            }
        } else {
            for (int i = 0; i < 26; ++i) {
                // final String item = String.valueOf(i + 1);
                final String item = String.valueOf(i + 1);
                // System.out.println(item);
                rotorOffsetsList.add(item);
            }
        }
    }
    private void fillRotorOffsetSpinners() {
        for (int i = 0; i < ROTOR_COUNT; ++i)
            rotorOffsetSpinners.add(new ListSpinner(rotorOffsetsList));
    }

    /**
     * Initialize "Rotor Offsets" panel.
     */
    private void initializeRotorOffsets() {
        fillRotorOffsetsList();
        fillRotorOffsetSpinners();
    }



    /************************************************************************
     * Support code for "Plugboard Connections" panel.
     */
    
    private int idToIndex(String id) { return Integer.valueOf(id); }

    private int[] plugboardLetterCounts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private int[] plugboardMap = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    private ArrayList<Pair> plugs = new ArrayList<Pair>(PrimaryController.PLUG_COUNT);

    public void setPlugText(int index, String text) {
        plugs.get(index).set(text);
        countLetterUsage(plugboardLetterCounts, plugs);
    }

    public void sanitizePlug(int index) { plugs.get(index).sanitize(); }

    public String getPlugText(int index)	{ return plugs.get(index).get(); }
    public int getPlugCount(int index)		{ return plugs.get(index).count(); }

    public boolean isPlugValid(int index) {
        Pair plug = plugs.get(index);

        if (plug.isEmpty())
            return true;

        if (!plug.isValid())
            return false;

        final String text = plug.get();
        for (int i = 0; i < text.length(); ++i)
            if (plugboardLetterCounts[Rotor.charToIndex(text.charAt(i))] > 1)
                return false;

        return true;
    }

    public void setPlugText(String id, String text) { setPlugText(idToIndex(id), text); }
    public void sanitizePlug(String id) { sanitizePlug(idToIndex(id)); }

    public String getPlugText(String id)	{ return getPlugText(idToIndex(id)); }
    public int getPlugCount(String id)		{ return getPlugCount(idToIndex(id)); }
    public boolean isPlugValid(String id)	{ return isPlugValid(idToIndex(id)); }

    public boolean isPlugboardValid() {
        for (Pair pair : plugs)
            if (!pair.isValid())
                return false;

        int[] counts = getPlugboardLetterCounts();
        for (int i = 0; i < counts.length; ++i)
            if (counts[i] > 1)
                return false;

        return true;
    }

    public int[] getPlugboardLetterCounts() {
        return plugboardLetterCounts;
    }

    private void setPlugboardMap() {
        for (int i = 0; i < plugboardMap.length; ++i)
            plugboardMap[i] = i;

        for (Pair plug : plugs) {
            if (plug.isEmpty())
                continue;

            final String text = plug.get();
            int a = Rotor.charToIndex(text.charAt(0));
            int b = Rotor.charToIndex(text.charAt(1));
            plugboardMap[a] = b;
            plugboardMap[b] = a;
        }
    }

    /**
     * Initialize "Plugboard Connections" panel.
     */
    private void initializePlugboardConnections() {
        for (int i = 0; i < PrimaryController.PLUG_COUNT; ++i)
            plugs.add(new Pair());
    }

    private void countLetterUsage(int[] counts, ArrayList<Pair> list) {

        for (int i = 0; i < counts.length; ++i) 
            counts[i] = 0;

        for (Pair item : list) {
            final String text = item.get();
            for (int i = 0; i < text.length(); ++i) {
                final char ch = text.charAt(i);
                if (Character.isAlphabetic(ch)) {
                    final int index = Rotor.charToIndex(ch);
                    counts[index]++;
                }
            }
        }
    }



    /************************************************************************
     * Support code for "Encipher" panel.
     */

    public boolean isConfigValid() {
        return isPlugboardValid() && isReflectorValid();
    }

    private boolean encipher = false;

    
    public boolean isEncipher() {
        return encipher;
    }

    private void dumpMapping(int[] map) {
        for (int i = 0; i < map.length; ++i)
            System.out.print(Rotor.indexToString(map[i]));

        System.out.println();
    }

    private Rotor getRotor(ObservableList<Rotor> list, String target) {
        for (Rotor rotor : list)
             if (rotor.is(target))
                return rotor;

        return null;
    }

    private int[] getReflectorMap() {
        
        if (reconfigurable)
            return reconfigurableReflectorMap;
        else {
            Rotor rotor = getRotor(m4, reflectorChoice);
            if (rotor != null)
                return rotor.getMap();

            rotor = getRotor(rocket, reflectorChoice);
            if (rotor != null)
                return rotor.getMap();

            rotor = getRotor(swissK, reflectorChoice);
            if (rotor != null)
                return rotor.getMap();
        }

        return null;
    }

    private int[] map1;
    private int[] map2;
    private int[] map3;

    private ArrayList<int[]> pipeline = new ArrayList<int[]>(9);
    
    private void advanceRotors() {
        incrementRotorOffset(1, 1);
        incrementRotorOffset(2, 2);
        incrementRotorOffset(3, 5);
    }

    public void test() {
        advanceRotors();
    }

    private void buildPipeline() {
        
        advanceRotors();

        pipeline.clear();

        pipeline.add(plugboardMap);

        pipeline.add(map3);
        pipeline.add(map2);
        pipeline.add(map1);

        pipeline.add(reflectorMap);

        pipeline.add(map1);
        pipeline.add(map2);
        pipeline.add(map3);

        pipeline.add(plugboardMap);
    }

    private void dumpMappings() {

        for (int[] map : pipeline)
            dumpMapping(map);
    }

    private void lockdownSettings() {
        setPlugboardMap();
        setReflectorMap();
        reflectorMap = getReflectorMap();
        
    }
    
    public void setEncipher(boolean state) {
        encipher = state;
        // System.out.println("setEncipher(" + encipher + ").");
        if (encipher) {
            lockdownSettings();
            buildPipeline();
            
            dumpMappings();
            System.out.println("Enter text");
        }
    }

    /**
     * Initialize "Encipher" panel.
     */
    private void initializeEncipher() {
    }

}