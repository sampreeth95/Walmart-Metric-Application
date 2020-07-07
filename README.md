# Walmart-Metric-Application
Application to detect the outliers on User defined metrics and notifying them at scheduled time intervals

Data-Base Used: Microsoft Azure
Programming language: Java 8

## Instructions for Application Setup:
  •	Please unzip the Walmart-metrics zip file.
  •	Open the Walmart-metrics folder in your IDE.
  •	RUN the main class App.java
  
## Database Schema:
```
  ### Product Table:
  product_name	  varchar(100) 
  category	      varchar(100)
  units_sold	    varchar(100)
  curr_date	      timestamp
  ```

```
### User_Metrics Table:

  user_id	              int
  metric_name	          varchar(100)
  metric_desc	          varchar(100)
  query	               varchar(500)
```
```
### Alerts Table:
  user_id	               int	
  alerts_desc	           varchar(100)
  curr_date	             timestamp
  ```

## Application Documentation:
    ### Model (Package for creating database entities)
      •	Alerts (Creates Alert instances for notification to users)
      •	Product (Creates product instances for storing to products)
      •	User_Metric (Creates user instances with the given metrics)
    ### Utils (Package for common utilities for database creation, string generation, validation)
      •	DatabaseConnection (Makes connection with Azure DB)
      •	Scheduler (Runs user metrics at scheduled time Interval)
      •	StringGen (Generates random strings for product names)
      •	Validation (Business logic to validate user Metrics)
    ### App (Class for initiating application).

## NOTE:
  Designed Scheduler thread which checks for outlier every 15 secs
  Adding random products for every 30 secs.
  Checking outliers for every 15 secs.

## Result:
  Application checks for alerts (notification for users) for every 15sec (which can be parametrized) and prints the alerts in the console to get notified.


