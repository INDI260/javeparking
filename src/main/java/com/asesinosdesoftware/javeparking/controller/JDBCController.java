package com.asesinosdesoftware.javeparking.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class JDBCController {

    private ResourceBundle reader = null;

    public Connection getConnection() {
        try{
            reader = ResourceBundle.getBundle("dbconfig.properties");
            Connection conn= DriverManager.getConnection(reader.getString("db.url"),reader.getString("db.username"),reader.getString("db.password"));
            return conn;
        }catch(Exception e){System.out.println(e);}
        return null;
    }
}
