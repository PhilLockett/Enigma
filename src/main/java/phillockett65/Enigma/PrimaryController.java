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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class PrimaryController {

    public final static int PLUG_COUNT = 13;
    public final static int PAIR_COUNT = 12;

    private Stage stage;
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

        initializeWheelOrder();
        initializeRingSettings();
        initializePlugboardConnections();
        initializeReflector();
        initializeEncipher();
    }

    /**
     * Called by Application after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     */
    public void init(Stage stage) {
        // System.out.println("PrimaryController init.");
        this.stage = stage;
        model.init();
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

    }

    private void editableWheelOrder(boolean editable) {
        // System.out.println("editableReflector(" + editable + ")");

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

        wheel0Choicebox.setValue(model.getWheel0Choice());
        wheel1Choicebox.setValue(model.getWheel1Choice());
        wheel2Choicebox.setValue(model.getWheel2Choice());
        wheel3Choicebox.setValue(model.getWheel3Choice());

        wheel0Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheel0Choice(newValue);
        });

        wheel1Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheel1Choice(newValue);
        });

        wheel2Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheel2Choice(newValue);
        });

        wheel3Choicebox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setWheel3Choice(newValue);
        });
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

    @FXML
    private CheckBox useLettersCheckbox;

    @FXML
    void useLettersCheckboxActionPerformed(ActionEvent event) {
        model.setUseLetters(useLettersCheckbox.isSelected());
    }

    /**
     * Initialize "Ring Settings" panel.
     */
    private void initializeRingSettings() {
        ringSetting0Spinner.setValueFactory(model.getRingSetting0SVF());
        ringSetting1Spinner.setValueFactory(model.getRingSetting1SVF());
        ringSetting2Spinner.setValueFactory(model.getRingSetting2SVF());
        ringSetting3Spinner.setValueFactory(model.getRingSetting3SVF());

        ringSetting0Spinner.getValueFactory().wrapAroundProperty().set(true);
        ringSetting1Spinner.getValueFactory().wrapAroundProperty().set(true);
        ringSetting2Spinner.getValueFactory().wrapAroundProperty().set(true);
        ringSetting3Spinner.getValueFactory().wrapAroundProperty().set(true);

        ringSetting0Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting0Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting0(newValue);
        });

        ringSetting1Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting1Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting1(newValue);
        });

        ringSetting2Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting2Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting2(newValue);
        });

        ringSetting3Spinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("ringSetting3Spinner.valueProperty().Listener(" + newValue + "));");
            model.setRingSetting3(newValue);
        });

        useLettersCheckbox.setSelected(model.getUseLetters());
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
     * Support code for "Reflector" panel.
     */

    @FXML
    private ChoiceBox<String> reflectorChoicebox;

    @FXML
    private CheckBox reconfigurableCheckbox;

    @FXML
    void reconfigurableCheckboxActionPerformed(ActionEvent event) {
        model.setReconfigurable(reconfigurableCheckbox.isSelected());
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
    void reflectorActionPerformed(ActionEvent event) {
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

    private void editableReflector(boolean editable) {
        // System.out.println("editableReflector(" + editable + ")");

        reflectorChoicebox.setDisable(!editable);
        reconfigurableCheckbox.setDisable(!editable);
        for (TextField field : pairs)
            field.setEditable(editable);
    }

    /**
     * Initialize "Reflector" panel.
     */
    private void initializeReflector() {
        reconfigurableCheckbox.setSelected(model.isReconfigurable());
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
     * Support code for "Encipher" panel.
     */

    final String mainMessage = "Configure Settings";

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
        editablePlugboard(!encipher);
        editableReflector(!encipher);
        editableWheelOrder(!encipher);

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




    /************************************************************************
     * Support code for debug stuff.
     */

    private boolean dumpPlugboard() {
        System.out.println("dumpPlugboard().");

        // for (TextField plug : plugs)
        //     setValidTextField(plug, true);

        int[] counts = model.getPlugboardLetterCounts();
        for (int i = 0; i < counts.length; ++i) {
            String letter = "" + Rotor.indexToString(i) + " ";
            System.out.println(letter + " " + counts[i]);
        }

        for (int i = 0; i < plugs.size(); ++i) {
            final Boolean valid = model.isPlugValid(i);
            System.out.println(i + " " + valid);
        }

        return model.isPlugboardValid();
    }

    private boolean dumpReflector() {
        System.out.println("dumpReflector().");

        int[] counts = model.getReflectorLetterCounts();
        for (int i = 0; i < counts.length; ++i) {
            String letter = "" + Rotor.indexToString(i) + " ";
            System.out.println(letter + " " + counts[i]);
        }

        return model.isReflectorValid();
    }


    @FXML
    private Button primaryButton;

    @FXML
    void buttonActionPerformed(ActionEvent event) {
        System.out.println("Button pressed.");
        model.dumpRotorWiring();
        dumpPlugboard();
        dumpReflector();
    }

    

}
