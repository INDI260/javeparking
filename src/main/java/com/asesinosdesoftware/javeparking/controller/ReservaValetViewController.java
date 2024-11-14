package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.exceptions.ReservasException;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.ReservaValetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.sql.SQLException;

public class ReservaValetViewController {

    Reserva reservaValet;
    ReservaValetService reservaValetService = new ReservaValetService();

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
     * Metodo que usa el servicio ReservaValetService para la creacion de la reserva de valet parking
     * @throws SQLException
     */
    @FXML
    private void crearReservaValet() throws SQLException {
        try {
            if (reservaValet == null) {
                reservaValet = new Reserva();
                reservaValetService.crearReservaValet(IDHoraEntrada.getValue(), IDHoraSalida.getValue(), IDplaca.getText(), reservaValet);
            }
            reservaValet = null;
            showSuccess("Reserva de Valet Parking creada con éxito");
            cargarReservas();
        } catch (ReservasException e) {
            showError(e.toString());
        }
    }

    /**
     * Metodo que usa el servicio ReservaValetService para editar una reserva de valet parking
     * @throws SQLException
     */
    @FXML
    private void editarReservaValet() throws SQLException {
        // Lógica para editar la reserva seleccionada
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            try {
                reservaValetService.editarReservaValet(IDHoraEntrada.getValue(), IDHoraSalida.getValue(), IDplaca.getText(), reservaSeleccionada);
                showSuccess("Reserva de Valet Parking editada con éxito");
                cargarReservas();
            } catch (ReservasException e) {
                showError(e.toString());
            }
        } else {
            showError("Seleccione una reserva para editar");
        }
    }

    /**
     * Metodo que usa el servicio ReservaValetService para eliminar una reserva de valet parking
     */
    @FXML
    private void eliminarReservaValet() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            try {
                reservaValetService.eliminarReservaValet(reservaSeleccionada);
                showSuccess("Reserva de Valet Parking eliminada con éxito");
                cargarReservas();
            } catch (Exception e) {
                showError("Error al eliminar la reserva");
                e.printStackTrace();
            }
        } else {
            showError("Seleccione una reserva para eliminar");
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