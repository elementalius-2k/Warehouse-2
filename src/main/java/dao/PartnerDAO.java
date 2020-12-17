package dao;

import entities.Partner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartnerDAO extends BaseDAO implements DAO<Partner> {
    public PartnerDAO() {
        super("partners");
    }

    @Override
    public void createTable() {
        super.executeUpdateSQLStatement("CREATE TABLE IF NOT EXISTS partners(" +
                "id BIGSERIAL PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "payment_account TEXT NOT NULL CHECK (LENGTH(payment_account) = 20)," +
                "address TEXT NOT NULL," +
                "email TEXT NOT NULL);",
                "Checking for table partners existence");
    }

    @Override
    public long insert(Partner partner) {
        return super.executeAndGetInsertID("INSERT INTO partners(name, payment_account, address, email) VALUES" +
                "($unkey$" + partner.getName() + "$unkey$, $unkey$" + partner.getPaymentAccount() +
                "$unkey$, $unkey$" + partner.getAddress() + "$unkey$, $unkey$" + partner.getEmail() + "$unkey$);",
                "Added record to table partners");
    }

    @Override
    public void delete(long id) {
        super.executeUpdateSQLStatement("DELETE FROM partners WHERE id = " + id + ";",
                "Deleted record form table partners");
    }

    @Override
    public void update(Partner partner) {
        super.executeUpdateSQLStatement("UPDATE partners SET (name, payment_account, address, email) = " +
                "($unkey$" + partner.getName() + "$unkey$, $unkey$" + partner.getPaymentAccount() + "$unkey$, $unkey$" +
                partner.getAddress() + "$unkey$, $unkey$" + partner.getEmail() + "$unkey$) WHERE id = " + partner.getId() + ";",
                "Updated record in table partners");
    }

    @Override
    public Partner getByID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM partners WHERE id = " + id + ";",
                "Search it table partners by id");
        List<Partner> partners = convertResultSet(result);
        if (partners != null && !partners.isEmpty())
            return partners.get(0);
        else
            return null;
    }

    @Override
    public List<Partner> getAll() {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM partners ORDER BY id;",
                "Getting all records from table partners");
        return convertResultSet(result);
    }

    public List<Partner> getByName(String name) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM partners WHERE name LIKE $unkey$%" + name + "%$unkey$;",
                "Search in table partners by name");
        return convertResultSet(result);
    }

    public int numberOfInvoices(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT COUNT(id) as count FROM invoices WHERE partner_id = " + id + ";",
                "Count records in table invoices for the corresponding partner");
        try {
            if (result.next())
                return result.getInt("count");
            else
                return -1;
        } catch (SQLException e) {
            System.out.println("Error when counting records in table invoices for the corresponding partner");
            e.printStackTrace();
            return -1;
        }
    }

    private List<Partner> convertResultSet(ResultSet result) {
        try {
            List<Partner> partners = new ArrayList<>();
            while (result.next()) {
                long id = result.getLong("id");
                String name = result.getString("name");
                String paymentAccount = result.getString("payment_account");
                String address = result.getString("address");
                String email = result.getString("email");
                partners.add(new Partner(id, name, paymentAccount, address, email));
            }
            return partners;
        } catch (SQLException e) {
            System.out.println("ResultSet converting error in PartnerDAO");
            e.printStackTrace();
            return null;
        }
    }
}
