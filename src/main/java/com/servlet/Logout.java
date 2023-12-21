package com.servlet;

import com.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Logout", value = "/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("auth_user");

        if (currentUser != null) {
            req.getSession().removeAttribute("auth_user");
            res.sendRedirect("login.jsp");
        } else {
            res.sendRedirect("index.jsp");
        }
    }
}