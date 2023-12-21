package com.servlet;

import com.model.Cart;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "removeFromCart", value = "/remove-from-cart")
public class RemoveFromCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        ArrayList<Cart> cartList = (ArrayList<Cart>) req.getSession().getAttribute("cart_list");

        if (cartList != null) {
            for (Cart c : cartList) {
                if (c.getId() == id) {
                    cartList.remove(cartList.indexOf(c));
                    break;
                }
            }
            res.sendRedirect("cart.jsp");
        } else {
            res.sendRedirect("cart.jsp");
        }

    }
}