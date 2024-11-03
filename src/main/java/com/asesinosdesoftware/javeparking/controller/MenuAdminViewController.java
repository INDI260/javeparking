package com.asesinosdesoftware.javeparking.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MenuAdminViewController {

    @FXML
    private void gestionarReservas(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de reservas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asesinosdesoftware/javeparking/ReservaAdminView.fxml"));
            Parent root = loader.load();

            // Obtener la escena actual y cambiar la vista
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("Error al cargar la vista de reservas. Por favor, inténtelo de nuevo más tarde.");
        }
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}



