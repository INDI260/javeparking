package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Administrador;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorRepository {

    /**
     * Método que busca un Administrador en la base de datos a partir de su cedula
     * @param connection: Conexión a la base de datos
     * @param cedula: Dato a partir del cual se hace la busqueda
     * @return Si se encuentra retorna un objero tipo administrador con los parametros encontrados en la base de datos o de lo contrario retorna null
     * @throws SQLException
     */
    public Administrador buscarAdministrador(Connection connection, String cedula, Administrador administrador) throws SQLException {

        String sql = "select * from administrador where cedula = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cedula);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getString("cedula").equals(cedula)) {
                administrador.setId(resultSet.getInt("id"));
                administrador.setCedula(resultSet.getString("cedula"));
                administrador.setNombre(resultSet.getString("nombre"));
                administrador.setApellido(resultSet.getString("apellido"));
                administrador.setHash(resultSet.getString("hash"));
                return administrador;
            }
        }
        return null;
    }

    /**
     * Método que agrega un administrador a la base de datos a partir de un objeto tipo Administrador
     * @param connection: Conexión a la base de datos
     * @param administrador: Objeto tipo administrador a partir del cual se crea la fila en la base de datos
     * @throws SQLException
     * @throws RepositoryException
     */
    public void agregarAdministrador(Connection connection, Administrador administrador) throws SQLException, RepositoryException {

        if(buscarAdministrador(connection, administrador.getCedula(), new Administrador()) == null) {
            String sql = "INSERT INTO `javeparking`.`administrador` (`cedula`, `nombre`, `apellido`, `hash`) VALUES ( ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, administrador.getCedula());
            ps.setString(2, administrador.getNombre());
            ps.setString(3, administrador.getApellido());
            ps.setString(4, administrador.getHash());
            ps.executeUpdate();
        }
        else {
            throw new RepositoryException("Ese administrador ya existe");
        }
    }
}
