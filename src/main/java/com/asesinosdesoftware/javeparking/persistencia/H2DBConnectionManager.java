package com.asesinosdesoftware.javeparking.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class H2DBConnectionManager implements IDBConnectionManager{

    private ResourceBundle reader = null;
    private static final String FILENAME = "dbconfig";

    @Override
    public Connection getConnection() throws SQLException {
        reader = ResourceBundle.getBundle(FILENAME);
        return DriverManager.getConnection("jdbc:h2:file:./javeparking;MODE=MySQL","sa","");
    }
}
