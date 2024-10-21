package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.repository.*;
import com.asesinosdesoftware.javeparking.services.PagoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PagoReservaController {

    PagoService pagoService;

    @FXML
    public TextField IDPlaca;
    @FXML
    public TextField IDReserva;

    @FXML
    private void pagoReservas()  {
        try{
            pagoService.pagarReserva(IDPlaca.getText(),IDReserva.getText());
        }catch (SQLException e) {
            showError("Error en el pago de reservas: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarprecio(){
        {
            try{
                Vehiculo V = new Vehiculo();
                VehiculoRepository VR = new VehiculoRepository();
                Cliente C = new Cliente();
                ClienteRepository CR = new ClienteRepository();
                Puesto P = new Puesto();
                PuestoRepository PR = new PuestoRepository();
                Reserva R = new Reserva();
                ReservaRepository RR = new ReservaRepository();
                Parqueadero Pq =new Parqueadero();
                ParqueaderoRepository PQR = new ParqueaderoRepository();
                PagoReserva Pago = new PagoReserva();
                JDBCService jdbcService = new JDBCService();
                Connection connection = jdbcService.getConnection();

                VR.buscarVehiculo(connection,IDPlaca.getText(),V);

                int hola =Integer.parseInt(IDReserva.getText());

                RR.buscarReservaPorId(connection,hola,R);

                PR.buscarPuesto(R.getPuesto().getId(),connection,P);

                PQR.buscarParqueaderoPorId(connection,P.getPq(),Pq);

                LocalDateTime fechaActual = LocalDateTime.now();

                Pago.setFechaPago(fechaActual);
                Pago.setReserva(R);
                Pago.setMetodoPago("Online");

                if(P.getTamano()=='m'){
                    double valorpuesto=Pq.getTarifaEstandar()*1.5;
                    Duration duracion = Duration.between(R.getHoraEntrada(), R.getHoraSalida());
                    int hours = (int) duracion.toHours();// Se fuerza a int si es necesario
                    double ValorTotal=valorpuesto*hours;
                    Pago.setValor(ValorTotal);
                }
                if(P.getTamano()=='p'){
                    double valorpuesto=Pq.getTarifaEstandar()*1;
                    Duration duracion = Duration.between(R.getHoraEntrada(), R.getHoraSalida());
                    int hours = (int) duracion.toHours();// Se fuerza a int si es necesario
                    double ValorTotal=valorpuesto*hours;
                    Pago.setValor(ValorTotal);

                }
                if(P.getTamano()=='g'){
                    double valorpuesto=Pq.getTarifaEstandar()*2;
                    Duration duracion = Duration.between(R.getHoraEntrada(), R.getHoraSalida());
                    int hours = (int) duracion.toHours();// Se fuerza a int si es necesario
                    double ValorTotal=valorpuesto*hours;
                    Pago.setValor(ValorTotal);

                }

                showSuccess("El valor de la reserva es: "+Pago.getValor());


            }catch (SQLException e) {
                showError("Pago fallido");
                e.printStackTrace();

            }
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

