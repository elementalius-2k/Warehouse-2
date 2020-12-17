package servlets;

import entities.Price;
import entities.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ProductInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBLoader.load();
        req.setAttribute("product",null);
        if (req.getParameter("toProductInfo") != null)
            req.setAttribute("product", ProductService.getInstance().getByID(Long.parseLong(req.getParameter("productID"))));

        req.getRequestDispatcher("views/product_info.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("delete") != null) {
            if (deleteProduct(Long.parseLong(req.getParameter("id"))))
                req.getRequestDispatcher("/products").forward(req, resp);
        }
        if (req.getParameter("update") != null) {
            if (req.getParameter("addPrice") != null) {
                updateProduct(Long.parseLong(req.getParameter("id")), req.getParameter("newName"),
                        Integer.parseInt(req.getParameter("newQuantity")),
                        req.getParameter("newGroup"), req.getParameter("newManufacturer"),
                        Double.parseDouble(req.getParameter("addPrice")), LocalDate.parse(req.getParameter("addStart")),
                        LocalDate.parse(req.getParameter("addEnd")), req.getParameter("addDesc"));
                req.getRequestDispatcher("/products").forward(req, resp);
            }
            else
                updateProduct(Long.parseLong(req.getParameter("id")), req.getParameter("newName"),
                        Integer.parseInt(req.getParameter("newQuantity")), req.getParameter("newGroup"),
                        req.getParameter("newManufacturer"), Double.POSITIVE_INFINITY, null, null, "");
        }
        if (req.getParameter("changePrice") != null) {
            updatePrice(Long.parseLong(req.getParameter("priceID")), Double.parseDouble(req.getParameter("newPrice")),
                    LocalDate.parse(req.getParameter("newStart")), LocalDate.parse(req.getParameter("newEnd")),
                    req.getParameter("newDesc"));
        }
        if (req.getParameter("addNewPrice") != null) {
            addPrice(Long.parseLong(req.getParameter("productID")), Double.parseDouble(req.getParameter("addPrice")),
                    LocalDate.parse(req.getParameter("addStart")), LocalDate.parse(req.getParameter("addEnd")),
                    req.getParameter("addDesc"));
        }

        doGet(req, resp);
    }

    private boolean deleteProduct(long id) {
        List<Price> prices = PriceService.getInstance().getByProductID(id);
        for (Price price : prices)
            PriceService.getInstance().delete(price.getId());
        ProductService.getInstance().delete(id);
        return true;
    }

    private boolean updateProduct(long id, String name, int quantity, String groupName, String manufacturerName,
                                  double price, LocalDate start, LocalDate end, String desc) {
        if (!name.equals("") && quantity > 0 && !groupName.equals("") && !manufacturerName.equals("")) {
            long groupId = GroupService.getInstance().getByName(groupName).get(0).getId();
            long manId = ManufacturerService.getInstance().getByName(manufacturerName).get(0).getId();
            if (id == -1) {
                long newID = ProductService.getInstance().insert(new Product(-1, name, quantity, groupId, manId));
                boolean priceAdded = true;
                if (price != Double.POSITIVE_INFINITY)
                    priceAdded = addPrice(newID, price, start, end, desc);
                return (newID != -1 && priceAdded);
            } else {
                Product product = ProductService.getInstance().getByID(id);
                product.setName(name);
                product.setQuantity(quantity);
                product.setGroupID(groupId);
                product.setManufacturerID(manId);
                ProductService.getInstance().update(product);
                return true;
            }
        }
        return false;
    }

    private boolean addPrice(long productID, double price, LocalDate start, LocalDate end, String description) {
        if (price > 0) {
            long id = PriceService.getInstance().insert(new Price(-1, productID, price, start, end, description));
            return id != -1;
        }
        return false;
    }

    private boolean updatePrice(long id, double price1, LocalDate start, LocalDate end, String description) {
        if (price1 > 0) {
            Price price = PriceService.getInstance().getByID(id);
            price.setPrice(price1);
            price.setStartDate(start);
            price.setEndDate(end);
            price.setDescription(description);
            PriceService.getInstance().update(price);
            return true;
        }
        return false;
    }
}
