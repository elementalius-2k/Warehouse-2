<%@ page import="entities.Manufacturer" %>
<%@ page import="java.util.List" %>
<%@ page import="services.ManufacturerService" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Manufacturers</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<div class="container-lg">
    <div class="text-center font-weight-bold">
        <h2>Manufacturers</h2>
        <button class="btn btn-info" onclick="location.href='/'">Back to main menu</button>
    </div>
    <div>
        <h6>Add new manufacturer:</h6>
        <form method="post" class="form-inline">
            <label>Name:&nbsp;
                <input class="form-control" type="text" name="addName">
            </label>&nbsp;&nbsp;
            <label>Address:&nbsp;
                <input class="form-control" type="text" name="addAddress">
            </label>&nbsp;&nbsp;
            <button class='btn btn-success btn-sm' type='submit' name='add'>Submit</button>
        </form>
    </div>
    <div>
        <%
            if (request.getAttribute("added") != null) {
                boolean added = (boolean) request.getAttribute("added");
                if (added)
                    out.println("<p>New manufacturer added successfully</p>");
                if (!added)
                    out.println("<p>Manufacturer with same name and address already exists");
            }
        %>
    </div>
    <div>
        <h6>Find manufacturer by name or by address:</h6>
        <form method='post' class="form-inline">
            <label>Name:&nbsp;
                <input class="form-control" type='text' name='findName'><br>&nbsp;
            </label>&nbsp;&nbsp;
            <label>Address:&nbsp;
                <input class="form-control" type='text' name='findAddress'><br>&nbsp;
            </label>&nbsp;&nbsp;
            <button class="btn btn-success btn-sm" type='submit' name='find'>Find</button>
        </form>
    </div>
    <div>
        <%
            List<Manufacturer> manufacturers = (List<Manufacturer>) request.getAttribute("foundManufacturers");
            if (manufacturers != null) {
                if (manufacturers.isEmpty())
                    out.println("<p>Manufacturer with this name doesn't exist</p>");
                else {
        %>
        <table class="table table-bordered table-hover table-sm">
            <thead>
                <tr><th>ID</th><th>Name</th><th>Address</th><th>Items produces</th></tr>
            </thead>
            <tbody>
            <tr>
                <%
                    for (Manufacturer manufacturer : manufacturers) {
                        String manName = manufacturer.getName();
                        String manAddress = manufacturer.getAddress();
                        out.println("<form method='post' style='display:inline-block;'>" +
                                "<tr><td>" + manufacturer.getId() + "</td>" +
                                "<td><input class='form-control' type='text' name='newName' value='" + manName + "'/></td>" +
                                "<td><input class='form-control' type='text' name='newAddress' value='" + manAddress + "'/></td>" +
                                "<td>" + ManufacturerService.getInstance().numberOfProducts(manufacturer.getId()) + "</td>" +
                                "<td><input type='hidden' name='manufacturerID' value='" + manufacturer.getId() + "'> " +
                                "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>&nbsp;" +
                                "<button class='btn btn-info btn-sm' type='submit' name='update'>Update</button>&nbsp;" +
                                "</form>" +
                                "<form action='/products' method='get' style='display:inline-block;'>" +
                                "<button class='btn btn-warning btn-sm' type='submit' name='fromManufacturersToProducts'>Products</button>" +
                                "<input type='hidden' name='manufacturerID' value='" + manufacturer.getId() + "'> " +
                                "</form></td></tr>");
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
                    out.println("<p>Manufacturer deleted successfully</p>");
                if (!deleted)
                    out.println("<p>Can't delete manufacturer: this manufacturer includes some products.</p>");
            }
        %>
    </div>
    <div>
        <h6>All manufacturers:</h6>
        <%
            List<Manufacturer> all = (List<Manufacturer>) request.getAttribute("all");
            if (all != null) {
                if (all.isEmpty())
                    out.println("<p>No manufacturers added yet</p>");
                else {
        %>
        <table class="table table-bordered table-hover table-sm">
            <thead>
            <tr><th>ID</th><th>Name</th><th>Address</th><th>Items produces</th></tr>
            </thead>
            <tbody>
            <%
                for (Manufacturer manufacturer : all) {
                    String manName = manufacturer.getName();
                    String manAddress = manufacturer.getAddress();
                    out.println("<form method='post' style='display:inline-block;'>" +
                            "<tr><td>" + manufacturer.getId() + "</td>" +
                            "<td><input class='form-control' type='text' name='newName' value='" + manName + "'/></td>" +
                            "<td><input class='form-control' type='text' name='newAddress' value='" + manAddress + "'/></td>" +
                            "<td>" + ManufacturerService.getInstance().numberOfProducts(manufacturer.getId()) + "</td>" +
                            "<td><input type='hidden' name='manufacturerID' value='" + manufacturer.getId() + "'> " +
                            "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>&nbsp;" +
                            "<button class='btn btn-info btn-sm' type='submit' name='update'>Update</button>&nbsp;" +
                            "</form>" +
                            "<form action='/products' method='get' style='display:inline-block;'>" +
                            "<button class='btn btn-warning btn-sm' type='submit' name='fromManufacturersToProducts'>Products</button>" +
                            "<input type='hidden' name='manufacturerID' value='" + manufacturer.getId() + "'> " +
                            "</form></td></tr>");
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
