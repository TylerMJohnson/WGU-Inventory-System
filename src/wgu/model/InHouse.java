package wgu.model;

/**
 * @version 1.0.0
 * @author Tyler Johnson
 */
public class InHouse extends Part{

    /**
     * Machine id
     */
    private int machineId;

    /**
     * Constructor
     * @param id the parts id
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the amount in stock
     * @param min the minimum amount in inventory
     * @param max the maximum amount in inventory
     * @param machineId the machine associated with the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Gets the machine id
     * @return int - the machines id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the machine id
     * @param machineId - the id to set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
