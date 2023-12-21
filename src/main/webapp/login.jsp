<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.model.User" %>
<%@ page import="com.connection.ConnectionDB" %>
<%@ page import="com.model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%
    User auth = (User) request.getSession().getAttribute("auth_user");
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }

%>
<!DOCTYPE html>
<html>
<head>
    <title>Shoping Cart Login</title>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <%@ include file="common/navbar.jsp" %>
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center">User Login</div>
        <div class="card-body">
            <form action="user-login" method="post">
                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" class="form-control" name="email" placeholder="Enter your email" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="password" placeholder="********" required>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="common/footer.jsp" %>
</body>
</html>