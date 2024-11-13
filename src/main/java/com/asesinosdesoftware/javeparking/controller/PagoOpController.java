package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.PagoOp;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PagoOpController {

    private PagoService pagoService = new PagoService();

    @FXML
    private TextField placaTextField;

    @FXML
    private TextField horasEstacionadoTextField;

    @FXML
    private TextField valorTextField;

    @FXML
    private TextField metodoPagoTextField;

    @FXML
    private TextField fechaTextField;

    /**
     * Método para calcular el pago de la operación cuando el usuario lo solicita
     */
    @FXML
    private void calcularPagoOp() {
        String placa = placaTextField.getText();
        String horasEstacionadoStr = horasEstacionadoTextField.getText();

        if (placa.isEmpty() || horasEstacionadoStr.isEmpty()) {
            // Mostrar un mensaje de error si falta algún dato
            mostrarError("Por favor, ingrese todos los datos requeridos.");
            return;
        }

        try {
            // Convertir la duración en horas a un valor entero
            int horasEstacionado = Integer.parseInt(horasEstacionadoStr);

            // Crear un objeto PagoOp donde se almacenará el resultado
            PagoOp pagoOp = new PagoOp();

            // Usar el servicio para calcular el pago de la operación
            pagoService.calcularPagoOp(placa, horasEstacionado, pagoOp);

            // Mostrar los resultados en los campos de texto correspondientes
            valorTextField.setText(pagoOp.getValor().toString());
            metodoPagoTextField.setText(pagoOp.getMetodoPago());
            fechaTextField.setText(pagoOp.getFecha().toString());

        } catch (SQLException e) {
            // Manejar errores de base de datos
            mostrarError("Error al calcular el pago: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Manejar errores si las horas ingresadas no son un número válido
            mostrarError("Por favor, ingrese un número válido para las horas de estacionamiento.");
        }
    }

    /**
     * Método para mostrar un mensaje de error en un cuadro de alerta
     * @param mensaje: El mensaje de error a mostrar
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}



