package com.servlet;

import com.model.Cart;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "quantityIncDec", value = "/quantity-inc-dec")
public class QuantityIncDec extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");

        ArrayList<Cart> cartList = (ArrayList<Cart>) req.getSession().getAttribute("cart_list");

        if (cartList != null) {
            if (action.equals("inc")) {
                for (Cart c : cartList) {
                    if (c.getId() == id) {
                        int quantity = c.getQuantity();
                        quantity++;
                        c.setQuantity(quantity);
                        break;
                    }
                }
                res.sendRedirect("cart.jsp");
            }
            if (action.equals("dec")) {
                for (Cart c : cartList) {
                    if (c.getId() == id && c.getQuantity() > 1) {
                        int quantity = c.getQuantity();
                        quantity--;
                        c.setQuantity(quantity);
                        break;
                    }
                }
                res.sendRedirect("cart.jsp");
            }
        } else {
            res.sendRedirect("cart.jsp");
        }
    }
}