package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.VehiculoRepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculoRepository {

    /**
     * Método que busca un vehiculo en la base de datos a partir de su placa
     * @param connection: Conexión a la base de datos
     * @param placa: Dato a pardir del cual se hace la busqueda
     * @return Si se encuentra retorna un objero tipo Vehiculo con los parametros encontrados en la base de datos o de lo contrario retorna null
     * @throws SQLException
     */
    public Vehiculo buscarVehiculo(Connection connection, String placa) throws SQLException {

        String sql = "SELECT * FROM vehiculos WHERE placa = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if(rs.getString("placa").equals(placa)) {
                return new Vehiculo(rs.getInt("id"), rs.getString("placa"), rs.getString("tamano").charAt(0), rs.getString("tipo").charAt(0));
            }
        }
        return null;
    }

    /**
     * Método que agrega un vehiculo a la base de datos a partir de un objeto tipo vehiculo
     * @param connection: Conexión a la base de datos
     * @param vehiculo: Objeto tipo Vehiculo a partir del cual se crea la ila en la base de datos
     * @throws SQLException
     * @throws VehiculoRepositoryException
     */
    public void agregarVehiculo(Connection connection, Vehiculo vehiculo) throws SQLException, VehiculoRepositoryException {

        if(buscarVehiculo(connection, vehiculo.getPlaca()) != null) {
            String sql = "INSERT INTO `javeparking`.`vehiculo` (`placa`, `tamano`, `tipo`) VALUES ( ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, Character.toString(vehiculo.getTamano()));
            ps.setString(3,Character.toString(vehiculo.getTipo()));
            ps.executeUpdate();
        }
        else
            throw new VehiculoRepositoryException("el vehiculo ya existe");
    }
}
