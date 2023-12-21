<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.model.Cart" %>
<%@ page import="com.model.User" %>
<%@ page import="com.connection.ConnectionDB" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.model.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dao.OrderDao" %>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    User auth = (User) request.getSession().getAttribute("auth_user");
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");
    List<Order> orders = null;
    if (auth != null) {
        request.setAttribute("person", auth);
        OrderDao orderDao = new OrderDao(ConnectionDB.getConnection());
        orders = orderDao.userOrders(auth.getId());
    }
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    } else {
        response.sendRedirect("login.jsp");
    }

%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Page</title>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<%@ include file="common/navbar.jsp" %>

<div class="container">
    <div class="card-header my-3">All Orders</div>
    <table class="table table-light">
        <thead>
        <tr>
            <td>Date</td>
            <td>Name</td>
            <td>Category</td>
            <td>Quantity</td>
            <td>Price</td>
            <td>Cancel</td>
        </tr>
        </thead>
        <tbody>
        <%
            if (orders != null) {
                for (Order o : orders) {%>
        <tr>
            <td><%=o.getDate() %>
            </td>
            <td><%=o.getName() %>
            </td>
            <td><%=o.getCategory() %>
            </td>
            <td><%=o.getQuantity() %>
            </td>
            <td><%=dcf.format(o.getPrice()) %>
            </td>
            <td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%=o.getOrderId()%>">Cancel Order</a></td>
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