package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaParqViewController {

    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();
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
    private void CrearReserva() {
        try {
            Reserva R = new Reserva();

            LocalDate D = LocalDate.now();
            LocalTime TE = LocalTime.parse(IDHoraEntrada.getValue());
            LocalDateTime HoradeEntrada = LocalDateTime.of(D, TE);
            LocalTime TS = LocalTime.parse(IDHoraSalida.getValue());
            LocalDateTime HoradeSalida = LocalDateTime.of(D, TS);
            if (HoradeSalida.isBefore(HoradeEntrada)||HoradeSalida.isEqual(HoradeEntrada)) {
                showError("Hora de entrada y salida mal definida");
                return ;
            }
            Vehiculo V = new Vehiculo();
            VehiculoRepository VR = new VehiculoRepository();
            VR.buscarVehiculo(IDplaca.getText(),V);

            Puesto P = new Puesto();
            PuestoRepository PR = new PuestoRepository();
            PR.buscarPuesto(IdTamano.getValue(),false,P);
            if(V.getTamano()!=IdTamano.getValue().charAt(0)){
                showError("Tamaño de reserva y de auto no coinciden");
                return ;
            }
            R.setHoraEntrada(HoradeEntrada);
            R.setHoraSalida(HoradeSalida);
            R.setVehiculo(V);
            R.setPuesto(P);
            ReservaRepository RR = new ReservaRepository();
           // if(buscarReservaVehiculo(connection,R)!=null){
               // showError("Reserva ya existe");
               // return;
          //  }
            RR.agregarReserva(R);
            P.setDisponibilidad(true);
            PR.actualizarPuesto(P);
            showSuccess("Reserva de Parqueadero Exitosa");

        } catch (Exception e) {
            showError("Error al crear Reserva");
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

