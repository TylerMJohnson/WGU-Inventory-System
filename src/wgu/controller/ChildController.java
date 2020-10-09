package wgu.controller;

//https://stackoverflow.com/questions/49634507/calling-a-controller-method-from-a-second-controller-javafx

/**
 * A controller that keeps track of the parent controller so we can easily switch between menus.
 * @version 1.0.0
 * @author Tyler Johnson
 */
public class ChildController {

    /**
     * The parent of this controller.
     */
    MainController parent;

    /**
     * Sets the parent.
     * @param controller - the parent
     */
    public void setParentController(MainController controller) {
        this.parent = controller;
    }
}
