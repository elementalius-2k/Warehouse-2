<%@ page import="entities.Product" %>
<%@ page import="entities.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="services.GroupService" %>
<%@ page import="entities.Manufacturer" %>
<%@ page import="services.ManufacturerService" %>
<%@ page import="entities.Price" %>
<%@ page import="services.PriceService" %>
<%@ page import="services.ItemService" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Product info</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<%
    Product product;
    if (request.getAttribute("product") == null)
        product = new Product(-1, "", 0, 0, 0);
    else
        product = (Product) request.getAttribute("product");
%>
<div class="container-lg">
    <div>
        <div class="text-center font-weight-bold">
            <h2>
                <%
                    if (product != null)
                        out.print("Product \"" + product.getName() + "\"");
                    else
                        out.print("Add new product");
                %>
            </h2>
            <button class="btn btn-info" onclick="location.href='/'">Back to main menu</button>&nbsp;
            <button class="btn btn-info" onclick="location.href='/products'">Back to products</button>&nbsp;
        </div>
    </div>
    <div><br>
        <b>Product ID: <% out.print(product.getId()); %></b>
        <form method='post'>
            <div class="form-row">
            <input type='hidden' name="id" value="<% out.print(product.getId()); %>">
            <label>Full product name:&nbsp;
                <input class='form-control' type='text' name='newName' value='<% out.print(product.getName()); %>'><br>&nbsp;
            </label>&nbsp;&nbsp;&nbsp;&nbsp;
            <label>Product group:
                <select class='form-control' name='newGroup'>
                    <%
                        List<Group> groups = GroupService.getInstance().getAll();
                        for (Group group : groups)
                            if (product.getGroupID() == group.getId())
                                out.print("<option selected>" + group.getName() + "</option>");
                            else
                                out.print("<option>" + group.getName() + "</option>");
                    %>
                </select>
            </label>&nbsp;&nbsp;&nbsp;
            <label>Quantity in stock:&nbsp;
                <input class='form-control' type='number' name='newQuantity' min='0' step="0.01" value='<% out.print(product.getQuantity()); %>'><br>&nbsp;
            </label>&nbsp;&nbsp;&nbsp;&nbsp;
            <label>Manufacturer:
                <select class='form-control' name='newManufacturer'>
                    <%
                        List<Manufacturer> manufacturers = ManufacturerService.getInstance().getAll();
                        for (Manufacturer manufacturer : manufacturers)
                            if (product.getManufacturerID() == manufacturer.getId())
                                out.print("<option selected>" + manufacturer.getName() + "</option>");
                            else
                                out.print("<option>" + manufacturer.getName() + "</option>");
                    %>
                </select>
            </label>
            </div>
            <button class="btn btn-success btn-sm" type="submit" name="update">Save</button>&nbsp;&nbsp;
            <button class="btn btn-success btn-sm" type="submit" name="delete">Delete</button>
        <%
            if (product.getId() != -1)
                out.print("</form>");
        %>
    </div>
    <%
        if (product.getId() != -1)
            out.print("<div><table class='table table-bordered table-hover'>" +
                    "<thead><tr><th>Sold in total</th><th>Bought in total</th></tr></thead>" +
                    "<tbody><tr><th>" + ItemService.getInstance().soldInTotal(product.getId()) + "</th>" +
                    "<th>" + ItemService.getInstance().boughtInTotal(product.getId()) + "</th></tr></tbody></table></div><br>");
    %>
    <div>
        <b>Prices:</b>
        <table class="table table-bordered table-hover">
            <thead>
                <tr><th>Price per one</th><th>Start date</th><th>End date</th><th>Description</th></tr>
            </thead>
            <tbody>
            <%
                List<Price> prices = PriceService.getInstance().getByProductID(product.getId());
                for (Price price : prices) {
                    if (price.equals(PriceService.getInstance().getCurrentByProductID(product.getId())))
                        out.print("<tr class='table-danger'>");
                    else
                        out.print("<tr>");
                    out.print("<form method='post'>" +
                            "<td><input class='form-control' type='number' name='newPrice' min='0' step='0.01' value='" + price.getPrice() + "'/></td>" +
                            "<td><input class='form-control' type='date' name='newStart' value='" + price.getStartDate() + "'/></td>" +
                            "<td><input class='form-control' type='date' name='newEnd' value='" + price.getEndDate() + "'/></td>" +
                            "<td><input class='form-control' type='text' name='newDesc' value='" + price.getDescription() + "'/></td>" +
                            "<td><input type='hidden' name='priceID' value='" +  price.getId() + "'>" +
                            "<button class='btn btn-success btn-sm' type='submit' name='changePrice'>Change price</button></td>" +
                            "</td></form></tr>");
                }
                if (product.getId() != -1)
                    out.print("<form method='post'>");
            %>
                <tr class="table-success">
                    <td><input class="form-control" type="number" min="0" step="0.01" name="addPrice"/></td>
                    <td><input class="form-control" type="date" name="addStart"/></td>
                    <td><input class="form-control" type="date" name="addEnd"/></td>
                    <td><input class="form-control" type="text" name="addDesc"/></td>
                    <%
                        if (product.getId() != -1) {
                    %>
                    <td>
                        <input type="hidden" name="productID" value="<% out.print(product.getId()); %>">
                        <button class="btn btn-success btn-sm" type="submit" name="addNewPrice">Add price</button>
                    </td>
                    <%
                        }
                    %>
                </tr>
            </form>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
