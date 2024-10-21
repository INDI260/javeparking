package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.JDBCService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import java.io.IOException;

public class PagoClViewController {

    // Contenedor principal del layout, inyectado desde el archivo FXML
    @FXML
    private AnchorPane contenedor;

    @FXML
    private void Reserva() {
        try {
            // Crear un nuevo FXMLLoader para cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asesinosdesoftware/javeparking/PagoReserva.fxml"));

            // Cargar la nueva vista y obtener el pane
            AnchorPane pane = loader.load();

            // Limpiar los hijos actuales del contenedor principal
            contenedor.getChildren().clear();

            // Establecer los anclajes para que el nuevo pane ocupe todo el contenedor
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

    @FXML
    private void Suscripcion() {
        try {
            // Cargar la vista de pago de suscripción
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asesinosdesoftware/javeparking/PagoSuscripcion.fxml"));

            // Cargar la nueva vista y obtener el pane
            AnchorPane pane = loader.load();

            // Limpiar los hijos actuales del contenedor principal
            contenedor.getChildren().clear();

            // Establecer los anclajes para que el nuevo pane ocupe t0do el contenedor
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

