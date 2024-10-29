package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.SuscripcionRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SuscripcionViewController {

    IDBConnectionManager dbConnectionManager = new DBConnectionManager();
    VehiculoRepository vehiculoRepository = new VehiculoRepository();
    SuscripcionRepository suscripcionRepository = new SuscripcionRepository();
    @FXML
    private DatePicker fechaInicioPicker; // Campo para ingresar la fecha de fin
    @FXML
    private DatePicker fechaFinPicker; // Campo para ingresar la fecha de fin
    @FXML
    private TextField placaTextField;

    @FXML
    private void agregarSuscripcion() {
        try {

            LocalDateTime fechaInicioSuscripcion = fechaInicioPicker.getValue().atStartOfDay(); // Obtener fecha de inicio del DatePicker
            LocalDateTime fechaFinSuscripcion = fechaFinPicker.getValue().atStartOfDay(); // Obtener fecha de fin del DatePicker
            String placa = placaTextField.getText();

            // Crear objeto Cliente
            Cliente cliente = new Cliente();
            ClienteRepository CR = new ClienteRepository();

            // Conectar a la base de datos
            Connection connection = dbConnectionManager.getConnection();
            CR.buscarCliente(connection,Sesion.getcedula(),cliente);

            // Crear objeto Suscripcion
            Suscripcion suscripcion = new Suscripcion();
            suscripcion.setCliente(cliente);
            suscripcion.setFechaInicio(fechaInicioSuscripcion);
            suscripcion.setFechaFin(fechaFinSuscripcion);
            suscripcion.setVehiculo(new Vehiculo());
            if(vehiculoRepository.buscarVehiculo(dbConnectionManager.getConnection(), placa, suscripcion.getVehiculo()) == null)
                showError("El vehiculo no está registrado");

            // Calcular el estado de la suscripción
            LocalDateTime fechaActual = LocalDateTime.now();
            String estadoSuscripcion;
            if (fechaActual.isAfter(fechaFinSuscripcion) || fechaActual.isBefore(fechaInicioSuscripcion)) {
                estadoSuscripcion = "Inactiva"; // La suscripción es inactiva
            } else {
                estadoSuscripcion = "Activa"; // La suscripción es activa
            }

            if (fechaFinSuscripcion.isBefore(fechaInicioSuscripcion)) {
                showError("Fecha de Fin menor a la de inicio.");
                return;
            }
            // Establecer el estado de la suscripción
            suscripcion.setEstado(estadoSuscripcion);

            // Agregar suscripción a la base de datos
            suscripcionRepository.agregarSuscripcion(connection, suscripcion);

            connection.close(); // Cerrar la conexión
            showSuccess("Suscripción agregada exitosamente");

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error al agregar suscripción: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Por favor, ingrese un ID de cliente válido.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error en la entrada de datos: " + e.getMessage());
        }
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
