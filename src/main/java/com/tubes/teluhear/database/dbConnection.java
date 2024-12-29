package com.tubes.teluhear.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/telu-hear";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi berhasil!");
        } catch (SQLException e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        connect();
    }
}
