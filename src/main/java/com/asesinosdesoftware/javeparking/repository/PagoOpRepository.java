package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.PagoOp;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Parqueadero;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class PagoOpRepository {

    private IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();


    /**
     * Método para registrar un pago de un vehículo en el sistema.
     * @param pago: Objeto PagoOp que contiene los detalles del pago.
     * @throws SQLException: En caso de error en la consulta.
     */
    public void registrarPago(PagoOp pago) throws SQLException {
        String sql = "INSERT INTO pagoop (placa, valor, fechapago, metodopago) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pago.getVehiculo().getPlaca());
            ps.setBigDecimal(2, pago.getValor());
            ps.setObject(3, pago.getFecha());
            ps.setString(4, pago.getMetodoPago());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                pago.setId(generatedKeys.getInt(1));  // Obtener y asignar el ID generado al pago
            }
        }
    }
}




