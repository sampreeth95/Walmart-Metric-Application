package model;

import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Product Entity to store the details of sales of each product along with Category.
 */
public class Product {
    String productName;
    String category;
    int unitsSold;
    Date curr_date;

    public Product() {}

    /**
     * Defines Product Entity in the database.
     */
    public static void createProductEntity() {
        try {
            Connection conn = DatabaseConnection.getJDBCConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Walmart_Metrics.PRODUCT (product_name VARCHAR(100), category VARCHAR (100), units_sold VARCHAR (100), curr_date TIMESTAMP )");
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts product instances int to database.
     * @param prodName
     * @param category
     * @param unitsSold
     */
    public void save(String prodName, String category, int unitsSold) {
        try {
            Connection conn = DatabaseConnection.getJDBCConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Walmart_Metrics.PRODUCT (product_name, category, units_sold, curr_date) VALUES (?, ?, ?, ?)");
            stmt.setString(1, prodName);
            stmt.setString(2, category);
            stmt.setInt(3, unitsSold);
            stmt.setTimestamp(4, new Timestamp(new Date().getTime()));
            stmt.executeUpdate();
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }
}
