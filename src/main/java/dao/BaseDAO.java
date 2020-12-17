package dao;

import java.io.Closeable;
import java.sql.*;

public class BaseDAO implements Closeable {
    Connection connection;
    String tableName;

    BaseDAO(String tableName) {
        this.tableName = tableName;
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Can't open DB connection");
            e.printStackTrace();
        }
    }

    void reopenConnection() {
        try {
            if (connection == null || connection.isClosed())
                connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Can't open DB reconnection");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Can't close DB connection");
            e.printStackTrace();
        }
    }

    void executeUpdateSQLStatement(String sql, String description) {
        try {
            reopenConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            if (description != null)
                System.out.println(description);
            close();
        } catch (SQLException e) {
            System.out.println("Can't execute statement of type 'update' in table " + tableName);
            e.printStackTrace();
        } finally {
            close();
        }
    }

    void executeUpdateSQLStatement(String sql) {
        executeUpdateSQLStatement(sql,null);
    }

    ResultSet executeQuerySQLStatement(String sql, String description) {
        try {
            reopenConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            statement.close();
            if (description != null)
                System.out.println(description);
            close();
            return result;
        } catch (SQLException e) {
            System.out.println("Can't execute statement of type 'query' in table " + tableName);
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    ResultSet executeQuerySQLStatement(String sql) {
        return executeQuerySQLStatement(sql,null);
    }

    long executeAndGetInsertID(String sql, String description) {
        try {
            reopenConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (description != null)
                System.out.println(description);
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return result.getLong(1);
            }
            else
                return 0;
        } catch (SQLException e) {
            System.out.println("Can't insert or find id of inserted object in table " + tableName);
            e.printStackTrace();
            return 0;
        } finally {
            close();
        }
    }

    long executeAndGetInsertID(String sql) {
        return executeAndGetInsertID(sql,null);
    }
}
