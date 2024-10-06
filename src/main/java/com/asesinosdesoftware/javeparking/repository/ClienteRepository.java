package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.exceptions.ClienteRepositoryException;

import java.sql.*;

public class ClienteRepository {

    /**
     * Método que busca un cliente en la base de datos a partir de su cedula
     * @param connection: Conexión a la base de datos
     * @param cedula: Dato a partir del cual se hace la busqueda
     * @return Si se encuentra retorna un objero tipo cliente con los parametros encontrados en la base de datos o de lo contrario retorna null
     * @throws SQLException
     */
    public Cliente buscarCliente(Connection connection, String cedula, Cliente cliente) throws SQLException {

        String sql = "SELECT * FROM cliente WHERE cedula = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cedula);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getString("cedula").equals(cedula)) {
                cliente.setId(resultSet.getInt("id"));
                cliente.setCedula(resultSet.getString("cedula"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                cliente.setUniversidad(resultSet.getString("universidad").charAt(0));
                cliente.setHash(resultSet.getString("hash"));
                return cliente;
            }
        }
        return null;
    }

    /**
     * Método que agrega un cliente a la base de datos a partir de un objeto tipo cliente
     * @param connection: Conexión a la base de datos
     * @param cliente: Objeto tipo cliente a partir del cual se crea la fila en la base de datos
     * @throws SQLException
     * @throws ClienteRepositoryException
     */
    public void agregarCliente(Connection connection, Cliente cliente) throws SQLException, ClienteRepositoryException {

        if(buscarCliente(connection, cliente.getCedula(), new Cliente()) == null) {
            String sql = "INSERT INTO `javeparking`.`cliente` (`cedula`, `nombre`, `apellido`, `universidad`, `hash`) VALUES ( ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, Character.toString(cliente.getUniversidad()));
            ps.setString(5, cliente.getHash());
            ps.executeUpdate();
        }
        else {
            throw new ClienteRepositoryException("El cliente ya existe");
        }
    }
}
