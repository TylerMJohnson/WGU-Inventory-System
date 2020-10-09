package wgu.model;

/**
 * @version 1.0.0
 * @author Tyler Johnson
 */
public class OutSourced extends Part{

    /**
     * The company from where this part originated from.
     */
    private String companyName;

    /**
     * Constructor
     * @param id the parts id
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the amount in stock
     * @param min the minimum amount in inventory
     * @param max the maximum amount in inventory
     * @param companyname the company associated with the part
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyname) {
        super(id, name, price, stock, min, max);
        this.companyName = companyname;
    }

    /**
     * Get the company this part originated from
     * @return companyName the name of the company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company this part originated from
     * @param companyName the name of the company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
