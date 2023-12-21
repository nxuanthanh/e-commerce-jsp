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

@WebServlet(name = "orderNow", value = "/order-now")
public class OrderNow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        PrintWriter printWriter = res.getWriter();

        User auth = (User) req.getSession().getAttribute("auth_user");

        if (auth != null) {
            String prId = req.getParameter("id");
            int prQuantity = Integer.parseInt(req.getParameter("quantity"));

            if (prQuantity <= 0) {
                prQuantity = 1;
            }

            Order order = new Order();
            order.setId(Integer.parseInt(prId));
            order.setUid(auth.getId());
            order.setQuantity(prQuantity);
            order.setDate(date_formatter.format(date));

            OrderDao orderDao = new OrderDao(ConnectionDB.getConnection());
            boolean result = orderDao.insertOrder(order);

            if (result) {
                ArrayList<Cart> cartList = (ArrayList<Cart>) req.getSession().getAttribute("cart_list");

                if (cartList != null) {
                    for (Cart c : cartList) {
                        if (c.getId() == Integer.parseInt(prId)) {
                            cartList.remove(cartList.indexOf(c));
                            break;
                        }
                    }
                }
                res.sendRedirect("orders.jsp");
            } else {
                printWriter.println("order failed");
            }
        } else {
            res.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}