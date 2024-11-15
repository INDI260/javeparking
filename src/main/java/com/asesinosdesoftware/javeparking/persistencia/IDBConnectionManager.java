package com.asesinosdesoftware.javeparking.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interfaz para reducir dependencia de la conexión a una base de datos concreta
 */
public interface IDBConnectionManager {

    public Connection getConnection() throws SQLException;
}
