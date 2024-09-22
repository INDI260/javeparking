package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Puesto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PuestoRepository {

    /**
     * Método que busca un puesto en la base de datos dependiendo de los parametros que se necesitan
     * @param tamano: Tamaño del puesto buscado
     * @param disponibilidad: Disponibilidad del espacio, 0 para desocupado, 1 para ocupado
     * @param connection:Conexión a la base de datos
     * @param puesto: Objeto puesto para asignar los valores y restornar
     * @return Retorna un objeto tipo puesto con el primer puesto que cumpla las condiciones buscadas
     * @throws SQLException
     */
    public Puesto buscarPuesto(String tamano, boolean disponibilidad, Connection connection, Puesto puesto) throws SQLException {

        String sql = "SELECT * FROM Puesto WHERE tamano = ? and disponibilidad = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, tamano);
        ps.setBoolean(2, disponibilidad);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if (tamano.equals(rs.getString("tamano")) && disponibilidad == rs.getBoolean("disponibilidad")) {
                puesto.setId(rs.getInt("id"));
                puesto.setTamano(rs.getString("tamano").charAt(0));
                puesto.setDisponibilidad(rs.getBoolean("disponibilidad"));
                return puesto;
            }
        }
        return null;
    }

    /**
     * Método que agrega un puesto de las caracteristicas deseadas a la base de datos
     * @param connection: conexión a la base de datos
     * @param puesto: Objeto tipo puesto con las características deseadas
     * @throws SQLException
     */
    public void agregarPuesto(Connection connection,Puesto puesto) throws SQLException {

        String sql = "INSERT INTO `javeparking`.`puesto` (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES (?, ?, b'1');";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, Character.toString(puesto.getTamano()));
        ps.setBoolean(2, puesto.isDisponibilidad());
        ps.executeUpdate();
    }
}
