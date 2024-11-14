package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculoRepository {

    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    /**
     * Método que busca un vehiculo en la base de datos a partir de su placa
     * @param placa: Dato a partir del cual se hace la búsqueda
     * @return Si se encuentra retorna un objeto tipo Vehículo con los parámetros encontrados en la base de datos o de lo contrario retorna null
     * @throws SQLException
     */
    public Vehiculo buscarVehiculo(String placa, Vehiculo vehiculo) throws SQLException{

        String sql = "SELECT * FROM vehiculo WHERE placa = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
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
     * @param id: Dato a partir del cual se hace la búsqueda
     * @return Si se encuentra retorna un objeto tipo Vehículo con los parámetros encontrados en la base de datos o de lo contrario retorna null
     * @throws SQLException
     */
    public Vehiculo buscarVehiculoID(int id, Vehiculo vehiculo) throws SQLException {

        String sql = "SELECT * FROM vehiculo WHERE id = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
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
     * @param vehiculo: Objeto tipo Vehículo a partir del cual se crea la ila en la base de datos
     * @throws SQLException
     * @throws RepositoryException
     */
    public void agregarVehiculo(Vehiculo vehiculo) throws SQLException, RepositoryException {

        if(buscarVehiculo(vehiculo.getPlaca(), new Vehiculo()) == null) {
            String sql = "INSERT INTO vehiculo (`placa`, `tamano`, `tipo`,`clienteid`) VALUES ( ?, ?, ?,?);";
            PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, Character.toString(vehiculo.getTamano()));
            ps.setString(3,Character.toString(vehiculo.getTipo()));
            ps.setInt(4, vehiculo.getClienteid());
            ps.executeUpdate();
        }
        else
            throw new RepositoryException("El vehiculo ya existe");
    }

    /**
     * Método que elimina un vehiculo de la base de datos a partir de su placa.
     * @param vehiculo: Vehiculo que se va a eliminar.
     * @throws SQLException
     * @throws RepositoryException
     */
    public void eliminarVehiculo(Vehiculo vehiculo) throws SQLException, RepositoryException {

        if (vehiculo != null) {
            String sql = "DELETE FROM vehiculo WHERE `placa` = ?;";
            PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
            ps.setString(1, vehiculo.getPlaca());
            ps.executeUpdate();
        } else {
            throw new RepositoryException("El vehículo no existe");
        }
    }

}

