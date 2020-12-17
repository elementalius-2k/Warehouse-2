package services;

import dao.DAO;
import dao.InvoiceDAO;
import entities.Invoice;

import java.time.LocalDate;
import java.util.List;

public class InvoiceService implements DAO<Invoice> {
    private static final InvoiceService instance = new InvoiceService();

    private final InvoiceDAO dao;

    public static InvoiceService getInstance() {
        return instance;
    }

    private InvoiceService() {
        dao = new InvoiceDAO();
    }

    public void createTable() {
        dao.createTable();
    }

    public long insert(Invoice invoice) {
        return dao.insert(invoice);
    }

    public void delete(long id) {
        dao.delete(id);
    }

    public void update(Invoice invoice) {
        dao.update(invoice);
    }

    public Invoice getByID(long id) {
        return dao.getByID(id);
    }

    public List<Invoice> getAll() {
        return dao.getAll();
    }

    public List<Invoice> getByPartnerID(long id) {
        return dao.getByPartnerID(id);
    }

    public List<Invoice> getByRelease(boolean isRelease) {
        return dao.getByRelease(isRelease);
    }

    public List<Invoice> getByDate(LocalDate date) {
        return dao.getByDate(date);
    }
}
