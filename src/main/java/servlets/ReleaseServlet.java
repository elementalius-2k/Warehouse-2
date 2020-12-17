package servlets;

import entities.Invoice;
import entities.Item;
import entities.Partner;
import entities.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.*;

import java.io.IOException;
import java.time.LocalDate;

public class ReleaseServlet extends HttpServlet {
    Invoice invoice;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBLoader.load();
        req.setAttribute("invoice", invoice);
        req.getRequestDispatcher("views/release.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("save") != null && req.getParameter("date") != null && !req.getParameter("date").equals("")) {
            invoice = addInvoice(Long.parseLong(req.getParameter("invoiceID")), req.getParameter("partner"),
                    LocalDate.parse(req.getParameter("date")));
            req.setAttribute("invoice", invoice);
        }
        if (req.getParameter("add") != null && req.getParameter("addQuantity") != null) {
            if (req.getParameter("addPrID") != null && !req.getParameter("addPrID").equals("")) {
                boolean added = addItem(Long.parseLong(req.getParameter("invoiceID")), Long.parseLong(req.getParameter("addPrID")), "",
                        Integer.parseInt(req.getParameter("addQuantity")));
                req.setAttribute("added", added);
                req.setAttribute("prod", ProductService.getInstance().getByID(Long.parseLong(req.getParameter("addPrID"))));
            }
            else if (req.getParameter("addPrName") != null) {
                boolean added = addItem(Long.parseLong(req.getParameter("invoiceID")), -1, req.getParameter("addPrName"),
                        Integer.parseInt(req.getParameter("addQuantity")));
                req.setAttribute("added", added);
                req.setAttribute("prod", ProductService.getInstance().getByName(req.getParameter("addPrName")).get(0));
            }
        }
        if (req.getParameter("deleteItem") != null)
            deleteItem(Long.parseLong(req.getParameter("itID")));
        if (req.getParameter("changeItem") != null) {
            if (req.getParameter("itProdID") != null && !req.getParameter("itProdID").equals("")) {
                boolean updated = updateItem(Long.parseLong(req.getParameter("itID")), Long.parseLong(req.getParameter("itProdID")), "",
                        Integer.parseInt(req.getParameter("itQuantity")), Double.parseDouble(req.getParameter("itPrice")));
                req.setAttribute("updated", updated);
                req.setAttribute("prod", ProductService.getInstance().getByID(Long.parseLong(req.getParameter("itProdID"))));
            }
            else if (req.getParameter("itProdName") != null) {
                boolean updated = updateItem(Long.parseLong(req.getParameter("itID")), -1, req.getParameter("itProdName"),
                        Integer.parseInt(req.getParameter("itID")), Long.parseLong(req.getParameter("itPrice")));
                req.setAttribute("updated", updated);
                req.setAttribute("prod", ProductService.getInstance().getByName(req.getParameter("itProdName")).get(0));
            }
        }
        if (req.getParameter("clear") != null)
            invoice = null;

        doGet(req, resp);
    }

    private Invoice addInvoice(long invoiceID, String partnerName, LocalDate date) {
        Partner partner = PartnerService.getInstance().getByName(partnerName).get(0);
        if (invoiceID == -1) {
            invoiceID = InvoiceService.getInstance().insert(new Invoice(-1, date, partner.getId(),true));
        }
        else {
            Invoice invoice = InvoiceService.getInstance().getByID(invoiceID);
            invoice.setPartnerID(partner.getId());
            invoice.setDate(date);
            InvoiceService.getInstance().update(invoice);
        }
        return InvoiceService.getInstance().getByID(invoiceID);
    }

    private boolean addItem(long invoiceID, long productID, String productName, int quantity) {
        if (productID == -1)
            productID = ProductService.getInstance().getByName(productName).get(0).getId();
        Product product = ProductService.getInstance().getByID(productID);
        if (product != null && invoiceID != -1 && product.getQuantity() >= quantity
                && PriceService.getInstance().getCurrentByProductID(productID) != null) {
            double price = PriceService.getInstance().getCurrentByProductID(productID).getPrice();
            long id = ItemService.getInstance().insert(new Item(-1, invoiceID, productID, quantity, price));
            product.setQuantity(product.getQuantity() - quantity);
            ProductService.getInstance().update(product);
            return id != -1;
        }
        return false;
    }

    private void deleteItem(long id) {
        ItemService.getInstance().delete(id);
    }

    private boolean updateItem(long id, long productID, String productName, int quantity, double price) {
        Item item = ItemService.getInstance().getByID(id);
        if (productID == -1)
            productID = ProductService.getInstance().getByName(productName).get(0).getId();
        Product product = ProductService.getInstance().getByID(productID);
        if (product.getQuantity() >= quantity) {
            item.setProductId(productID);
            item.setQuantity(quantity);
            item.setPrice(price);
            ItemService.getInstance().update(item);
            product.setQuantity(product.getQuantity() - quantity);
            ProductService.getInstance().update(product);
            return true;
        }
        return false;
    }
}
