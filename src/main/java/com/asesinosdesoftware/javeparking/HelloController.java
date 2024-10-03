package com.asesinosdesoftware.javeparking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HelloController {

    // Contenedor principal del layout, inyectado desde el archivo FXML
    @FXML
    private BorderPane contenedor;

    @FXML
    private void Salir() {
        System.exit(0);
    }

    // Método para cargar y mostrar la vista de registrar persona
    @FXML
    private void registrarCliente() {
        try {
            // Carga la vista desde el archivo FXML
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Registro_View.fxml"));
            // Establece la vista cargada en el centro del contenedor principal
            contenedor.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Método para cargar y mostrar la vista de Inicio de sesion
    @FXML
    private void InicioSesion() {
        try {
            // Carga la vista desde el archivo FXML
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Inicio_sesion_view.fxml"));
            // Establece la vista cargada en el centro del contenedor principal
            contenedor.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
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