package com.servlet;

import com.connection.ConnectionDB;
import com.dao.OrderDao;
import com.model.Cart;
import com.model.Order;
import com.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "CartCheckOut", value = "/cart-check-out")
public class CartCheckOut extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        ArrayList<Cart> cart_list = (ArrayList<Cart>) req.getSession().getAttribute("cart_list");
        User auth = (User) req.getSession().getAttribute("auth_user");

        if (cart_list != null && auth != null) {
            for (Cart c : cart_list) {
                Order order = new Order();
                order.setId(c.getId());
                order.setUid(auth.getId());
                order.setQuantity(c.getQuantity());
                order.setDate(date_formatter.format(date));

                OrderDao oDao = new OrderDao(ConnectionDB.getConnection());
                boolean result = oDao.insertOrder(order);
                if (!result) break;
            }

            cart_list.clear();
            res.sendRedirect("orders.jsp");
        } else if (auth == null) {
            res.sendRedirect("login.jsp");

        } else {
            res.sendRedirect("cart.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}