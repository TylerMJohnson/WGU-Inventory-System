package wgu.model;


/**
 * @version 1.0.0
 * @author Tyler Johnson
 *
 * Abstract class Part which OutSourced and InHouse parts extend.
 */
public abstract class Part {
    /**
     * The part id.
     */
    private int id;
    /**
     * The part name.
     */
    private String name;
    /**
     * The part price.
     */
    private double price;
    /**
     * The amount in stock.
     */
    private int stock;
    /**
     * The minimum amount carried.
     */
    private int min;
    /**
     * The maximum amount carried.
     */
    private int max;

    /**
     * The part constructor
     * @param id the id of the part
     * @param name the name of the part
     * @param price the cost of the part stored as a double
     * @param stock the amount in stock.
     * @param min the minimum amount carried
     * @param max the maximum amount carried
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}
