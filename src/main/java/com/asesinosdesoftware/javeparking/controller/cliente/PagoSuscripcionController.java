package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.PagoSuscripcion;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PagoSuscripcionController {

    PagoService pagoService = new PagoService();
    PagoSuscripcion pagoSuscripcion;

    @FXML
    public TextField IDPlaca;


    /**
     * Metodo que permite registrar el pago de las suscripciones del cliente,
     * el registro queda guardado en la tabla pagosuscripcion
     * la logica del programa la maneja pagoService
     * @throws SQLException
     */
    @FXML
    private void pagoSuscripcion() throws SQLException {
        try {
            if (pagoSuscripcion == null) {
                pagoSuscripcion = new PagoSuscripcion();
                pagoService.calcularPagoSuscripcion(IDPlaca.getText(), pagoSuscripcion);
            }
            pagoService.pagarSuscripcion(pagoSuscripcion);
            pagoSuscripcion = null;
            showSuccess("Pago de suscripción realizado con éxito.");
        }catch (SQLException e){
            pagoSuscripcion = null;
            e.printStackTrace();
        }
    }

    /**
     * Metodo que permite mostrar los valores a pagar por la suscripcion asociada al vehiculo
     * la logica se maneja en pagoService
     */
    @FXML
    private void mostrarPrecioSuscripcion() {
        try {

            pagoSuscripcion = new PagoSuscripcion();
            pagoService.calcularPagoSuscripcion(IDPlaca.getText(), pagoSuscripcion);
            showSuccess("Su factura de suscripción es de un valor de: " + pagoSuscripcion.getValor());
        } catch (SQLException e) {
            showError("Error en el cálculo de suscripción: " + e.getMessage());
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

    public PagoService getPagoService() {
        return pagoService;
    }

    public void setPagoService(PagoService pagoService) {
        this.pagoService = pagoService;
    }
}











