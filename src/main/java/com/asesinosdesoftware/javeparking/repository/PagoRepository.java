package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.PagoReserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagoRepository {

    // Método para registrar un pago de reserva
    public static void agregarPagoReserva(Connection connection, PagoReserva pagoReserva) throws SQLException {
        String sql = "INSERT INTO Pago (reserva_id, valor, fecha_pago) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pagoReserva.getReserva().getId());
            ps.setDouble(2, pagoReserva.getValor());
            ps.setObject(3, pagoReserva.getFechaPago());
            ps.executeUpdate();
        }
    }

}

