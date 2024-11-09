package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaRepository {

    VehiculoRepository vehiculoRepository = new VehiculoRepository();
    PuestoRepository puestoRepository = new PuestoRepository();
    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    /**

     * Método que agrega una reserva a la base de datos de acuerdo a los parámetros dados
     * @param connection: Conexión con la base de datos
     * @param reserva: Objeto tipo reserva con los parámetros deseados
     * @throws SQLException
     */
    public void agregarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO `javeparking`.`reserva` (`horaEntrada`, `horaSalida`, `vehiculoID`, `puestoID`) VALUES ( ?, ?, ?, ?)";

        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setObject(1,reserva.getHoraEntrada());
        ps.setObject(2,reserva.getHoraSalida());
        ps.setInt(3, reserva.getVehiculo().getId());
        ps.setInt(4, reserva.getPuesto().getId());
        ps.executeUpdate();
    }

    /**

     * Método que busca una reserva en la base de datos a partir del id de su vehiculo
     * @param reserva: Objeto tipo reserva con los parámetros a buscar
     * @return Un objeto tipo reserva si se encuentra o retorna null si no se encuentra
     * @throws SQLException
     */
    public Reserva buscarReservaVehiculo(Reserva reserva) throws SQLException {
        String sql = "SELECT * FROM `javeparking`.`reserva` WHERE vehiculoID = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1,reserva.getVehiculo().getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getInt("vehiculoID") == reserva.getVehiculo().getId()) {
                reserva.setId(rs.getInt("id"));
                reserva.setVehiculo(vehiculoRepository.buscarVehiculo(rs.getInt("vehiculoID"), reserva.getVehiculo()));
                reserva.setHoraEntrada((LocalDateTime) rs.getObject("horaEntrada"));
                reserva.setHoraSalida((LocalDateTime) rs.getObject("horaSalida"));
                reserva.setPuesto(puestoRepository.buscarPuesto(rs.getInt("puestoID"), reserva.getPuesto()));
                return reserva;
            }
        }
        return null;
    }

    /**
     * Método que busca una reserva en la base de datos a partir de su iid
     * @param reservaId: Dato a partir del cual se va a buscar.
     * @param reserva: Objeto tipo reserva con los parámetros deseados.
     * @return Un objeto tipo reserva con los datos hallados o null si no se encuentra ninguna.
     * @throws SQLException
     */
    public Reserva buscarReservaPorId(int reservaId, Reserva reserva) throws SQLException {
        String sql = "SELECT * FROM `javeparking`.`reserva` WHERE `id` = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1, reservaId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            reserva.setId(rs.getInt("id"));

            // Inicializa el objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            reserva.setVehiculo(vehiculoRepository.buscarVehiculo(rs.getInt("vehiculoID"), vehiculo));

            reserva.setHoraEntrada((LocalDateTime) rs.getObject("horaEntrada"));
            reserva.setHoraSalida((LocalDateTime) rs.getObject("horaSalida"));

            // Inicializa el objeto Puesto
            Puesto puesto = new Puesto();
            reserva.setPuesto(puestoRepository.buscarPuesto(rs.getInt("puestoID"), puesto));

            return reserva;
        }

        return null; // Si no se encuentra la reserva
    }

    /**
     * Método que actualiza una reserva en la base de datos
     * @param connection: Conexión a la base de datos
     * @param reserva: Objeto tipo reserva con los nuevos parámetros
     * @throws SQLException
     */
    public void actualizarReserva(Connection connection, Reserva reserva) throws SQLException {
        String sql = "UPDATE `javeparking`.`reserva` SET `horaEntrada` = ?, `horaSalida` = ?, `vehiculoID` = ?, `puestoID` = ? WHERE `id` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, reserva.getHoraEntrada());
        ps.setObject(2, reserva.getHoraSalida());
        ps.setInt(3, reserva.getVehiculo().getId());
        ps.setInt(4, reserva.getPuesto().getId());
        ps.setInt(5, reserva.getId());
        ps.executeUpdate();
    }

    /**
     * Método que elimina una reserva de la base de datos
     * @param connection: Conexión a la base de datos
     * @param reserva: Objeto tipo reserva a eliminar
     * @throws SQLException
     */
    public void eliminarReserva(Connection connection, Reserva reserva) throws SQLException {
        String sql = "DELETE FROM `javeparking`.`reserva` WHERE `id` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, reserva.getId());
        ps.executeUpdate();
    }

    /**
     * Método que obtiene todas las reservas de la base de datos
     * @param connection: Conexión a la base de datos
     * @return Lista de objetos Reserva
     * @throws SQLException
     */
    public List<Reserva> obtenerTodasReservas(Connection connection) throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM `javeparking`.`reserva`";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Reserva reserva = new Reserva();
            reserva.setId(rs.getInt("id"));
            reserva.setHoraEntrada((LocalDateTime) rs.getObject("horaEntrada"));
            reserva.setHoraSalida((LocalDateTime) rs.getObject("horaSalida"));

            // Inicializa el objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            reserva.setVehiculo(vehiculoRepository.buscarVehiculo(connection, rs.getInt("vehiculoID"), vehiculo));

            // Inicializa el objeto Puesto
            Puesto puesto = new Puesto();
            reserva.setPuesto(puestoRepository.buscarPuesto(rs.getInt("puestoID"), connection, puesto));
            reservas.add(reserva);
        }

        return reservas;
    }
}

