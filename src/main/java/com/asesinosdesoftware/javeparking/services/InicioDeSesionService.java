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
     * @param cedula: Cédula ingresada por el usuario
     * @param password: Contraseña ingresada por el usuario
     * @throws SQLException
     * @throws InicioDeSesionException
     */
    public void InicioDeSesion(Connection connection, String cedula, String password) throws SQLException, InicioDeSesionException {
        if(Sesion.getTipo() != 'n'){
            throw new InicioDeSesionException("Ya hay una sesión iniciada. Por favor cierre la sesión antes de comenzar otra.");
        }
        else if ((administrador = administradorRepository.buscarAdministrador(cedula,administrador)) != null){
            if(PasswordService.checkPassword(password,administrador.getHash())){
                Sesion.setcedula(cedula);
                Sesion.setTipo('a');
                administrador = new Administrador();
            }
            else{
                throw new InicioDeSesionException("Contraseña incorrecta");
            }
        }
        else if((cliente = this.clienteRepository.buscarCliente(cedula, cliente)) != null){
            if(PasswordService.checkPassword(password, cliente.getHash())){
                Sesion.setcedula(cedula);
                Sesion.setTipo('c');
                cliente = new Cliente();
            }
            else
                throw new InicioDeSesionException("Contraseña incorrecta");
        }
        else if((empleado = empleadoRepository.buscarEmpleado(cedula,empleado)) != null){
            if(PasswordService.checkPassword(password, empleado.getHash())){
                Sesion.setcedula(cedula);
                Sesion.setTipo('e');
                empleado = new Empleado();
            }
            else
                throw new InicioDeSesionException("Contraaseña incorrecta");
        }
        else
            throw new InicioDeSesionException("No existe un usuario con ese documento");

    }

    public void CerrarSesion(){
        Sesion.setTipo('n');
    }
}
