package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.services.RegistroOpService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class RegistroOpController {

    @FXML
    private TextField txtPlaca;

    @FXML
    private ComboBox<String> cmbTamano;

    @FXML
    private TextField txtTipo;  // Cambié a TextField para que el tipo de vehículo sea digitado

    private RegistroOpService registroOpService;

    // Inicialización del servicio
    public RegistroOpController() {
        this.registroOpService = new RegistroOpService();
    }

    @FXML
    public void initialize() {
        // No es necesario inicializar ComboBoxes si ya los maneja el servicio
        // Solo se asegura que los elementos ya estén cargados correctamente en el ComboBox
    }

    @FXML
    public void agregarVehiculo(ActionEvent event) {
        try {
            // Recoger los datos de la vista
            String placa = txtPlaca.getText();
            String tamanoStr = cmbTamano.getValue();  // Obtiene el valor String
            String tipo = txtTipo.getText();  // Obtiene el valor del TextField

            // Verificar si los campos están completos
            if (placa.isEmpty() || tamanoStr == null || tipo.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios.", AlertType.ERROR);
                return;
            }

            // Convertir el String a Character para el tamaño
            char tamano = tamanoStr.charAt(0);  // Convierte el String a Character

            // Crear el objeto Vehiculo (el ID y el clienteId se asignan automáticamente por el servicio)
            Vehiculo vehiculo = new Vehiculo(0, placa, tamano, tipo.charAt(0), 0);

            // Llamar al servicio para registrar el vehículo
            registroOpService.registrarVehiculo(vehiculo);

            // Mostrar alerta de éxito
            mostrarAlerta("Éxito", "Vehículo registrado correctamente", AlertType.INFORMATION);

        } catch (Exception e) {
            // Mostrar alerta de error si algo sale mal
            mostrarAlerta("Error", "No se pudo registrar el vehículo: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}



