package services;

import dao.DAO;
import dao.ItemDAO;
import entities.Item;

import java.util.List;

public class ItemService implements DAO<Item> {
    private static final ItemService instance = new ItemService();

    private final ItemDAO dao;

    public static ItemService getInstance() {
        return instance;
    }

    private ItemService() {
        dao = new ItemDAO();
    }

    public void createTable() {
        dao.createTable();
    }

    public long insert(Item item) {
        return dao.insert(item);
    }

    public void delete(long id) {
        dao.delete(id);
    }

    public void update(Item item) {
        dao.update(item);
    }

    public Item getByID(long id) {
        return dao.getByID(id);
    }

    public List<Item> getAll() {
        return dao.getAll();
    }

    public List<Item> getByInvoiceID(long id) {
        return dao.getByInvoiceID(id);
    }

    public List<Item> getByProductID(long id) {
        return dao.getByProductID(id);
    }

    public int soldInTotal(long id) {
        return dao.soldInTotal(id);
    }

    public int boughtInTotal(long id) {
        return dao.boughtInTotal(id);
    }
}
