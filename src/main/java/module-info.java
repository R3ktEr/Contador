module com.cristian.Contador {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens controller to javafx.fxml;
    exports controller;
}
