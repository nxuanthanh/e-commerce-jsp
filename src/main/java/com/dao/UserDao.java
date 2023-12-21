package com.dao;

import java.sql.*;

import com.model.User;

public class UserDao {
    private String query;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public UserDao(Connection con) {
        this.con = con;
    }

    public User userLogin(String email, String password) {
        User currentUser = null;

        try {
            query = "select * from users where email=? and password=?";
            pst = this.con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            rs = pst.executeQuery();
            while (rs.next()) {
                currentUser = new User();
                currentUser.setId(rs.getInt("id"));
                currentUser.setEmail(rs.getString("email"));
                currentUser.setName(rs.getString("name"));
                currentUser.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return currentUser;
    }

}
