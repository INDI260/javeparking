package com.asesinosdesoftware.javeparking.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuClienteViewController {

    // Contenedor principal del layout, inyectado desde el archivo FXML
    @FXML
    private AnchorPane contenedor;


    // Método para cargar y mostrar la vista de registrar cliente
    @FXML
    private void vehiculo() {
        try {
            // Cargar la vista desde el archivo FXML
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/asesinosdesoftware/javeparking/VehiculoView.fxml"));
            // Limpiar los hijos actuales del contenedor principal
            contenedor.getChildren().clear();
            // Ajustar las propiedades de posicionamiento de AnchorPane para que ocupe todo el espacio
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            // Agregar la nueva vista cargada al contenedor principal
            contenedor.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
