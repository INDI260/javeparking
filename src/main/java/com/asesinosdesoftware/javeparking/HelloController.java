package com.asesinosdesoftware.javeparking;

import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.services.InicioDeSesionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class HelloController {

    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    @FXML
    private BorderPane contenedor;

    @FXML
    public TextField Usuario;
    @FXML
    public TextField Contrasena;

    @FXML
    private void Salir() {
        System.exit(0);
    }

    @FXML
    private void CerrarSesion() {
        InicioDeSesionService cerrar = new InicioDeSesionService();
        cerrar.CerrarSesion();
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            contenedor.setTop(pane);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al cargar la vista de inicio de sesión.");
        }
    }

    @FXML
    private void registrarCliente() {
        try {
            // Cargar Registro_View.fxml usando Parent en lugar de AnchorPane
            Parent registroView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Registro_View.fxml")));
            contenedor.setCenter(registroView); // Establecer en el centro del BorderPane
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al cargar la vista de registro.");
        }
    }

    @FXML
    private void InicioSesion() {
        try {
            InicioDeSesionService inicioService = new InicioDeSesionService();
            inicioService.InicioDeSesion(Usuario.getText(), Contrasena.getText());
            Menu();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error al iniciar sesión.");
        }
    }

    @FXML
    private void Menu() {
        try {
            Parent pane = null;
            switch (Sesion.getTipo()) {
                case 'a':
                    pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MenuAdminView.fxml")));
                    break;
                case 'c':
                    pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MenuClienteView.fxml")));
                    break;
                case 'e':
                    pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MenuOperarioView.fxml")));
                    break;
            }
            if (pane != null) {
                contenedor.setCenter(pane);
            } else {
                showError("No se pudo determinar el tipo de sesión.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al cargar el menú.");
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
}
