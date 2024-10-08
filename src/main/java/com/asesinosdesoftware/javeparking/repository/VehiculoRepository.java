package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;

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
    public static Vehiculo buscarVehiculo(Connection connection, String placa, Vehiculo vehiculo) throws SQLException {

        String sql = "SELECT * FROM vehiculo WHERE placa = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if(rs.getString("placa").equals(placa)) {
                vehiculo.setId(rs.getInt("id"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setTamano(rs.getString("tamano").charAt(0));
                vehiculo.setTipo(rs.getString("tipo").charAt(0));
                vehiculo.setClienteid(rs.getInt("clienteid"));
                return vehiculo;
            }
        }
        return null;
    }

    /**
     * Método que busca un vehiculo en la base de datos a partir de su Id
     * @param connection: Conexión a la base de datos
     * @param id: Dato a pardir del cual se hace la busqueda
     * @return Si se encuentra retorna un objero tipo Vehiculo con los parametros encontrados en la base de datos o de lo contrario retorna null
     * @throws SQLException
     */
    public static Vehiculo buscarVehiculo(Connection connection, int id, Vehiculo vehiculo) throws SQLException {

        String sql = "SELECT * FROM vehiculo WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if(rs.getInt("id") == id) {
                vehiculo.setId(rs.getInt("id"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setTamano(rs.getString("tamano").charAt(0));
                vehiculo.setTipo(rs.getString("tipo").charAt(0));
                vehiculo.setClienteid(rs.getInt("clienteid"));
                return vehiculo;
            }
        }
        return null;
    }

    /**
     * Método que agrega un vehiculo a la base de datos a partir de un objeto tipo vehiculo
     * @param connection: Conexión a la base de datos
     * @param vehiculo: Objeto tipo Vehiculo a partir del cual se crea la ila en la base de datos
     * @throws SQLException
     * @throws RepositoryException
     */
    public static void agregarVehiculo(Connection connection, Vehiculo vehiculo) throws SQLException, RepositoryException {

        if(buscarVehiculo(connection, vehiculo.getPlaca(), new Vehiculo()) == null) {
            String sql = "INSERT INTO `javeparking`.`vehiculo` (`placa`, `tamano`, `tipo`,`clienteid`) VALUES ( ?, ?, ?,?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, Character.toString(vehiculo.getTamano()));
            ps.setString(3,Character.toString(vehiculo.getTipo()));
            ps.setInt(4, vehiculo.getClienteid());
            ps.executeUpdate();
        }
        else
            throw new RepositoryException("El vehiculo ya existe");
    }
}
