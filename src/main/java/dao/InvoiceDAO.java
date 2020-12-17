package dao;

import entities.Invoice;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO extends BaseDAO implements DAO<Invoice> {
    public InvoiceDAO() {
        super("invoices");
    }

    @Override
    public void createTable() {
        super.executeUpdateSQLStatement("CREATE TABLE IF NOT EXISTS invoices(" +
                "id BIGSERIAL PRIMARY KEY," +
                "invoice_date DATE NOT NULL," +
                "partner_id BIGINT NOT NULL REFERENCES partners (id)," +
                "is_release BOOLEAN NOT NULL);",
                "Checking for table invoices existence");
    }

    @Override
    public long insert(Invoice invoice) {
        return super.executeAndGetInsertID("INSERT INTO invoices(invoice_date, partner_id, is_release) VALUES" +
                "($unkey$" + Date.valueOf(invoice.getDate()) + "$unkey$, " + invoice.getPartnerID() + ", " + invoice.isRelease() + ");",
                "Added record to table invoices");
    }

    @Override
    public void delete(long id) {
        super.executeUpdateSQLStatement("DELETE FROM invoices WHERE id = " + id + ";",
                "Deleted record from table invoices");
    }

    @Override
    public void update(Invoice invoice) {
        super.executeUpdateSQLStatement("UPDATE invoices SET (invoice_date, partner_id, is_release) = " +
                "($unkey$" + Date.valueOf(invoice.getDate()) + "$unkey$, " + invoice.getPartnerID() + ", " + invoice.isRelease() + ") " +
                "WHERE id = " + invoice.getId() +  ";",
                "Updates record in table invoices");
    }

    @Override
    public Invoice getByID(long id) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM invoices WHERE id = " + id + ";",
                "Search in table invoices by id");
        List<Invoice> invoices = convertResultSet(result);
        if (invoices != null && !invoices.isEmpty())
            return invoices.get(0);
        else
            return null;
    }

    @Override
    public List<Invoice> getAll() {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM invoices ORDER BY id;",
                "Getting all records from table invoices");
        return convertResultSet(result);
    }

    public List<Invoice> getByPartnerID(long partnerID) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM invoices WHERE partner_id = " + partnerID + ";",
                "Search in table invoices by partner's id");
        return convertResultSet(result);
    }

    public List<Invoice> getByRelease(boolean isRelease) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM invoices WHERE is_release = " + isRelease + ";",
                "Search in table invoices by buying/selling");
        return convertResultSet(result);
    }

    public List<Invoice> getByDate(LocalDate date) {
        ResultSet result = super.executeQuerySQLStatement("SELECT * FROM invoices WHERE invoice_date = '$unkey$" +
                        Date.valueOf(date) + "$unkey$;",
                "Search in table invoices by date");
        return convertResultSet(result);
    }

    private List<Invoice> convertResultSet(ResultSet result) {
        try {
            List<Invoice> invoices = new ArrayList<>();
            while (result.next()) {
                long id = result.getLong("id");
                LocalDate date = result.getDate("invoice_date").toLocalDate();
                long partnerID = result.getLong("partner_id");
                boolean isRelease = result.getBoolean("is_release");
                invoices.add(new Invoice(id, date, partnerID, isRelease));
            }
            return invoices;
        } catch (SQLException e) {
            System.out.println("ResultSet converting error in InvoiceDAO");
            e.printStackTrace();
            return null;
        }
    }
}