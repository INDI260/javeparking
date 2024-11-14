package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
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
    PagoSuscripcionRepository pagoSuscripcionRepository = new PagoSuscripcionRepository();
    PagoOpRepository pagoOpRepository = new PagoOpRepository();

    /**
     * Método que calcula la información del pago de una reserva
     * @param placa: Placa del vehículo en la reserva
     * @param idReserva: ID de la Reserva a pagar
     * @return: Un objeto tipo PagoReserva con la información del pago
     * @throws SQLException
     */
    public void calcularPagoReserva(String placa, String idReserva, PagoReserva pagoReserva) throws SQLException {

       Reserva reserva = new Reserva();
       Vehiculo vehiculo = new Vehiculo();
       Puesto puesto = new Puesto();
       Parqueadero parqueadero = new Parqueadero();
        vehiculoRepository.buscarVehiculoPlaca(placa, vehiculo);
        reservaRepository.buscarReservaPorId(Integer.parseInt(idReserva), reserva);
        puestoRepository.buscarPuesto(reserva.getPuesto().getId(),puesto);
        parqueaderoRepository.buscarParqueaderoPorId(puesto.getParqueaderoID(), parqueadero);


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
     * Calcula el pago por parte del operario en función del tamaño del vehículo y las horas de estancia.
     * @param placa Placa del vehículo a pagar.
     * @param horasEstacionado Horas de permanencia en el parqueadero.
     * @param pagoOp Objeto que almacenará los detalles del pago por operación.
     * @throws SQLException si ocurre un error de base de datos.
     */
    public void calcularPagoOp(String placa, int horasEstacionado, PagoOp pagoOp) throws SQLException, RepositoryException {
        // Obtener el vehículo, puesto y parqueadero asociados a la placa
        Vehiculo vehiculo = new Vehiculo();
        vehiculoRepository.buscarVehiculoPlaca(placa,vehiculo);

        if (vehiculo == null) {
            throw new SQLException("Vehículo no encontrado en el sistema con placa: " + placa);
        }

        Parqueadero parqueadero = new Parqueadero();
        parqueaderoRepository.buscarParqueaderoPorId(1,parqueadero);

        if (parqueadero == null) {
            throw new SQLException("Parqueadero no encontrado para el vehículo con placa: " + placa);
        }

        // Establecer la fecha y método de pago
        LocalDateTime fechaActual = LocalDateTime.now();
        pagoOp.setFecha(fechaActual);
        pagoOp.setMetodoPago("Online");

        // Calcular la tarifa según el tamaño del vehículo
        BigDecimal tarifa;
        switch (vehiculo.getTamano()) {
            case 'p' -> tarifa = parqueadero.getTarifaPequeno();
            case 'm' -> tarifa = parqueadero.getTarifaMediano();
            case 'g' -> tarifa = parqueadero.getTarifaGrande();
            default -> throw new SQLException("Tamaño de vehículo no válido");
        }

        // Calcular el valor del pago basado en la duración en horas
        BigDecimal valorPago = tarifa.multiply(BigDecimal.valueOf(horasEstacionado));
        pagoOp.setValor(valorPago);
        pagoOp.setVehiculo(vehiculo);

        // Registrar el pago en la base de datos
        pagoOpRepository.registrarPago(pagoOp);

    }

    /**
     * Método que agrega un pago de una reserva a la base de datos
     * @param pagoReserva: Pago a añadir a la base de datos
     * @throws SQLException
     */
    public void pagarReserva(PagoReserva pagoReserva) throws SQLException {
        pagoRepository.agregarPagoReserva(pagoReserva);
    }

    /**
     * Método que agrega un pago de una suscripcion a la base de datos
     * @param pagoSuscripcion: Pago a añadir a la base de datos
     * @throws SQLException
     */
    public void pagarSuscripcion(PagoSuscripcion pagoSuscripcion) throws SQLException {
        pagoSuscripcionRepository.agregarPagoSuscripcion(pagoSuscripcion);
    }

    /**
     * Registra el pago de una operación en la base de datos.
     * @param pagoOp Pago de operación a registrar.
     * @throws SQLException si ocurre un error de base de datos.
     */
    public void pagarOperacion(PagoOp pagoOp) throws SQLException {
        pagoOpRepository.registrarPago(pagoOp);
    }
}
