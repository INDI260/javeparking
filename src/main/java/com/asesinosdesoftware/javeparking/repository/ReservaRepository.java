package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservaRepository {

    /**
     * Método que agrega una reserva a la base de datos de acuerdo a los parametros dados
     * @param connection: Conexión con la base de datos
     * @param reserva: Objeto tipo reserva con los parametros deseados
     * @throws SQLException
     */
    public void agregarReserva(Connection connection, Reserva reserva) throws SQLException {
        String sql = "INSERT INTO `javeparking`.`reserva` (`fecha`, `horaEntrada`, `horaSalida`, `vehiculoID`, `puestoID`) VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,reserva.getFecha().toString());
        ps.setString(2,reserva.getHoraEntrada());
        ps.setString(3,reserva.getHoraSalida());
        ps.setInt(4, reserva.getVehiculo().getId());
        ps.setInt(5, reserva.getPuesto().getId());
        ps.executeUpdate();

    }
}
