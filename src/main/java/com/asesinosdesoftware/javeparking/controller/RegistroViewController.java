package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.services.JDBCService;
import com.asesinosdesoftware.javeparking.services.PasswordService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class RegistroViewController {

    @FXML
    public TextField IdPassword;
    @FXML
    private ComboBox <String> Iduniversity;
    @FXML
    private TextField IDCedula;
    @FXML
    private TextField IdNombre;
    @FXML
    private TextField IdApellido;

    @FXML
    private void registro() {

        try {

            Cliente C = new Cliente();
            C.setCedula(IDCedula.getText());
            C.setNombre(IdNombre.getText());
            C.setApellido(IdApellido.getText());
            C.setUniversidad(Iduniversity.getValue().charAt(0));
            C.setHash(PasswordService.hashPassword(IdPassword.getText()));

            JDBCService controller = new JDBCService();
            Connection connection = controller.getConnection();
            ClienteRepository repository = new ClienteRepository();

            repository.agregarCliente(connection,C);

            connection.close();//No olvidar siempre cerrar la conexión una vez esta se termine de usar
            showSuccess("Registro de cliente exitoso");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Registro de cliente Fallido");

        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje de éxito
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}


