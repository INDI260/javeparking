package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaCService {

    VehiculoRepository VR = new VehiculoRepository();
    PuestoRepository PR = new PuestoRepository();
    ReservaRepository RR= new ReservaRepository();

    /**
     * Metodo por el cual el Cliente/Usuario puede crear la reserva para su vehiculo
     * @param IDHoraEntrada
     * @param IDHoraSalida
     * @param IDplaca
     * @param IdTamano
     * @param R
     * @throws ServiceException
     * @throws SQLException
     */
    public void CrearReserva(String IDHoraEntrada, String IDHoraSalida,String IDplaca, String IdTamano,Reserva R) throws ServiceException, SQLException {

            LocalDate D = LocalDate.now();
            LocalTime TE = LocalTime.parse(IDHoraEntrada);
            LocalDateTime HoradeEntrada = LocalDateTime.of(D, TE);
            LocalTime TS = LocalTime.parse(IDHoraSalida);
            LocalDateTime HoradeSalida = LocalDateTime.of(D, TS);
            if (HoradeSalida.isBefore(HoradeEntrada)||HoradeSalida.isEqual(HoradeEntrada)) {
                throw new ServiceException("Hora de entrada y salida mal definida");

            }
            Vehiculo V = new Vehiculo();
            VR.buscarVehiculo(IDplaca,V);

            Puesto P = new Puesto();

            PR.buscarPuesto(IdTamano,false,P);
            if(V.getTamano()!=IdTamano.charAt(0)){
                throw new ServiceException("Tamaño de reserva y de auto no coinciden");
            }

            R.setHoraEntrada(HoradeEntrada);
            R.setHoraSalida(HoradeSalida);
            R.setVehiculo(V);
            R.setPuesto(P);
            // if(buscarReservaVehiculo(connection,R)!=null){
            // showError("Reserva ya existe");
            // return;
            //  }
            RR.agregarReserva(R);
            P.setDisponibilidad(true);
            PR.actualizarPuesto(P);


    }

}
