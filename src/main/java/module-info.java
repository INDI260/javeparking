module com.asesinosdesoftware.javeparking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.security.crypto;
    requires com.h2database;
    requires java.sql.rowset;

    opens com.asesinosdesoftware.javeparking to javafx.fxml;
    opens com.asesinosdesoftware.javeparking.controller to javafx.fxml;
    exports com.asesinosdesoftware.javeparking;
    opens com.asesinosdesoftware.javeparking.services to javafx.fxml;

    // Abrir el paquete 'entities' a javafx.base y javafx.fxml
    opens com.asesinosdesoftware.javeparking.entities to javafx.fxml, javafx.base;
    opens com.asesinosdesoftware.javeparking.init to javafx.fxml;
    exports com.asesinosdesoftware.javeparking.entities;
    exports com.asesinosdesoftware.javeparking.init;
    exports com.asesinosdesoftware.javeparking.persistencia;
    exports com.asesinosdesoftware.javeparking.repository;
    exports com.asesinosdesoftware.javeparking.services;
    exports com.asesinosdesoftware.javeparking.exceptions;
    opens com.asesinosdesoftware.javeparking.controller.menu to javafx.fxml;
    opens com.asesinosdesoftware.javeparking.controller.cliente to javafx.fxml;
    opens com.asesinosdesoftware.javeparking.controller.admin to javafx.fxml;
    exports com.asesinosdesoftware.javeparking.controller;
    exports com.asesinosdesoftware.javeparking.controller.operario;
    opens com.asesinosdesoftware.javeparking.controller.operario to javafx.fxml;
    exports com.asesinosdesoftware.javeparking.controller.menu;
}