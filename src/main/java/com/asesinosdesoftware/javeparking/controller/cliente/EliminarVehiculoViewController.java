package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.VehiculoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EliminarVehiculoViewController {

    Vehiculo vehiculo = new Vehiculo();
    VehiculoService vehiculoService = new VehiculoService();
    Cliente dueno = new Cliente();

    @FXML
    public TextField IDPlaca;


    /**
     * Metodo que le permite al cliente eliminar el vehiculo asociado a el
     * la logica del metodo se encuentra en vehiculoService
     */
    @FXML
    private void EliminarVehiculo(){
    try {

        vehiculoService.EliminarVehiculo(IDPlaca.getText(),vehiculo,dueno);
        showSuccess("Vehiculo eliminado con exito");


    }catch (Exception e) {
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
