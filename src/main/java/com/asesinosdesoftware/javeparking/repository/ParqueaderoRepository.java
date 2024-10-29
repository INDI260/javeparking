package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Parqueadero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ParqueaderoRepository {

    /**
     * Método que busca un Parqueadero por su ID
     * @param connection: Conexión a la base de datos
     * @param id: ID del parqueadero a buscar
     * @param parqueadero: Objeto Parqueadero que se actualizará si se encuentra el parqueadero
     * @return Si se encuentra, retorna el objeto Parqueadero actualizado, de lo contrario retorna null
     * @throws SQLException
     */
    public Parqueadero buscarParqueaderoPorId(Connection connection, int id, Parqueadero parqueadero) throws SQLException {
        String sql = "SELECT * FROM `javeparking`.`parqueadero` WHERE `id` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        // Si el parqueadero con el ID dado existe
        if (rs.next()) {
            parqueadero.setId(rs.getInt("id"));
            parqueadero.setTarifaPequeno(rs.getBigDecimal("TarifaPequeno"));
            parqueadero.setTarifaMediano(rs.getBigDecimal("TarifaMediano"));
            parqueadero.setTarifaGrande(rs.getBigDecimal("TarifaGrande"));
            return parqueadero;
        }

        // Si no se encuentra el parqueadero
        return null;
    }

}
