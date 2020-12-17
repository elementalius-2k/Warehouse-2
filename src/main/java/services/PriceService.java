package services;

import dao.DAO;
import dao.PriceDAO;
import entities.Price;

import java.util.List;

public class PriceService implements DAO<Price> {
    private static final PriceService instance = new PriceService();

    private final PriceDAO dao;

    public static PriceService getInstance() {
        return instance;
    }

    private PriceService() {
        dao = new PriceDAO();
    }

    @Override
    public void createTable() {
        dao.createTable();
    }

    @Override
    public long insert(Price price) {
        return dao.insert(price);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }

    @Override
    public void update(Price price) {
        dao.update(price);
    }

    @Override
    public Price getByID(long id) {
        return dao.getByID(id);
    }

    @Override
    public List<Price> getAll() {
        return dao.getAll();
    }

    public List<Price> getByProductID(long id) {
        return dao.getByProductID(id);
    }

    public Price getCurrentByProductID(long id) { return dao.getCurrentByProductID(id); }
}
