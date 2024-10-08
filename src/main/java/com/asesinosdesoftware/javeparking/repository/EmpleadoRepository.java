package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Administrador;
import com.asesinosdesoftware.javeparking.entities.Empleado;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoRepository {

    /**
     * Método que busca un Empleado en la base de datos a partir de su cedula
     * @param connection: Conexión a la base de datos
     * @param cedula: Dato a partir del cual se hace la busqueda
     * @return Si se encuentra retorna un objero tipo Empleado con los parametros encontrados en la base de datos o de lo contrario retorna null
     * @throws SQLException
     */
    public static Empleado buscarEmpleado(Connection connection, String cedula, Empleado empleado) throws SQLException {

        String sql = "select * from empleado where cedula = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cedula);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getString("cedula").equals(cedula)) {
                empleado.setId(resultSet.getInt("id"));
                empleado.setCedula(resultSet.getString("cedula"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setApellido(resultSet.getString("apellido"));
                empleado.setHash(resultSet.getString("hash"));
                return empleado;
            }
        }
        return null;
    }

    /**
     * Método que agrega un empleado a la base de datos a partir de un objeto tipo Empleado
     * @param connection: Conexión a la base de datos
     * @param empleado: Objeto tipo Empleado a partir del cual se crea la fila en la base de datos
     * @throws SQLException
     * @throws RepositoryException
     */
    public static void agregarEmpleado(Connection connection, Empleado empleado) throws SQLException, RepositoryException {

        if(buscarEmpleado(connection, empleado.getCedula(), new Empleado()) == null) {
            String sql = "INSERT INTO `javeparking`.`empleado` (`cedula`, `nombre`, `apellido`, `hash`) VALUES ( ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, empleado.getCedula());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setString(4, empleado.getHash());
            ps.executeUpdate();
        }
        else {
            throw new RepositoryException("Ese empleado ya existe");
        }
    }
}
