module com.asesinosdesoftware.javeparking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asesinosdesoftware.javeparking to javafx.fxml;
    exports com.asesinosdesoftware.javeparking;
}