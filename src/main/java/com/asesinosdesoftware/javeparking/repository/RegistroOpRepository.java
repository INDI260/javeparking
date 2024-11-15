package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.RegistroOp;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;

public class RegistroOpRepository {

    private VehiculoRepository vehiculoRepository = new VehiculoRepository();
    private IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    /**
     * Método que agrega un registro a la base de datos
     * @param registroOp:Registro que se va a agregar
     * @throws SQLException
     * @throws RepositoryException
     */
    public void agregarRegistroOp(RegistroOp registroOp) throws SQLException, RepositoryException {
        // Insertar el registro de operación en la tabla `registroop`
        String sql = "INSERT INTO registroop (vehiculoid, hora_entrada,puestoid) VALUES (?, ?,?)";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, registroOp.getVehiculo().getId());
        ps.setObject(2, registroOp.getHoraEntrada());  // Hora de entrada actual
        ps.setInt(3,registroOp.getPuesto().getId());

        ps.executeUpdate();
    }
}





