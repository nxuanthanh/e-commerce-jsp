package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Order;
import com.model.Product;

public class OrderDao {
    private Connection con;
    private String query;
    private PreparedStatement pst;
    private ResultSet rs;


    public OrderDao(Connection con) {
        super();
        this.con = con;
    }

    public boolean insertOrder(Order model) {
        boolean result = false;

        try {
            query = "insert into orders (pid, uid, oquantity, odate) values(?,?,?,?)";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, model.getId());
            pst.setInt(2, model.getUid());
            pst.setInt(3, model.getQuantity());
            pst.setString(4, model.getDate());
            pst.executeUpdate();

            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<Order> userOrders(int id) {
        List<Order> orders = new ArrayList<>();

        query = "select * from orders where uid=? order by orders.oid desc";
        try {
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                int pId = rs.getInt("pid");

                ProductDao productDao = new ProductDao(this.con);
                Product product = productDao.getSingleProduct(pId);
                Order order = new Order();

                order.setOrderId(rs.getInt("oid"));
                order.setId(pId);
                order.setName(product.getName());
                order.setCategory(product.getCategory());
                order.setPrice(product.getPrice() * rs.getInt("oquantity"));
                order.setQuantity(rs.getInt("oquantity"));
                order.setDate(rs.getString("odate"));

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    public void cancelOrder(int id) {
        try {
            query = "delete from orders where oid=?";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
