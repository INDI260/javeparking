package com.asesinosdesoftware.javeparking.controller.operario;

import com.asesinosdesoftware.javeparking.entities.PagoOp;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class PagoOpController {

    private final PagoService pagoService = new PagoService();

    @FXML
    private TextField placaTextField;

    @FXML
    private ComboBox<String> metodoPagoComboBox;  // El usuario selecciona el método de pago (efectivo o tarjeta)

    @FXML
    private TextField horasEstacionadoTextField;

    @FXML
    private TextField valorTextField;

    @FXML
    private TextField fechaTextField;

    /**
     * Método para calcular el pago de la operación cuando el usuario lo solicita
     */
    @FXML
    private void calcularPagoOp() {
        String placa = placaTextField.getText();
        String metodoPago = metodoPagoComboBox.getValue();
        String horasEstacionadoStr = horasEstacionadoTextField.getText();

        if (placa.isEmpty() || metodoPago == null || horasEstacionadoStr.isEmpty()) {
            // Mostrar un mensaje de error si falta algún dato
            mostrarError("Por favor, ingrese todos los datos requeridos.");
            return;
        }

        int horasEstacionado;
        try {
            horasEstacionado = Integer.parseInt(horasEstacionadoStr);
        } catch (NumberFormatException e) {
            mostrarError("Ingrese un número válido de horas.");
            return;
        }

        try {
            // Crear un objeto PagoOp donde se almacenará el resultado
            PagoOp pagoOp = new PagoOp();

            // Usar el servicio para calcular el pago de la operación
            pagoService.calcularPagoOp(placa, horasEstacionado, pagoOp);

            // Establecer el método de pago seleccionado por el usuario
            pagoOp.setMetodoPago(metodoPago);

            // Establecer la fecha actual
            LocalDateTime fechaActual = LocalDateTime.now();
            pagoOp.setFecha(fechaActual);

            // Mostrar los resultados en los campos de texto correspondientes
            valorTextField.setText(pagoOp.getValor().toString());
            metodoPagoComboBox.setValue(pagoOp.getMetodoPago());
            fechaTextField.setText(pagoOp.getFecha().toString());
            showSuccess("Pago exitoso");

        } catch (SQLException e) {
            // Manejar errores de base de datos
            mostrarError("Error al calcular el pago: " + e.getMessage());
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
    // Método para mostrar un mensaje de éxito
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





