package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PagoReservaController {

    PagoService pagoService = new PagoService();
    PagoReserva pagoReserva;

    @FXML
    public TextField IDPlaca;
    @FXML
    public TextField IDReserva;

    @FXML
    private void pagoReservas() throws SQLException {

        if(pagoReserva == null) {
            pagoService.calcularPago(IDPlaca.getText(),IDReserva.getText(), pagoReserva);
        }
        pagoService.pagarReserva(pagoReserva);
        pagoReserva = null;
        showSuccess("Pago realizado con éxito.");
    }

    @FXML
    private void mostrarPrecio(){

        try {
            pagoService.calcularPago(IDPlaca.getText(), IDReserva.getText(), pagoReserva);
            showSuccess("Su factura es de un valor de: "+ pagoReserva.getValor());
        }
        catch (SQLException e) {
            showError("Error en el pago de reservas: " + e.getMessage());
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

