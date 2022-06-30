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

    private static final int OTHER = -1;
    private static final int SLOW = 0;
    private static final int LEFT = 1;
    private static final int MIDDLE = 2;
    private static final int RIGHT = 3;


    /************************************************************************
     * General support code.
     */

    private int idToIndex(String id) { return Integer.valueOf(id); }

    private void countLetterUsage(int[] counts, ArrayList<Pair> list) {

        for (int i = 0; i < counts.length; ++i) 
            counts[i] = 0;

        for (Pair pair : list) {
            for (int i = 0; i < pair.count(); ++i) {
                if (pair.isCharAt(i)) {
                    final int index = pair.indexAt(i);
                    counts[index]++;
                }
            }
        }
    }



    /************************************************************************
     * Initialization support code.
     */

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
        initializeRotorStates();
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

    private int[] reflectorLetterCounts;
    private int[] reconfigurableReflectorMap;
    private int[] reflectorMap;
    private Swapper reflector;

    private ArrayList<Pair> pairs = new ArrayList<Pair>(PrimaryController.PAIR_COUNT + 1);


    public ObservableList<String> getReflectorList()   { return reflectorList; }
    public String getReflectorChoice()   { return reflectorChoice; }
    public void setReflectorChoice(String choice)   { reflectorChoice = choice; }

    public void setReconfigurable(boolean state) { reconfigurable = state; }
    public boolean isReconfigurable() { return reconfigurable; }


    public void setPairText(int index, String text) {
        pairs.get(index).set(text);
        countLetterUsage(reflectorLetterCounts, pairs);
    }

    public void sanitizePairs() {
        for (Pair pair : pairs)
            pair.sanitize();
    }

    public String getPairText(int index)	{ return pairs.get(index).get(); }
    public int getPairCount(int index)		{ return pairs.get(index).count(); }

    public boolean isPairValid(int index) {
        Pair pair = pairs.get(index);

        if (pair.isEmpty())
            return true;

        if (!pair.isValid())
            return false;

        for (int i = 0; i < 2; ++i)
            if (reflectorLetterCounts[pair.indexAt(i)] != 1)
                return false;

        return true;
    }

    public void setPairText(String id, String text) { setPairText(idToIndex(id), text); }

    public String getPairText(String id)	{ return getPairText(idToIndex(id)); }
    public int getPairCount(String id)		{ return getPairCount(idToIndex(id)); }
    public boolean isPairValid(String id)	{ return isPairValid(idToIndex(id)); }

    public boolean isReconfigurableReflectorValid() {
        for (Pair pair : pairs)
            if (!pair.isValid())
                return false;

        for (int i = 0; i < reflectorLetterCounts.length; ++i)
            if (reflectorLetterCounts[i] != 1)
                return false;

        return true;
    }

    public boolean isReflectorValid() {
        if (reconfigurable)
            return isReconfigurableReflectorValid();

        return true;
    }

    private void setReconfigurableReflectorMap() {
        for (int i = 0; i < reconfigurableReflectorMap.length; ++i)
            reconfigurableReflectorMap[i] = 0;

        for (Pair pair : pairs) {
            if (pair.isEmpty())
                continue;

            final int a = pair.indexAt(0);
            final int b = pair.indexAt(1);
            reconfigurableReflectorMap[a] = b;
            reconfigurableReflectorMap[b] = a;
        }
    }

    private int[] getReflectorMap() {
        
        if (reconfigurable) {
            setReconfigurableReflectorMap();

            return reconfigurableReflectorMap;
        } else {
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
        reflectorLetterCounts = new int[26];
        reconfigurableReflectorMap = new int[26];
        fillReflectorList();

        for (int i = 0; i < PrimaryController.PAIR_COUNT + 1; ++i)
            pairs.add(new Pair());

        // Set up "hard wired" J-Y reflector connection as last pair.
        pairs.get(PrimaryController.PAIR_COUNT).set("JY");
    }



    /************************************************************************
     * Support code for "Rotor States" panel.
     */

    private ArrayList<RotorState> rotorStates = new ArrayList<RotorState>(ROTOR_COUNT);

    private class RotorState {
        private String wheelChoice;
        private ListSpinner ringSetting;
        private ListSpinner offset;

        public RotorState(String wheelChoice, ListSpinner ringSetting, ListSpinner offset) {
            this.wheelChoice = wheelChoice;
            this.ringSetting = ringSetting;
            this.offset = offset;
        }

        public String getWheelChoice() { return wheelChoice; }
        public void setWheelChoice(String choice) { wheelChoice = choice; }

        public SpinnerValueFactory<String> getRingSettingSVF() { return ringSetting.getSVF(); }

        public String getRingSetting() { return ringSetting.getCurrent(); }
        public int getRingIndex() { return ringSetting.getIndex(); }
        public void setRingSetting(String value) { ringSetting.setCurrent(value); }
        public void setRingIndex(int value) { ringSetting.setIndex(value); }
        public void incrementRingSetting(int step) { ringSetting.increment(step); }

        public SpinnerValueFactory<String> getRotorOffsetSVF() { return offset.getSVF(); }

        public String getRotorOffset() { return offset.getCurrent(); }
        public int getRotorIndex() { return offset.getIndex(); }
        public void setRotorOffset(String value) { offset.setCurrent(value); }
        public void setRotorIndex(int value) { offset.setIndex(value); }
        public void incrementRotorOffset(int step) { offset.increment(step); }
        
    }

    private void fillRotorStates() {
        final String first = wheelList.get(0);

        for (int i = 0; i < ROTOR_COUNT; ++i) {
            rotorStates.add(new RotorState(first, 
                new ListSpinner(ringSettingsList), 
                new ListSpinner(rotorOffsetsList)));
        }
    }

    /**
     * Initialize "Rotor States".
     */
    private void initializeRotorStates() {
        fillRotorStates();
    }



    /************************************************************************
     * Support code for "Wheel Order" panel.
     */

    ObservableList<String> wheelList = FXCollections.observableArrayList();

    public ObservableList<String> getWheelList() { return wheelList; }

    public String getWheelChoice(int index) { return rotorStates.get(index).getWheelChoice(); }
    public void setWheelChoice(int index, String choice) { rotorStates.get(index).setWheelChoice(choice); }

    private boolean fourthWheel = false;

    public void setFourthWheel(boolean state) { 
        fourthWheel = state; 
    }

    public boolean isFourthWheel() { return fourthWheel; }

    private void fillWheelList() {
        wheelList.clear();

        for (Rotor rotor : m3)
            wheelList.add(rotor.getId());
    }

    /**
     * Initialize "Wheel Order" panel.
     */
    private void initializeWheelOrder() {
        fillWheelList();
    }




    /************************************************************************
     * Support code for "Ring Settings" panel.
     */

    ObservableList<String> ringSettingsList = FXCollections.observableArrayList();

    public SpinnerValueFactory<String> getRingSettingSVF(int index) { return rotorStates.get(index).getRingSettingSVF(); }
    public String getRingSetting(int index) { return rotorStates.get(index).getRingSetting(); }
    public int getRingIndex(int index) { return rotorStates.get(index).getRingIndex(); }
    public void setRingSetting(int index, String value) { rotorStates.get(index).setRingSetting(value); }
    public void setRingIndex(int index, int value) { rotorStates.get(index).setRingIndex(value); }
    public void incrementRingSetting(int index, int step) { rotorStates.get(index).incrementRingSetting(step); }


    private void switchRingSettingsList() {

        int[] indices = new int[ROTOR_COUNT];
        for (int i = 0; i < ROTOR_COUNT; ++i)
            indices[i] = getRingIndex(i);

        if (useLetters) {
            for (int i = 0; i < 26; ++i)
                ringSettingsList.set(i, Rotor.indexToString(i));
        } else {
            for (int i = 0; i < 26; ++i)
                ringSettingsList.set(i, String.valueOf(i + 1));
        }

        for (int i = 0; i < ROTOR_COUNT; ++i)
            setRingIndex(i, indices[i]);
    }

    private void fillRingSettingsList() {
        ringSettingsList.clear();

        if (useLetters) {
            for (int i = 0; i < 26; ++i)
                ringSettingsList.add(Rotor.indexToString(i));
        } else {
            for (int i = 0; i < 26; ++i)
                ringSettingsList.add(String.valueOf(i + 1));
        }
    }

    /**
     * Initialize "Ring Settings" panel.
     */
    private void initializeRingSettings() {
        fillRingSettingsList();
    }



    /************************************************************************
     * Support code for "Rotor Offsets" panel.
     */

    ObservableList<String> rotorOffsetsList = FXCollections.observableArrayList();

    public SpinnerValueFactory<String> getRotorOffsetSVF(int index) { return rotorStates.get(index).getRotorOffsetSVF(); }
    public String getRotorOffset(int index) { return rotorStates.get(index).getRotorOffset(); }
    public int getRotorIndex(int index) { return rotorStates.get(index).getRotorIndex(); }
    public void setRotorOffset(int index, String value) { rotorStates.get(index).setRotorOffset(value); }
    public void setRotorIndex(int index, int value) { rotorStates.get(index).setRotorIndex(value); }
    public void incrementRotorOffset(int index, int step) { rotorStates.get(index).incrementRotorOffset(step); }


    private boolean useLetters = true;

    public void setUseLetters(boolean state) { 
        useLetters = state; 
        switchRingSettingsList();
        switchRotorOffsetsList();
    }

    public boolean isUseLetters() { return useLetters; }

    private void switchRotorOffsetsList() {

        int[] indices = new int[ROTOR_COUNT];
        for (int i = 0; i < ROTOR_COUNT; ++i)
            indices[i] = getRotorIndex(i);

        if (useLetters) {
            for (int i = 0; i < 26; ++i)
                rotorOffsetsList.set(i, Rotor.indexToString(i));
        } else {
            for (int i = 0; i < 26; ++i)
                rotorOffsetsList.set(i, String.valueOf(i + 1));
        }

        for (int i = 0; i < ROTOR_COUNT; ++i)
            setRotorIndex(i, indices[i]);
    }

    private void fillRotorOffsetsList() {
        rotorOffsetsList.clear();

        if (useLetters) {
            for (int i = 0; i < 26; ++i)
                rotorOffsetsList.add(Rotor.indexToString(i));
        } else {
            for (int i = 0; i < 26; ++i)
                rotorOffsetsList.add(String.valueOf(i + 1));
        }
    }

    /**
     * Initialize "Rotor Offsets" panel.
     */
    private void initializeRotorOffsets() {
        fillRotorOffsetsList();
    }



    /************************************************************************
     * Support code for "Plugboard Connections" panel.
     */
    
    private int[] plugboardLetterCounts;
    private int[] plugboardMap;
    private Swapper plugboard;

    private ArrayList<Pair> plugs = new ArrayList<Pair>(PrimaryController.PLUG_COUNT);


    public void setPlugText(int index, String text) {
        plugs.get(index).set(text);
        countLetterUsage(plugboardLetterCounts, plugs);
    }

    public void sanitizePlugs() {
        for (Pair pair : plugs)
            pair.sanitize();
    }

    public String getPlugText(int index)	{ return plugs.get(index).get(); }
    public int getPlugCount(int index)		{ return plugs.get(index).count(); }

    public boolean isPlugValid(int index) {
        Pair plug = plugs.get(index);

        if (plug.isEmpty())
            return true;

        if (!plug.isValid())
            return false;

        for (int i = 0; i < 2; ++i)
            if (plugboardLetterCounts[plug.indexAt(i)] > 1)
                return false;

        return true;
    }

    public void setPlugText(String id, String text) { setPlugText(idToIndex(id), text); }

    public String getPlugText(String id)	{ return getPlugText(idToIndex(id)); }
    public int getPlugCount(String id)		{ return getPlugCount(idToIndex(id)); }
    public boolean isPlugValid(String id)	{ return isPlugValid(idToIndex(id)); }

    public boolean isPlugboardValid() {
        for (Pair pair : plugs)
            if (!pair.isValid())
                return false;

        for (int i = 0; i < plugboardLetterCounts.length; ++i)
            if (plugboardLetterCounts[i] > 1)
                return false;

        return true;
    }

    private void setPlugboardMap() {
        for (int i = 0; i < plugboardMap.length; ++i)
            plugboardMap[i] = i;

        for (Pair plug : plugs) {
            if (plug.isEmpty())
                continue;

            int a = plug.indexAt(0);
            int b = plug.indexAt(1);
            plugboardMap[a] = b;
            plugboardMap[b] = a;
        }
    }

    /**
     * Initialize "Plugboard Connections" panel.
     */
    private void initializePlugboardConnections() {
        plugboardLetterCounts = new int[26];
        plugboardMap = new int[26];

        for (int i = 0; i < PrimaryController.PLUG_COUNT; ++i)
            plugs.add(new Pair());
    }



    /************************************************************************
     * Support code for "Encipher" panel.
     */

    private boolean encipher = false;

    public boolean isConfigValid() {
        return isPlugboardValid() && isReflectorValid();
    }

    public boolean isEncipher() {
        return encipher;
    }

    private Rotor getRotor(ObservableList<Rotor> list, String target) {
        for (Rotor rotor : list)
             if (rotor.is(target))
                return rotor;

        return null;
    }


    /**
     * Advances the right rotor then checks the other rotors. The notch point 
     * of the middle rotor is used to check for a step of the left rotor and a 
     * double step of the middle rotor. The turnover point of the right rotor 
     * is used to check for a step of the middle rotor
     */
    private void advanceRotors() {
        // Normal step of right rotor.
        incrementRotorOffset(RIGHT, 1);

        Rotor rotor = getRotor(m3, getWheelChoice(MIDDLE));
        if (rotor.isNotchPoint(getRotorIndex(MIDDLE))) {
            // Double step of middle rotor, normal step of left rotor.
            incrementRotorOffset(MIDDLE, 1);
            incrementRotorOffset(LEFT, 1);
        }

        rotor = getRotor(m3, getWheelChoice(RIGHT));
        if (rotor.isTurnoverPoint(getRotorIndex(RIGHT))) {
            // Right rotor takes middle rotor one step further.
            incrementRotorOffset(MIDDLE, 1);
        }
    }


    public int translatePipeline(int index) {

        System.out.print("Key: " + Rotor.indexToString(index) + "  ");

        for (Translation translator : pipeline)
            index = translator.translate(index);

        System.out.println("Lamp: " + Rotor.indexToString(index));

        return index;
    }


    private class Translation {
        private final int pos;
        private final Swapper swapper;
        private final int dir;


        public Translation(int id, Swapper swapper, int dir) {
            this.pos = id;
            this.swapper = swapper;
            this.dir = dir;
        }

        public boolean conditionallyUpdate(int target, int offset) {
            if (target == pos) {
                swapper.setOffset(offset);

                return true;
            }

            return false;
        }
    
        public int translate(int index) {
            return swapper.swap(dir, index);
        }	

    }

    private ArrayList<Translation> pipeline = new ArrayList<Translation>(9);

    private void updatePipeline() {
        advanceRotors();

        for (int i = 0; i < ROTOR_COUNT; ++i) {
            int offset = getRotorIndex(i);

            for (Translation translator : pipeline)
                translator.conditionallyUpdate(i, offset);
        }
    }

    public int test1(char key) {
        updatePipeline();
        return translatePipeline(Rotor.charToIndex(key));
        // return translate(Rotor.charToIndex(key));
    }

    public int test5() {
        int output = 0;
        
        final String input = "AAAAA";
        for (int i = 0; i < input.length(); ++i)
            output = test1(input.charAt(i));

        return output;
    }

    public int test() {
        // lockdownSettings();

        return test1('A');
        // return test5();
    }


    private void buildPipeline() {
        
        pipeline.clear();

        Rotor slow = getRotor(m3, getWheelChoice(SLOW));

        Rotor left = getRotor(m3, getWheelChoice(LEFT));
        Rotor middle = getRotor(m3, getWheelChoice(MIDDLE));
        Rotor right = getRotor(m3, getWheelChoice(RIGHT));

        pipeline.add(new Translation(OTHER, plugboard, Swapper.RIGHT_TO_LEFT));

        if (fourthWheel)
            pipeline.add(new Translation(SLOW, slow, Swapper.RIGHT_TO_LEFT));

        pipeline.add(new Translation(RIGHT, right, Swapper.RIGHT_TO_LEFT));
        pipeline.add(new Translation(MIDDLE, middle, Swapper.RIGHT_TO_LEFT));
        pipeline.add(new Translation(LEFT, left, Swapper.RIGHT_TO_LEFT));

        pipeline.add(new Translation(OTHER, reflector, Swapper.RIGHT_TO_LEFT));

        pipeline.add(new Translation(LEFT, left, Swapper.LEFT_TO_RIGHT));
        pipeline.add(new Translation(MIDDLE, middle, Swapper.LEFT_TO_RIGHT));
        pipeline.add(new Translation(RIGHT, right, Swapper.LEFT_TO_RIGHT));

        if (fourthWheel)
            pipeline.add(new Translation(SLOW, slow, Swapper.LEFT_TO_RIGHT));

        pipeline.add(new Translation(OTHER, plugboard, Swapper.LEFT_TO_RIGHT));
    }

    private void lockdownSettings() {
        setPlugboardMap();
        reflectorMap = getReflectorMap();

        plugboard = new Swapper("Plugboard", plugboardMap);
        reflector = new Swapper("Reflector", reflectorMap);

        Rotor rotor;
        for (int i = 0; i < ROTOR_COUNT; ++i) {
            rotor = getRotor(m3, getWheelChoice(i));
            rotor.setRingSetting(getRingIndex(i));

            // rotor.dumpRightMap();
            // rotor.dumpLeftMap();
        }

        buildPipeline();
        // dumpPipeline();
}
    
    public void setEncipher(boolean state) {
        encipher = state;
        // System.out.println("setEncipher(" + encipher + ").");
        if (encipher) {
            lockdownSettings();
    
            System.out.println("Enter text");
        }
    }

    /**
     * Initialize "Encipher" panel.
     */
    private void initializeEncipher() {
    }

}