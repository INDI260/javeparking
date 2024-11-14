package com.asesinosdesoftware.javeparking.controller.admin;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.services.PuestosService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AgregarPuestosViewController {

    @FXML
    public TextField idparq;
    @FXML
    public TextField cantP;
    @FXML
    public TextField cantM;
    @FXML
    public TextField cantG;

    PuestosService puestosService = new PuestosService();
    Puesto puesto;

    @FXML
    private void agregarpuestos(){
        int parq;
        int pequeno;
        int mediano;
        int grande;

        if(cantP.getText().isEmpty()){
            pequeno=0;
        }
        if(cantM.getText().isEmpty()){
            mediano=0;
        }
        if(cantG.getText().isEmpty()){
            grande=0;
        }
        parq = Integer.parseInt(idparq.getText());
        pequeno = Integer.parseInt(cantP.getText());
        mediano = Integer.parseInt(cantM.getText());
        grande = Integer.parseInt(cantG.getText());

        try{

            if(puesto==null){
                puesto =new Puesto();
                puestosService.agregarPuestos(parq,pequeno,mediano,grande,puesto);
            }
            puesto=null;
            showSuccess("Puestos añadidos al parqueadero de forma exitosa");

        }catch (SQLException | ServiceException e){
            puesto=null;
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

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
