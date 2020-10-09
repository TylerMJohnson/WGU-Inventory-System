package wgu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @version 1.0.0
 * @author Tyler Johnson
 */
public class Inventory {

    /**
     * Our list of parts.
     */
    private ObservableList<Part> allParts;

    /**
     * Our list of products.
     */
    private ObservableList<Product> allProducts;

    /**
     * Inventory Singleton to ensure only one copy of the Inventory is created and allows for global access across all controllers.
     */
    private static Inventory instance = null;

    /**
     * Contstructor
     * Initializes our two Lists of parts and products.
     */
    private Inventory(){
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    /**
     * If our instance already exists, return the one we have. If not,
     * we create a new one.
     * @return Inventory
     */
    public static Inventory getInstance(){
        if(instance == null){
            instance = new Inventory();
        }
        return instance;
    }
    /**
     * Adds a part to the inventory.
     * @param p - the part to add.
     */
    public void addPart(Part p){
        allParts.add(p);
    }

    /**
     * Adds a product to the inventory.
     * @param p - the product to add.
     */
    public void addProduct(Product p){
        allProducts.add(p);
    }

    /**
     * Searches for a part based on id
     * @param id - the id to search for
     * @return the part if found else null
     */
    public Part lookupPart(int id){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == id){
                return allParts.get(i);
            }
        }
        return null;
    }

    /**
     * Searches for a product based on id
     * @param id - the id to search for
     * @return the product if found else null
     */
    public Product lookupProduct(int id){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == id){
                return allProducts.get(i);
            }
        }
        return null;
    }

    /**
     * Searches for a part based on name;
     * @param name - the name to search for
     * @return ObservableList of all parts which equal the name
     */
    public ObservableList<Part> lookupPart(String name){
        ObservableList<Part> temp = FXCollections.observableArrayList();
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName().equalsIgnoreCase(name)){
                temp.add(allParts.get(i));
            }
        }
        return temp;
    }

    /**
     * Searches for a product based on name;
     * @param name - the name to search for
     * @return ObservableList of all products which equal the name
     */
    public ObservableList<Product> lookupProduct(String name){
        ObservableList<Product> temp = FXCollections.observableArrayList();
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getName().equalsIgnoreCase(name)){
                temp.add(allProducts.get(i));
            }
        }
        return temp;
    }
    /**
     * Updates a part object based on index
     * @param index the slot to update
     * @param selected the new art
     */
    public void updatePart(int index, Part selected){
        allParts.set(index, selected);
    }

    /**
     * Updates a product object based on index
     * @param index the slot to update
     * @param selected the new product
     */
    public void updateProduct(int index, Product selected){
        allProducts.set(index, selected);
    }

    /**
     * Removes a part from the inventory.
     * @param selectedPart the part to be deleted
     * @return true if part exists else false
     */
    public boolean deletePart(Part selectedPart){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == selectedPart.getId()){
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a product from the inventory.
     * @param selectedProduct the product to be deleted
     * @return true if product exists else false
     */
    public boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == selectedProduct.getId()){
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Get a list of all parts
     * @return ObservableList of parts.
     */
    public ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Get a list of all products.
     * @return ObservableList of products.
     */
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * Gets the next part id by searching all parts and findings the highest id to add 1 to.
     * @return int next id
     */
    public int getNextPartId() {
        int cur = 0;
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getId() > cur) {
                cur = allParts.get(i).getId();
            }
        }
        return cur + 1;
    }

    /**
     * Gets the next product id by searching all products and findings the highest id to add 1 to.
     * @return int next id
     */
    public int getNextProductId() {
        int cur = 0;
        for(int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getId() > cur) {
                cur = allProducts.get(i).getId();
            }
        }
        return cur + 1;
    }
}
