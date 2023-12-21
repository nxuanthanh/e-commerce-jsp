package com.servlet;

import com.connection.ConnectionDB;
import com.dao.OrderDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CancelOrder", value = "/cancel-order")
public class CancelOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String id = req.getParameter("id");

        if(id != null){
            OrderDao orderDao = new OrderDao(ConnectionDB.getConnection());
            orderDao.cancelOrder(Integer.parseInt(id));
        }
        res.sendRedirect("orders.jsp");
    }
}