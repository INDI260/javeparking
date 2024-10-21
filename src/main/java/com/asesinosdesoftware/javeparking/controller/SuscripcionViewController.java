package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.SuscripcionRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.JDBCService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class SuscripcionViewController {

    @FXML
    private TextField placaTextField; // Campo para ingresar la placa
    @FXML
    private DatePicker fechaInicioPicker; // Campo para ingresar la fecha de inicio
    @FXML
    private DatePicker fechaFinPicker; // Campo para ingresar la fecha de fin

    @FXML
    private void agregarSuscripcion() {
        Connection connection = null;
        try {
            String placa = placaTextField.getText();
            LocalDate fechaInicioSuscripcion = fechaInicioPicker.getValue();
            LocalDate fechaFinSuscripcion = fechaFinPicker.getValue();

            // Validar fechas
            if (fechaInicioSuscripcion == null || fechaFinSuscripcion == null) {
                showError("Por favor, ingrese ambas fechas.");
                return;
            }

            Cliente cliente = new Cliente();
            ClienteRepository CR = new ClienteRepository();

            // Conectar a la base de datos
            JDBCService jdbcService = new JDBCService();
            connection = jdbcService.getConnection();
            CR.buscarCliente(connection, Sesion.getcedula(), cliente);

            Vehiculo vehiculo = new Vehiculo();
            VehiculoRepository vehiculoRepository = new VehiculoRepository();
            boolean encontrado = vehiculoRepository.buscarVehiculoPorPlaca(connection, placa, vehiculo);

            // Verificar si el vehículo existe
            if (!encontrado) {
                showError("Vehículo no encontrado. Por favor, verifique la placa.");
                return;
            }

            Suscripcion suscripcion = new Suscripcion();
            suscripcion.setCliente(cliente);
            suscripcion.setVehiculo(vehiculo);
            suscripcion.setFechaInicio(fechaInicioSuscripcion);
            suscripcion.setFechaFin(fechaFinSuscripcion);

            // Calcular el estado de la suscripción
            LocalDate fechaActual = LocalDate.now();
            String estadoSuscripcion = (fechaActual.isAfter(fechaFinSuscripcion) || fechaActual.isBefore(fechaInicioSuscripcion)) ? "Inactiva" : "Activa";

            if (fechaFinSuscripcion.isBefore(fechaInicioSuscripcion)) {
                showError("Fecha de Fin menor a la de inicio.");
                return;
            }

            suscripcion.setEstado(estadoSuscripcion);

            SuscripcionRepository suscripcionRepository = new SuscripcionRepository();
            suscripcionRepository.agregarSuscripcion(connection, suscripcion);
            showSuccess("Suscripción agregada exitosamente");

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error al agregar suscripción: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Por favor, ingrese un ID de cliente válido.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error en la entrada de datos: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // Asegúrate de cerrar la conexión en el bloque finally
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int calcularDiasSuscripcion(LocalDate fechaInicio, LocalDate fechaFin) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    // Método para mostrar un mensaje de error
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
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


