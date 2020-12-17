package services;

import dao.DAO;
import dao.ProductDAO;
import entities.Product;

import java.util.List;

public class ProductService implements DAO<Product> {
    private static final ProductService instance = new ProductService();

    private final ProductDAO dao;

    public static ProductService getInstance() {
        return instance;
    }

    private ProductService() {
        dao = new ProductDAO();
    }

    public void createTable() {
        dao.createTable();
    }

    public long insert(Product product) {
        return dao.insert(product);
    }

    public void delete(long id) {
        dao.delete(id);
    }

    public void update(Product product) {
        dao.update(product);
    }

    public Product getByID(long id) {
        return dao.getByID(id);
    }

    public List<Product> getAll() {
        return dao.getAll();
    }

    public List<Product> getByName(String name) {
        return dao.getByName(name);
    }

    public List<Product> getByGroupID(long id) {
        return dao.getByGroupID(id);
    }

    public List<Product> getByManufacturerID(long id) {
        return dao.getByManufacturerID(id);
    }
}
