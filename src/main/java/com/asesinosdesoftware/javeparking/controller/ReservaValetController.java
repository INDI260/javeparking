package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.ReservaValet;
import com.asesinosdesoftware.javeparking.repository.ReservaValetRepository;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaValetController {

    private final IDBConnectionManager dbConnectionManager;
    private final ReservaValetRepository reservaValetRepository;

    @FXML
    private TextField idClienteField;
    @FXML
    private TextField placaVehiculoField;
    @FXML
    private DatePicker fechaReservaPicker;
    @FXML
    private TextField horaReservaField;
    @FXML
    private ComboBox<String> metodoPagoComboBox;

    public ReservaValetController(IDBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        this.reservaValetRepository = new ReservaValetRepository();
    }

    @FXML
    private void confirmarReserva() {
        try (Connection connection = dbConnectionManager.getConnection()) {
            // Obtener y validar los datos directamente del formulario
            String idCliente = idClienteField.getText().trim();
            String placaVehiculo = placaVehiculoField.getText().trim();
            LocalDateTime fechaHoraReserva = LocalDateTime.of(fechaReservaPicker.getValue(), LocalTime.parse(horaReservaField.getText().trim()));
            String metodoPago = metodoPagoComboBox.getValue();

            if (idCliente.isEmpty() || placaVehiculo.isEmpty() || metodoPago == null || fechaHoraReserva == null) {
                mostrarError("Todos los campos deben estar completos.");
                return;
            }

            // Crea una nueva reserva usando los datos obtenidos
            ReservaValet nuevaReserva = new ReservaValet(
                    Integer.parseInt(idCliente), // Asignamos directamente el ID de cliente
                    placaVehiculo,               // Asignamos directamente la placa del vehículo
                    fechaHoraReserva,
                    metodoPago,
                    "Pendiente"
            );

            reservaValetRepository.agregarReserva(connection, nuevaReserva);
            mostrarExito("Reserva de Valet confirmada exitosamente.");

        } catch (SQLException e) {
            mostrarError("Error al realizar la reserva de valet: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error en los datos de entrada: " + e.getMessage());
        }
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