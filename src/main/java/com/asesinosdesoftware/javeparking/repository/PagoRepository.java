package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.PagoReserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagoRepository {

    // Método para registrar un pago de reserva
    public static void agregarPagoReserva(Connection connection, PagoReserva pagoReserva) throws SQLException {
        String sql = "INSERT INTO pagoReserva (reservaID, valor, fechaPago, metodoPago) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pagoReserva.getReserva().getId()); // ID de la reserva
            ps.setDouble(2, pagoReserva.getValor()); // Valor del pago
            ps.setObject(3, pagoReserva.getFechaPago()); // Fecha del pago
            ps.setString(4, pagoReserva.getMetodoPago()); // Método de pago
            ps.executeUpdate();
        }
    }


}

