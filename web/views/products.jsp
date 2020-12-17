<%@ page import="entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="services.PriceService" %>
<%@ page import="entities.Price" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<div class="container-lg">
    <div>
        <div class="text-center font-weight-bold">
            <h2>Products</h2>
            <button class="btn btn-info" onclick="location.href='/'">Back to main menu</button>&nbsp;
            <button class="btn btn-info" onclick="location.href='/groups'">Product groups</button>&nbsp;
            <button class="btn btn-success" onclick="location.href='/product_info'">Add new product</button>
        </div>
    </div>
    <div><br>
        <h6>Find product by name, group, or by manufacturer:</h6>
        <form method='post' class="form-inline">
            <label>Name or group:&nbsp;
                <input class="form-control" type='text' name='findName'><br>&nbsp;
            </label>&nbsp;&nbsp;
            <label>Manufacturer:&nbsp;
                <input class="form-control" type='text' name='findManufacturer'><br>&nbsp;
            </label>&nbsp;&nbsp;
            <button class="btn btn-success btn-sm" type='submit' name='find'>Find</button>
        </form>
    </div>
    <div>
        <%
            List<Product> products = (List<Product>) request.getAttribute("foundProducts");
            if (products != null) {
                if (products.isEmpty())
                    out.println("<p>Products with this name doesn't exist</p>");
                else {
        %>
        <table class="table table-bordered table-hover table-sm">
            <thead>
            <tr><th>ID</th><th>Name</th><th>Current price</th><th>Quantity in stock</th></tr>
            </thead>
            <tbody>
            <tr>
                <%
                    for (Product product : products) {
                        Price curr = PriceService.getInstance().getCurrentByProductID(product.getId());
                        out.println("<form action='/product_info' method='get'>" +
                                "<tr><td>" + product.getId() + "</td>" +
                                "<td>" + product.getName() + "</td>");
                        if (curr == null)
                            out.print("<td>------</td>");
                        else
                            out.print("<td>" + PriceService.getInstance().getCurrentByProductID(product.getId()).getPrice() + "</td>");
                        out.print("<td>" + product.getQuantity() + "</td>" +
                                "<td><input type='hidden' name='productID' value='" + product.getId() + "'> " +
                                "<button class='btn btn-warning btn-sm' type='submit' name='toProductInfo'>Details</button>" +
                                "</td></tr>" +
                                "</form>");
                    }
                %>
            </tr>
            </tbody>
        </table>
        <%
                }
            }
        %>
    </div>
    <div>
        <h6>All products:</h6>
        <%
            List<Product> all = (List<Product>) request.getAttribute("all");
            if (all != null) {
                if (all.isEmpty())
                    out.println("<p>No products added yet</p>");
                else {
        %>
        <table class="table table-bordered table-hover table-sm">
            <thead>
            <tr><th>ID</th><th>Name</th><th>Current price</th><th>Quantity in stock</th></tr>
            </thead>
            <tbody>
            <%
                for (Product product : all) {
                    Price curr = PriceService.getInstance().getCurrentByProductID(product.getId());
                    out.println("<form action='/product_info' method='get'>" +
                            "<tr><td>" + product.getId() + "</td>" +
                            "<td>" + product.getName() + "</td>");
                    if (curr == null)
                        out.print("<td>------</td>");
                    else
                        out.print("<td>" + PriceService.getInstance().getCurrentByProductID(product.getId()).getPrice() + "</td>");
                    out.print("<td>" + product.getQuantity() + "</td>" +
                            "<td><input type='hidden' name='productID' value='" + product.getId() + "'/>" +
                            "<button class='btn btn-warning btn-sm' type='submit' name='toProductInfo'>Details</button>" +
                            "</td></tr>" +
                            "</form>");
                }
            %>
            </tbody>
        </table>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>
