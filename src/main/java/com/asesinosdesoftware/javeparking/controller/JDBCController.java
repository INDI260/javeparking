package com.asesinosdesoftware.javeparking.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class JDBCController {

    private ResourceBundle reader = null;
    private static final String FILENAME = "dbconfig";

    public Connection getConnection() {
        try{
            reader = ResourceBundle.getBundle(FILENAME);
            return DriverManager.getConnection(reader.getString("db.url"),reader.getString("db.username"),reader.getString("db.password"));
        }catch(Exception e){System.out.println(e);}
        return null;
    }
}
