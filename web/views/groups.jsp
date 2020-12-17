<%@ page import="java.util.List" %>
<%@ page import="entities.Group" %>
<%@ page import="services.GroupService" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Product groups</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<div class="container-lg">
    <div class='text-center font-weight-bold'>
        <h2>Product groups</h2>
        <button class="btn btn-info" onclick="location.href='/products'">Back to products</button>
    </div>
    <div>
        <h6>Add new group:</h6>
        <form class="form-inline" method='post'>
            <label>Name:&nbsp;
                <input class="form-control" type='text' name='addName'><br>&nbsp;
            </label>
            <button class='btn btn-success btn-sm' type='submit' name='add'>Submit</button>
        </form>
    </div>
    <div>
        <%
            if (request.getAttribute("added") != null) {
                boolean added = (boolean) request.getAttribute("added");
                if (added)
                    out.println("<p>New group added successfully</p>");
                if (!added)
                    out.println("<p>Group with same name already exists</p>");
            }
        %>
    </div>
    <div>
        <h6>Find group by name:</h6>
        <form class="form-inline" method='post'>
            <label>Name:&nbsp;
                <input class="form-control" type='text' name='findName'><br>&nbsp;
            </label>
            <button class="btn btn-success btn-sm" type='submit' name='find'>Find</button>
        </form>
    </div>
    <div>
        <%
            List<Group> groups = (List<Group>) request.getAttribute("foundGroups");
            if (groups != null) {
                if (groups.isEmpty())
                    out.println("<p>Groups with this name doesn't exist</p>");
                else {
        %>
                    <table class="table table-bordered table-hover table-sm">
                        <thead>
                            <tr><th>ID</th><th>Name</th><th>Number of products</th></tr>
                        </thead>
                        <tbody>
                            <%
                                for (Group group : groups) {
                                    String name = group.getName();
                                    out.println("<form method='post' style='display:inline-block;'>" +
                                            "<tr><td>" + group.getId() + "</td>" +
                                            "<td><input class='form-control' type='text' name='newName' value='" + name + "'></td>" +
                                            "<td>" + GroupService.getInstance().numberOfProductsInGroup(group.getId()) + "</td>" +
                                            "<td><input type='hidden' name='groupID' value='" + group.getId() + "'> " +
                                            "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>&nbsp;" +
                                            "<button class='btn btn-info btn-sm' type='submit' name='update'>Update</button>&nbsp;" +
                                            "</form>" +
                                            "<form action='/products' method='get' style='display:inline-block;'>" +
                                            "<button class='btn btn-warning btn-sm' type='submit' name='fromGroupsToProducts'>Products</button>" +
                                            "<input type='hidden' name='groupID' value='" + group.getId() + "'> " +
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
    <div>
        <%
            if (request.getAttribute("deleted") != null) {
                boolean deleted = (boolean) request.getAttribute("deleted");
                if (deleted)
                    out.println("<p>Group deleted successfully</p>");
                if (!deleted)
                    out.println("<p>Can't delete group containing products</p>");
            }
        %>
    </div>
    <div>
        <h6>All groups:</h6>
        <%
            List<Group> all = (List<Group>) request.getAttribute("all");
            if (all != null) {
                if (all.isEmpty())
                    out.println("<p>No group added yet</p>");
                else {
        %>
                    <table class="table table-bordered table-hover table-sm">
                        <thead>
                            <tr><th>ID</th><th>Name</th><th>Number of products</th></tr>
                        </thead>
                        <tbody>
                            <%
                                for (Group group : all) {
                                    String name = group.getName();
                                    out.println("<form method='post' style='display:inline-block;'>" +
                                            "<tr><td>" + group.getId() + "</td>" +
                                            "<td><input class='form-control' type='text' name='newName' value='" + name + "'/></td>" +
                                            "<td>" + GroupService.getInstance().numberOfProductsInGroup(group.getId()) + "</td>" +
                                            "<td><input type='hidden' name='groupID' value='" + group.getId() + "'> " +
                                            "<button class='btn btn-info btn-sm' type='submit' name='delete'>Delete</button>&nbsp;" +
                                            "<button class='btn btn-info btn-sm' type='submit' name='update'>Update</button>&nbsp;" +
                                            "</form>" +
                                            "<form action='/products' method='get' style='display:inline-block;'>" +
                                            "<button class='btn btn-warning btn-sm' type='submit' name='fromGroupsToProducts'>Products</button>" +
                                            "<input type='hidden' name='groupID' value='" + group.getId() + "'> " +
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