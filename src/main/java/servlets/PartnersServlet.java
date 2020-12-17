package servlets;

import entities.Partner;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DBLoader;
import services.PartnerService;

import java.io.IOException;
import java.util.List;

public class PartnersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBLoader.load();
        req.setAttribute("all", getAllPartners());
        req.getRequestDispatcher("views/partners.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("add") != null) {
            if (addNewPartner(req.getParameter("addName"), req.getParameter("addPaymentAccount"),
                    req.getParameter("addAddress"), req.getParameter("addEmail")))
                req.setAttribute("added",true);
            else
                req.setAttribute("added",false);
        }
        if (req.getParameter("find") != null) {
            req.setAttribute("foundPartners", findPartner(req.getParameter("findName")));
        }
        if (req.getParameter("delete") != null && req.getParameter("id") != null) {
            if (deletePartner(Long.parseLong(req.getParameter("id"))))
                req.setAttribute("deleted",true);
            else
                req.setAttribute("deleted",false);
        }
        if (req.getParameter("update") != null && req.getParameter("id") != null) {
            if(updatePartner(Long.parseLong(req.getParameter("id")), req.getParameter("newName"),
                    req.getParameter("newAccount"), req.getParameter("newAddress"), req.getParameter("newEmail")))
                req.setAttribute("updated",true);
            else
                req.setAttribute("updated",false);
        }

        doGet(req, resp);
    }

    private boolean addNewPartner(String name, String paymentAccount, String address, String email) {
        long id = PartnerService.getInstance().insert(new Partner(0, name, paymentAccount, address, email));
        return id != 0;
    }

    private List<Partner> findPartner(String name) {
        return PartnerService.getInstance().getByName(name);
    }

    private boolean deletePartner(long id) {
        if (PartnerService.getInstance().numberOfInvoices(id) == 0) {
            PartnerService.getInstance().delete(id);
            return true;
        }
        return false;
    }

    private boolean updatePartner(long id, String name, String paymentAccount, String address, String email) {
        PartnerService service = PartnerService.getInstance();
        Partner partner = service.getByID(id);
        if (!name.equals("") && !paymentAccount.equals("") && !address.equals("") && !email.equals("") && paymentAccount.length() == 20) {
            partner.setName(name);
            partner.setPaymentAccount(paymentAccount);
            partner.setAddress(address);
            partner.setEmail(email);
            service.update(partner);
            return true;
        }
        return false;
    }

    private List<Partner> getAllPartners() {
        return PartnerService.getInstance().getAll();
    }
}
