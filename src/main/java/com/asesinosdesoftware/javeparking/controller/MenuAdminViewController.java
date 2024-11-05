package com.asesinosdesoftware.javeparking.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MenuAdminViewController {
    @FXML

    public AnchorPane contenedor;

    @FXML
    private void gestionarReservas(ActionEvent event) {
        try {
            // Cargar la vista desde el archivo FXML
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/asesinosdesoftware/javeparking/ReservaAdminView.fxml"));
            // Limpiar los hijos actuales del contenedor principal
            contenedor.getChildren().clear();
            // Ajustar las propiedades de posicionamiento de AnchorPane para que ocupe todo el espacio
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            // Agregar la nueva vista cargada al contenedor principal
            contenedor.getChildren().add(pane);
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



