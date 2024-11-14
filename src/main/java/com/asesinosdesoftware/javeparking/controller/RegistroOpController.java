package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.services.RegistroOpService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import java.time.LocalDateTime;

public class RegistroOpController {

    @FXML
    private TextField txtPlaca;

    @FXML
    private ComboBox<String> cmbTamano;

    @FXML
    private TextField txtTipo;

    private RegistroOpService registroOpService;

    // Constructor
    public RegistroOpController() {
        this.registroOpService = new RegistroOpService();
    }

    @FXML
    public void initialize() {
        // Inicializamos el ComboBox con los valores de tamaño posibles ('p', 'm', 'g')
        cmbTamano.getItems().addAll("p", "m", "g");
    }

    @FXML
    public void agregarVehiculo(ActionEvent event) {
        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) {
            mostrarAlerta("Error", "La placa no puede estar vacía", AlertType.ERROR);
            return;
        }

        String tipo = txtTipo.getText().trim();
        if (tipo.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un tipo de vehículo", AlertType.ERROR);
            return;
        }

        // Asegurarnos de que el tamaño fue seleccionado
        String tamano = cmbTamano.getValue();
        if (tamano == null || tamano.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un tamaño para el vehículo", AlertType.ERROR);
            return;
        }

        // Obtener la hora actual de entrada
        LocalDateTime horaEntrada = LocalDateTime.now();

        try {
            // Llamamos al servicio para registrar la entrada con tipo y tamaño como String
            registroOpService.registrarEntrada(placa, tipo, tamano, horaEntrada);
            mostrarAlerta("Éxito", "El vehículo ha ingresado correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al registrar la entrada: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}





