package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.RegistroOp;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;

public class RegistroOpRepository {

    private VehiculoRepository vehiculoRepository = new VehiculoRepository();
    private IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    public void agregarRegistroOp(RegistroOp registroOp) throws SQLException {
        // Primero, obtenemos el vehículo por la placa
        Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPlaca(registroOp.getPlaca(), new Vehiculo());

        // Verificar que el vehículo existe antes de registrar la entrada
        if (vehiculo != null) {
            // Insertar el registro de entrada
            String sql = "INSERT INTO registro_op (placa, tamano, tipo, hora_entrada) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, registroOp.getPlaca());

            // Convertimos tipo y tamaño de String a char al momento de insertar
            ps.setString(2, Character.toString(registroOp.getTamano()));
            ps.setString(3,Character.toString(vehiculo.getTipo()));
            ps.setObject(4, registroOp.getHoraEntrada().toLocalTime());  // Hora de entrada actual

            ps.executeUpdate();
        } else {
            throw new SQLException("El vehículo con la placa " + registroOp.getPlaca() + " no existe.");
        }
    }
}



