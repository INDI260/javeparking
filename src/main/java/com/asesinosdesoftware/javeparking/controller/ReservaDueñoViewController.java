package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDueñoViewController {

    IDBConnectionManager dbConnectionManager = new DBConnectionManager();

    @FXML
    private TableView<Puesto> tablaReservas;

    @FXML
    private TableColumn<Puesto, Integer> columnaID;

    @FXML
    private TableColumn<Puesto, String> columnaTamano;

    @FXML
    public ComboBox<String> IDHoraEntrada;

    @FXML
    public ComboBox<String> IdTamano;

    @FXML
    public ComboBox<String> IDHoraSalida;

    @FXML
    public TextField IDplaca;

    private ObservableList<Reserva> reservasObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaTamano.setCellValueFactory(new PropertyValueFactory<>("tamano"));

        IdTamano.setOnAction(event -> {
            String tamanoSeleccionado = IdTamano.getValue();
            if (tamanoSeleccionado != null) {
                cargarPuestosFiltrados(tamanoSeleccionado.charAt(0));
            }
        });

        cargarReservas();
    }

    private void cargarPuestosFiltrados(char tamanoSeleccionado) {
        List<Puesto> puestos = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection()) {
            PuestoRepository puestoRepository = new PuestoRepository();
            puestoRepository.listarPuestos(connection, puestos, false, tamanoSeleccionado);
            tablaReservas.setItems(FXCollections.observableArrayList(puestos));

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error al cargar los puestos filtrados");
        }
    }

    private void cargarReservas() {
        try (Connection connection = dbConnectionManager.getConnection()) {
            ReservaRepository reservaRepository = new ReservaRepository();
            reservasObservableList.setAll(reservaRepository.obtenerTodasReservas(connection));
            tablaReservas.setItems(reservasObservableList);

        } catch (Exception e) {
            showError("Error al cargar reservas");
            e.printStackTrace();
        }
    }

    @FXML
    private void crearReserva() {
        try (Connection connection = dbConnectionManager.getConnection()) {
            Reserva reserva = new Reserva();
            LocalDate fechaActual = LocalDate.now();
            LocalTime horaEntrada = LocalTime.parse(IDHoraEntrada.getValue());
            LocalDateTime horaEntradaCompleta = LocalDateTime.of(fechaActual, horaEntrada);
            LocalTime horaSalida = LocalTime.parse(IDHoraSalida.getValue());
            LocalDateTime horaSalidaCompleta = LocalDateTime.of(fechaActual, horaSalida);

            if (horaSalidaCompleta.isBefore(horaEntradaCompleta) || horaSalidaCompleta.isEqual(horaEntradaCompleta)) {
                showError("La hora de salida debe ser posterior a la hora de entrada");
                return;
            }

            Vehiculo vehiculo = new Vehiculo();
            VehiculoRepository vehiculoRepository = new VehiculoRepository();
            vehiculoRepository.buscarVehiculo(connection, IDplaca.getText(), vehiculo);

            Puesto puesto = new Puesto();
            PuestoRepository puestoRepository = new PuestoRepository();
            puestoRepository.buscarPuesto(IdTamano.getValue(), false, connection, puesto);

            if (vehiculo.getTamano() != IdTamano.getValue().charAt(0)) {
                showError("El tamaño del vehículo no coincide con el tamaño del puesto");
                return;
            }

            reserva.setHoraEntrada(horaEntradaCompleta);
            reserva.setHoraSalida(horaSalidaCompleta);
            reserva.setVehiculo(vehiculo);
            reserva.setPuesto(puesto);

            ReservaRepository reservaRepository = new ReservaRepository();
            reservaRepository.agregarReserva(connection, reserva);

            puesto.setDisponibilidad(true);
            puestoRepository.actualizarPuesto(connection, puesto);

            cargarReservas();
            showSuccess("Reserva creada exitosamente");

        } catch (Exception e) {
            showError("Error al crear la reserva");
            e.printStackTrace();
        }
    }

    @FXML
    private void editarReserva() {
        // Lógica para editar la reserva seleccionada
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            try (Connection connection = dbConnectionManager.getConnection()) {
                // Actualizar datos de reserva aquí
                ReservaRepository reservaRepository = new ReservaRepository();
                reservaRepository.actualizarReserva(connection, reservaSeleccionada);
                cargarReservas();
                showSuccess("Reserva actualizada exitosamente");

            } catch (Exception e) {
                showError("Error al actualizar la reserva");
                e.printStackTrace();
            }
        } else {
            showError("Seleccione una reserva para editar");
        }
    }

    @FXML
    private void eliminarReserva() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            try (Connection connection = dbConnectionManager.getConnection()) {
                ReservaRepository reservaRepository = new ReservaRepository();
                reservaRepository.eliminarReserva(connection, reservaSeleccionada);
                cargarReservas();
                showSuccess("Reserva eliminada exitosamente");

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
                    writer.append(reserva.getId()).append(", ")
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