package dao;

import entities.Price;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PriceDAO extends BaseDAO implements DAO<Price> {
    public PriceDAO() {
        super("prices");
    }

    @Override
    public void createTable() {
        super.executeUpdateSQLStatement("CREATE TABLE IF NOT EXISTS prices(" +
                "id BIGSERIAL PRIMARY KEY," +
                "product_id BIGINT NOT NULL REFERENCES products(id)," +
                "price REAL NOT NULL CHECK (price >= 0)," +
                "start_date DATE," +
                "end_date DATE," +
                "description TEXT);",
                "Checking for table prices existence");
    }

    @Override
    public long insert(Price price) {
        return super.executeAndGetInsertID("INSERT INTO  prices(product_id, price, start_date, end_date, description) VALUES" +
                        "(" + price.getProductID() + ", " + price.getPrice() + ", $unkey$" + Date.valueOf(price.getStartDate()) +
                        "$unkey$, $unkey$" + Date.valueOf(price.getEndDate()) + "$unkey$, $unkey$" + price.getDescription() + "$unkey$);",
                "Added record to table prices");
    }

    @Override
    public void delete(long id) {
        super.executeUpdateSQLStatement("DELETE FROM prices WHERE id = " + id + ";",
                "Deleted record from table prices");
    }

    @Override
    public void update(Price price) {
        super.executeUpdateSQLStatement("UPDATE prices SET (product_id, price, start_date, end_date, description) = " +
                        "(" + price.getProductID() + ", " + price.getPrice() + ", $unkey$" + Date.valueOf(price.getStartDate()) +
                        "$unkey$, $unkey$" + Date.valueOf(price.getEndDate()) + "$unkey$, $unkey$" + price.getDescription() + "$unkey$) " +
                        "WHERE id = " + price.getId() + ";",
                "Updated record in table prices");
    }

    @Override
    public Price getByID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM prices WHERE id = " + id + ";",
                "Search in table prices by id");
        List<Price> prices = convertResultSet(result);
        if (prices != null && !prices.isEmpty())
            return prices.get(0);
        else
            return null;
    }

    @Override
    public List<Price> getAll() {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM prices ORDER BY id;",
                "Getting all records from table prices");
        return convertResultSet(result);
    }

    public List<Price> getByProductID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM prices WHERE product_id = " + id + ";",
                "Search in table prices by product_id");
        return convertResultSet(result);
    }

    public Price getCurrentByProductID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM prices WHERE product_id = " + id +
                " AND (SELECT (start_date, end_date) OVERLAPS ((SELECT CURRENT_DATE), (SELECT CURRENT_DATE))) = true",
                "Search for current price in table prices by product's id");
        List<Price> prices = convertResultSet(result);
        if (prices != null && !prices.isEmpty())
            return prices.get(0);
        else
            return null;
    }

    private List<Price> convertResultSet(ResultSet result) {
        try {
            List<Price> prices = new ArrayList<>();
            while (result.next()) {
                long id = result.getLong("id");
                long productID = result.getLong("product_id");
                double price = result.getDouble("price");
                LocalDate startDate = result.getDate("start_date").toLocalDate();
                LocalDate endDate = result.getDate("end_date").toLocalDate();
                String description = result.getString("description");
                prices.add(new Price(id, productID, price, startDate, endDate, description));
            }
            return prices;
        } catch (SQLException e) {
            System.out.println("ResultSet converting error in PriceDAO");
            e.printStackTrace();
            return null;
        }
    }
}
