package com.leshka_and_friends.lgvb.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
