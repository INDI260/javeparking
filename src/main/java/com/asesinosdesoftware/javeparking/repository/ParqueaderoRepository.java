package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Parqueadero;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ParqueaderoRepository {

    IDBConnectionManager dbconnectionManager = new H2DBConnectionManager();

    /**
     * Método que busca un Parqueadero por su ID
     * @param id: ID del parqueadero a buscar
     * @param parqueadero: Objeto Parqueadero que se actualizará si se encuentra el parqueadero
     * @return Si se encuentra, retorna el objeto Parqueadero actualizado, de lo contrario retorna null
     * @throws SQLException
     */
    public Parqueadero buscarParqueaderoPorId(int id, Parqueadero parqueadero) throws SQLException {
        String sql = "SELECT * FROM parqueadero WHERE `id` = ?";
        PreparedStatement ps = dbconnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        // Si el parqueadero con el ID dado existe
        if (rs.next()) {
            parqueadero.setId(rs.getInt("id"));
            parqueadero.setTarifaPequeno(rs.getBigDecimal("TarifaPequeno"));
            parqueadero.setTarifaMediano(rs.getBigDecimal("TarifaMediano"));
            parqueadero.setTarifaGrande(rs.getBigDecimal("TarifaGrande"));
            parqueadero.setSuscripcionPequeno(rs.getBigDecimal("SuscripcionPequeno"));
            parqueadero.setSuscripcionMediano(rs.getBigDecimal("SuscripcionMediano"));
            parqueadero.setSuscripcionGrande(rs.getBigDecimal("SuscripcionGrande"));
            parqueadero.setDescuentoJaveriano(rs.getBigDecimal("DescuentoJaveriano"));
            return parqueadero;
        }

        // Si no se encuentra el parqueadero
        return null;
    }

}
