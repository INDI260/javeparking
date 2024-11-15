package com.asesinosdesoftware.javeparking.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Clase de legado que permite realizar la conexión a una base de datos MySQL
 */
public class MySQLDBConnectionManager implements IDBConnectionManager{

    private static final String FILENAME = "dbconfig";

    @Override
    public Connection getConnection() throws SQLException {
        ResourceBundle reader = ResourceBundle.getBundle(FILENAME);
        return DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
    }
}
