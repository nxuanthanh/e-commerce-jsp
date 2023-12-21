package com.dao;

import com.model.Cart;
import com.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private String query;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public ProductDao(Connection con) {
        this.con = con;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();

        try {
            query = "select * from products";
            pst = this.con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));

                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public Product getSingleProduct(int id) {
        Product product = new Product();

        query = "select * from products where id=?";
        try {
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                product.setId(id);
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> products = new ArrayList<Cart>();

        if (!cartList.isEmpty()) {
            for (Cart c : cartList) {
                try {
                    query = "select * from products where id=?";
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, c.getId());
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        Cart cart = new Cart();
                        cart.setId(rs.getInt("id"));
                        cart.setName(rs.getString("name"));
                        cart.setCategory(rs.getString("category"));
                        cart.setPrice(rs.getDouble("price") * c.getQuantity());
                        cart.setQuantity(c.getQuantity());

                        products.add(cart);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return products;
    }

    public double getTotalPrice(ArrayList<Cart> cartList) {
        double totalPrice = 0;

        if (!cartList.isEmpty()) {
            for (Cart c : cartList) {
                try {
                    query = "select price from products where id=?";
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, c.getId());
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        totalPrice += rs.getDouble("price") * c.getQuantity();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return totalPrice;
    }

}
