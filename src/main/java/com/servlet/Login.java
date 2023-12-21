package com.servlet;

import com.connection.ConnectionDB;
import com.dao.UserDao;
import com.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "login", value = "/user-login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDao userDao = new UserDao(ConnectionDB.getConnection());
        User user = userDao.userLogin(email, password);

        if(user != null){
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("auth_user", user);
            res.sendRedirect("index.jsp");
        }else {
            res.sendError(401);
        }
    }
}