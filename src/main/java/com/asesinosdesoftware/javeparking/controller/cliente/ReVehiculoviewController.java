package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.VehiculoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ReVehiculoviewController {

    Vehiculo vehiculo ;
    VehiculoService vehiculoService = new VehiculoService();
    @FXML
    public ComboBox <String> IdTamano;
    @FXML
    public TextField IdTipo;
    @FXML
    public TextField IdPlaca;

    @FXML
    private void registrovehiculo() {
    try {

        if(vehiculo==null) {
            vehiculoService.registroVehiculo(IdTamano.getValue(),IdTipo.getText(),IdPlaca.getText(),vehiculo);
        }
        vehiculo=null;
        showSuccess("Vehiculo registrado con exito");

    } catch (Exception e){
        showError(e.toString());

    }

    }
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
