package utils;

import model.Alerts;
import model.User_Metric;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Validation {
    public Validation() {}

    /**
     * The Logical Unit to validate outliers.
     * Retrives all Alerts if any present.
     * @param users
     * @param prev
     * @return
     */
    public List<Alerts> validateUsers(List<User_Metric> users, Date prev) {
        List<Alerts> alerts = new ArrayList<>();
        Connection conn = DatabaseConnection.getJDBCConnection();
        for (User_Metric metric : users) {
            String metricName = metric.getMetricName();
            String query = metric.getQuery();
            try {
                if(metric.getUserId() ==  1)
                    query += " AND curr_date > '" + new Timestamp(prev.getTime()) +"'";
                else if(metric.getUserId() == 4)
                    query += " WHERE units_sold > 10000 AND curr_date > '" + new Timestamp(prev.getTime()) +"'";
                else
                    query += " WHERE curr_date > '" + new Timestamp(prev.getTime()) +"'";

                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    boolean res = false;
                    switch (metricName) {
                        case "AVG":
                            double avg = rs.getDouble(1);
                            int result = validateAVG(avg);
                            if(result == 1) {
                                Alerts alert = new Alerts();
                                alert.setUserId(metric.getUserId());
                                alert.setAlert(" FOUND AVERAGE SALES MORE THAN EXPECTED, AVERAGE VALUE RECORDED IS: "+ avg);
                                alerts.add(alert);
                            }
                            if(result == -1) {
                                Alerts alert = new Alerts();
                                alert.setUserId(metric.getUserId());
                                alert.setAlert(" FOUND AVERAGE SALES LESS THAN EXPECTED, AVERAGE VALUE RECORDED IS: " + avg);
                                alerts.add(alert);
                            }
                            break;
                        case "MIN":
                            int min = rs.getInt(1);
                            res = validateMIN(min);
                            if(res) {
                                Alerts alert = new Alerts();
                                alert.setUserId(metric.getUserId());
                                alert.setAlert("FOUND MINIMUM SALES LESS THAN EXPECTED MINIMUM RECORDED IS : "+min);
                                alerts.add(alert);
                            }
                            break;
                        case "MAX":
                            int max = rs.getInt(1);
                            res = validateMAX(max);
                            if(res) {
                                Alerts alert = new Alerts();
                                alert.setUserId(metric.getUserId());
                                alert.setAlert("FOUND MAXIMUM SALES MORE THAN EXPECTED MAXIMUM RECORDED IS : "+max);
                                alerts.add(alert);
                            }
                            break;
                        case "COUNT":
                            int count = rs.getInt(1);
                            res = validateCOUNT(count);
                            break;
                        case "SUM":
                            int sum = rs.getInt(1);
                            int result1 = validateSUM(sum);
                            if(result1 == 1) {
                                Alerts alert = new Alerts();
                                alert.setUserId(metric.getUserId());
                                alert.setAlert(" FOUND TOTAL SALES MORE THAN EXPECTED, TOTAL VALUE RECORDED IS: "+ sum);
                                alerts.add(alert);
                            }
                            if(result1 == -1) {
                                Alerts alert = new Alerts();
                                alert.setUserId(metric.getUserId());
                                alert.setAlert(" FOUND TOTAL SALES LESS THAN EXPECTED, TOTAL VALUE RECORDED IS: " + sum);
                                alerts.add(alert);
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return alerts;
    }

    //Logical unit for validating AVG metric
    public int validateAVG(double avg) {
        double lowOutlier = 5.0;
        double highOutlier = 100.00;
        return highOutlier < avg ?  1 : lowOutlier > avg ? -1 : 0;
    }

    //Logical unit for validating MIN metric
    public boolean validateMIN(int min) {
        int outlier = 5;
        return min < outlier;
    }

    //Logical unit for validating MAX metric
    public boolean validateMAX(int max) {
        int outlier = 100;
        return outlier < max;
    }

    //Logical unit for validating SUM metric
    public int validateSUM(int sum) {
        int lowOutlier = 10;
        int highOutlier = 10000;
        return highOutlier < sum ?  1 : lowOutlier > sum ? -1 : 0;
    }

    public boolean validateCOUNT(int count) {
        return false;
    }
}
