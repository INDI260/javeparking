package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.services.PasswordService;
import com.asesinosdesoftware.javeparking.services.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    RegistroService registroService = new RegistroService();
    Cliente cliente;

    @FXML
    private void registro() {

        try {

            if(cliente == null){
                cliente = new Cliente();
                registroService.registro(IdNombre.getText(),IdApellido.getText(),IDCedula.getText(),IdPassword.getText(),Iduniversity.getValue(),cliente);
            }
           cliente = null;
            showSuccess("Registro creado");

        } catch (Exception e) {
            cliente = null;
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


