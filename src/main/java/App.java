import model.Alerts;
import utils.DatabaseConnection;
import model.Product;
import model.User_Metric;
import utils.Scheduler;
import utils.StringGen;
import utils.Validation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    public static int time;
    public void setTime(int time) {
        this.time = time;
    }
    public static void main(String[] args) throws SQLException {
        App app = new App();
        app.setTime(1000);

        //Create all the requires tables
        app.createEntities();

        //Defines all user metrics for each functionality
        app.addUserMetrics();

        //Starts the Scheduler thread which checks for outliers for every 15 secs
        new Scheduler().start();
        int count = 0;

        //Application adds new products for every 30secs.
        while(true) {
            app.addProducts();
            try {
                System.out.println("ADDED NEW PRODUCTS");
                count++;
                Thread.sleep(30000);
                if(count % 3 == 0) {
                    System.out.println("ADDING OUTLIERS");
                    app.addProductOutliers();
                }
            }
            catch (Exception e) {};
        }

    }
    public void addUserMetrics() {
        User_Metric user = new User_Metric();
        user.saveUserMetric(1, "COUNT", "Count Metric", "SELECT COUNT(units_sold) from Walmart_Metrics.PRODUCT where category in ('Electronics')");
        user.saveUserMetric(2, "SUM", "Sum Metric", "SELECT SUM(units_sold) from Walmart_Metrics.PRODUCT");
        user.saveUserMetric(3, "MIN", "Min Metric", "SELECT MIN(units_sold) from Walmart_Metrics.PRODUCT");
        user.saveUserMetric(4, "MAX", "Maximum Metric", "SELECT MAX(units_sold) from Walmart_Metrics.PRODUCT");
        user.saveUserMetric(5, "AVG", "Average Metric", "SELECT AVG(units_sold) from Walmart_Metrics.PRODUCT");
    }

    /**
     * Creates all the required Entities for the Application
     * @throws SQLException
     */
    public void createEntities() throws SQLException{
        DatabaseConnection.createDatabase();
        Product.createProductEntity();
        User_Metric.createUserEntity();
        Alerts.createAlertEntity();
    }

    /**
     * Adds Random products with specified category into Product Table
     */
    public void addProducts() {
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Electronics");
        categoryList.add("Food");
        categoryList.add("Clothing");
        categoryList.add("Footwear");
        categoryList.add("Groceries");
        categoryList.add("Furniture");
        Product prod = new Product();
        for(int i = 1; i <= 20; i++) {
            String prodName = StringGen.generateRandomChars();
            String category = categoryList.get(i % 6);
            int unitsSold = (i) * (2);
            prod.save(prodName, category, unitsSold);
        }
    }

    /**
     * Adding outliers for Verification of the Application
     */
    public void addProductOutliers() {
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Electronics");
        categoryList.add("Food");
        categoryList.add("Clothing");
        categoryList.add("Footwear");
        categoryList.add("Groceries");
        categoryList.add("Furniture");
        Product prod = new Product();
        for(int i = 1; i <= 4; i++) {
            String prodName = StringGen.generateRandomChars();
            String category = categoryList.get(i % 6);
            int unitsSold = (i * 20000) + (i / 7);
            if(i == 4)
                unitsSold = 0;

            prod.save(prodName, category, unitsSold);
        }
    }
}
