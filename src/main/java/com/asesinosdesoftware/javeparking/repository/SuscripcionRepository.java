package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;

public class SuscripcionRepository {

    VehiculoRepository vehiculoRepository = new VehiculoRepository();
    ClienteRepository clienteRepository = new ClienteRepository();
    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    /**
     * Método para registrar una suscripción
     * @param suscripcion: Objeto Suscripcion que contiene los detalles de la suscripción a registrar
     * @throws SQLException
     */
    public void agregarSuscripcion(Suscripcion suscripcion) throws SQLException {
        // Primero obtenemos el vehiculoID basado en la placa del vehículo
        vehiculoRepository.buscarVehiculo(suscripcion.getVehiculo().getPlaca(), suscripcion.getVehiculo());

        // Modificamos la consulta para incluir el vehiculoID
        String sql = "INSERT INTO Suscripcion (clienteID, vehiculoID, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, suscripcion.getCliente().getId()); // clienteID
        ps.setInt(2, suscripcion.getVehiculo().getId()); // vehiculoID obtenido de la placa
        ps.setObject(3, suscripcion.getFechaInicio()); // fecha_inicio
        ps.setObject(4, suscripcion.getFechaFin()); // fecha_fin
        ps.setString(5, suscripcion.getEstado()); // estado

        ps.executeUpdate();

        // Obtener el ID de la suscripción generada
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            suscripcion.setId(rs.getInt(1)); // Establece el ID de la suscripción
        }
    }

    /**
     * Método que busca una suscripción en la base de datos a partir de la placa del vehículo
     * @param placa: Placa a partir de la cual se busca en la base de datos
     * @param suscripcion: Objeto tipo Suscripcion que contiene los parámetros a buscar
     * @return Un objeto tipo Suscripcion con los detalles encontrados o null si no se encuentra
     * @throws SQLException
     */
    public Suscripcion buscarSuscripcionPorVehiculo(String placa, Suscripcion suscripcion) throws SQLException {
        // Primero buscamos el vehículo por placa
        Vehiculo vehiculo = vehiculoRepository.buscarVehiculo(placa, new Vehiculo());
        if (vehiculo == null) {
            return null; // Si no se encuentra el vehículo, retornamos null
        }

        // Ahora buscamos la suscripción asociada al vehículo
        String sql = "SELECT * FROM suscripcion WHERE vehiculoID = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1, vehiculo.getId());
        ResultSet rs = ps.executeQuery();

        // Verificamos si encontramos algún resultado
        if (rs.next()) {
            // Si encontramos la suscripción, la asignamos
            suscripcion.setId(rs.getInt("id"));
            suscripcion.setVehiculo(vehiculo);
            suscripcion.setCliente(clienteRepository.buscarCliente(rs.getInt("clienteID"), new Cliente()));
            suscripcion.setFechaInicio((LocalDateTime) rs.getObject("fecha_inicio"));
            suscripcion.setFechaFin((LocalDateTime) rs.getObject("fecha_fin"));
            suscripcion.setEstado(rs.getString("estado"));
            return suscripcion;
        }
        return null; // Si no encontramos una suscripción para ese vehículo
    }
}

