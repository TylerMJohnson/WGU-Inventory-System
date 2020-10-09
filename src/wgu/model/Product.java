package wgu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @version 1.0.0
 * @author Tyler Johnson
 */
public class Product {

    /**
     * Product Id
     */
    private int id;
    /**
     * Product name
     */
    private String name;
    /**
     * Product Price
     */
    private double price;
    /**
     * Product Stock
     */
    private int stock;
    /**
     * Product Min
     */
    private int min;
    /**
     * Product Max
     */
    private int max;
    /**
     * Associated Parts of this product.
     */
    private final ObservableList<Part> associatedParts;

    /**
     * Constructor
     * @param id the id
     * @param name the product name
     * @param price the product price
     * @param stock the amount in stock
     * @param min the minimum amount in stock
     * @param max the maxmimum amount in stock
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Gets the product id
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the product id
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the product name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product
     * @param name - the name of the product
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the product price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product
     * @param price - amount
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Gets the product stock
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock of the product
     * @param stock - amount
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the product min
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Set the minimum of the product
     * @param min - amount
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the product max
     * @return max
     */
    public int getMax() {
        return max;
    }
    /**
     * Sets the product max
     * @param max - amount
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Add associated part
     * @param part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Delete associated part.
     * @param part - the part to delete from the ObservableList
     */
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }

    /**
     * Get all associated parts of this product.
     * @return ObservableList of associated products.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
