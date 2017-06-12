package com.empresa.sistema.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    public static Connection getConnection() throws Exception{
        Connection conn = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/cointrackerdb", "SA", "");
        } catch (SQLException e) {
            throw  new SQLException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw  new Exception("Problemas ao conectar no banco de dados");
        }

        return conn;
    }
}
