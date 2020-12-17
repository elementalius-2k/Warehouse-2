package servlets;

import entities.Manufacturer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DBLoader;
import services.ManufacturerService;
import java.io.IOException;
import java.util.List;

public class ManufacturerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBLoader.load();
        req.setAttribute("all", getAllManufacturers());
        req.getRequestDispatcher("views/manufacturers.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("add") != null) {
            if (addNewManufacturer(req.getParameter("addName"), req.getParameter("addAddress")))
                req.setAttribute("added",true);
            else
                req.setAttribute("added",false);
        }
        if (req.getParameter("find") != null) {
            if (!req.getParameter("findName").equals(""))
                req.setAttribute("foundManufacturers", findManufacturerByName(req.getParameter("findName")));
            if (!req.getParameter("findAddress").equals(""))
                req.setAttribute("foundManufacturers", findManufacturerByAddress(req.getParameter("findAddress")));
        }
        if (req.getParameter("delete") != null && req.getParameter("manufacturerID") != null) {
            if (deleteManufacturer(Long.parseLong(req.getParameter("manufacturerID"))))
                req.setAttribute("deleted",true);
            else
                req.setAttribute("deleted",false);
        }
        if (req.getParameter("update") != null && req.getParameter("manufacturerID") != null) {
            if(updateManufacturer(Long.parseLong(req.getParameter("manufacturerID")), req.getParameter("newName"), req.getParameter("newAddress")))
                req.setAttribute("updated",true);
            else
                req.setAttribute("updated",false);
        }

        doGet(req, resp);
    }

    private boolean addNewManufacturer(String name, String address) {
        long id = ManufacturerService.getInstance().insert(new Manufacturer(0, name, address));
        return id != 0;
    }

    private List<Manufacturer> findManufacturerByName(String name) {
        return ManufacturerService.getInstance().getByName(name);
    }

    private List<Manufacturer> findManufacturerByAddress(String address) {
        return ManufacturerService.getInstance().getByAddress(address);
    }

    private boolean deleteManufacturer(long id) {
        if (ManufacturerService.getInstance().numberOfProducts(id) == 0) {
            ManufacturerService.getInstance().delete(id);
            return true;
        }
        return false;
    }

    private boolean updateManufacturer(long id, String name, String address) {
        ManufacturerService service = ManufacturerService.getInstance();
        Manufacturer manufacturer = service.getByID(id);
        if (!name.equals("") && !address.equals("")) {
            manufacturer.setName(name);
            manufacturer.setAddress(address);
            service.update(manufacturer);
            return true;
        }
        return false;
    }

    private List<Manufacturer> getAllManufacturers() {
        return ManufacturerService.getInstance().getAll();
    }
}
