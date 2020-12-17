package dao;

import entities.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends BaseDAO implements DAO<Group> {
    public GroupDAO() {
        super("groups");
    }

    @Override
    public void createTable() {
        super.executeUpdateSQLStatement("CREATE TABLE IF NOT EXISTS groups(" +
                        "id BIGSERIAL PRIMARY KEY," +
                        "name TEXT NOT NULL UNIQUE);",
                "Checking for table groups existence");
    }

    @Override
    public long insert(Group group) {
        return super.executeAndGetInsertID("INSERT INTO groups(name) VALUES($unkey$" + group.getName() + "$unkey$);",
                "Added record to table");
    }

    @Override
    public void delete(long id) {
        super.executeUpdateSQLStatement("DELETE FROM groups WHERE id = " + id + ";",
                "Deleted record from table groups");
    }

    @Override
    public void update(Group group) {
        super.executeUpdateSQLStatement("UPDATE groups SET name = $unkey$" + group.getName() + "$unkey$ " +
                "WHERE id = " + group.getId() + ";",
                "Updated record in table groups");
    }

    @Override
    public Group getByID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM groups WHERE id = " + id + ";",
                "Search in table groups by id");
        List<Group> groups = convertResultSet(result);
        if (groups != null)
            return groups.get(0);
        else
            return null;
    }

    @Override
    public List<Group> getAll() {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM groups ORDER BY id;",
                "Getting all records from table groups");
        return convertResultSet(result);
    }

    public List<Group> getByName(String name) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM groups WHERE name LIKE $unkey$%" + name + "%$unkey$;",
                "Search in table groups by name");
        return convertResultSet(result);
    }

    public int numberOfProductsInGroup(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT COUNT(id) as count FROM products WHERE group_id = " + id + ";",
                "Count records in table products for the corresponding group");
        try {
            if (result.next())
                return result.getInt("count");
            else
                return -1;
        } catch (SQLException e) {
            System.out.println("Error when counting records in table products for the corresponding group");
            e.printStackTrace();
            return -1;
        }
    }

    private List<Group> convertResultSet(ResultSet result)  {
        try {
            List<Group> groups = new ArrayList<>();
            while (result.next()) {
                long id = result.getLong("id");
                String name = result.getString("name");
                groups.add(new Group(id, name));
            }
            return groups;
        } catch (SQLException e) {
            System.out.println("ResultSet converting error in GroupDAO");
            e.printStackTrace();
            return null;
        }
    }
}
