package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Suscripcion;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;

import java.sql.*;
import java.time.LocalDateTime;

public class SuscripcionRepository {

    VehiculoRepository vehiculoRepository;
    ClienteRepository clienteRepository;

    /**
     * Método para registrar una suscripción
     * @param connection: Conexión a la base de datos
     * @param suscripcion: Objeto Suscripcion que contiene los detalles de la suscripción a registrar
     * @throws SQLException
     */
    public void agregarSuscripcion(Connection connection, Suscripcion suscripcion) throws SQLException {
        // Primero obtenemos el vehiculoID basado en la placa del vehículo
        vehiculoRepository.buscarVehiculo(connection, suscripcion.getVehiculo().getPlaca(), suscripcion.getVehiculo());

        // Modificamos la consulta para incluir el vehiculoID
        String sql = "INSERT INTO Suscripcion (clienteID, vehiculoID, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
     * Método que busca una reserva en la base de datos a partir del id de su vehiculo
     * @param connection: Conexión a la base de datos
     * @param suscripcion: Objeto tipo suscripcion con los parámetros a buscar
     * @return Un objeto tipo reserva si se encuentra o retorna null si no se encuentra
     * @throws SQLException
     */
    public Reserva buscarReservaVehiculo(Connection connection, Suscripcion suscripcion) throws SQLException {
        String sql = "SELECT * FROM `javeparking`.`suscripcion` WHERE vehiculoID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,suscripcion.getVehiculo().getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            if(rs.getInt("vehiculoID") == suscripcion.getVehiculo().getId()) {
                suscripcion.setId(rs.getInt("id"));
                suscripcion.setVehiculo(vehiculoRepository.buscarVehiculo(connection, rs.getInt("vehiculoID"), suscripcion.getVehiculo()));
                suscripcion.setCliente(clienteRepository.buscarCliente(connection, rs.getInt("clienteID"), suscripcion.getCliente()));
                suscripcion.setFechaInicio((LocalDateTime) rs.getObject("fecha_inicio"));
                suscripcion.setPuesto(puestoRepository.buscarPuesto(rs.getInt("puestoID"), connection, reserva.getPuesto()));
                return reserva;
            }
        }
        return null;
    }

}
