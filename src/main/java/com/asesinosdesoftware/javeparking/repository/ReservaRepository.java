package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReservaRepository {

    VehiculoRepository vehiculoRepository;
    PuestoRepository puestoRepository;

    /**
     * Método que agrega una reserva a la base de datos de acuerdo a los parametros dados
     * @param connection: Conexión con la base de datos
     * @param reserva: Objeto tipo reserva con los parametros deseados
     * @throws SQLException
     */
    public void agregarReserva(Connection connection, Reserva reserva) throws SQLException {
        String sql = "INSERT INTO `javeparking`.`reserva` (`horaEntrada`, `horaSalida`, `vehiculoID`, `puestoID`) VALUES ( ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1,reserva.getHoraEntrada());
        ps.setObject(2,reserva.getHoraSalida());
        ps.setInt(3, reserva.getVehiculo().getId());
        ps.setInt(4, reserva.getPuesto().getId());
        ps.executeUpdate();

    }

    /**
     * Método que busca una reserva en la base de datso a partir del id de su vehiculo
     * @param connection: Conexión a la base de datos
     * @param reserva: Objeto tipo reserva con los parametros a buscar
     * @return Un objeto tipo reserva si se encuetnra o retorna null si no se encuentra
     * @throws SQLException
     */
    public Reserva buscarReservaVehiculo(Connection connection, Reserva reserva) throws SQLException {
        String sql = "SELECT * FROM `javeparking`.`reserva` WHERE vehiculoID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,reserva.getVehiculo().getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            if(rs.getInt("vehiculoID") == reserva.getVehiculo().getId()) {
                reserva.setId(rs.getInt("id"));
                reserva.setVehiculo(vehiculoRepository.buscarVehiculo(connection, rs.getInt("vehiculoID"), reserva.getVehiculo()));
                reserva.setHoraEntrada((LocalDateTime) rs.getObject("horaEntrada"));
                reserva.setHoraSalida((LocalDateTime) rs.getObject("horaSalida"));
                reserva.setPuesto(puestoRepository.buscarPuesto(rs.getInt("puestoID"), connection, reserva.getPuesto()));
                return reserva;
            }
        }
        return null;
    }

    public Reserva buscarReservaPorId(Connection connection, int reservaId, Reserva reserva) throws SQLException {
        String sql = "SELECT * FROM `javeparking`.`reserva` WHERE `id` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, reservaId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            reserva.setId(rs.getInt("id"));

            // Inicializa el objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            reserva.setVehiculo(VehiculoRepository.buscarVehiculo(connection, rs.getInt("vehiculoID"), vehiculo));

            reserva.setHoraEntrada((LocalDateTime) rs.getObject("horaEntrada"));
            reserva.setHoraSalida((LocalDateTime) rs.getObject("horaSalida"));

            // Inicializa el objeto Puesto
            Puesto puesto = new Puesto();
            reserva.setPuesto(PuestoRepository.buscarPuesto(rs.getInt("puestoID"), connection, puesto));

            return reserva;
        }

        return null; // Si no se encuentra la reserva
    }

}
