package wgu.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.model.*;

import java.io.IOException;

/**
 * Controller for the main menu.
 *
 * Futures versions of this application can benefit from:
 * Json or MySQL storage for loading and saving of data
 *
 * @version 1.0.0
 * @author Tyler Johnson
 */
public class MainController {

    @FXML
    private TableView partsTableView;
    @FXML
    private Button partsModifyButton;
    @FXML
    private Button partsDeleteButton;
    @FXML
    private Button partsAddButton;
    @FXML
    private TextField partSearchField;
    @FXML
    private TableColumn partidcolumn;
    @FXML
    private TableColumn partnamecolumn;
    @FXML
    private TableColumn partlevelcolumn;
    @FXML
    private TableColumn partcostcolumn;

    @FXML
    private TableView productsTableView;
    @FXML
    private Button productsModifyButton;
    @FXML
    private Button productsDeleteButton;
    @FXML
    private Button productsAddButton;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableColumn productidcolumn;
    @FXML
    private TableColumn productnamecolumn;
    @FXML
    private TableColumn productlevelcolumn;
    @FXML
    private TableColumn productcostcolumn;

    /**
     * Default Constructor
     */
    public MainController(){

    }

    @FXML
    /**
     * Create the menu and sample items. Sets table cell values and
     * Sets up searchfield with an onkeyreleased listener. If the search field is empty, reloads all parts from inventory.
     * If the search field is not empty, filter parts based on inputted string and populates the table with parts that have an id or name which contain inputted text
     */
    private void initialize(){
        InHouse inexample = new InHouse(0, "inexample", 4.00,1,10, 20, 123);
        OutSourced outexample = new OutSourced(1, "outexample", 5.99, 1, 1, 10, "Walmart");
        Product proexample = new Product(2, "product ex", 1.99, 1, 1, 10);
        proexample.addAssociatedPart(inexample);
        proexample.addAssociatedPart(outexample);

        Inventory.getInstance().addPart(inexample);
        Inventory.getInstance().addPart(outexample);
        Inventory.getInstance().addProduct(proexample);

        partidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partlevelcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partcostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productnamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productlevelcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productcostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.resetTables();

        this.partSearchField.setOnKeyReleased(keyEvent -> {
            if(partSearchField.getText().length() < 1){
                partsTableView.setItems(Inventory.getInstance().getAllParts());
            } else {
                FilteredList<Part> filteredData = new FilteredList<>(Inventory.getInstance().getAllParts(), p -> true);
                String s = partSearchField.getText().toLowerCase();
                filteredData.setPredicate(part -> String.valueOf(part.getId()).toLowerCase().contains(s) || part.getName().toLowerCase().contains(s));
                SortedList<Part> parts = new SortedList<>(filteredData);
                parts.comparatorProperty().bind(partsTableView.comparatorProperty());
                partsTableView.setItems(parts);
            }
        });

        this.productSearchField.setOnKeyReleased(keyEvent -> {
            if(productSearchField.getText().length() < 1){
                productsTableView.setItems(Inventory.getInstance().getAllProducts());
            } else {
                FilteredList<Product> filteredData = new FilteredList<>(Inventory.getInstance().getAllProducts(), p -> true);
                String s = productSearchField.getText().toLowerCase();
                filteredData.setPredicate(product -> String.valueOf(product.getId()).toLowerCase().contains(s) || product.getName().toLowerCase().contains(s));
                SortedList<Product> products = new SortedList<>(filteredData);
                products.comparatorProperty().bind(productsTableView.comparatorProperty());
                productsTableView.setItems(products);
            }
        });
    }

    @FXML
    /**
     *Handles all button events and switches between scenes if needed.
     * @param ActionEvent called by the button the user clicks on
     */
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage owner;
        FXMLLoader loader;

        if(event.getSource() == partsAddButton){
            owner = (Stage) partsAddButton.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("/wgu/view/AddPartForm.fxml"));
        } else if(event.getSource()==partsModifyButton){
            if(this.partsTableView.getSelectionModel().getSelectedCells().size() == 1){
                owner = (Stage) partsModifyButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/wgu/view/ModifyPartForm.fxml"));
            } else {
                Alert("You must select a part to modify.");
                return;
            }
        } else if(event.getSource()==partsDeleteButton){
            Part p = (Part) partsTableView.getSelectionModel().getSelectedItem();
            if(p == null){
                Alert("There is not a part selected");
                return;
            }
            Inventory.getInstance().deletePart(p);
            return;
        }else if(event.getSource()==productsModifyButton){
            if(this.productsTableView.getSelectionModel().getSelectedCells().size() == 1){
                owner = (Stage) productsModifyButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/wgu/view/ModifyProductForm.fxml"));
            } else {
                Alert("You must select a product to modify.");
                return;
            }
        } else if(event.getSource()==productsAddButton){
            System.out.println("here");
            owner = (Stage) productsAddButton.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("/wgu/view/AddProductForm.fxml"));
        } else if(event.getSource()==productsDeleteButton){
            Product p = (Product) productsTableView.getSelectionModel().getSelectedItem();
            if(p == null){
                Alert("There is not a product selected");
                return;
            }
            if(p.getAllAssociatedParts().size() > 0){
                Alert("You can't delete a product that has associated parts");
                return;
            }
            Inventory.getInstance().deleteProduct(p);
            return;
        } else {
            owner = (Stage) partsModifyButton.getScene().getWindow();
            loader = new FXMLLoader(getClass().getResource("/wgu/view/MainForm.fxml"));
        }
        Parent root = loader.load();
        Stage next = new Stage();
        next.initOwner(owner);
        Scene scene = new Scene(root);

        ChildController controller = loader.getController();
        controller.setParentController(this);

        if(controller instanceof ModifyPartController){
            if(this.partsTableView.getSelectionModel().getSelectedCells().size() == 1) {
                Part p = (Part) partsTableView.getSelectionModel().getSelectedItem();
                ModifyPartController mpc = (ModifyPartController) loader.getController();
                mpc.autofill(p);
            }
        } else if(controller instanceof ModifyProductController){
            if(this.productsTableView.getSelectionModel().getSelectedCells().size() == 1) {
                Product p = (Product) productsTableView.getSelectionModel().getSelectedItem();
                ModifyProductController mpc = (ModifyProductController) loader.getController();
                mpc.autofill(p);
            }
        } else if(controller instanceof AddProductController){
            AddProductController apc = (AddProductController) loader.getController();
            apc.setup();
        }


        next.setScene(scene);
        owner.hide();
        next.showAndWait();
        owner.show();
    }

    /**
     * Resets the parts table and product table with all parts & products from the inventory.
     */
    private void resetTables(){
        partsTableView.setItems(Inventory.getInstance().getAllParts());
        productsTableView.setItems(Inventory.getInstance().getAllProducts());
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
