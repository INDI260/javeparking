package com.asesinosdesoftware.javeparking.controller.admin;

import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.services.ReservaAdService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

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
        } catch (ServiceException e){
            R = null;
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

            } catch (ServiceException e) {
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

    /**
     * Metodo que usa el servicio ReservaAdService para la creacion del informe por parte del Administrador
     */
    @FXML
    private void generarReporteReservas() {
        RAd.generarReporteReservas(reservasObservableList);
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