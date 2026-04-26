# SupplyChainManagement-JAVA-Project


This is a Java desktop application that simulates a procurement and delivery tracking system. I built this to demonstrate how to use core data structures, multithreading, and a database in a single Java Swing project.

## Features
- **Delivery Tracking (Threads):** The app uses Java `Runnable` background threads to simulate delivery trucks driving, so the main UI doesn't freeze.
- **Thread Safety:** I used `SwingUtilities.invokeLater()` and synchronized lists to make sure the background threads can safely update the console safely.
- **Data Structures utilized:**
    - **Queues:** Used for the `ProcurementQueue` so orders are processed first-in, first-out.
    - **Stacks:** Used for the `DeliveryLog` so the most recent deliveries sit at the top of the records.
- **Database:** Connects to a local MySQL database using pure JDBC to save vendors, orders, and shipment logs.

## Technologies Used
- Java
- Java Swing (UI)
- MySQL Server + JDBC Driver

## Setup Instructions

1. Make sure MySQL is running on your computer.
2. Open your MySQL command line and run: 
   sql
      CREATE DATABASE supply_chain_db;
   
3. Open `src/DatabaseConnection.java` and type in your local MySQL password.
4. Add the MySQL JDBC Connector `.jar` file to your project.
5. To compile and run:
   bash
     cd src
     javac *.java
     java SCMSGUI
   

## How to use it
Once the dashboard opens, you can place a supply order by filling out the form. Click "Send Delivery Truck" to poll the Queue and start the background thread. Once the truck finishes its route, click "Confirm Truck Arrival" to push it onto the delivery Stack.
