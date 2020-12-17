<%@ page import="entities.Invoice" %>
<%@ page import="java.util.List" %>
<%@ page import="services.PartnerService" %>
<%@ page import="entities.Partner" %>
<%@ page import="entities.Item" %>
<%@ page import="services.ItemService" %>
<%@ page import="services.ProductService" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Invoices</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<div class="container-lg">
    <div class="text-center font-weight-bold">
        <h2>Invoices</h2>
        <button class="btn btn-info" onclick="location.href='/'">Back to main menu</button>
    </div>
    <div>
        <h6>Find invoice by date or by buying/selling:</h6>
        <form method='post' class="form-inline">
            <label>Date:&nbsp;
                <input class="form-control" type='date' name='findDate'><br>&nbsp;
            </label>&nbsp;&nbsp;
            <label>Selling or buying:&nbsp;&nbsp;
                <select class='form-control' name='findRelease'>
                    <option selected>Selling</option>
                    <option>Buying</option>
                </select><br>&nbsp;
            </label>&nbsp;&nbsp;
            <button class="btn btn-success btn-sm" type='submit' name='find'>Find</button>
        </form>
    </div>
    <div>
        <%
            List<Invoice> invoices = (List<Invoice>) request.getAttribute("foundInvoices");
            if (invoices != null) {
                if (invoices.isEmpty())
                    out.println("<p>Invoices don't exist</p>");
                else {
                    for (Invoice invoice : invoices) {
                        double sum = 0;
                        out.print("<b>Invoice №" + invoice.getId() + "</b>. Date: " + invoice.getDate().toString() + ".");
                        if (invoice.isRelease())
                            out.print("<b> Selling.</b><br>Buyer: ");
                        else
                            out.print("<b> Buying.</b><br>Provider: ");
                        Partner partner = PartnerService.getInstance().getByID(invoice.getPartnerID());
                        out.print(partner.getName() + ", payment account: " + partner.getPaymentAccount() + ".<br>");
        %>
            <table class="table table-bordered">
                <thead>
                    <tr><th>Product ID</th><th>Product name</th><th>Quantity</th><th>Price per one</th><th>Total price</th></tr>
                </thead>
                <tbody>
                    <%
                        List<Item> items = ItemService.getInstance().getByInvoiceID(invoice.getId());
                        for (Item item : items) {
                            out.print("<tr><td>" + item.getProductId() + "</td><td>" +
                                    ProductService.getInstance().getByID(item.getProductId()).getName() +
                                    "</td><td>" + item.getQuantity() + "</td>" +
                                    "<td>" + item.getPrice() + "</td>" +
                                    "<td>" + item.getQuantity() * item.getPrice() + "</td></tr>");
                            sum += item.getQuantity() * item.getPrice();
                        }
                    %>
                </tbody>
            </table>
        <%
                        out.print("Total cost: " + sum + "<br>" +
                                "<form method='post'>" +
                                "<td><input type='hidden' name='id' value='" + invoice.getId() + "'>" +
                                "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>" +
                                "</form><br>");
                    }
                }
            }
        %>
    </div>
    <div>
        <%
            if (request.getAttribute("deleted") != null) {
                boolean deleted = (boolean) request.getAttribute("deleted");
                if (deleted)
                    out.println("<p>Invoice deleted successfully</p>");
                if (!deleted)
                    out.println("<p>Error: can't delete this invoice.</p>");
            }
        %>
    </div>
    <div>
        <h5>All invoices:</h5>
        <%
            List<Invoice> all = (List<Invoice>) request.getAttribute("all");
            if (all != null) {
                if (all.isEmpty())
                    out.println("<p>Invoices don't exist</p>");
                else {
                    for (Invoice invoice : all) {
                        double sum = 0;
                        out.print("<b>Invoice №" + invoice.getId() + "</b>. Date: " + invoice.getDate().toString() + ".");
                        if (invoice.isRelease())
                            out.print("<b> Selling.</b><br>Buyer: ");
                        else
                            out.print("<b> Buying.</b><br>Provider: ");
                        Partner partner = PartnerService.getInstance().getByID(invoice.getPartnerID());
                        String partnerName = partner.getName();
                        String partnerAccount = partner.getPaymentAccount();
                        out.print(partnerName + ", payment account: " + partnerAccount + ";<br>");
        %>
        <table class="table table-bordered">
            <thead>
                <tr><th>Product ID</th><th>Product name</th><th>Quantity</th><th>Price per one</th><th>Total price</th></tr>
            </thead>
            <tbody>
                <%
                    List<Item> items = ItemService.getInstance().getByInvoiceID(invoice.getId());
                    for (Item item : items) {
                        String prodName = ProductService.getInstance().getByID(item.getProductId()).getName();
                        out.print("<tr><td>" + item.getProductId() + "</td><td>" + prodName +
                                "</td><td>" + item.getQuantity() + "</td>" +
                                "<td>" + item.getPrice() + "</td>" +
                                "<td>" + item.getQuantity() * item.getPrice() + "</td></tr>");
                        sum += item.getQuantity() * item.getPrice();
                    }
                %>
            </tbody>
        </table>
        <%
                        out.print("Total cost: " + sum + "<br>" +
                                "<form method='post'>" +
                                "<td><input type='hidden' name='id' value='" + invoice.getId() + "'>" +
                                "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>" +
                                "</form><br>");
                    }
                }
            }
        %>
    </div>
</div>
</body>
</html>
