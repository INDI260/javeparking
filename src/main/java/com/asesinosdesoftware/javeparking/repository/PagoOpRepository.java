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
     * Método para buscar un vehículo por placa, obtener su puesto y la tarifa del parqueadero.
     * @param placa: Placa del vehículo.
     * @return Vehiculo: Vehículo con los detalles del puesto y la tarifa, o null si no se encuentra.
     * @throws SQLException: En caso de error en la consulta.
     */
    public Vehiculo buscarVehiculoPorPlacaYParqueadero(String placa) throws SQLException {
        String sql = "SELECT v.id AS vehiculo_id, v.placa, v.tamano, v.tipo, v.clienteid, " +
                "p.id AS puesto_id, p.tamano AS puesto_tamano, p.disponibilidad, p.parqueaderoID, " +
                "pa.id AS parqueadero_id, pa.tarifa_pequeno, pa.tarifa_mediano, pa.tarifa_grande " +
                "FROM vehiculo v " +
                "JOIN registro_op r ON v.placa = r.placa " +
                "JOIN puesto p ON r.puesto_id = p.id " +
                "JOIN parqueadero pa ON p.parqueaderoID = pa.id " +
                "WHERE v.placa = ?";

        try (PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, placa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Construir el vehículo
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setId(rs.getInt("vehiculo_id"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setTamano(rs.getString("tamano").charAt(0));
                vehiculo.setTipo(rs.getString("tipo").charAt(0));
                vehiculo.setClienteid(rs.getInt("clienteid"));

                // Construir el puesto
                Puesto puesto = new Puesto();
                puesto.setId(rs.getInt("puesto_id"));
                puesto.setTamano(rs.getString("puesto_tamano").charAt(0));
                puesto.setDisponibilidad(rs.getBoolean("disponibilidad"));
                puesto.setParqueaderoID(rs.getInt("parqueaderoID"));

                // Construir el parqueadero
                Parqueadero parqueadero = new Parqueadero();
                parqueadero.setId(rs.getInt("parqueadero_id"));
                parqueadero.setTarifaPequeno(rs.getBigDecimal("tarifa_pequeno"));
                parqueadero.setTarifaMediano(rs.getBigDecimal("tarifa_mediano"));
                parqueadero.setTarifaGrande(rs.getBigDecimal("tarifa_grande"));

                parqueadero.setPuestos(List.of(puesto));

                // Asignar el parqueadero al vehículo
                vehiculo.setParqueadero(parqueadero);

                return vehiculo;
            } else {
                return null;
            }
        }
    }

    /**
     * Método para registrar un pago de un vehículo en el sistema.
     * @param pago: Objeto PagoOp que contiene los detalles del pago.
     * @throws SQLException: En caso de error en la consulta.
     */
    public void registrarPago(PagoOp pago) throws SQLException {
        String sql = "INSERT INTO pago_op (vehiculo_placa, valor, fecha, metodo_pago) VALUES (?, ?, ?, ?)";

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




