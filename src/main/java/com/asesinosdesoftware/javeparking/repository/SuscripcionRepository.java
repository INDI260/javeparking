package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;

import java.sql.*;

public class SuscripcionRepository {

    /**
     * Método para registrar una suscripción
     * @param connection: Conexión a la base de datos
     * @param suscripcion: Objeto Suscripcion que contiene los detalles de la suscripción a registrar
     * @throws SQLException
     */
    public void agregarSuscripcion(Connection connection, Suscripcion suscripcion) throws SQLException {
        // Primero obtenemos el vehiculoID basado en la placa del vehículo
        int vehiculoID = obtenerVehiculoIDPorPlaca(connection, suscripcion.getVehiculo().getPlaca());

        // Modificamos la consulta para incluir el vehiculoID
        String sql = "INSERT INTO Suscripcion (clienteID, vehiculoID, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, suscripcion.getCliente().getId()); // clienteID
        ps.setInt(2, vehiculoID); // vehiculoID obtenido de la placa
        ps.setObject(3, suscripcion.getFechaInicio()); // fecha_inicio
        ps.setObject(4, suscripcion.getFechaFin()); // fecha_fin
        ps.setString(5, suscripcion.getEstado()); // estado

        ps.executeUpdate();

        // Obtener el ID de la suscripción generada
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            suscripcion.setId(rs.getInt(1)); // Establece el ID de la suscripción
        }
    }

    /**
     * Método para obtener el vehiculoID basado en la placa
     * @param connection: Conexión a la base de datos
     * @param placa: Placa del vehículo
     * @return El ID del vehículo asociado a la placa
     * @throws SQLException
     */
    public int obtenerVehiculoIDPorPlaca(Connection connection, String placa) throws SQLException {
        String sql = "SELECT id FROM Vehiculo WHERE placa = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, placa);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("No se encontró el vehículo con la placa: " + placa);
        }
    }

    /**
     * Método que obtiene una suscripción por la placa del vehículo
     * @param connection: Conexión a la base de datos
     * @param placa: Placa del vehículo para buscar la suscripción
     * @return Retorna un objeto Suscripcion si se encuentra, de lo contrario retorna null
     * @throws SQLException
     */
    public Suscripcion obtenerSuscripcionPorPlaca(Connection connection, String placa) throws SQLException {
        String sql = "SELECT s.id, s.clienteID, s.vehiculoID, s.fecha_inicio, s.fecha_fin, s.estado, " +
                "c.nombre AS cliente_nombre, c.apellido AS cliente_apellido, " +
                "v.placa AS vehiculo_placa, v.tamano AS vehiculo_tamano " + // Añadir tamaño del vehículo
                "FROM Suscripcion s " +
                "JOIN Cliente c ON s.clienteID = c.id " +
                "JOIN Vehiculo v ON s.vehiculoID = v.id " +
                "WHERE v.placa = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, placa); // Establecer la placa como parámetro en la consulta

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Crear una nueva instancia de Suscripcion y establecer sus atributos
            Suscripcion suscripcion = new Suscripcion();
            suscripcion.setId(rs.getInt("id"));

            // Establecer cliente
            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("clienteID"));
            cliente.setNombre(rs.getString("cliente_nombre"));
            cliente.setApellido(rs.getString("cliente_apellido"));
            suscripcion.setCliente(cliente);

            // Establecer vehículo
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setId(rs.getInt("vehiculoID"));
            vehiculo.setPlaca(rs.getString("vehiculo_placa"));
            String tamanoStr = rs.getString("vehiculo_tamano");
            if (tamanoStr != null && tamanoStr.length() > 0) {
                vehiculo.setTamano(tamanoStr.charAt(0)); // Establecer el tamaño como char
            } else {
                vehiculo.setTamano('\0'); // O manejar el caso donde el tamaño no esté definido
            }
            suscripcion.setVehiculo(vehiculo);

            // Establecer fechas y estado
            suscripcion.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
            suscripcion.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
            suscripcion.setEstado(rs.getString("estado"));

            return suscripcion; // Retornar la suscripción obtenida
        }
        return null; // Retornar null si no se encuentra la suscripción
    }

}
