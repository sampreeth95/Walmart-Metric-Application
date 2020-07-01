package utils;

import model.Alerts;
import model.User_Metric;

import java.util.Date;
import java.util.List;

public class Scheduler extends Thread {
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(15000);
                System.out.println("CHECKING ALERTS");
                User_Metric user = new User_Metric();
                Validation validation = new Validation();
                Alerts  alert = new Alerts();
                Date prev = new Date();
                Date prev1 = new Date(System.currentTimeMillis() - 31000);

                //Retrives User Defined Metrics
                List<User_Metric> users = user.getUserMetrics();

                //Validates all the products against the given metrics and retrieves Alerts
                List<Alerts> alerts = validation.validateUsers(users, prev1);

                //Inserts alerts into database.
                alert.insertAlerts(alerts);

                //Retrieves latest alerts after the given timestamp.
                List<Alerts> newAlerts = alert.getNewAlerts(prev);
                if(newAlerts.size() == 0)
                    System.out.println("NO OUTLIERS");
                for(Alerts al : newAlerts) {
                    System.out.println("ALERT FOR USER ID : " + al.getUserId()+ " DESCRIPTION : "+ al.getAlert());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
