package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

public class PagoService {

    Vehiculo vehiculo;
    VehiculoRepository vehiculoRepository;
    Cliente cliente;
    ClienteRepository clienteRepository;
    Puesto puesto;
    PuestoRepository puestoRepository;
    Reserva reserva;
    ReservaRepository reservaRepository;
    Parqueadero parqueadero;
    ParqueaderoRepository parqueaderoRepository;
    PagoRepository pagoRepository = new PagoRepository();
    IDBConnectionManager dbConnectionManager;


    /**
     * Método que calcula la información del pago de una reserva
     * @param placa: Placa del vehículo en la reserva
     * @param idReserva: ID de la Reserva a pagar
     * @return: Un objeto tipo PagoReserva con la información del pago
     * @throws SQLException
     */
    public void calcularPago(String placa, String idReserva, PagoReserva pagoReserva) throws SQLException {
        vehiculoRepository.buscarVehiculo(dbConnectionManager.getConnection(), placa, vehiculo);
        reservaRepository.buscarReservaPorId(dbConnectionManager.getConnection(), Integer.parseInt(idReserva), reserva);
        puestoRepository.buscarPuesto(reserva.getPuesto().getId(), dbConnectionManager.getConnection(), puesto);
        parqueaderoRepository.buscarParqueaderoPorId(dbConnectionManager.getConnection(), puesto.getParqueaderoID(), parqueadero);

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
        pagoRepository.agregarPagoReserva(pagoReserva);
    }


    /**
     * Método constructor por parámetros de PagoService
     * @param vehiculo
     * @param vehiculoRepository
     * @param cliente
     * @param clienteRepository
     * @param puesto
     * @param puestoRepository
     * @param reserva
     * @param reservaRepository
     * @param parqueadero
     * @param parqueaderoRepository
     * @param pagoReserva
     * @param dbConnectionManager
     */
    public PagoService(Vehiculo vehiculo, VehiculoRepository vehiculoRepository, Cliente cliente, ClienteRepository clienteRepository, Puesto puesto, PuestoRepository puestoRepository, Reserva reserva, ReservaRepository reservaRepository, Parqueadero parqueadero, ParqueaderoRepository parqueaderoRepository, PagoReserva pagoReserva, IDBConnectionManager dbConnectionManager) {
        this.vehiculo = vehiculo;
        this.vehiculoRepository = vehiculoRepository;
        this.cliente = cliente;
        this.clienteRepository = clienteRepository;
        this.puesto = puesto;
        this.puestoRepository = puestoRepository;
        this.reserva = reserva;
        this.reservaRepository = reservaRepository;
        this.parqueadero = parqueadero;
        this.parqueaderoRepository = parqueaderoRepository;
        this.dbConnectionManager = dbConnectionManager;
    }

    /*Getters y Setters*/
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public VehiculoRepository getVehiculoRepository() {
        return vehiculoRepository;
    }

    public void setVehiculoRepository(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClienteRepository getClienteRepository() {
        return clienteRepository;
    }

    public void setClienteRepository(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public PuestoRepository getPuestoRepository() {
        return puestoRepository;
    }

    public void setPuestoRepository(PuestoRepository puestoRepository) {
        this.puestoRepository = puestoRepository;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public ReservaRepository getReservaRepository() {
        return reservaRepository;
    }

    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }

    public ParqueaderoRepository getParqueaderoRepository() {
        return parqueaderoRepository;
    }

    public void setParqueaderoRepository(ParqueaderoRepository parqueaderoRepository) {
        this.parqueaderoRepository = parqueaderoRepository;
    }


    public IDBConnectionManager getDbConnectionManager() {
        return dbConnectionManager;
    }

    public void setDbConnectionManager(IDBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }
}
