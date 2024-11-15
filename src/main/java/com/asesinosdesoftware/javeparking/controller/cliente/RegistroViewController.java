package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.services.RegistroService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistroViewController {

    @FXML
    public TextField IdPassword;
    @FXML
    private ComboBox<String> Iduniversity;
    @FXML
    private TextField IDCedula;
    @FXML
    private TextField IdNombre;
    @FXML
    private TextField IdApellido;

    private RegistroService registroService = new RegistroService();
    private Cliente cliente;

    @FXML
    private void registro() {
        try {
            if (cliente == null) {
                cliente = new Cliente();
                registroService.registro(IdNombre.getText(), IdApellido.getText(), IDCedula.getText(), IdPassword.getText(), Iduniversity.getValue(), cliente);
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

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para regresar a la vista de inicio (hello-view.fxml)
    @FXML
    private void goToHelloView() {
        try {
            // Cargar la vista hello-view.fxml como un Parent
            Parent helloView = FXMLLoader.load(getClass().getResource("/com/asesinosdesoftware/javeparking/hello-view.fxml"));
            // Cambiar la raíz de la escena completa a hello-view
            IdPassword.getScene().setRoot(helloView);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al cargar la vista de inicio");
        }
    }
}
