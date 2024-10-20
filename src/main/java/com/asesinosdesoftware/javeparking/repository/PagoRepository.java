package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Pago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagoRepository {

    // Método para registrar un pago de reserva
    public static void agregarPagoReserva(Connection connection, Pago pago) throws SQLException {
        String sql = "INSERT INTO Pago (reserva_id, valor, fecha_pago) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pago.getReserva().getId());
            ps.setFloat(2, pago.getValor());
            ps.setDate(3, pago.getFechaPago());
            ps.executeUpdate();
        }
    }

    // Método para registrar un pago de suscripción
    public static void agregarPagoSuscripcion(Connection connection, Pago pago) throws SQLException {
        String sql = "INSERT INTO Pago (suscripcion_id, valor, fecha_pago) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pago.getSuscripcion().getId());
            ps.setFloat(2, pago.getValor());
            ps.setDate(3, pago.getFechaPago());
            ps.executeUpdate();
        }
    }
}

