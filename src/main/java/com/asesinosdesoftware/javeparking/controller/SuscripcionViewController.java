package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.SuscripcionRepository;
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
    private DatePicker fechaInicioPicker; // Campo para ingresar la fecha de fin
    @FXML
    private DatePicker fechaFinPicker; // Campo para ingresar la fecha de fin

    @FXML
    private void agregarSuscripcion() {
        try {

            LocalDate fechaInicioSuscripcion = fechaInicioPicker.getValue(); // Obtener fecha de inicio del DatePicker
            LocalDate fechaFinSuscripcion = fechaFinPicker.getValue(); // Obtener fecha de fin del DatePicker

            // Crear objeto Cliente
            Cliente cliente = new Cliente();
            ClienteRepository CR = new ClienteRepository();

            // Conectar a la base de datos
            JDBCService jdbcService = new JDBCService();
            Connection connection = jdbcService.getConnection();
            CR.buscarCliente(connection,Sesion.getcedula(),cliente);

            // Crear objeto Suscripcion
            Suscripcion suscripcion = new Suscripcion();
            suscripcion.setCliente(cliente);
            suscripcion.setFechaInicio(fechaInicioSuscripcion);
            suscripcion.setFechaFin(fechaFinSuscripcion);

            // Calcular el estado de la suscripción
            LocalDate fechaActual = LocalDate.now();
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
            SuscripcionRepository.agregarSuscripcion(connection, suscripcion);

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