package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.*;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.ParqueaderoRepository;
import com.asesinosdesoftware.javeparking.repository.SuscripcionRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import javafx.scene.control.DatePicker;

import java.sql.SQLException;
import java.time.LocalDateTime;


public class SuscripcionService {

    ClienteRepository clienteRepository = new ClienteRepository();
    VehiculoRepository vehiculoRepository = new VehiculoRepository();
    SuscripcionRepository suscripcionRepository = new SuscripcionRepository();
    ParqueaderoRepository parqueaderoRepository = new ParqueaderoRepository();

    /**
     * Metodo que permite al cliente crear una suscripcion por dias aosiciada a su Usuario
     * @param fechaInicioSuscripcion
     * @param fechaFinSuscripcion
     * @param placa
     * @param suscripcion
     * @throws SQLException
     * @throws ServiceException
     */

    public void agregarSuscripcion(LocalDateTime fechaInicioSuscripcion,LocalDateTime fechaFinSuscripcion, String placa,int idparq,Suscripcion suscripcion) throws SQLException, ServiceException {

            // Crear objeto Cliente
            Cliente cliente = new Cliente();
            Vehiculo vehiculo = new Vehiculo();
            Parqueadero parqueadero = new Parqueadero();
            vehiculoRepository.buscarVehiculo(placa,vehiculo);

            clienteRepository.buscarCliente(Sesion.getcedula(),cliente);


            // Crear objeto Suscripcion

            suscripcion.setCliente(cliente);
            suscripcion.setFechaInicio(fechaInicioSuscripcion);
            suscripcion.setFechaFin(fechaFinSuscripcion);
            suscripcion.setVehiculo(vehiculo);
            if(parqueaderoRepository.buscarParqueaderoPorId(idparq,parqueadero)==null){
                throw new ServiceException ("El parqueadero no existe");
            }
            suscripcion.setIdparq(idparq);
            if(suscripcion.getVehiculo() == null)
                throw new ServiceException ("El vehiculo no está registrado");

            // Calcular el estado de la suscripción
            LocalDateTime fechaActual = LocalDateTime.now();
            String estadoSuscripcion;
            if (fechaActual.isAfter(fechaFinSuscripcion) || fechaActual.isBefore(fechaInicioSuscripcion)) {
                estadoSuscripcion = "Inactiva"; // La suscripción es inactiva
            } else {
                estadoSuscripcion = "Activa"; // La suscripción es activa
            }

            if (fechaFinSuscripcion.isBefore(fechaInicioSuscripcion)) {
                throw new ServiceException("Fecha de Fin menor a la de inicio.");
            }
            // Establecer el estado de la suscripción
            suscripcion.setEstado(estadoSuscripcion);

            // Agregar suscripción a la base de datos
            suscripcionRepository.agregarSuscripcion(suscripcion);


    }
}
