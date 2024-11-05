package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.exceptions.InicioDeSesionException;
import com.asesinosdesoftware.javeparking.exceptions.ReservaCException;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.*;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaCService {

    VehiculoRepository VR = new VehiculoRepository();
    PuestoRepository PR = new PuestoRepository();
    ReservaRepository RR= new ReservaRepository();
    IDBConnectionManager dbConnectionManager= new DBConnectionManager();


    public void CrearReserva(String IDHoraEntrada, String IDHoraSalida,String IDplaca, String IdTamano,Reserva R) throws ReservaCException, SQLException {


            LocalDate D = LocalDate.now();
            LocalTime TE = LocalTime.parse(IDHoraEntrada);
            LocalDateTime HoradeEntrada = LocalDateTime.of(D, TE);
            LocalTime TS = LocalTime.parse(IDHoraSalida);
            LocalDateTime HoradeSalida = LocalDateTime.of(D, TS);
            if (HoradeSalida.isBefore(HoradeEntrada)||HoradeSalida.isEqual(HoradeEntrada)) {
                throw new ReservaCException("Hora de entrada y salida mal definida");

            }
            Vehiculo V = new Vehiculo();
            VR.buscarVehiculo(dbConnectionManager.getConnection(),IDplaca,V);

            Puesto P = new Puesto();

            PR.buscarPuesto(IdTamano,false,dbConnectionManager.getConnection(),P);
            if(V.getTamano()!=IdTamano.charAt(0)){
                throw new ReservaCException("Tamaño de reserva y de auto no coinciden");
            }
            R.setHoraEntrada(HoradeEntrada);
            R.setHoraSalida(HoradeSalida);
            R.setVehiculo(V);
            R.setPuesto(P);
            // if(buscarReservaVehiculo(connection,R)!=null){
            // showError("Reserva ya existe");
            // return;
            //  }
            RR.agregarReserva(dbConnectionManager.getConnection(),R);
            P.setDisponibilidad(true);
            PR.actualizarPuesto(dbConnectionManager.getConnection(),P);
        System.out.println("Hola");
            dbConnectionManager.getConnection().close();//No olvidar siempre cerrar la conexión una vez esta se termine de usar


    }

}
