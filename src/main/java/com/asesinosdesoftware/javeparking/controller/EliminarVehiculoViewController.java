package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EliminarVehiculoViewController {

    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();
    @FXML
    public TextField IDPlaca;

    @FXML
    private void   EliminarVehiculo(){
    try {

        Cliente dueno = new Cliente();
        ClienteRepository CR = new ClienteRepository();

        Vehiculo V = new Vehiculo();
        VehiculoRepository VR = new VehiculoRepository();


        CR.buscarCliente(dbConnectionManager.getConnection(),Sesion.getcedula(),dueno);
        VR.buscarVehiculo(dbConnectionManager.getConnection(),IDPlaca.getText(),V);

        if(dueno.getId()==V.getClienteid()){
            VR.eliminarVehiculo(dbConnectionManager.getConnection(),V);
            showSuccess("Vehiculo eliminado correctamente");
        }
        else showError("El vehiculo no te pertenece");


    }catch (Exception e) {
        showError("Error eliminar el Vehiculo");
        e.printStackTrace();

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
