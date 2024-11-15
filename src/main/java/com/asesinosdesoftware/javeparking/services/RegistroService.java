package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;

public class RegistroService {
    ClienteRepository clienteRepository = new ClienteRepository();

    /**
     * Metodo que permite le registro de nuevos clientes
     * @param nombre
     * @param apellido
     * @param cedula
     * @param contrasena
     * @param IdUni
     * @param cliente
     * @throws SQLException
     * @throws RepositoryException
     * @throws ServiceException
     */
    public void registro(String nombre,String apellido,String cedula,String contrasena,String IdUni, Cliente cliente) throws SQLException, RepositoryException, ServiceException {

        if (clienteRepository.buscarCliente(cedula,cliente)!=null){
            throw new ServiceException("El cliente ya existe");
        }

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCedula(cedula);
        cliente.setHash(PasswordService.hashPassword(contrasena));
        cliente.setUniversidad(IdUni.charAt(0));

        clienteRepository.agregarCliente(cliente);
    }
}
