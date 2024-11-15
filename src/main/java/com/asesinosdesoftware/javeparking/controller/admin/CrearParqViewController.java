package com.asesinosdesoftware.javeparking.controller.admin;

import com.asesinosdesoftware.javeparking.entities.Parqueadero;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.services.ParqueaderoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CrearParqViewController {

    Parqueadero parqueadero;
    ParqueaderoService parqueaderoService = new ParqueaderoService();

    @FXML
    public TextField idPpequeno;
    @FXML
    public TextField idPmediano;
    @FXML
    public TextField idPgrande;
    @FXML
    public TextField idSpequeno;
    @FXML
    public TextField idSmediano;
    @FXML
    public TextField idSgrande;
    @FXML
    public ComboBox <Double> iddescuento;

    /**
     * Metodo que permite la creacion del parqueadero y se le asignan los diferentes puestos
     * la logica del metodo esta controlada por parqueaderoService
     */
    @FXML
    private void crearparq(){

        try {

            if (parqueadero==null){
                parqueadero = new Parqueadero();
                parqueaderoService.crearparq(idPpequeno.getText(),idPmediano.getText(),idPgrande.getText(),idSpequeno.getText(),idSmediano.getText(),idSgrande.getText(),iddescuento.getValue(),parqueadero);
            }

            parqueadero = null;
            showSuccess("El nuevo parqueadero ha sido registrado");

        }catch (SQLException e){
            parqueadero = null;
            e.printStackTrace();
        }

    }

    public void limpiarPantalla() {
        idPpequeno.clear();
        idPmediano.clear();
        idPgrande.clear();
        idSpequeno.clear();
        idSmediano.clear();
        idSgrande.clear();
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
