module com.asesinosdesoftware.javeparking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.security.crypto;

    opens com.asesinosdesoftware.javeparking to javafx.fxml;
    opens com.asesinosdesoftware.javeparking.controller to javafx.fxml;
    exports com.asesinosdesoftware.javeparking;
    opens com.asesinosdesoftware.javeparking.services to javafx.fxml;

    // Abrir el paquete 'entities' a javafx.base y javafx.fxml
    opens com.asesinosdesoftware.javeparking.entities to javafx.fxml, javafx.base;
    opens com.asesinosdesoftware.javeparking.init to javafx.fxml;
}