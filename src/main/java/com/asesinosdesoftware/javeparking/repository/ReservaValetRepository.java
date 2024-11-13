package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.ReservaValet;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.entities.Cliente;
import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaValetRepository {

    VehiculoRepository vehiculoRepository = new VehiculoRepository();
    ClienteRepository clienteRepository = new ClienteRepository();
    PuestoRepository puestoRepository = new PuestoRepository();
    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    /**
     * Método que agrega una reserva de valet parking a la base de datos.
     * @param reservaValet: Objeto tipo ReservaValet con los parámetros deseados.
     * @throws SQLException
     */
    public void agregarReservaValet(ReservaValet reservaValet) throws SQLException {
        String sql = "INSERT INTO reserva_valet (`horaEntrada`, `horaSalida`, `vehiculoID`, `clienteID`, `puestoID`) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setObject(1, reservaValet.getHoraEntrada());
        ps.setObject(2, reservaValet.getHoraSalida());
        ps.setInt(3, reservaValet.getVehiculo().getId());
        ps.setInt(4, reservaValet.getCliente().getId());
        ps.setInt(5, reservaValet.getPuesto().getId());
        ps.executeUpdate();
    }

    /**
     * Método que busca una reserva de valet parking en la base de datos por el id.
     * @param reservaValetId: ID de la reserva de valet parking.
     * @return Un objeto tipo ReservaValet con los datos encontrados o null si no se encuentra.
     * @throws SQLException
     */
    public ReservaValet buscarReservaValetPorId(int reservaValetId) throws SQLException {
        String sql = "SELECT * FROM reserva_valet WHERE id = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1, reservaValetId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ReservaValet reservaValet = new ReservaValet();
            reservaValet.setId(rs.getInt("id"));
            reservaValet.setHoraEntrada((LocalDateTime) rs.getObject("horaEntrada"));
            reservaValet.setHoraSalida((LocalDateTime) rs.getObject("horaSalida"));

            // Inicializa el objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            reservaValet.setVehiculo(vehiculoRepository.buscarVehiculo(rs.getInt("vehiculoID"), vehiculo));

            // Inicializa el objeto Cliente
            Cliente cliente = new Cliente();
            reservaValet.setCliente(clienteRepository.buscarCliente(rs.getInt("clienteID"), cliente));

            // Inicializa el objeto Puesto
            Puesto puesto = new Puesto();
            reservaValet.setPuesto(puestoRepository.buscarPuesto(rs.getInt("puestoID"), puesto));

            return reservaValet;
        }

        return null; // Si no se encuentra la reserva
    }

    /**
     * Método que actualiza una reserva de valet parking en la base de datos.
     * @param reservaValet: Objeto tipo ReservaValet con los nuevos parámetros.
     * @throws SQLException
     */
    public void actualizarReservaValet(ReservaValet reservaValet) throws SQLException {
        String sql = "UPDATE reserva_valet SET horaEntrada = ?, horaSalida = ?, vehiculoID = ?, clienteID = ?, puestoID = ? WHERE id = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setObject(1, reservaValet.getHoraEntrada());
        ps.setObject(2, reservaValet.getHoraSalida());
        ps.setInt(3, reservaValet.getVehiculo().getId());
        ps.setInt(4, reservaValet.getCliente().getId());
        ps.setInt(5, reservaValet.getPuesto().getId());
        ps.setInt(6, reservaValet.getId());
        ps.executeUpdate();
    }

    /**
     * Método que elimina una reserva de valet parking de la base de datos.
     * @param reservaValet: Objeto tipo ReservaValet a eliminar.
     * @throws SQLException
     */
    public void eliminarReservaValet(ReservaValet reservaValet) throws SQLException {
        String sql = "DELETE FROM reserva_valet WHERE id = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1, reservaValet.getId());
        ps.executeUpdate();
    }

    /**
     * Método que obtiene todas las reservas de valet parking de la base de datos.
     * @return Lista de objetos ReservaValet.
     * @throws SQLException
     */
    public List<ReservaValet> obtenerTodasReservasValet() throws SQLException {
        List<ReservaValet> reservasValet = new ArrayList<>();
        String sql = "SELECT * FROM reserva_valet";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ReservaValet reservaValet = new ReservaValet();
            reservaValet.setId(rs.getInt("id"));
            reservaValet.setHoraEntrada((LocalDateTime) rs.getObject("horaEntrada"));
            reservaValet.setHoraSalida((LocalDateTime) rs.getObject("horaSalida"));

            // Inicializa el objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            reservaValet.setVehiculo(vehiculoRepository.buscarVehiculo(rs.getInt("vehiculoID"), vehiculo));

            // Inicializa el objeto Cliente
            Cliente cliente = new Cliente();
            reservaValet.setCliente(clienteRepository.buscarCliente(rs.getInt("clienteID"), cliente));

            // Inicializa el objeto Puesto
            Puesto puesto = new Puesto();
            reservaValet.setPuesto(puestoRepository.buscarPuesto(rs.getInt("puestoID"), puesto));

            reservasValet.add(reservaValet);
        }

        return reservasValet;
    }
}