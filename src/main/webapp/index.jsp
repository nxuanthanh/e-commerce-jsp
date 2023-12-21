<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.*" %>
<%@ page import="com.connection.ConnectionDB" %>
<%@ page import="com.dao.ProductDao" %>
<%
    User auth = (User) request.getSession().getAttribute("auth_user");
    if (auth != null) {
        request.getSession().setAttribute("auth_user", auth);
    }
    ProductDao productDao = new ProductDao(ConnectionDB.getConnection());
    List<Product> products = productDao.getAllProducts();

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/head.jsp" %>
    <title>Welcome to Shoping Cart!</title>
</head>
<body>
<%@ include file="common/navbar.jsp"%>
<div class="container">
    <div class="card-header my-3">All Products</div>
    <div class="row">
        <% if (!products.isEmpty()) {
            for (Product p : products) {%>
        <div class="col-md-3 my-3">
            <div class="card w-100" style="width: 18rem;">
                <img class="card-img-top" src="images/<%= p.getImage()%>" alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title"><%= p.getName()%>
                    </h5>
                    <h6 class="price">Price: $<%= p.getPrice()%>
                    </h6>
                    <h6 class="category">Category: <%= p.getCategory()%>
                    </h6>
                    <div class="mt-3 d-flex justify-content-between">
                        <a href="add-to-cart?id=<%= p.getId()%>" class="btn btn-dark">Add to cart</a>
                        <a href="order-now?quantity=1&id=<%= p.getId()%>" class="btn btn-primary">Buy Now</a>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>

    </div>
</div>
<%@ include file="common/footer.jsp" %>
</body>
</html>