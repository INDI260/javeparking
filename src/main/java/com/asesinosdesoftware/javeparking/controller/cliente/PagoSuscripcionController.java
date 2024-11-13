package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.PagoSuscripcion;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoSuscripcionController {

    @FXML
    private TextField placaField;

    @FXML
    private ComboBox<String> metodoPagoCombo;

    @FXML
    private DatePicker fechaPagoPicker;

    @FXML
    private TextField valorField;

    private PagoService pagoService = new PagoService();

    @FXML
    private void procesarPago() {
        String placa = placaField.getText();
        String metodoPago = metodoPagoCombo.getValue();
        LocalDateTime fechaPago = LocalDateTime.of(fechaPagoPicker.getValue(), java.time.LocalTime.now());

        // Validación de entrada
        if (placa.isEmpty() || metodoPago == null || fechaPagoPicker.getValue() == null) {
            mostrarAlerta("Error", "Por favor complete todos los campos.");
            return;
        }

        PagoSuscripcion pagoSuscripcion = new PagoSuscripcion(null, BigDecimal.ZERO, fechaPago, metodoPago);

        try {
            // Llamada al servicio para calcular el pago
            pagoService.calcularPagoSuscripcion(placa, pagoSuscripcion);
            valorField.setText(pagoSuscripcion.getValor().toString());

            // Llamada al servicio para registrar el pago
            pagoService.pagarSuscripcion(pagoSuscripcion);

            mostrarAlerta("Éxito", "El pago ha sido procesado exitosamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al procesar el pago: " + e.getMessage());
        }
    }

    /**
     * Muestra una alerta con el mensaje proporcionado
     * @param titulo El título de la alerta
     * @param mensaje El mensaje de la alerta
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}











