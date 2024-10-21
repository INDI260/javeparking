package com.asesinosdesoftware.javeparking.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDBConnectionManager {

    public Connection getConnection() throws SQLException;
}
