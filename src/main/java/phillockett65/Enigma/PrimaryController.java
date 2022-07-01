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
 * PrimaryController is the class that is responsible for centralizing control.
 * It is instantiated by the FXML loader creates the Model.
 */
package phillockett65.Enigma;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PrimaryController {

    public final static int PLUG_COUNT = 13;
    public final static int PAIR_COUNT = 12;

    private Model model;

    /************************************************************************
     * Support code for the Initialization of the Model.
     */

    /**
     * Responsible for constructing the Model and any local objects. Called by 
     * the FXMLLoader().
     */
    public PrimaryController() {
        // System.out.println("PrimaryController constructed.");
        model = new Model();
    }

    /**
     * Called by the FXML mechanism to initialize the controller. Called after 
     * the constructor to initialise all the controls.
     */
    @FXML public void initialize() {
        // System.out.println("PrimaryController initialized.");
        model.initialize();

        initializeReflector();
        initializeWheelOrder();
        initializeRingSettings();
        initializeRotorOffsets();
        initializePlugboardConnections();
        initializeEncipher();
    }

    /**
     * Called by Application after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     */
    public void init() {
        // System.out.println("PrimaryController init.");
        model.init();
    }



    /************************************************************************
     * Support code for "Reflector" panel.
     */

    @FXML
    private ChoiceBox<String> reflectorChoicebox;

    @FXML
    private CheckBox reconfigurableCheckbox;

    @FXML
    void reconfigurableCheckboxActionPerformed(ActionEvent event) {
        model.setReconfigurable(reconfigurableCheckbox.isSelected());
        setReconfigurable();
        checkConfigValid();
    }

    @FXML
    private TextField pair0;

    @FXML
    private TextField pair1;

    @FXML
    private TextField pair2;

    @FXML
    private TextField pair3;

    @FXML
    private TextField pair4;

    @FXML
    private TextField pair5;

    @FXML
    private TextField pair6;

    @FXML
    private TextField pair7;

    @FXML
    private TextField pair8;

    @FXML
    private TextField pair9;

    @FXML
    private TextField pair10;

    @FXML
    private TextField pair11;

    private ArrayList<TextField> pairs = new ArrayList<TextField>(PAIR_COUNT);

    @FXML
    void reflectorActionPerformed(KeyEvent event) {
        // System.out.println("reflectorActionPerformed().");

        TextField field = (TextField)event.getSource();
        model.setPairText(field.getId(), field.getText());

        checkReflector();
        checkConfigValid();
    }

    private void checkReflector() {
        // System.out.println("checkReflector().");

        for (int i = 0; i < pairs.size(); ++i) {
            final Boolean valid = model.isPairValid(i);
            setValidTextField(pairs.get(i), valid);
        }
    }

    private void editablePairs(boolean editable) {
        // System.out.println("editablePairs(" + editable + ")");

        for (TextField field : pairs)
            field.setEditable(editable);
    }

    private void editableReflector(boolean editable) {
        // System.out.println("editableReflector(" + editable + ")");

        if (editable) {
            setReconfigurable();
        } else {
            reflectorChoicebox.setDisable(true);
            editablePairs(false);
        }
        reconfigurableCheckbox.setDisable(!editable);
    }

    private void setReconfigurable() {
        final boolean reconfigurable = model.isReconfigurable();

        reflectorChoicebox.setDisable(reconfigurable);
        editablePairs(reconfigurable);
    }

    /**
     * Initialize "Reflector" panel.
     */
    private void initializeReflector() {
        reconfigurableCheckbox.setSelected(model.isReconfigurable());
        setReconfigurable();

        reflectorChoicebox.setItems(model.getReflectorList());
        reflectorChoicebox.setValue(model.getReflectorChoice());

        reflectorChoicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setReflectorChoice(newValue);
        });

        pairs.add(pair0);
        pairs.add(pair1);
        pairs.add(pair2);
        pairs.add(pair3);
        pairs.add(pair4);
        pairs.add(pair5);
        pairs.add(pair6);
        pairs.add(pair7);
        pairs.add(pair8);
        pairs.add(pair9);
        pairs.add(pair10);
        pairs.add(pair11);

        for (int i = 0; i < pairs.size(); ++i) {
            String id = String.valueOf(i);
            TextField pair = pairs.get(i);
            pair.setId(id);         // Use id as an index.
            
            setValidTextField(pair, model.isPairValid(i));
        }
    }



    /************************************************************************
     * Support code for "Wheel Order" panel.
     */

    @FXML
    private ChoiceBox<String> wheel0Choicebox;

    @FXML
    private ChoiceBox<String> wheel1Choicebox;

    @FXML
    private ChoiceBox<String> wheel2Choicebox;

    @FXML
    private ChoiceBox<String> wheel3Choicebox;

    @FXML
    private CheckBox fourthWheelCheckbox;

    @FXML
    void fourthWheelCheckboxActionPerformed(ActionEvent event) {
        model.setFourthWheel(fourthWheelCheckbox.isSelected());
        editableFourthWheel();
    }

    private void editableFourthWheel() {
        final boolean fourthWheel = model.isFourthWheel();
        final boolean disable = fourthWheel ? model.isEncipher() : true;

        wheel0Choicebox.setDisable(disable);
        ringSetting0Spinner.setDisable(disable);
        rotorOffset0Spinner.setDisable(!fourthWheel);
    }

    private void editableWheelOrder(boolean editable) {
        // System.out.println("editableReflector(" + editable + ")");

        fourthWheelCheckbox.setDisable(!editable);

        wheel1Choicebox.setDisable(!editable);
        wheel2Choicebox.setDisable(!editable);
        wheel3Choicebox.setDisable(!editable);
    }

    /**
     * Initialize "Wheel Order" panel.
     */
    private void initializeWheelOrder() {
        wheel0Choicebox.setItems(model.getWheelList());
        wheel1Choicebox.setItems(model.getWheelList());
        wheel2Choicebox.setItems(model.getWheelList());
        wheel3Choicebox.setItems(model.getWheelList());

        wheel0Choicebox.setValue(model.getWheelChoice(0));
        wheel1Choicebox.setValue(model.getWheelChoice(1));
        wheel2Choicebox.setValue(model.getWheelChoice(2));
        wheel3Choicebox.setValue(model.getWheelChoice(3));

        wheel0Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheelChoice(0, newValue);
        });

        wheel1Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheelChoice(1, newValue);
        });

        wheel2Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheelChoice(2, newValue);
        });

        wheel3Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheelChoice(3, newValue);
        });

        fourthWheelCheckbox.setSelected(model.isFourthWheel());
    }



    /************************************************************************
     * Support code for "Ring Settings" panel.
     */

    @FXML
    private Spinner<String> ringSetting0Spinner;

    @FXML
    private Spinner<String> ringSetting1Spinner;

    @FXML
    private Spinner<String> ringSetting2Spinner;

    @FXML
    private Spinner<String> ringSetting3Spinner;


    private void editableRingSettings(boolean editable) {
        // System.out.println("editableRingSettings(" + editable + ")");

        ringSetting1Spinner.setDisable(!editable);
        ringSetting2Spinner.setDisable(!editable);
        ringSetting3Spinner.setDisable(!editable);
    }

    /**
     * Initialize "Ring Settings" panel.
     */
    private void initializeRingSettings() {
        ringSetting0Spinner.setValueFactory(model.getRingSettingSVF(0));
        ringSetting1Spinner.setValueFactory(model.getRingSettingSVF(1));
        ringSetting2Spinner.setValueFactory(model.getRingSettingSVF(2));
        ringSetting3Spinner.setValueFactory(model.getRingSettingSVF(3));

        ringSetting0Spinner.getValueFactory().wrapAroundProperty().set(true);
        ringSetting1Spinner.getValueFactory().wrapAroundProperty().set(true);
        ringSetting2Spinner.getValueFactory().wrapAroundProperty().set(true);
        ringSetting3Spinner.getValueFactory().wrapAroundProperty().set(true);

        ringSetting0Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting0Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting(0, newValue);
        });

        ringSetting1Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting1Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting(1, newValue);
        });

        ringSetting2Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting2Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting(2, newValue);
        });

        ringSetting3Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting3Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting(3, newValue);
        });
    }



    /************************************************************************
     * Support code for "Rotor Offsets" panel.
     */

    @FXML
    private Spinner<String> rotorOffset0Spinner;

    @FXML
    private Spinner<String> rotorOffset1Spinner;

    @FXML
    private Spinner<String> rotorOffset2Spinner;

    @FXML
    private Spinner<String> rotorOffset3Spinner;

    @FXML
    private CheckBox useLettersCheckbox;

    @FXML
    private CheckBox showStepsCheckbox;

    @FXML
    void useLettersCheckboxActionPerformed(ActionEvent event) {
        model.setUseLetters(useLettersCheckbox.isSelected());
    }
    @FXML
    void showStepsCheckboxActionPerformed(ActionEvent event) {
        model.setShow(showStepsCheckbox.isSelected());
    }

    /**
     * Initialize "Rotor Offsets" panel.
     */
    private void initializeRotorOffsets() {
        rotorOffset0Spinner.setValueFactory(model.getRotorOffsetSVF(0));
        rotorOffset1Spinner.setValueFactory(model.getRotorOffsetSVF(1));
        rotorOffset2Spinner.setValueFactory(model.getRotorOffsetSVF(2));
        rotorOffset3Spinner.setValueFactory(model.getRotorOffsetSVF(3));

        rotorOffset0Spinner.getValueFactory().wrapAroundProperty().set(true);
        rotorOffset1Spinner.getValueFactory().wrapAroundProperty().set(true);
        rotorOffset2Spinner.getValueFactory().wrapAroundProperty().set(true);
        rotorOffset3Spinner.getValueFactory().wrapAroundProperty().set(true);

        rotorOffset0Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("rotorOffset0Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRotorOffset(0, newValue);
        });

        rotorOffset1Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("rotorOffset1Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRotorOffset(1, newValue);
        });

        rotorOffset2Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("rotorOffset2Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRotorOffset(2, newValue);
        });

        rotorOffset3Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("rotorOffset3Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRotorOffset(3, newValue);
        });

        useLettersCheckbox.setSelected(model.isUseLetters());
        showStepsCheckbox.setSelected(model.isShow());
    }



    /************************************************************************
     * Support code for "Plugboard Connections" panel.
     */

    @FXML
    private TextField plug0;

    @FXML
    private TextField plug1;

    @FXML
    private TextField plug2;

    @FXML
    private TextField plug3;

    @FXML
    private TextField plug4;

    @FXML
    private TextField plug5;

    @FXML
    private TextField plug6;

    @FXML
    private TextField plug7;

    @FXML
    private TextField plug8;

    @FXML
    private TextField plug9;

    @FXML
    private TextField plug10;

    @FXML
    private TextField plug11;

    @FXML
    private TextField plug12;

    private ArrayList<TextField> plugs = new ArrayList<TextField>(PLUG_COUNT);

    @FXML
    void plugBoardActionPerformed(KeyEvent event) {
        
        TextField field = (TextField)event.getSource();
        model.setPlugText(field.getId(), field.getText());
        // System.out.println("plugBoardActionPerformed(" + field.getId() + ", " + field.getText() + ").");
        
        checkPlugboard();
        checkConfigValid();
    }

    private void checkPlugboard() {
        // System.out.println("checkPlugboard().");

        for (int i = 0; i < plugs.size(); ++i) {
            final Boolean valid = model.isPlugValid(i);
            setValidTextField(plugs.get(i), valid);
        }
    }

    private static final String ERROR = "error-text-field";
    private void setValidTextField(TextField field, boolean valid) {

        if (valid) {
            field.getStyleClass().remove(ERROR);
        } else {
            if (!field.getStyleClass().contains(ERROR))
                field.getStyleClass().add(ERROR);
        }
        
    }

    private void editablePlugboard(boolean editable) {
        // System.out.println("editablePlugboard(" + editable + ")");

        for (TextField field : plugs)
            field.setEditable(editable);
    }

    /**
     * Initialize "Plugboard Connections" panel.
     */
    private void initializePlugboardConnections() {

        plugs.add(plug0);
        plugs.add(plug1);
        plugs.add(plug2);
        plugs.add(plug3);
        plugs.add(plug4);
        plugs.add(plug5);
        plugs.add(plug6);
        plugs.add(plug7);
        plugs.add(plug8);
        plugs.add(plug9);
        plugs.add(plug10);
        plugs.add(plug11);
        plugs.add(plug12);

        for (int i = 0; i < plugs.size(); ++i) {
            String id = String.valueOf(i);
            TextField plug = plugs.get(i);
            plug.setId(id);         // Use id as an index.
            
            setValidTextField(plug, model.isPlugValid(i));
        }
    }



    /************************************************************************
     * Support code for "Encipher" panel.
     */

    final String mainMessage = "Configure Settings";

    private int current = -1;


    @FXML
    private ToggleButton encipherButton;
    
    @FXML
    private Label mainLabel;


    @FXML
    void encipherButtonActionPerformed(ActionEvent event) {
        final boolean encipher = encipherButton.isSelected();
        // System.out.println("encipherButtonActionPerformed(" + encipher + ").");

        model.setEncipher(encipher);
        updateStatus();
    }

    private boolean updateStatus() {
        final boolean encipher = model.isEncipher();

        // System.out.println("updateStatus(" + encipher + ").");
        editableReflector(!encipher);
        editableWheelOrder(!encipher);
        editableRingSettings(!encipher);
        editablePlugboard(!encipher);
        editableFourthWheel();

        String message = !encipher ? "Start Translation" : "Change Settings";
        encipherButton.setText("Press to " + message);

        mainLabel.setText(encipher ? "" : mainMessage);

        return encipher;
    }

    private void checkConfigValid() {
        encipherButton.setDisable(!model.isConfigValid());
    }

    /**
     * Initialize "Encipher" panel.
     */
    private void initializeEncipher() {
        checkConfigValid();
        encipherButton.setSelected(updateStatus());
    }


    public void keyPress(KeyCode keyCode) {
        final boolean encipher = model.isEncipher();

        if (encipher) {
            if (current == -1) {
                current = Swapper.stringToIndex(keyCode.getChar());
                final int index = model.translate(current);

                mainLabel.setText(keyCode.getChar() + "->" + Rotor.indexToString(index));
            }
        }
    }

    public void keyRelease(KeyCode keyCode) {
        final boolean encipher = model.isEncipher();

        if (encipher) {
            final int index = Swapper.stringToIndex(keyCode.getChar());
            if (current == index)
                current = -1;
        }
    }


}
