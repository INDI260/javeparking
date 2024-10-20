package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Parqueadero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ParqueaderoRepository {

    public static Parqueadero buscarParqueaderoPorId(Connection connection, int id, Parqueadero parqueadero) throws SQLException {
        String sql = "SELECT * FROM `javeparking`.`parqueadero` WHERE `id` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        // Si el parqueadero con el ID dado existe
        if (rs.next()) {
            parqueadero.setId(rs.getInt("id"));
            parqueadero.setTarifaEstandar(rs.getFloat("TarifaEstandar"));
            return parqueadero;
        }

        // Si no se encuentra el parqueadero
        return null;
    }


}
