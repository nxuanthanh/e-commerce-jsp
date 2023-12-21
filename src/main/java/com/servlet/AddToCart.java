package com.servlet;

import com.model.Cart;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "addToCart", value = "/add-to-cart")
public class AddToCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");

        PrintWriter printWriter = res.getWriter();

        ArrayList<Cart> cartList = new ArrayList<>();
        int id = Integer.parseInt(req.getParameter("id"));
        Cart cart = new Cart();
        cart.setId(id);
        cart.setQuantity(1);

        HttpSession session = req.getSession();
        ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");

        if(cart_list == null){
            cartList.add(cart);
            session.setAttribute("cart_list", cartList);
            res.sendRedirect("index.jsp");
        }else {
            cartList = cart_list;
            boolean exist = false;

            for (Cart c: cartList){
                if (c.getId() == id) {
                    exist = true;
                    printWriter.println("<a href='cart.jsp'>Go to CartPage</a>");
                }
            }

            if (!exist) {
                cartList.add(cart);
                res.sendRedirect("index.jsp");
            }
        }

    }
}