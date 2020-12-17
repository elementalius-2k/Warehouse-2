<%@ page import="entities.Partner" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Partners</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<div class="container-lg">
    <div class="text-center font-weight-bold">
        <h2>Partners</h2>
        <button class="btn btn-info" onclick="location.href='/'">Back to main menu</button>
    </div>
    <div>
        <h6>Add new partner:</h6>
        <form method="post">
            <label>Name:&nbsp;
                <input class="form-control" type="text" name="addName">
            </label>&nbsp;&nbsp;&nbsp;
            <label>Payment account (20 digits):&nbsp;
                <input class="form-control" type="text" maxlength="20" name="addPaymentAccount">
            </label>&nbsp;&nbsp;&nbsp;
            <label>Address:&nbsp;
                <input class="form-control" type="text" name="addAddress">
            </label>&nbsp;&nbsp;&nbsp;
            <label>E-mail:&nbsp;
                <input class="form-control" type="email" name="addEmail">
            </label>&nbsp;&nbsp;&nbsp;
            <button class='btn btn-success btn-sm' type='submit' name='add'>Submit</button>
        </form>
    </div>
    <div>
        <%
            if (request.getAttribute("added") != null) {
                boolean added = (boolean) request.getAttribute("added");
                if (added)
                    out.println("<p>New partner added successfully</p>");
                if (!added)
                    out.println("<p>Partner with same payment account already exists" +
                            " or payment account was entered incorrectly </p>");
            }
        %>
    </div>
    <div>
        <h6>Find partner by name:</h6>
        <form class="form-inline" method='post'>
            <label>Name:&nbsp;
                <input class="form-control" type='text' name='findName'><br>&nbsp;
            </label>
            <button class="btn btn-success btn-sm" type='submit' name='find'>Find</button>
        </form>
    </div>
    <div>
        <%
            List<Partner> partners = (List<Partner>) request.getAttribute("foundPartners");
            if (partners != null) {
                if (partners.isEmpty())
                    out.println("<p>Partner with this name doesn't exist</p>");
                else {
        %>
        <table class="table table-bordered table-hover table-sm">
            <thead>
                <tr><th>ID</th><th>Name</th><th>Payment account</th><th>Address</th><th>E-mail</th></tr>
            </thead>
            <tbody>
            <tr>
                <%
                    for (Partner partner : partners) {
                        String partName = partner.getName();
                        String partAccount = partner.getPaymentAccount();
                        String partAddress = partner.getAddress();
                        String partEmail = partner.getEmail();
                        out.println("<form method='post' style='display:inline-block;'>" +
                                "<tr><td>" + partner.getId() + "</td>" +
                                "<td><input class='form-control' type='text' name='newName' value='" + partName + "'/></td>" +
                                "<td><input class='form-control' type='text' maxlength='20' name='newAccount' value='" + partAccount + "'/></td>" +
                                "<td><input class='form-control' type='text' name='newAddress' value='" + partAddress + "'/></td>" +
                                "<td><input class='form-control' type='email' name='newEmail' value='" + partEmail + "'/></td>" +
                                "<td><input type='hidden' name='id' value='" + partner.getId() + "'> " +
                                "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>&nbsp;" +
                                "<button class='btn btn-info btn-sm' type='submit' name='update'>Update</button>&nbsp;" +
                                "</form>" +
                                "<form action='/invoices' method='get' style='display:inline-block;'>" +
                                "<button class='btn btn-warning btn-sm' type='submit' name='fromPartnersToInvoices'>Invoices</button>" +
                                "<input type='hidden' name='partnerID' value='" + partner.getId() + "'> " +
                                "</form>" +
                                "<form action='/receipt' method='get' style='display:inline-block;'>" +
                                "<button class='btn btn-warning btn-sm' type='submit' name='formPartnersToReceipt'>Buy</button>" +
                                "<input type='hidden' name='partnerID' value='" + partner.getId() + "'>" +
                                "</form>" +
                                "<form action='/release' method='get' style='display:inline-block;'>" +
                                "<button class='btn btn-warning btn-sm' type='submit' name='formPartnersToRelease'>Sell</button>" +
                                "<input type='hidden' name='partnerID' value='" + partner.getId() + "'>" +
                                "</form>" +
                                "</td></tr>");
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
        <%
            if (request.getAttribute("deleted") != null) {
                boolean deleted = (boolean) request.getAttribute("deleted");
                if (deleted)
                    out.println("<p>Partner deleted successfully</p>");
                if (!deleted)
                    out.println("<p>Can't delete partner: this partner includes some invoices.</p>");
            }
        %>
    </div>
    <div>
        <h6>All partners:</h6>
        <%
            List<Partner> all = (List<Partner>) request.getAttribute("all");
            if (all != null) {
                if (all.isEmpty())
                    out.println("<p>No partners added yet</p>");
                else {
        %>
        <table class="table table-bordered table-hover table-sm">
            <thead>
            <tr><th>ID</th><th>Name</th><th>Payment account</th><th>Address</th><th>E-mail</th></tr>
            </thead>
            <tbody>
                <%
                    for (Partner partner : all) {
                        String partName = partner.getName();
                        String partAccount = partner.getPaymentAccount();
                        String partAddress = partner.getAddress();
                        String partEmail = partner.getEmail();
                        out.println("<form method='post' style='display:inline-block;'>" +
                                "<tr><td>" + partner.getId() + "</td>" +
                                "<td><input class='form-control' type='text' name='newName' value='" + partName + "'/></td>" +
                                "<td><input class='form-control' type='text' maxlength='20' name='newAccount' value='" + partAccount + "'/></td>" +
                                "<td><input class='form-control' type='text' name='newAddress' value='" + partAddress + "'/></td>" +
                                "<td><input class='form-control' type='email' name='newEmail' value='" + partEmail + "'/></td>" +
                                "<td><input type='hidden' name='id' value='" + partner.getId() + "'> " +
                                "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>&nbsp;" +
                                "<button class='btn btn-info btn-sm' type='submit' name='update'>Update</button>&nbsp;" +
                                "</form>" +
                                "<form action='/invoices' method='get' style='display:inline-block;'>" +
                                "<button class='btn btn-warning btn-sm' type='submit' name='fromPartnersToInvoices'>Invoices</button>" +
                                "<input type='hidden' name='partnerID' value='" + partner.getId() + "'> " +
                                "</form><br>" +
                                "<form action='/receipt' method='get' style='display:inline-block;'>" +
                                "<button class='btn btn-success btn-sm' type='submit' name='fromPartnersToReceipt'>Buy</button>" +
                                "<input type='hidden' name='partnerID' value='" + partner.getId() + "'>" +
                                "</form>&nbsp;" +
                                "<form action='/release' method='get' style='display:inline-block;'>" +
                                "<button class='btn btn-success btn-sm' type='submit' name='fromPartnersToRelease'>Sell</button>" +
                                "<input type='hidden' name='partnerID' value='" + partner.getId() + "'>" +
                                "</form>" +
                                "</td></tr>");
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
