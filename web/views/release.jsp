<%@ page import="java.util.List" %>
<%@ page import="entities.Partner" %>
<%@ page import="services.PartnerService" %>
<%@ page import="entities.Item" %>
<%@ page import="services.ProductService" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="entities.Invoice" %>
<%@ page import="services.ItemService" %>
<%@ page import="entities.Product" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Release products</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<div class="container-lg">
    <%
        Invoice invoice;
        if (request.getAttribute("invoice") == null)
            invoice = new Invoice(-1, LocalDate.now(), -1, true);
        else
            invoice = (Invoice) request.getAttribute("invoice");
    %>
    <div>
        <div class="text-center font-weight-bold">
            <h2>Release invoice</h2>
            <button class="btn btn-info" onclick="location.href='/'">Back to main menu</button>
            <button class="btn btn-info" onclick="location.href='/invoices'">To all invoices</button>
        </div>
    </div>
    <div>
        <form method="post">
            <label>Buyer:&nbsp;
                <select class="form-control" name="partner">
                    <%
                        long fromPartners = -1;
                        if (request.getAttribute("fromPartnersToRelease") != null)
                            fromPartners = (Long) request.getAttribute("partnerID");
                        List<Partner> partners = PartnerService.getInstance().getAll();
                        for (Partner partner : partners) {
                            if (partner.equals(PartnerService.getInstance().getByID(invoice.getPartnerID()))
                                    || partner.getId() == fromPartners)
                                out.print("<option selected>" + partner.getName() + "</option>");
                            else
                                out.print("<option>" + partner.getName() + "</option>");
                        }
                    %>
                </select>
            </label>&nbsp;&nbsp;
            <label>Date:&nbsp;
                <input class="form-control" type="date" name="date" value="<% out.print(invoice.getDate().toString()); %>"/>
            </label>
            <input type="hidden" name="invoiceID" value="<% out.print(invoice.getId()); %>">
            <button class="btn btn-success btn-sm" name="save">Save and go to items</button>
        </form>
        <%
            if (request.getAttribute("updated") != null) {
                boolean updated = (Boolean) request.getAttribute("updated");
                if (!updated) {
                    Product prod = (Product) request.getAttribute("prod");
                    out.print("<p>Can't update product " + prod.getName() + ", there are only " + prod.getQuantity() + " items in stock.</p>");
                }
            }
            if (invoice.getId() != -1) {
        %>
        <table class="table table-bordered table-hover">
            <thead>
            <tr><th>Product ID</th><th>Product name</th><th>Quantity</th><th>Price per one</th><th>Total</th></tr>
            </thead>
            <tbody>
            <%
                double sum = 0;
                List<Item> items = ItemService.getInstance().getByInvoiceID(invoice.getId());
                for (Item item : items) {
                    List<Product> products = ProductService.getInstance().getAll();
                    out.print("<tr><form method='post' style='display:form-inline'>" +
                            "<td><input class='form-control' type='number' min='0' name='itProdID' value='" +
                            item.getProductId() + "'/></td>" +
                            "<td><select class='form-control' name='itProdName'>");
                    for (Product product : products) {
                        if (product.equals(ProductService.getInstance().getByID(item.getProductId())))
                            out.print("<option selected>" + product.getName() + "</option>");
                        else
                            out.print("<option>" + product.getName() + "</option>");
                    }
                    out.print("</select></td>" +
                            "<td><input class='form-control' type='number' min='1' name='itQuantity' value='" +
                            item.getQuantity() + "'/></td>" +
                            "<td><input class='form-control' type='number' min='0' step='0.01' name='itPrice' value='" +
                            item.getPrice() + "'/></td>" +
                            "<td>" + item.getQuantity() * item.getPrice() + "</td>" +
                            "<td><input type='hidden' name='itID' value='" + item.getId() + "'>" +
                            "<button class='btn btn-info btn-sm' type='submit' name='deleteItem'>Delete</button>&nbsp;" +
                            "<button class='btn btn-info btn-sm' type='submit' name='changeItem'>Change</button></td>" +
                            "</form></tr>");
                    sum += item.getQuantity() * item.getPrice();
                }
            %>
            <tr>
                <form method="post">
                    <td><input class="form-control" type="number" min="0" name="addPrID"></td>
                    <td><select class="form-control" name="addPrName">
                            <%
                                List<Product> products = ProductService.getInstance().getAll();
                                for (Product product : products)
                                    out.print("<option>" + product.getName() + "</option>");
                            %>
                    </td>
                    <td><input class="form-control" type="number" min="1" name="addQuantity"></td>
                    <td>will be taken from price table</td>
                    <td>-----</td>
                    <td>
                        <input type="hidden" name="invoiceID" value="<% out.print(invoice.getId()); %>">
                        <button class="btn btn-warning btn-sm" type="submit" name="add">Add product</button>
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
        <%
            if (request.getAttribute("added") != null) {
                boolean added = (Boolean) request.getAttribute("added");
                if (!added) {
                    Product prod = (Product) request.getAttribute("prod");
                    out.print("<p>Can't add product " + prod.getName() + ", there are only " + prod.getQuantity() + " items in stock," +
                            " or there is no price set for thus product</p>");
                }
            }
        %>
        Total price for all: <% out.print(sum + "."); } %>
        <form method="post">
            <button class="btn btn-warning" type="submit" name="clear">Save all and create new selling invoice</button>
        </form>
    </div>
</div>
</body>
</html>
