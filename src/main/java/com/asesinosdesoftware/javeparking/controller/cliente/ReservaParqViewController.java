package com.asesinosdesoftware.javeparking.controller.cliente;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.ReservaCService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaParqViewController {

    ReservaCService reservaCService = new ReservaCService();
    Reserva reserva;

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
            PuestoRepository PR = new PuestoRepository();

            // Llamar al método del repositorio para listar puestos
            PR.listarPuestos(puestos, false, tamanoSeleccionado);

            // Convertir la lista en una lista observable para actualizar la tabla
            ObservableList<Puesto> puestosObservable = FXCollections.observableArrayList(puestos);
            tablaReservas.setItems(puestosObservable);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void CrearReserva()throws SQLException {
        try{
            if(reserva==null){
                reserva = new Reserva();
                reservaCService.CrearReserva(IDHoraEntrada.getValue(),IDHoraSalida.getValue(),IDplaca.getText(),IdTamano.getValue(),reserva);

            }
            reserva = null;
            showSuccess("Reserva Creada con exito");
        } catch (ServiceException e){
            reserva = null;
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

