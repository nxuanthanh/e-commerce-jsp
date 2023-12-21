<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.model.User" %>
<%@ page import="com.connection.ConnectionDB" %>
<%@ page import="com.dao.ProductDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.model.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Cart" %>
<%@ page import="java.text.DecimalFormat" %>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    User auth = (User) session.getAttribute("auth_user");

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");
    List<Cart> cartProducts = null;
    if (cart_list != null) {
        ProductDao productDao = new ProductDao(ConnectionDB.getConnection());
        cartProducts = productDao.getCartProducts(cart_list);
        double totalPrice = productDao.getTotalPrice(cart_list);
        request.setAttribute("cart_list", cart_list);
        request.setAttribute("total_price", totalPrice);
        request.setAttribute("dcf", dcf);
    }
%>
<html>
<head>
    <title>Cart Products</title>
    <%@ include file="common/head.jsp" %>

    <style>
        .table tbody td {
            vertical-align: middle;
        }

        .btn-decre, .btn-incre {
            box-shadow: none;
            font-size: 25px;
        }
    </style>
</head>
<body>
<%@ include file="common/navbar.jsp" %>
<div class="container">
    <div class="d-flex py-3">
        <h3>Total Price: $ ${(total_price > 0)  ? dcf.format(total_price): 0}</h3>
        <a href="cart-check-out" class="mx-3 btn btn-primary">Check Out</a>
    </div>
    <table class="table table-loght">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Price</th>
            <th scope="col">Buy Now</th>
            <th scope="col">Cancel</th>
        </tr>
        </thead>
        <tbody>
        <% if (cart_list != null) {
            for (Cart c : cartProducts) {%>
        <tr>
            <td><%= c.getName()%>
            </td>
            <td><%= c.getCategory()%>
            </td>
            <td><%= dcf.format(c.getPrice())%>$</td>
            <td>
                <form action="order-now" method="post" class="form-inline mb-0">
                    <input type="hidden" name="id" value="<%= c.getId()%>" class="form-input">
                    <div class="form-group d-flex justify-content-between mb-0 w-50">
                        <a href="quantity-inc-dec?action=dec&id=<%= c.getId()%>" class="btn btn-sm btn-decre"><i
                                class="fas fa-minus-square"></i></a>
                        <input type="text" name="quantity" value="<%= c.getQuantity()%>" class="form-control w-50" readonly>
                        <a href="quantity-inc-dec?action=inc&id=<%= c.getId()%>" class="btn btn-sm btn-incre"><i
                                class="fas fa-plus-square"></i></a>
                    </div>
                    <button type="submit" class="btn btn-sm btn-primary">Buy</button>
                </form>
            </td>
            <td><a href="remove-from-cart?id=<%= c.getId()%>" class="btn btn-danger">Remove</a></td>
        </tr>
        <%
                }
            }
        %>

        </tbody>
    </table>
</div>
<%@ include file="common/footer.jsp" %>
</body>
</html>
