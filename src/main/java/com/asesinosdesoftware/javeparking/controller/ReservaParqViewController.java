package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.JDBCService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaParqViewController {
    @FXML
    private TableView<Puesto> tablaReservas;

    @FXML
    private TableColumn<Puesto, Integer> columnaID;

    @FXML
    private TableColumn<Puesto, String> columnaIDParqueadero;

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
            JDBCService controller = new JDBCService();
            Connection connection = controller.getConnection();
            VR.buscarVehiculo(connection,IDplaca.getText(),V);

            Puesto P = new Puesto();
            PuestoRepository PR = new PuestoRepository();
            PR.buscarPuesto(IdTamano.getValue(),false,connection,P);
            if(V.getTamano()!=IdTamano.getValue().charAt(0)){
                showError("Tamaño de reserva y de auto no coinciden");
                return ;
            }
            R.setHoraEntrada(HoradeEntrada);
            R.setHoraSalida(HoradeSalida);
            R.setVehiculo(V);
            R.setPuesto(P);
            ReservaRepository RR = new ReservaRepository();
            RR.agregarReserva(connection,R);
            connection.close();//No olvidar siempre cerrar la conexión una vez esta se termine de usar
            showSuccess("Reserva de Parqueadero Exitosa");

        } catch (Exception e) {
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

