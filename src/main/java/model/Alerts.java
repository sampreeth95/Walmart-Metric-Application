package model;

import javafx.scene.control.Alert;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creates Alert instances for notification to users
 */
public class Alerts {
    int userId;
    String alert;
    Date date;

    public Alerts(int userId, String alert) {
        this.userId = userId;
        this.alert = alert;
    }

    public Alerts() {}

    /**
     * Creates Alert Enitity in the Database
     */
    public static void createAlertEntity() {
        try {
            Connection conn = DatabaseConnection.getJDBCConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Walmart_Metrics.ALERTS (user_id INTEGER(100), alerts_desc VARCHAR (100), curr_date TIMESTAMP )");
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts latest alerts into Database if any outliers found
     * @param alerts
     */
    public void insertAlerts(List<Alerts> alerts) {
        Connection conn = DatabaseConnection.getJDBCConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Walmart_Metrics.ALERTS(user_id, alerts_desc, curr_date) VALUES (?, ?, ?)");
            for(Alerts each : alerts) {
                ps.setInt(1, each.userId);
                ps.setString(2, each.alert);
                date = new Date();
                ps.setTimestamp(3, new Timestamp(date.getTime()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrives lastest Alerts after the given timestamp.
     * @param date
     * @return
     */
    public List<Alerts> getNewAlerts(Date date) {
        List<Alerts> alerts = new ArrayList();
        try {
            Connection conn = DatabaseConnection.getJDBCConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT user_id, alerts_desc, curr_date FROM Walmart_Metrics.ALERTS WHERE curr_date > ? ");
            stmt.setTimestamp(1, new Timestamp(date.getTime()));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Alerts alert = new Alerts();
                alert.setUserId(rs.getInt(1));
                alert.setAlert(rs.getString(2));
                alert.setDate(rs.getDate(3));
                alerts.add(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return alerts;
        }
        return alerts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
