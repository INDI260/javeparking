package com.asesinosdesoftware.javeparking.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;


public class MenuOperarioViewController {

    @FXML
    public AnchorPane contenedor;

    // Este método carga la vista de registro de vehículo
    @FXML
    private void IngresoVehiculoOp(ActionEvent event) {
        try {
            // Cargar la vista de registro de vehículo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asesinosdesoftware/javeparking/views/RegistroOpView.fxml"));
            AnchorPane root = loader.load();

            // Colocar la nueva vista dentro del contenedor
            contenedor.getChildren().setAll(root);

        } catch (Exception e) {
            // En caso de error al cargar la vista
            mostrarMensajeError("No se pudo cargar la vista de registro de vehículo: " + e.getMessage());
        }
    }

    @FXML
    private void IngresoPagoOp(ActionEvent event) {
        try {
            // Cargar la vista de PagoOp
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asesinosdesoftware/javeparking/views/PagoOpView.fxml"));
            AnchorPane root = loader.load();

            // Colocar la nueva vista dentro del contenedor
            contenedor.getChildren().setAll(root);

        } catch (Exception e) {
            // En caso de error al cargar la vista
            mostrarMensajeError("No se pudo cargar la vista de PagoOp: " + e.getMessage());
        }
    }

    // Método para mostrar alertas de error
    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
