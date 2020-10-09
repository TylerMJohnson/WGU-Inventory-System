module WGU {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.xml;

    opens wgu.controller to javafx.fxml;
    opens wgu.model to javafx.base;
    exports wgu to javafx.graphics;
}
