package dao;

import entities.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends BaseDAO implements DAO<Product> {
    public ProductDAO() {
        super("products");
    }

    @Override
    public void createTable() {
        super.executeUpdateSQLStatement("CREATE TABLE IF NOT EXISTS products(" +
                "id BIGSERIAL PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "quantity INTEGER NOT NULL CHECK (quantity >= 0)," +
                "group_id BIGINT NOT NULL REFERENCES groups(id)," +
                "manufacturer_id BIGINT NOT NULL REFERENCES manufacturers(id));",
                "Checking for table products existence");
    }

    @Override
    public long insert(Product product) {
        return super.executeAndGetInsertID("INSERT INTO products(name, quantity, group_id, manufacturer_id) VALUES" +
                "($unkey$" + product.getName() + "$unkey$, " + product.getQuantity() + ", " + product.getGroupID() + ", " + product.getManufacturerID() + ");",
                "Added record to table products");
    }

    @Override
    public void delete(long id) {
        super.executeUpdateSQLStatement("DELETE FROM products WHERE id = " + id + ";",
                "Deleted record from table products");
    }

    @Override
    public void update(Product product) {
        super.executeUpdateSQLStatement("UPDATE products SET (name, quantity, group_id, manufacturer_id) = " +
                "($unkey$" + product.getName() + "$unkey$, " + product.getQuantity() + ", " + product.getGroupID() + ", " +
                product.getManufacturerID() + ") WHERE id = " + product.getId() + ";",
                "Updated record in table products");
    }

    @Override
    public Product getByID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM products WHERE id = " + id + ";",
                "Search in table products by id");
        List<Product> products = convertResultSet(result);
        if (products != null && !products.isEmpty())
            return products.get(0);
        else
            return null;
    }

    @Override
    public List<Product> getAll() {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM products ORDER BY id;",
                "Getting all records from table products");
        return convertResultSet(result);
    }

    public List<Product> getByName(String name) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM products WHERE name LIKE $unkey$%" + name + "%$unkey$;",
                "Search in table products by name");
        return convertResultSet(result);
    }

    public List<Product> getByGroupID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM products WHERE group_id = " + id + ";",
                "Search in table products by group_id");
        return convertResultSet(result);
    }

    public List<Product> getByManufacturerID(long id) {
        ResultSet resultSet = super.executeQuerySQLStatement("SELECT * FROM products WHERE manufacturer_id = " + id +";",
                "Search in table products by manufacturer_id");
        return convertResultSet(resultSet);
    }

    private List<Product> convertResultSet(ResultSet result) {
        try {
            List<Product> products = new ArrayList<>();
            while (result.next()) {
                long id = result.getLong("id");
                String name = result.getString("name");
                int quantity = result.getInt("quantity");
                long groupID = result.getLong("group_id");
                long manufacturerID = result.getLong("manufacturer_id");
                products.add(new Product(id, name, quantity, groupID, manufacturerID));
            }
            return products;
        } catch (SQLException e) {
            System.out.println("ResultSet converting error in ProductDAO");
            e.printStackTrace();
            return null;
        }
    }
}
