package com.asesinosdesoftware.javeparking.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;

import java.io.IOException;


public class MenuOperarioViewController {

    @FXML
    public AnchorPane contenedor;

    /**
     * // Método para cargar y mostrar la vista de Ingreso de vehiculo por parte del operario
     * @param event
     */
    @FXML
    private void IngresoVehiculoOp(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/asesinosdesoftware/javeparking/RegistroOpView.fxml"));

            contenedor.getChildren().clear();

            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);

            contenedor.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo cargar la vista de registro de vehículo: " + e.getMessage());
        }
    }


    /**
     * // Método para cargar y mostrar la vista de ingreso del pago de operario
     * @param event
     */
    @FXML
    private void IngresoPagoOp(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/asesinosdesoftware/javeparking/PagoOpView.fxml"));

            contenedor.getChildren().clear();

            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            contenedor.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
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
