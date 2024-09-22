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
    public Cliente buscarCliente(Connection connection, String cedula) throws SQLException {

        String sql = "SELECT * FROM cliente WHERE cedula = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cedula);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getString("cedula").equals(cedula)) {
                return new Cliente(resultSet.getInt("id"),resultSet.getString("cedula"),resultSet.getString("nombre"),resultSet.getString("apellido"),resultSet.getString("universidad").charAt(0));
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

        if(buscarCliente(connection, cliente.getCedula()) != null) {
            String sql = "INSERT INTO `javeparking`.`cliente` (`cedula`, `nombre`, `apellido`, `universidad`) VALUES ( ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4,Character.toString(cliente.getUniversidad()));
            ps.executeUpdate();
        }
        else {
            throw new ClienteRepositoryException("El cliente ya existe");
        }
    }
}
