package com.asesinosdesoftware.javeparking.init;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.asesinosdesoftware.javeparking.entities.Administrador;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Empleado;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.MySQLDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.AdministradorRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.EmpleadoRepository;
import com.asesinosdesoftware.javeparking.services.PasswordService;
import org.h2.tools.RunScript;

/**
 * Esta clase se encarga de realizar las operaciones necesarias para la conexión y la incialización de la base de datos.
 */
public class JDBCInitializer {

    private IDBConnectionManager dbConnectionManager;
    private AdministradorRepository administradorRepository;
    private ClienteRepository clienteRepository;
    private EmpleadoRepository empleadoRepository;

    /**
     * Método constructor por parametros de JDBCInitializer
     * @param dbConnectionManager
     * @param administradorRepository
     * @param clienteRepository
     * @param empleadoRepositoy
     */
    public JDBCInitializer(IDBConnectionManager dbConnectionManager, AdministradorRepository administradorRepository, ClienteRepository clienteRepository, EmpleadoRepository empleadoRepositoy) {
        this.dbConnectionManager = dbConnectionManager;
        this.administradorRepository = administradorRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepositoy;
    }


    /**
     * Método que crea las tablas en la base de datos y las incializa con algunos datos para realizar pruebas
     * @throws SQLException
     */
    public void inicializarTablas(){
        try (Connection conn = dbConnectionManager.getConnection();
             InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/javeparking.sql"))) {

            if (reader == null) {
                throw new FileNotFoundException("Archivo javeparking.sql no encontrado en el classpath");
            }

            RunScript.execute(conn, reader);

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Programa independiente que permite inicializar las tablas sin necesidad de correr la aplicación completa
     * @param args
     * @throws SQLException
     * @throws RepositoryException
     */
    public static void main(String[] args) throws SQLException, RepositoryException {
        new JDBCInitializer(new H2DBConnectionManager(), new AdministradorRepository(), new ClienteRepository(), new EmpleadoRepository()).inicializarTablas();

    }

}
