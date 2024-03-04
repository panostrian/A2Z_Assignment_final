package com.Panos.Final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconnector {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:mysql://localhost:3306/java_assignment_db"; 
            String username = "PanosTr"; 
            String password = "123456"; 

            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                throw new IllegalStateException("Cannot connect to database!", ex);
            }
        }


        return conn;
    }
}