package model;

import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User_Metric {
    int userId;
    String description;
    String metricName;
    String query;

    public User_Metric() {}
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    //Defines User_Metric entity in database.
    public static void createUserEntity() {
        try {
            Connection conn = DatabaseConnection.getJDBCConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Walmart_Metrics.USER_METRICS (user_id INTEGER (100), metric_name VARCHAR (100), metric_desc VARCHAR (100), query VARCHAR (500))");
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Inserts user_metric instance into database.
    public void saveUserMetric(int userId, String metricName, String description, String query) {
        try {
            Connection conn = DatabaseConnection.getJDBCConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Walmart_Metrics.USER_METRICS (user_id, metric_name, metric_desc, query) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, userId);
            stmt.setString(2, metricName);
            stmt.setString(3, description);
            stmt.setString(4, query);
            stmt.executeUpdate();
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Retrieves all the defined metrics.
    public List<User_Metric> getUserMetrics() {
        List<User_Metric> users = new ArrayList();
        try {
            Connection conn = DatabaseConnection.getJDBCConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT user_id, metric_name, metric_desc, query FROM Walmart_Metrics.USER_METRICS");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                User_Metric user = new User_Metric();
                user.setUserId(rs.getInt(1));
                user.setMetricName(rs.getString(2));
                user.setDescription(rs.getString(3));
                user.setQuery(rs.getString(4));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return users;
        }
        return users;
    }
}
