package dao;

import java.sql.*;

public class DBConnection {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/warehouse";
    public static final String DB_Driver = "org.postgresql.Driver";
    public static boolean isDriverLoad = false;

    public static void loadDriver() {
        try {
            Class.forName(DB_Driver);
            isDriverLoad = true;
            System.out.println("DB Driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find DB Driver");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!isDriverLoad)
            loadDriver();
        return DriverManager.getConnection(DB_URL,"postgres","user");
    }
}
