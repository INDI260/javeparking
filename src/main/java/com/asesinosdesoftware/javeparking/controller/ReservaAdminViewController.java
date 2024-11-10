package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.ReservasException;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.ReservaAdService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaAdminViewController {
    Reserva R;
    ReservaAdService RAd = new ReservaAdService();

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private TableColumn<Reserva, Integer> columnaID;

    @FXML
    private TableColumn<Reserva, String> columnaTamano;

    @FXML
    public ComboBox<String> IDHoraEntrada;

    @FXML
    public ComboBox<String> IdTamano;

    @FXML
    public ComboBox<String> IDHoraSalida;

    @FXML
    public TextField IDplaca;

    private ObservableList<Reserva> reservasObservableList = FXCollections.observableArrayList();

    /**
     * Metodo para inicializar los elementos mostrados en pantalla
     */
    @FXML
    public void initialize() {
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaTamano.setCellValueFactory(new PropertyValueFactory<>("tamano"));

        IDHoraEntrada.getItems().addAll("06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        IDHoraSalida.getItems().addAll("07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
                "14:00", "15:00", "16:00", "17:00", "18:00", "19:00");

        // Opciones para el Tamaño del Puesto
        IdTamano.getItems().addAll("p", "m", "g");

        cargarReservas();
    }

    /**
     * Metodo para cargar las reservas existentes para poder mostrarlas en la pantalla
     */
    private void cargarReservas() {
        try {
            ReservaRepository reservaRepository = new ReservaRepository();
            reservasObservableList.setAll(reservaRepository.obtenerTodasReservas());
            tablaReservas.setItems(reservasObservableList);

        } catch (Exception e) {
            showError("Error al cargar reservas");
            e.printStackTrace();
        }
    }

    /**
     * Metodo que usa el servicio ReservaAdService para la creacion de la reserva por parte del Administrador
     * @throws SQLException
     */
    @FXML
    private void crearReserva() throws SQLException {
        try{
            if(R==null){
            R = new Reserva();
            RAd.crearReserva(IDHoraEntrada.getValue(),IDHoraSalida.getValue(),IDplaca.getText(),IdTamano.getValue(),R);

        }
            R = null;
            showSuccess("Reserva Creada con exito");
            cargarReservas();
        } catch (ReservasException e){
            showError(e.toString());
        }
    }

    /**
     * Metodo que usa el servicio ReservaAdService para edicion de la reserva por parte del Administrador
     * @throws SQLException
     */
    @FXML
    private void editarReserva() throws SQLException{
        // Lógica para editar la reserva seleccionada
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {

            try {
                // Actualizar datos de reserva aquí

                RAd.editarReserva(IDHoraEntrada.getValue(),IDHoraSalida.getValue(),IDplaca.getText(),IdTamano.getValue(),reservaSeleccionada);
                showSuccess("Reserva editada con exito");
                cargarReservas();

            } catch (ReservasException e) {
                showError(e.toString());
            }
        } else {
            showError("Seleccione una reserva para editar");
        }
    }

    /**
     * Metodo que usa el servicio ReservaAdService para la eliminacion de la reserva por parte del Administrador
     */
    @FXML
    private void eliminarReserva() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            try  {

                RAd.eliminarReserva(reservaSeleccionada);
                showSuccess("Reserva editada con exito");
                cargarReservas();

            } catch (Exception e) {
                showError("Error al eliminar la reserva");
                e.printStackTrace();
            }
        } else {
            showError("Seleccione una reserva para eliminar");
        }
    }

    @FXML
    private void generarReporteReservas() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte de Reservas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV", "*.csv"));
        File archivo = fileChooser.showSaveDialog(null);

        if (archivo != null) {
            try (FileWriter writer = new FileWriter(archivo)) {
                writer.append("ID Reserva, Placa, Hora Entrada, Hora Salida\n");
                for (Reserva reserva : reservasObservableList) {
                    writer.append(String.valueOf(reserva.getId())).append(", ")
                            .append(reserva.getVehiculo().getPlaca()).append(", ")
                            .append(reserva.getHoraEntrada().toString()).append(", ")
                            .append(reserva.getHoraSalida().toString()).append("\n");
                }
                showSuccess("Reporte generado exitosamente");

            } catch (IOException e) {
                showError("Error al generar el reporte");
                e.printStackTrace();
            }
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