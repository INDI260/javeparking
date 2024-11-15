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

    /**
     * Método que agrega un parqueadero a la base de datos
     * @param parqueadero: Parqueadero que se va a agregar
     * @throws SQLException
     */
    public void agregarParqueadero(Parqueadero parqueadero) throws SQLException {
        String sql = "INSERT INTO parqueadero (TarifaPequeno, TarifaMediano, TarifaGrande, "
                + "SuscripcionPequeno, SuscripcionMediano, SuscripcionGrande, DescuentoJaveriano) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = dbconnectionManager.getConnection().prepareStatement(sql);
        ps.setBigDecimal(1, parqueadero.getTarifaPequeno());
        ps.setBigDecimal(2, parqueadero.getTarifaMediano());
        ps.setBigDecimal(3, parqueadero.getTarifaGrande());
        ps.setBigDecimal(4, parqueadero.getSuscripcionPequeno());
        ps.setBigDecimal(5, parqueadero.getSuscripcionMediano());
        ps.setBigDecimal(6, parqueadero.getSuscripcionGrande());
        ps.setBigDecimal(7, parqueadero.getDescuentoJaveriano());

        // Ejecutar la inserción
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Parqueadero agregado exitosamente.");
        } else {
            System.out.println("Error al agregar el parqueadero.");
        }

        // Cerrar el PreparedStatement
        ps.close();
    }

}
