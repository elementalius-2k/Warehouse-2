package servlets;

import entities.Invoice;
import entities.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DBLoader;
import services.InvoiceService;
import services.ItemService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class InvoicesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBLoader.load();
        if (req.getParameter("fromPartnersToInvoices") != null)
            req.setAttribute("foundInvoices", findByPartnerID(Long.parseLong(req.getParameter("partnerID"))));
        req.setAttribute("all", getAll());
        req.getRequestDispatcher("views/invoices.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("find") != null) {
            if (!req.getParameter("findDate").equals(""))
                req.setAttribute("foundInvoices", findByDate(LocalDate.parse(req.getParameter("findDate"))));
            else {
                if (req.getParameter("findRelease").equals("Selling"))
                    req.setAttribute("foundInvoices", findByRelease(true));
                if (req.getParameter("findRelease").equals("Buying"))
                    req.setAttribute("foundInvoices", findByRelease(false));
            }
        }
        if (req.getParameter("delete") != null && req.getParameter("id") != null) {
            if (deleteInvoice(Long.parseLong(req.getParameter("id"))))
                req.setAttribute("deleted",true);
            else
                req.setAttribute("deleted",false);
        }

        doGet(req, resp);
    }

    private List<Invoice> findByPartnerID(long id) {
        return InvoiceService.getInstance().getByPartnerID(id);
    }

    private List<Invoice> findByDate(LocalDate date) {
        return InvoiceService.getInstance().getByDate(date);
    }

    private List<Invoice> findByRelease(boolean isRelease) {
        return InvoiceService.getInstance().getByRelease(isRelease);
    }

    private List<Invoice> getAll() {
        return InvoiceService.getInstance().getAll();
    }

    private boolean deleteInvoice(long id) {
        List<Item> items = ItemService.getInstance().getByInvoiceID(id);
        for (Item item : items)
            ItemService.getInstance().delete(item.getId());
        InvoiceService.getInstance().delete(id);
        return true;
    }
}
