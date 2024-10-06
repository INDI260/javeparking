package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Administrador;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Empleado;
import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.exceptions.InicioDeSesionException;
import com.asesinosdesoftware.javeparking.repository.AdministradorRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.EmpleadoRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class InicioDeSesionService {

    private AdministradorRepository administradorRepository = new AdministradorRepository();
    private ClienteRepository clienteRepository = new ClienteRepository();
    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private Administrador administrador = new Administrador();
    private Cliente cliente = new Cliente();
    private Empleado empleado = new Empleado();

    /**
     * Método que permite iniciar sesión a partir de una cedula y una contraseña, determina si el usuario es un cliente, un empleado o un administrador y actualiza la sesión de accuerdo con ello
     * @param connection: Conexión a la base de datos
     * @param cedula: Cedula ingresada por el usuario
     * @param password: Contraseña ingresada por el usuario
     * @throws SQLException
     * @throws InicioDeSesionException
     */
    public void InicioDeSesion(Connection connection, String cedula, String password) throws SQLException, InicioDeSesionException {
        //Por ahora solo está implementado para clientes
        if((cliente = this.clienteRepository.buscarCliente(connection, cedula, cliente)) != null){
            if(PasswordService.checkPassword(password, cliente.getHash())){
                Sesion.setTipo('c');
                cliente = new Cliente();
            }
            else
                throw new InicioDeSesionException("Contraseña incorrecta");
        }
        else
            throw new InicioDeSesionException("No existe un usuario con ese documento");

    }
}
