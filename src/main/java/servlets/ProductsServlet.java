package servlets;

import entities.Group;
import entities.Manufacturer;
import entities.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DBLoader;
import services.GroupService;
import services.ManufacturerService;
import services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBLoader.load();
        req.setAttribute("all", ProductService.getInstance().getAll());
        if (req.getParameter("fromManufacturersToProducts") != null)
            req.setAttribute("foundProducts", getByManufacturerID(Long.parseLong(req.getParameter("manufacturerID"))));
        if (req.getParameter("fromGroupsToProducts") != null)
            req.setAttribute("foundProducts", getByGroupID(Long.parseLong(req.getParameter("groupID"))));

        req.getRequestDispatcher("views/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("find") != null) {
            if (!req.getParameter("findName").equals(""))
                req.setAttribute("foundProducts", findProductByName(req.getParameter("findName")));
            if (!req.getParameter("findManufacturer").equals(""))
                req.setAttribute("foundProducts", findProductsByManufacturer(req.getParameter("findManufacturer")));
        }

        doGet(req, resp);
    }

    private List<Product> getByManufacturerID(long id) {
        return ProductService.getInstance().getByManufacturerID(id);
    }

    private List<Product> getByGroupID(long id) {
        return ProductService.getInstance().getByGroupID(id);
    }

    private List<Product> findProductByName(String name) {
        List<Product> products = ProductService.getInstance().getByName(name);
        List<Group> groups = GroupService.getInstance().getByName(name);
        for (Group group : groups)
            products.addAll(ProductService.getInstance().getByGroupID(group.getId()));
        return products;
    }

    private List<Product> findProductsByManufacturer(String name) {
        List<Product> products = new ArrayList<>();
        List<Manufacturer> manufacturers = ManufacturerService.getInstance().getByName(name);
        for (Manufacturer manufacturer : manufacturers)
            products.addAll(ProductService.getInstance().getByManufacturerID(manufacturer.getId()));
        return products;
    }
}
