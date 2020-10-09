package wgu.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import wgu.model.*;

import java.io.IOException;

/**
 * Futures versions of this Controller can benefit from
 *  -The ability to edit tables by double clicking cells (like excel)
 *  -The ability to drag parts from the parts table to associated parts table.
 * @version 1.0.0
 * @author Tyler Johnson
 *
 *  The Controller for ModifyProductForm
 */
public class ModifyProductController extends ChildController {

    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn partidcolumn;
    @FXML
    private TableColumn partnamecolumn;
    @FXML
    private TableColumn partlevelcolumn;
    @FXML
    private TableColumn partcostcolumn;
    @FXML
    private TableColumn associatedpartidcolumn;
    @FXML
    private TableColumn associatedpartnamecolumn;
    @FXML
    private TableColumn associatedpartlevelcolumn;
    @FXML
    private TableColumn associatedpartcostcolumn;
    @FXML
    private TextField idTextField;
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
    private TextField searchField;

    /**
     * The product being modified
     */
    private Product modify = null;
    /**
     * Default no args Constuctor
     */
    public ModifyProductController(){

    }

    @FXML
    /**
     * Setup our controller
     *
     * Sets up searchfield with an onkeyreleased listener. If the search field is empty, reloads all parts from inventory.
     * If the search field is not empty, filter parts based on inputted string and populates the table with parts that have an id or name which contain inputted text
     */
    private void initialize(){
        this.searchField.setOnKeyReleased(keyEvent -> {
            if(searchField.getText().length() < 1){
                partsTableView.setItems(Inventory.getInstance().getAllParts());
            } else {
                FilteredList<Part> filteredData = new FilteredList<>(Inventory.getInstance().getAllParts(), p -> true);
                String s = searchField.getText().toLowerCase();
                filteredData.setPredicate(part -> String.valueOf(part.getId()).toLowerCase().contains(s) || part.getName().toLowerCase().contains(s));
                SortedList<Part> parts = new SortedList<>(filteredData);
                parts.comparatorProperty().bind(partsTableView.comparatorProperty());
                partsTableView.setItems(parts);
            }
        });
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
     * If any input is invalid (min being greater than max for example) Alert(message) is called to prompt the user of error.
     * Cancel Button simply hides the windo and returns back to main menu.
     * @param ActionEvent called by the button the user clicks on
     */
    private void handleButtonAction(ActionEvent event) throws IOException {
        if(event.getSource() == saveButton) {
            if (this.nameTextField.getText().length() < 1) {
                Alert("Name is required");
                return;
            } else if (this.invTextField.getText().length() < 1) {
                Alert("Inventory is required");
                return;
            } else if (this.priceTextField.getText().length() < 1) {
                Alert("Price is required");
                return;
            } else if (this.maxTextField.getText().length() < 1) {
                Alert("Max is required");
                return;
            } else if (this.minTextField.getText().length() < 1) {
                Alert("Min is required");
                return;
            } else if (Integer.parseInt(this.minTextField.getText()) > Integer.parseInt(this.maxTextField.getText())) {
                Alert("Max field must be greater than or equal to min field");
                return;
            } else if(!priceIsRight()){
                Alert("Cost must be greater than total cost of parts...");
                return;
            } else {
                int id = modify.getId();
                String name = this.nameTextField.getText();
                int stock = Integer.parseInt(this.invTextField.getText());
                double price = Double.parseDouble(this.priceTextField.getText());
                int max = Integer.parseInt(this.maxTextField.getText());
                int min = Integer.parseInt(this.minTextField.getText());
                if(stock < 0 || price < 0 || max < 0 || min < 0){
                    Alert("Input cannot be negative #s");
                    return;
                }
                if(stock < min || stock > max){
                    Alert("Amount in stock must be less than or equal to max and greater than or equal to min.");
                    return;
                }
                Product p = new Product(id, name, price, stock, min, max);
                for(int i = 0; i < associatedPartsTableView.getItems().size(); i++){
                    p.addAssociatedPart(associatedPartsTableView.getItems().get(i));
                }
                Inventory.getInstance().updateProduct(Inventory.getInstance().getAllProducts().indexOf(modify), p);
            }
            saveButton.getScene().getWindow().hide();
        } else if(event.getSource() == cancelButton){
            cancelButton.getScene().getWindow().hide();
        } else if(event.getSource() == addButton){
            if(partsTableView.getSelectionModel().getSelectedItem() == null){
                return;
            }
            //The user then clicks the Add button, and the part is copied to the bottom table.
            //which means I don't have to delete it.
            Part p = partsTableView.getSelectionModel().getSelectedItem();
            ObservableList<Part> parts = associatedPartsTableView.getItems();
            if(parts.isEmpty()){ // weird
                partidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                partnamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                partlevelcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partcostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                this.partsTableView.setItems(Inventory.getInstance().getAllParts());
            }
            parts.add(p);
        } else if(event.getSource() == removeButton){
            Part p = associatedPartsTableView.getSelectionModel().getSelectedItem();
            ObservableList<Part> parts = associatedPartsTableView.getItems();
            parts.remove(p);
            associatedPartsTableView.refresh();
        }
    }

    /**
     * Autofills our text fields and tables with information from which product we're modifying
     * @param p the product to modify
     */
    public void autofill(Product p) {
        if(p != null){
            this.modify = p;
            this.nameTextField.setText(p.getName());
            this.invTextField.setText("" + p.getStock());
            this.priceTextField.setText("" + p.getPrice());
            this.maxTextField.setText("" + p.getMax());
            this.minTextField.setText("" + p.getMin());
        }
        this.nameTextField.requestFocus();

        partidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partlevelcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partcostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedpartidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedpartnamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedpartlevelcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedpartcostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.partsTableView.setItems(Inventory.getInstance().getAllParts());
        this.associatedPartsTableView.setItems(modify.getAllAssociatedParts());
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

    /**
     * We want to make sure products have a profit so we check for the combined price
     * of associated items compared to the priceTextField
     * @return true if total cost of product is less than sum of parts else false.
     */
    private boolean priceIsRight(){
        ObservableList<Part> associates = associatedPartsTableView.getItems();
        double total = 0;
        for(int i = 0; i < associates.size(); i++){
            total += associates.get(i).getPrice();
        }
        return total < Double.parseDouble(priceTextField.getText());
    }
}
