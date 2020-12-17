package services;

import dao.DAO;
import dao.ManufacturerDAO;
import entities.Manufacturer;

import java.util.List;

public class ManufacturerService implements DAO<Manufacturer> {
    private static final ManufacturerService instance = new ManufacturerService();

    private final ManufacturerDAO dao;

    public static ManufacturerService getInstance() {
        return instance;
    }

    private ManufacturerService() {
        dao = new ManufacturerDAO();
    }

    @Override
    public void createTable() {
        dao.createTable();
    }

    @Override
    public long insert(Manufacturer manufacturer) {
        return dao.insert(manufacturer);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }

    @Override
    public void update(Manufacturer manufacturer) {
        dao.update(manufacturer);
    }

    @Override
    public Manufacturer getByID(long id) {
        return dao.getByID(id);
    }

    @Override
    public List<Manufacturer> getAll() {
        return dao.getAll();
    }

    public List<Manufacturer> getByName(String name) {
        return dao.getByName(name);
    }

    public List<Manufacturer> getByAddress(String address) {
        return dao.getByAddress(address);
    }

    public int numberOfProducts(long id) { return dao.numberOfProducts(id); }
}
