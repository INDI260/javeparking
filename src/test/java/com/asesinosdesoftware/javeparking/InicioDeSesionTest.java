package com.asesinosdesoftware.javeparking;

import com.asesinosdesoftware.javeparking.entities.Sesion;
import com.asesinosdesoftware.javeparking.exceptions.InicioDeSesionException;
import com.asesinosdesoftware.javeparking.init.JDBCInitializer;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.AdministradorRepository;
import com.asesinosdesoftware.javeparking.repository.ClienteRepository;
import com.asesinosdesoftware.javeparking.repository.EmpleadoRepository;
import com.asesinosdesoftware.javeparking.services.InicioDeSesionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;

public class InicioDeSesionTest {

    InicioDeSesionService inicioDeSesionService;
    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    @BeforeEach

    void setUp() {
        try {
            JDBCInitializer jdbcInitializer = new JDBCInitializer(dbConnectionManager, new AdministradorRepository(), new ClienteRepository(), new EmpleadoRepository());
            inicioDeSesionService = new InicioDeSesionService();
            jdbcInitializer.inicializarTablas();
            inicioDeSesionService.CerrarSesion();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void cerrarSesion(){
        inicioDeSesionService.CerrarSesion();
        assertEquals('n', Sesion.getTipo());
    }

    @Test
    void inicioDeSesionAdmin() {
        try {
            inicioDeSesionService.InicioDeSesion( "10", "1234");
            assertEquals('a', Sesion.getTipo());
        } catch (SQLException | InicioDeSesionException e) {
            fail(e.getMessage());
        }

    }

    @Test
    void inicioDeSesionEmpleado() {
        try {
            inicioDeSesionService.InicioDeSesion("20", "1234");
            assertEquals('e', Sesion.getTipo());
        } catch (SQLException | InicioDeSesionException e) {
            fail(e.getMessage());
        }

    }

    @Test
    void inicioDeSesionCliente() {
        try {
            inicioDeSesionService.InicioDeSesion("30", "1234");
            assertEquals('c', Sesion.getTipo());
        } catch (SQLException | InicioDeSesionException e) {
            fail(e.getMessage());
        }

    }

    @Test
    void inicioDeSesionConSesionYaAbierta() {
        try{
            inicioDeSesionService.InicioDeSesion("10", "1234");
            inicioDeSesionService.InicioDeSesion("20", "1234");
            fail();
        }
        catch (SQLException | InicioDeSesionException e) {

        }
    }

    @Test
    void inicioDeSesionContraseniaIncorrecta() {
        try{
            inicioDeSesionService.InicioDeSesion("10", "2345");
            fail();
        }
        catch (SQLException | InicioDeSesionException e) {

        }
    }

    @Test
    void inicioDeSesionUsuarioNoExistente() {
        try{
            inicioDeSesionService.InicioDeSesion("00", "1234");
            fail();
        }
        catch (SQLException | InicioDeSesionException e) {

        }
    }


}
