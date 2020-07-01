package utils;

import java.sql.*;

public class DatabaseConnection {

    static Connection conn = null;

    /**
     * Creates Singleton instance of Connection class for Microsoft Azure MYSQL databse.
     * @return
     */
    public static Connection getJDBCConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://walmart-metric.mysql.database.azure.com/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true&requireSSL=false", "sampreeth@walmart-metric", "Vamsireddy.123");
//                conn = DriverManager.getConnection("jdbc:mysql://localhost/", "sboddire", "root");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return conn;
    }

    /**
     * Cretes specified Database in the given MYSQL server
     * @throws SQLException
     */
    public static void createDatabase() throws SQLException {
        Connection conn = getJDBCConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS Walmart_Metrics");
        stmt.close();
    }

}
