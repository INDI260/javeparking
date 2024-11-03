package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class ReVehiculoviewController {
    @FXML
    public ComboBox <String> IdTamano;
    @FXML
    public TextField IdTipo;
    @FXML
    public TextField IdPlaca;
    @FXML
    private void registrovehiculo(){
    try {
        Cliente Dueno = new Cliente();
        Vehiculo v = new Vehiculo();

        v.setTamano(IdTamano.getValue().charAt(0));
        v.setTipo(IdTipo.getText().charAt(0));
        v.setPlaca(IdPlaca.getText());

        VehiculoRepository vehiculoRepository = new VehiculoRepository();
        ClienteRepository clienterepository = new ClienteRepository();

        clienterepository.buscarCliente(Sesion.getcedula(),Dueno);
        v.setClienteid(Dueno.getId());
        vehiculoRepository.agregarVehiculo(v);

        showSuccess("Registro de Vehiculo Exitoso");
    } catch (Exception e){
        e.printStackTrace();
        showError("Error al agregar vehiculo");
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
