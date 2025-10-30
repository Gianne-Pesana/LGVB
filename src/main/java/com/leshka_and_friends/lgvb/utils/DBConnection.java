package com.leshka_and_friends.lgvb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {

    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = AppConfig.get("db.url");
            String user = AppConfig.get("db.user");
            String password = AppConfig.get("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public static void testConnection() {
        try {
            System.out.println("Attempting to connect to the database...");
            getConnection();
            System.out.println("Database connection successful.");
        } catch (SQLException e) {
            String message = "Database connection failed. Please check your configuration and ensure the database server is running.";
            System.err.println(message);
            System.err.println(e.getMessage());
            
            JOptionPane.showMessageDialog(null, message, "Test Configuration", JOptionPane.ERROR_MESSAGE);
            
            // Optionally, rethrow as a runtime exception to halt the application
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }
}
