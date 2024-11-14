package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.RegistroOp;
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

    private RegistroOpService registroOpService = new RegistroOpService();
    RegistroOp registroOp;

    /**
     * Método para agregar un vehículo nuevo y registrar su entrada.
     * @param event El evento de acción que dispara el registro.
     */
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

        // Verificar que el tamaño ha sido seleccionado
        String tamano = cmbTamano.getValue();
        if (tamano == null || tamano.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un tamaño para el vehículo", AlertType.ERROR);
            return;
        }

        // Obtener la hora actual de entrada
        LocalDateTime horaEntrada = LocalDateTime.now();

        try {
            if (registroOp==null){
                registroOp = new RegistroOp();
                registroOpService.registrarEntrada(placa, tipo, tamano, horaEntrada,registroOp);
            }
            // Llamamos al servicio para registrar la entrada del vehículo
            mostrarAlerta("Éxito", "El vehículo ha ingresado correctamente.", AlertType.INFORMATION);
            registroOp = null;
            // Limpiar los campos después de registrar
            limpiarCampos();
        } catch (Exception e) {
            registroOp = null;
            mostrarAlerta("Error", "Ocurrió un error al registrar la entrada: " + e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Método para mostrar una alerta en pantalla.
     * @param titulo Título de la alerta.
     * @param mensaje Mensaje de contenido de la alerta.
     * @param tipo Tipo de alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método para limpiar los campos de entrada después de registrar un vehículo.
     */
    private void limpiarCampos() {
        txtPlaca.clear();
        txtTipo.clear();
        cmbTamano.getSelectionModel().clearSelection();
    }
}





