package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;

import java.sql.SQLException;

public class VehiculoService {

    VehiculoRepository vehiculoRepository = new VehiculoRepository();
    ClienteRepository clienterepository = new ClienteRepository();

    public void registroVehiculo(String IdTamano, String IdTipo, String IdPlaca,Vehiculo vehiculo) throws SQLException, RepositoryException {

        Cliente Dueno = new Cliente();

        vehiculo = new Vehiculo();
        vehiculo.setTamano(IdTamano.charAt(0));
        vehiculo.setTipo(IdTipo.charAt(0));
        vehiculo.setPlaca(IdPlaca);

        clienterepository.buscarCliente(Sesion.getcedula(),Dueno);
        vehiculo.setClienteid(Dueno.getId());
        vehiculoRepository.agregarVehiculo(vehiculo);

    }

    public void EliminarVehiculo(String IDPlaca, Vehiculo vehiculo,Cliente dueno) throws SQLException, ServiceException, RepositoryException {

        clienterepository.buscarCliente(Sesion.getcedula(),dueno);
        vehiculoRepository.buscarVehiculo(IDPlaca,vehiculo);

        if(dueno.getId()==vehiculo.getClienteid()){
            vehiculoRepository.eliminarVehiculo(vehiculo);
        }
        else throw new ServiceException("El vehiculo no te pertenece");

    }

}
