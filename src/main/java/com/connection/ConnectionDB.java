package com.connection;

import java.sql.*;

public class ConnectionDB {
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_cart", "root", "567099a@");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return con;
    }
}
