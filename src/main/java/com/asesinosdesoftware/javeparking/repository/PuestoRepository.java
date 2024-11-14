package com.asesinosdesoftware.javeparking.repository;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PuestoRepository {

    IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();

    /**
     * Método que busca un puesto en la base de datos dependiendo de los parametros que se necesitan
     * @param tamano: Tamaño del puesto buscado
     * @param disponibilidad: Disponibilidad del espacio, 0 para desocupado, 1 para ocupado
     * @param puesto: Objeto puesto para asignar los valores y restornar
     * @return Retorna un objeto tipo puesto con el primer puesto que cumpla las condiciones buscadas
     * @throws SQLException
     */
    public Puesto buscarPuesto(String tamano, boolean disponibilidad, Puesto puesto) throws SQLException {

        String sql = "SELECT * FROM Puesto WHERE tamano = ? and disponibilidad = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
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
     * Método que busca un puesto en la base de datos a partir de su id
     * @param id: id a buscar
     * @param puesto: Objeto tipo puesto para almacenar lo encontrado
     * @return Un objeto tipo puesto si se encuentra o null si no se encuentra
     * @throws SQLException
     */
    public Puesto buscarPuesto(int id, Puesto puesto) throws SQLException {

        String sql = "SELECT * FROM Puesto WHERE id = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if (rs.getInt("id") == id) {
                puesto.setId(rs.getInt("id"));
                puesto.setTamano(rs.getString("tamano").charAt(0));
                puesto.setDisponibilidad(rs.getBoolean("disponibilidad"));
                puesto.setParqueaderoID(rs.getInt("parqueaderoID"));
                return puesto;
            }
        }
        return null;
    }

    /**
     * Método que agrega un puesto de las caracteristicas deseadas a la base de datos
     * @param puesto: Objeto tipo puesto con las características deseadas
     * @throws SQLException
     */
    public void agregarPuesto(Puesto puesto) throws SQLException {

        String sql = "INSERT INTO puesto (`tamano`, `disponibilidad`, `parqueaderoID`) VALUES (?, ?, ?);";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setString(1, Character.toString(puesto.getTamano()));
        ps.setBoolean(2, puesto.isDisponibilidad());
        ps.setInt(3, puesto.getParqueaderoID());
        ps.executeUpdate();
    }

    /**
     * Método que actualiza la disponibilidad de un puesto en la base de datos, de acuerdo con el objeto puesto que se recibe
     * @param puesto: Objeto tipo puesto a partir del cual se va a modificar al disponibilidad
     * @throws SQLException
     */
    public void actualizarPuesto(Puesto puesto) throws SQLException {
        String sql = "UPDATE puesto SET `tamano` = ?, `parqueaderoID` = ?, `disponibilidad` = ? WHERE `id` = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);

        // Establece los valores para cada campo
        ps.setString(1, Character.toString(puesto.getTamano()));
        ps.setInt(2, puesto.getParqueaderoID());
        ps.setBoolean(3, puesto.isDisponibilidad());
        ps.setInt(4, puesto.getId());

        // Ejecutar la actualización
        ps.executeUpdate();
    }
    /**
     * Método que retorna una lista de puestos de la base de datos con una disponibilidad y un tamaño dados
     * @param puestos:Lista de puestos encontrados
     * @param disponibilidad: Disponibilidad de la cual se buscaran los puestos
     * @param tamano: Tamaño del cual se buscaran los puestos
     * @throws SQLException
     */
    public void listarPuestos(List<Puesto> puestos, boolean disponibilidad, char tamano) throws SQLException {

        String sql = "SELECT * FROM puesto WHERE `disponibilidad` = ? and tamano = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setBoolean(1, disponibilidad);
        ps.setString(2, Character.toString(tamano));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            puestos.add(new Puesto(rs.getInt("id"),rs.getString("tamano").charAt(0),rs.getBoolean("disponibilidad"),rs.getInt("parqueaderoID")));
        }
    }

    public List<Puesto> obtenerTodosPuestos() throws SQLException {
        List<Puesto> puestos = new ArrayList<>();
        String sql = "SELECT * FROM puesto";  // Asumiendo que la tabla es 'puesto'

        // Usamos try-with-resources para cerrar conexiones y recursos automáticamente
        try (PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Inicializamos el objeto Puesto
                Puesto puesto = new Puesto();
                puesto.setId(rs.getInt("id"));
                puesto.setTamano(rs.getString("tamano").charAt(0));
                puesto.setDisponibilidad(rs.getBoolean("disponibilidad"));
                puesto.setParqueaderoID(rs.getInt("parqueaderoID"));

                // Agregamos el objeto Puesto a la lista
                puestos.add(puesto);
            }

        } catch (SQLException e) {
            // Manejo de excepción, por ejemplo, mostrar un mensaje o registrar el error
            System.err.println("Error al obtener los puestos: " + e.getMessage());
            throw e;
        }

        return puestos;
    }

    public void eliminarPuesto(Puesto puesto) throws SQLException {
        String sql = "DELETE FROM puesto WHERE `id` = ?";
        PreparedStatement ps = dbConnectionManager.getConnection().prepareStatement(sql);
        ps.setInt(1, puesto.getId());
        ps.executeUpdate();
    }


}
