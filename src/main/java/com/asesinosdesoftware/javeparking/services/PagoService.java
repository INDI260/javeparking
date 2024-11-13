package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.*;
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

    public void calcularPagoOp(String placa, int horasEstacionado, PagoOp pagoOp) throws SQLException {

        // Crear la entidad Vehiculo
        Vehiculo vehiculo = new Vehiculo();

        // Buscar el vehículo por placa
        vehiculoRepository.buscarVehiculoPlaca(placa, vehiculo);

        // Buscar el parqueadero asociado al vehículo
        Parqueadero parqueadero = new Parqueadero();
        parqueaderoRepository.buscarParqueaderoPorId(vehiculo.getClienteid(), parqueadero);

        // Establecer la fecha del pago
        LocalDateTime fechaActual = LocalDateTime.now();
        pagoOp.setFecha(fechaActual);
        pagoOp.setMetodoPago("Online");  // Suponiendo que el pago es online

        // Calcular la tarifa según el tamaño del vehículo
        BigDecimal tarifa;
        switch (vehiculo.getTamano()) {
            case 'p':  // Vehículo pequeño
                tarifa = parqueadero.getTarifaPequeno();
                break;
            case 'm':  // Vehículo mediano
                tarifa = parqueadero.getTarifaMediano();
                break;
            case 'g':  // Vehículo grande
                tarifa = parqueadero.getTarifaGrande();
                break;
            default:
                throw new SQLException("Tamaño de vehículo no válido");
        }

        // Calcular el valor del pago basado en la duración en horas
        BigDecimal valorPago = tarifa.multiply(BigDecimal.valueOf(horasEstacionado));
        pagoOp.setValor(valorPago);  // Asignar el valor calculado al objeto PagoOp

        // Asociar el vehículo al pago
        pagoOp.setVehiculo(vehiculo);

        // Registrar el pago en la base de datos
        pagoOpRepository.agregarPagoOp(pagoOp);
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
     * Método que agrega un pago de una operación a la base de datos
     * @param pagoOp: Objeto PagoOp con la información del pago
     * @throws SQLException
     */
    public void pagarOperacion(PagoOp pagoOp) throws SQLException {
        pagoOpRepository.agregarPagoOp(pagoOp);  // Llamar al repositorio para guardar el pago
    }

}
