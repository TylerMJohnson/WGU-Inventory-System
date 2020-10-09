package wgu.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import wgu.model.InHouse;
import wgu.model.Inventory;
import wgu.model.OutSourced;
import wgu.model.Part;

import java.io.IOException;

/**
 * @version 1.0.0
 * @author Tyler Johnson
 *
 * The Controller for AddPartForm
 */
public class AddPartController extends ChildController {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outSourcedRadioButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TextField machineorcompany;
    @FXML
    private Label machineorcompanylabel;

    /**
     * Default no args Constuctor
     */
    public AddPartController(){

    }

    @FXML
    /**
     * Sets up the menu with default selection of nameTextField and inHouseRadioButton selected.
     */
    private void initialize(){
        Platform.runLater(() -> nameTextField.requestFocus());
        Platform.runLater(() -> this.inHouseRadioButton.setSelected(true));
    }

    @FXML
    /**
     * Handles button clicks.
     * Save Button will check input before saving:
     * -nameTextField
     * -invTextField
     * -priceTextField
     * -maxTextField
     * -minTextField
     * -machineorcompanyname
     * If any input is invalid Alert(message) is called to prompt the user of error.
     * Cancel Button simply hides the windo and returns back to main menu.
     * @param ActionEvent called by the button the user clicks on
     */
    private void handleButtonAction(ActionEvent event) throws IOException {
        if(event.getSource() == saveButton){
            if(this.nameTextField.getText().length() < 1){
                Alert("You must enter a name.");
                return;
            } else if(this.invTextField.getText().length() < 1){
                Alert("You must enter an inventory value");
                return;
            } else if(this.priceTextField.getText().length() < 1){
                Alert("You must enter a price/cost");
                return;
            } else if(this.maxTextField.getText().length() < 1){
                Alert("You must enter a max value");
                return;
            } else if(this.minTextField.getText().length() < 1){
                Alert("You must enter an inventory value");
                return;
            } else if(this.machineorcompany.getText().length() < 1){
                if(this.outSourcedRadioButton.isSelected()){
                    Alert("You must enter an company name");
                } else {
                    Alert("You must enter an machine id");
                }
                return;
            } else if(this.machineorcompany.getText().length() < 1){
                if(this.outSourcedRadioButton.isSelected()){
                    Alert("You must enter an company name");
                } else {
                    Alert("You must enter an machine id");
                }
                return;
            } else if(Integer.parseInt(this.minTextField.getText()) > Integer.parseInt(this.maxTextField.getText())){
                Alert("Max field must be greater than or equal to min field");
                return;
            } else {
                String name;
                int stock;
                double price;
                int max;
                int min;
                try {
                    name = this.nameTextField.getText();
                    stock = Integer.parseInt(this.invTextField.getText());
                    price = Double.parseDouble(this.priceTextField.getText());
                    max = Integer.parseInt(this.maxTextField.getText());
                    min = Integer.parseInt(this.minTextField.getText());
                } catch (NullPointerException | NumberFormatException ex){
                    Alert("Incorrect inputted data. Check your entered information.");
                    return;
                }
                if(stock < 0 || price < 0 || max < 0 || min < 0){
                    Alert("Input cannot be negative #s");
                    return;
                }
                if(stock < min || stock > max){
                    Alert("Amount in stock must be less than or equal to max and greater than or equal to min.");
                    return;
                }
                Part part;
                if(inHouseRadioButton.isSelected()){
                    part = new InHouse(Inventory.getInstance().getNextPartId(), name, price, stock, min, max, Integer.parseInt(machineorcompany.getText()));
                } else {
                    part = new OutSourced(Inventory.getInstance().getNextPartId(), name, price, stock, min, max, machineorcompany.getText());
                }
                Inventory.getInstance().addPart(part);
                saveButton.getScene().getWindow().hide();
            }
        } else if(event.getSource() == cancelButton){
            cancelButton.getScene().getWindow().hide();
        }
    }

    @FXML
    /**
     * Handles radio buttons and switches the label for required text
     * If inHouseRadioButton is selected, we need the machine id.
     * If outsourcedRadioButton is selected, we need the company name.
     * @param ActionEvent called by the button the user clicks on
     */
    private void handleRadioAction(ActionEvent event) throws IOException {
        if(event.getSource() == this.inHouseRadioButton){
            this.outSourcedRadioButton.setSelected(false);
            this.machineorcompanylabel.setText("Machine ID");
        } else if(event.getSource() == this.outSourcedRadioButton) { //outsourced
            this.inHouseRadioButton.setSelected(false);
            this.machineorcompanylabel.setText("Company");
        }
    }

    /**
     * Stops the user from continuing further and alerts them of error messages.
     * @param message an informational string
     */
    private void Alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Required Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
