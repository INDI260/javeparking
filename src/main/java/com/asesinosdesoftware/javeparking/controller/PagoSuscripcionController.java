package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.SuscripcionRepository;
import com.asesinosdesoftware.javeparking.services.InicioDeSesionService;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PagoSuscripcionController {


    Suscripcion suscripcion;
    SuscripcionRepository suscripcionRepository;

    @FXML
    private TextField IDPlaca;
    @FXML
    private Label pagoMensualLabel;
    @FXML
    private Label pagoTotalLabel;
    @FXML
    private Button mostrarValorButton;
    @FXML
    private Button pagarButton;

    @FXML

    private void mostrarValor() {

        try {
            suscripcion = suscripcionRepository.buscarSuscripcionPorVehiculo(IDPlaca.getText().trim(), suscripcion);

            if (suscripcion != null) {
                calcularYMostrarValores(suscripcion);
            } else {
                mostrarError("No se encontró ninguna suscripción para la placa ingresada.");
            }
        } catch (SQLException e) {
            mostrarError("Error al verificar el precio: " + e.getMessage());
        }
    }


    private void calcularYMostrarValores(Suscripcion suscripcion) {
        LocalDateTime fechaInicio = suscripcion.getFechaInicio();
        LocalDateTime fechaFin = suscripcion.getFechaFin();

        // Validar que las fechas no sean null
        if (fechaInicio == null || fechaFin == null) {
            pagoTotalLabel.setText("Fechas no válidas");
            pagoMensualLabel.setText("Fechas no válidas");
            return;
        }

        long diasSuscripcion = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        char tamanoVehiculo = suscripcion.getVehiculo().getTamano();
        double precioDiario = calcularPrecioSuscripcion(tamanoVehiculo);
        double valorSuscripcionTotal = precioDiario * diasSuscripcion;
        double valorSuscripcionMensual = precioDiario * 30;

        // Mostrar los valores en las etiquetas
        pagoTotalLabel.setText("$" + String.format("%.2f", valorSuscripcionTotal));
        pagoMensualLabel.setText(diasSuscripcion > 30 ? "$" + String.format("%.2f", valorSuscripcionMensual) : "No aplica");
    }

    private double calcularPrecioSuscripcion(char tamano) {
        switch (tamano) {
            case 'p': return 5000; // Pequeño
            case 'm': return 7500; // Mediano
            case 'g': return 10000; // Grande
            default:
                return 0;
        }
    }

    @FXML
    private void realizarPago() {
        // Aquí se puede llamar al servicio para hacer el pago



    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}









