package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import java.sql.*;

public class SuscripcionRepository {

    // Método para agregar una suscripción
    public static void agregarSuscripcion(Connection connection, Suscripcion suscripcion) throws SQLException {
        String sql = "INSERT INTO Suscripcion (clienteID, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, suscripcion.getCliente().getId());
        ps.setDate(2, java.sql.Date.valueOf(suscripcion.getFechaInicio())); // Usar LocalDate para obtener la fecha
        ps.setDate(3, java.sql.Date.valueOf(suscripcion.getFechaFin())); // Usar LocalDate para obtener la fecha de fin
        ps.setString(4, suscripcion.getEstado()); // Usa el estado de la suscripción

        ps.executeUpdate();

        // Obtener el ID de la suscripción generada
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            suscripcion.setId(rs.getInt(1)); // Establece el ID de la suscripción
        }
    }

    // Método para registrar el pago de una mensualidad
    public static void pagarMensualidad(Connection connection, int suscripcionId, float valor) throws SQLException {
        String sql = "INSERT INTO Pago (suscripcion_id, valor, fecha_pago) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, suscripcionId);
        ps.setFloat(2, valor);
        ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
        ps.executeUpdate();
    }
}
