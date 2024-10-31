package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.ReservaValet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaValetRepository {

    public void agregarReserva(Connection connection, ReservaValet reservaValet) throws SQLException {
        String sql = "INSERT INTO ReservaValet (clienteID, vehiculoID, fecha_hora_reserva, metodo_pago, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reservaValet.getCliente().getId());
            ps.setInt(2, reservaValet.getVehiculo().getId());
            ps.setObject(3, reservaValet.getFechaHoraReserva());
            ps.setString(4, reservaValet.getMetodoPago());
            ps.setString(5, reservaValet.getEstado());
            ps.executeUpdate();
        }
    }

    public ReservaValet buscarReservaPorId(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM ReservaValet WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Aquí se realiza la creación del objeto ReservaValet usando los datos del ResultSet
                }
            }
        }
        return null;
    }

    public List<ReservaValet> obtenerReservasPorCliente(Connection connection, int clienteId) throws SQLException {
        List<ReservaValet> reservas = new ArrayList<>();
        String sql = "SELECT * FROM ReservaValet WHERE clienteID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Agrega cada reserva al listado de reservas
                }
            }
        }
        return reservas;
    }
}