package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

public class PagoService {

    VehiculoRepository vehiculoRepository= new VehiculoRepository();
    PuestoRepository puestoRepository = new PuestoRepository();
    ReservaRepository reservaRepository = new ReservaRepository();
    ParqueaderoRepository parqueaderoRepository= new ParqueaderoRepository();
    PagoRepository pagoRepository = new PagoRepository();
    IDBConnectionManager dbConnectionManager = new DBConnectionManager();


    /**
     * Método que calcula la información del pago de una reserva
     * @param placa: Placa del vehículo en la reserva
     * @param idReserva: ID de la Reserva a pagar
     * @return: Un objeto tipo PagoReserva con la información del pago
     * @throws SQLException
     */
    public void calcularPago(String placa, String idReserva, PagoReserva pagoReserva) throws SQLException {
       Reserva reserva = new Reserva();
       Vehiculo vehiculo = new Vehiculo();
       Puesto puesto = new Puesto();
       Parqueadero parqueadero = new Parqueadero();
        Connection connection = dbConnectionManager.getConnection();
        vehiculoRepository.buscarVehiculo(connection, placa, vehiculo);
        reservaRepository.buscarReservaPorId(connection, Integer.parseInt(idReserva), reserva);
        puestoRepository.buscarPuesto(reserva.getPuesto().getId(), connection, puesto);
        parqueaderoRepository.buscarParqueaderoPorId(connection, puesto.getParqueaderoID(), parqueadero);

        LocalDateTime fechaActual = LocalDateTime.now();

        pagoReserva.setFecha(fechaActual);
        pagoReserva.setReserva(reserva);
        pagoReserva.setMetodoPago("Online");

        BigDecimal tarifa;
        if(puesto.getTamano() == 'p'){
            tarifa = parqueadero.getTarifaPequeno();
        }
        else if(puesto.getTamano() == 'm'){
            tarifa = parqueadero.getTarifaMediano();

        }
        else {
            tarifa = parqueadero.getTarifaGrande();

        }

        int duracion = (int) Duration.between(reserva.getHoraEntrada(), reserva.getHoraSalida()).toHours();

        pagoReserva.setValor(tarifa.multiply(BigDecimal.valueOf(duracion)));

    }


    /**
     * Método que agrega un pago de una reserva a la base de datos
     * @param pagoReserva: Pago a añadir a la base de datos
     * @throws SQLException
     */
    public void pagarReserva(PagoReserva pagoReserva) throws SQLException {
        pagoRepository.agregarPagoReserva(dbConnectionManager.getConnection(), pagoReserva);
    }

}
