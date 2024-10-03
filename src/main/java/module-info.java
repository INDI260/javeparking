module com.asesinosdesoftware.javeparking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.asesinosdesoftware.javeparking to javafx.fxml;
    opens com.asesinosdesoftware.javeparking.controller to javafx.fxml;
    exports com.asesinosdesoftware.javeparking;
    opens com.asesinosdesoftware.javeparking.services to javafx.fxml;
}