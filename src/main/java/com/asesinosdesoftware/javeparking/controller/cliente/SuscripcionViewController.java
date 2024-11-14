package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.SuscripcionRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.SuscripcionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class SuscripcionViewController {


    Suscripcion suscripcion;
    SuscripcionService suscripcionService = new SuscripcionService();

    @FXML
    private DatePicker fechaInicioPicker; // Campo para ingresar la fecha de fin
    @FXML
    private DatePicker fechaFinPicker; // Campo para ingresar la fecha de fin
    @FXML
    private TextField placaTextField;
    @FXML
    private TextField idparq;
    @FXML
    private void agregarSuscripcion() {
        int idParq = Integer.parseInt(idparq.getText());
        try{
            if(suscripcion==null){
                suscripcion = new Suscripcion();
                suscripcionService.agregarSuscripcion(fechaInicioPicker.getValue().atStartOfDay(),fechaFinPicker.getValue().atStartOfDay(),placaTextField.getText(),idParq,suscripcion);
            }
            suscripcion = null;
            showSuccess("Suscripcion Creada con exito");
        } catch (ServiceException | SQLException e){
            suscripcion = null;
            showError(e.toString());
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
