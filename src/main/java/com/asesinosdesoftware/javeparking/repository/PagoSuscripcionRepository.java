package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.PagoSuscripcion;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagoSuscripcionRepository {

    IDBConnectionManager dbConnectionManager = new DBConnectionManager();
    /**
     * Método para registrar un pago de suscripción
     * @param pagoSuscripcion: Objeto PagoSuscripcion que contiene los detalles del pago a registrar
     * @throws SQLException
     */
    public void agregarPagoSuscripcion(PagoSuscripcion pagoSuscripcion) throws SQLException {
        String sql = "INSERT INTO PagoSuscripcion (suscripcionID, valor, fecha_pago, metodo_pago) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, pagoSuscripcion.getSuscripcion().getId());
            ps.setBigDecimal(2, pagoSuscripcion.getValor()); // Asegúrate de que esto sea double
            ps.setObject(3, pagoSuscripcion.getFechaPago()); // Debería ser LocalDateTime
            ps.setString(4, pagoSuscripcion.getMetodoPago());
            ps.executeUpdate();
        }
    }
}
