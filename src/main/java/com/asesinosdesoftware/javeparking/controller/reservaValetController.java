package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.ReservaValet;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.repository.ReservaValetRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
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
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;

    @FXML
    private TextField idClienteField;
    @FXML
    private TextField placaVehiculoField; // Campo definido
    @FXML
    private DatePicker fechaReservaPicker;
    @FXML
    private TextField horaReservaField;
    @FXML
    private ComboBox<String> metodoPagoComboBox;

    public ReservaValetController(IDBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        this.reservaValetRepository = new ReservaValetRepository();
        this.clienteRepository = new ClienteRepository();
        this.vehiculoRepository = new VehiculoRepository();
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

            // Obtener el cliente y el vehículo
            Cliente cliente = clienteRepository.buscarClientePorId(connection, Integer.parseInt(idCliente));
            Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPorPlaca(connection, placaVehiculo);

            if (cliente == null || vehiculo == null) {
                mostrarError("Cliente o vehículo no encontrado.");
                return;
            }

            // Crear y guardar la reserva de valet
            ReservaValet nuevaReserva = new ReservaValet(cliente, vehiculo, fechaHoraReserva, metodoPago, "Pendiente");
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