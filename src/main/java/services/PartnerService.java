package services;

import dao.DAO;
import dao.PartnerDAO;
import entities.Partner;

import java.util.List;

public class PartnerService implements DAO<Partner> {
    private static final PartnerService instance = new PartnerService();

    private final PartnerDAO dao;

    public static PartnerService getInstance() {
        return instance;
    }

    private PartnerService() {
        dao = new PartnerDAO();
    }

    public void createTable() {
        dao.createTable();
    }

    public long insert(Partner partner) {
        return dao.insert(partner);
    }

    public void delete(long id) {
        dao.delete(id);
    }

    public void update(Partner partner) {
        dao.update(partner);
    }

    public Partner getByID(long id) {
        return dao.getByID(id);
    }

    public List<Partner> getAll() {
        return dao.getAll();
    }

    public List<Partner> getByName(String name) {
        return dao.getByName(name);
    }

    public int numberOfInvoices(long id) { return dao.numberOfInvoices(id); }
}
