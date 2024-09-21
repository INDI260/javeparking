module com.asesinosdesoftware.javeparking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.asesinosdesoftware.javeparking to javafx.fxml;
    exports com.asesinosdesoftware.javeparking;
}