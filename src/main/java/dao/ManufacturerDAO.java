package dao;

import entities.Manufacturer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDAO extends BaseDAO implements DAO<Manufacturer> {
    public ManufacturerDAO() {
        super("manufacturers");
    }

    @Override
    public void createTable() {
        super.executeUpdateSQLStatement("CREATE TABLE IF NOT EXISTS manufacturers(" +
                "id BIGSERIAL PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "UNIQUE(name, address));",
                "Checking for table manufacturers existence");
    }

    @Override
    public long insert(Manufacturer manufacturer) {
        return super.executeAndGetInsertID("INSERT INTO manufacturers(name, address) VALUES" +
                        "($unkey$" + manufacturer.getName() + "$unkey$, $unkey$" + manufacturer.getAddress() + "$unkey$);",
                "Added record to table manufacturers");
    }

    @Override
    public void delete(long id) {
        super.executeUpdateSQLStatement("DELETE FROM manufacturers WHERE id = " + id + ";",
                "Deleted record from table manufacturers");
    }

    @Override
    public void update(Manufacturer manufacturer) {
        super.executeUpdateSQLStatement("UPDATE manufacturers SET (name, address) = " +
                        "($unkey$" + manufacturer.getName() + "$unkey$, $unkey$" + manufacturer.getAddress() + "$unkey$) " +
                        "WHERE id = " + manufacturer.getId() + ";",
                "Updated record in table manufacturers");
    }

    @Override
    public Manufacturer getByID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM manufacturers WHERE id = " + id + ";",
                "Searching in table manufacturers by id");
        List<Manufacturer> manufacturers = convertResultSet(result);
        if (manufacturers != null)
            return manufacturers.get(0);
        else
            return null;
    }

    @Override
    public List<Manufacturer> getAll() {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM manufacturers ORDER BY id;",
                "Getting all records from table manufacturers");
        return convertResultSet(result);
    }

    public List<Manufacturer> getByName(String name) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM manufacturers WHERE name LIKE $unkey$%" + name + "%$unkey$;",
                "Search in table manufacturers by name");
        return convertResultSet(result);
    }

    public List<Manufacturer> getByAddress(String address) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM manufacturers WHERE address LIKE $unkey$%" + address + "%$unkey$;",
                "Search in table manufacturers by address");
        return convertResultSet(result);
    }

    public int numberOfProducts(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT COUNT(id) as count FROM products WHERE manufacturer_id = " + id + ";",
                "Count records in table product for the corresponding manufacturer");
        try {
            if (result.next())
                return result.getInt("count");
            else {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Error when counting records in table product for the corresponding manufacturer");
            e.printStackTrace();
            return -1;
        }
    }

    private List<Manufacturer> convertResultSet(ResultSet result) {
        try {
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (result.next()) {
                long id = result.getLong("id");
                String name = result.getString("name");
                String address = result.getString("address");
                manufacturers.add(new Manufacturer(id, name, address));
            }
            return manufacturers;
        } catch (SQLException e) {
            System.out.println("ResultSet converting error in ManufacturerDAO");
            e.printStackTrace();
            return null;
        }
    }
}
