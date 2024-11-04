package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.ReservaValet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservaValetRepository {

    public void agregarReserva(Connection connection, ReservaValet reserva) throws SQLException {
        String sql = "INSERT INTO ReservaValet (id_cliente, placa_vehiculo, fecha_hora_reserva, metodo_pago, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reserva.getIdCliente());              // Utiliza el ID del cliente
            ps.setString(2, reserva.getPlacaVehiculo());       // Utiliza la placa del vehículo
            ps.setObject(3, reserva.getFechaHoraReserva());    // Fecha y hora de la reserva
            ps.setString(4, reserva.getMetodoPago());          // Método de pago
            ps.setString(5, reserva.getEstado());              // Estado de la reserva
            ps.executeUpdate();
        }
    }
}