package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.PagoOp;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagoOpRepository {

    IDBConnectionManager dbconnectionManager = new H2DBConnectionManager();

    /**
     * Método para registrar un pago de operación
     * @param pagoOp: Objeto PagoOp que contiene los detalles del pago a registrar
     * @throws SQLException
     */
    public void agregarPagoOp(PagoOp pagoOp) throws SQLException {
        String sql = "INSERT INTO pagoop (vehiculo_placa, valor, fecha, metodoPago) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = dbconnectionManager.getConnection().prepareStatement(sql)) {
            // Si 'pagoOp' tiene un atributo 'vehiculo' de tipo 'Vehiculo'
            ps.setString(1, pagoOp.getVehiculo().getPlaca());  // Acceder a la placa del vehículo
            ps.setBigDecimal(2, pagoOp.getValor());              // Establecer el valor del pago
            ps.setObject(3, pagoOp.getFecha());                  // Establecer la fecha del pago
            ps.setString(4, pagoOp.getMetodoPago());             // Establecer el método de pago

            // Ejecutar la consulta para insertar el pago
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar el pago en la base de datos: " + e.getMessage());
            throw e;
        }
    }
}

