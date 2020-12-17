<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <title>Warehouse</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
  </head>
  <body>
  <div class="container-lg">
  <div>
    <h2 class="text-center">Warehouse</h2>
    <h6 class="text-center">Warehouse management system. Stores and allows to edit information about manufacturers,
    products, groups of products, suppliers and customers, invoices. Allows to create new invoices for purchase
    and sale of products.<br>
    Works with PostgreSQL 42.2.18 and Tomcat 10.0.0-M9 (uses Tomcat's libraries jsp-api.jar and servlet-api.jar)</h6><br>
  </div>
  <div>
    <div class="text-center">
      <button class="btn btn-info" onclick="location.href='/products'">Products</button>&nbsp;
      <button class="btn btn-info" onclick="location.href='/partners'">Partners</button>&nbsp;
      <button class="btn btn-info" onclick="location.href='/invoices'">Invoices</button>&nbsp;
      <button class="btn btn-info" onclick="location.href='/manufacturers'">Manufacturers</button>
      <br><br>
      <button class="btn btn-success" onclick="location.href='/release'">Release products</button>&nbsp;
      <button class="btn btn-success" onclick="location.href='/receipt'">Receipt products</button>
    </div>
  </div>
  </div>
  </body>
</html>
