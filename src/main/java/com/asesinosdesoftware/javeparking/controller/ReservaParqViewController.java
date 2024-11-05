package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.exceptions.ReservasException;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.services.ReservaCService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaParqViewController {
    Reserva R;
    ReservaCService RC = new ReservaCService();
    IDBConnectionManager dbConnectionManager = new DBConnectionManager();
    @FXML
    private TableView<Puesto> tablaReservas;

    @FXML
    private TableColumn<Puesto, Integer> columnaID;

    @FXML
    private TableColumn<Puesto, String> columnaTamano;
    @FXML
    public ComboBox <String>IDHoraEntrada;
    @FXML
    public ComboBox <String>IdTamano;
    @FXML
    public ComboBox <String>IDHoraSalida;
    @FXML
    public TextField IDplaca;


    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaTamano.setCellValueFactory(new PropertyValueFactory<>("tamano"));

        // Escuchar cambios en el ComboBox de tamaño
        IdTamano.setOnAction(event -> {
            String tamanoSeleccionado = IdTamano.getValue();
            if (tamanoSeleccionado != null) {
                cargarPuestosFiltrados(tamanoSeleccionado.charAt(0));
            }
        });
    }

    private void cargarPuestosFiltrados(char tamanoSeleccionado) {
        // Crear lista de puestos
        List<Puesto> puestos = new ArrayList<>();
        try {
            // Obtener la conexión (ajusta con tu configuración)
            Connection connection = dbConnectionManager.getConnection();
            PuestoRepository PR = new PuestoRepository();

            // Llamar al método del repositorio para listar puestos
            PR.listarPuestos(puestos, false, tamanoSeleccionado);

            // Convertir la lista en una lista observable para actualizar la tabla
            ObservableList<Puesto> puestosObservable = FXCollections.observableArrayList(puestos);
            tablaReservas.setItems(puestosObservable);

            connection.close();  // Cerrar conexión

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void CrearReserva() throws SQLException{
        try {


            if(R==null){
                R = new Reserva();
                RC.CrearReserva(IDHoraEntrada.getValue(),IDHoraSalida.getValue(),IDplaca.getText(),IdTamano.getValue(),R);

            }
            R = null;
            showSuccess("Reserva Creada con exito");
        } catch (ReservasException e){
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

