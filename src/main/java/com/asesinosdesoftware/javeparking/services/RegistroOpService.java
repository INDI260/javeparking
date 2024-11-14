package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.RegistroOp;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.RegistroOpRepository;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class RegistroOpService {

   RegistroOpRepository registroOpRepository = new RegistroOpRepository();
    PuestoRepository puestoRepository = new PuestoRepository();
   VehiculoRepository vehiculoRepository = new VehiculoRepository();


    /**
     * Método para registrar la entrada de un vehículo en el sistema.
     * @param placa: Placa del vehículo.
     * @param tipo: Tipo de vehículo.
     * @param tamano: Tamaño del vehículo.
     * @param horaEntrada: Hora en la que el vehículo ingresa.
     * @throws SQLException: En caso de que ocurra un error al acceder a la base de datos.
     */
    public void registrarEntrada(String placa, String tipo, String tamano, LocalDateTime horaEntrada,RegistroOp registroOp) throws SQLException, RepositoryException {
        // Crear un objeto Vehiculo y asignarle los valores
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(placa);
        vehiculo.setTipo(tipo.charAt(0));
        vehiculo.setTamano(tamano.charAt(0));
        vehiculo.setClienteid(0);
        Puesto puesto = new Puesto();
        vehiculoRepository.agregarVehiculo(vehiculo);
        vehiculoRepository.buscarVehiculo(placa,vehiculo);
        puestoRepository.buscarPuesto(tamano,false,puesto);

        // Crear un objeto RegistroOp y asignarle el vehículo y la hora de entrada
        registroOp.setVehiculo(vehiculo);
        registroOp.setHoraEntrada(horaEntrada);
        registroOp.setPuesto(puesto);

        // Llamar al repositorio para agregar el registro de la entrada
        registroOpRepository.agregarRegistroOp(registroOp);
    }
}










