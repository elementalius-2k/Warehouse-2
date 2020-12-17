package dao;

import entities.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends BaseDAO implements DAO<Item> {
    public ItemDAO() {
        super("items");
    }

    @Override
    public void createTable() {
        super.executeUpdateSQLStatement("CREATE TABLE IF NOT EXISTS items(" +
                "id BIGSERIAL PRIMARY KEY," +
                "invoice_id BIGINT NOT NULL REFERENCES invoices (id)," +
                "product_id BIGINT NOT NULL REFERENCES products (id)," +
                "quantity INTEGER NOT NULL CHECK (quantity > 0)," +
                "price REAL NOT NULL CHECK (price >= 0));",
                "Checking for table items existence");
    }

    @Override
    public long insert(Item item) {
        return super.executeAndGetInsertID("INSERT INTO items(invoice_id, product_id, quantity, price) VALUES" +
                "(" + item.getInvoiceId() + ", " + item.getProductId() + ", " + item.getQuantity() + ", " + item.getPrice() + ");",
                "Added record to table items");
    }

    @Override
    public void delete(long id) {
        super.executeUpdateSQLStatement("DELETE FROM items WHERE id = " + id + ";",
                "Deleted record from table items");
    }

    @Override
    public void update(Item item) {
        super.executeUpdateSQLStatement("UPDATE items SET (invoice_id, product_id, quantity, price) = " +
                 "(" + item.getInvoiceId() + ", " + item.getProductId() + ", " + item.getQuantity() + ", " + item.getPrice() + ") " +
                 "WHERE id = " + item.getId() + ";",
                "Updated record in table items");
    }

    @Override
    public Item getByID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM items WHERE id = " + id + ";",
                "Search in table items by id");
        List<Item> items = convertResultSet(result);
        if (items != null)
            return items.get(0);
        else
            return null;
    }

    @Override
    public List<Item> getAll() {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM items ORDER BY id;",
                "Getting all records from table items");
        return convertResultSet(result);
    }

    public List<Item> getByInvoiceID(long invoiceID) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM items WHERE invoice_id = " + invoiceID + ";",
                "Search in table items by invoice's ID");
        return convertResultSet(result);
    }

    public List<Item> getByProductID(long productID) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM items WHERE product_id = " + productID + ";",
                "Seach in table items by product's ID");
        return convertResultSet(result);
    }

    public int soldInTotal(long productID) {
        ResultSet result = super.executeQuerySQLStatement("SELECT pr.id AS id, SUM(it_plus.quantity) AS sold FROM " +
                "products AS pr " +
                "JOIN invoices AS inv_plus ON inv_plus.is_release = true " +
                "JOIN items AS it_plus ON (pr.id = it_plus.product_id AND inv_plus.id = it_plus.invoice_id) " +
                "GROUP BY pr.id " +
                "ORDER BY pr.id ASC;",
                "Count how many items was sold by product's ID");
        int sum = 0;
        try {
            while (result.next()) {
                if (result.getLong("id") == productID) {
                    sum = result.getInt("sold");
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public int boughtInTotal(long productID) {
        ResultSet result = super.executeQuerySQLStatement("SELECT pr.id AS id, SUM(it_minus.quantity) AS bought FROM " +
                "products AS pr " +
                "JOIN invoices AS inv_minus ON inv_minus.is_release = false " +
                "JOIN items AS it_minus ON (pr.id = it_minus.product_id AND inv_minus.id = it_minus.invoice_id) " +
                "GROUP BY pr.id " +
                "ORDER BY pr.id ASC;",
                "Count how many items wos bought by product's ID");
        int sum = 0;
        try {
            while (result.next()) {
                if (result.getLong("id") == productID) {
                    sum = result.getInt("bought");
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    private List<Item> convertResultSet(ResultSet result) {
        try {
            List<Item> items = new ArrayList<>();
            while (result.next()) {
                long id = result.getLong("id");
                long invoiceID = result.getLong("invoice_id");
                long productID = result.getLong("product_id");
                int quantity = result.getInt("quantity");
                double price = result.getDouble("price");
                items.add(new Item(id, invoiceID, productID, quantity, price));
            }
            return items;
        } catch (SQLException e) {
            System.out.println("ResultSet converting error in ItemDAO");
            e.printStackTrace();
            return null;
        }
    }
}
