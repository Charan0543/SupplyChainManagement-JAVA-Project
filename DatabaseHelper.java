// DatabaseHelper.java
// Contains all database operations (insert and display)
// Uses JDBC - PreparedStatement and ResultSet

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHelper {

    Connection con;

    // Constructor - get the connection
    public DatabaseHelper() {
        con = DatabaseConnection.getConnection();
    }

    // -----------------------------------------------
    // Create all tables (run once at start)
    // -----------------------------------------------
    public void createTables() {
        try {
            Statement stmt = con.createStatement();

            // vendors table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS vendors (" +
                            "vendor_id VARCHAR(10) PRIMARY KEY, " +
                            "name VARCHAR(100), " +
                            "rating DOUBLE)"
            );

            // purchase_orders table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS purchase_orders (" +
                            "po_id VARCHAR(10) PRIMARY KEY, " +
                            "vendor_id VARCHAR(10), " +
                            "deadline DATE, " +
                            "status VARCHAR(20))"
            );

            // delivery_log table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS delivery_log (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "details VARCHAR(200), " +
                            "logged_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
            );

            // shipments table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS shipments (" +
                            "shipment_id VARCHAR(10) PRIMARY KEY, " +
                            "origin VARCHAR(100), " +
                            "destination VARCHAR(100), " +
                            "status VARCHAR(20))"
            );

            System.out.println("All tables created successfully.");

        } catch (Exception e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Insert vendor into database
    // -----------------------------------------------
    public void insertVendor(Vendor v) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO vendors VALUES (?, ?, ?)"
            );
            ps.setString(1, v.getVendorId());
            ps.setString(2, v.getName());
            ps.setDouble(3, v.getRating());
            ps.executeUpdate();
            System.out.println("Vendor saved to DB: " + v.getVendorId());

        } catch (Exception e) {
            System.out.println("Insert vendor error: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Insert purchase order into database
    // -----------------------------------------------
    public void insertOrder(PurchaseOrder po) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO purchase_orders (po_id, vendor_id, deadline, status) VALUES (?, ?, ?, ?)"
            );
            ps.setString(1, po.getPoId());
            ps.setString(2, po.getVendorId());
            ps.setDate  (3, new java.sql.Date(po.getDeadlineDate().getTime()));
            ps.setString(4, "PENDING");
            ps.executeUpdate();
            System.out.println("Order saved to DB: " + po.getPoId());

        } catch (Exception e) {
            System.out.println("Insert order error: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Insert delivery log into database
    // -----------------------------------------------
    public void insertDeliveryLog(String details) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO delivery_log (details) VALUES (?)"
            );
            ps.setString(1, details);
            ps.executeUpdate();
            System.out.println("Delivery log saved to DB.");

        } catch (Exception e) {
            System.out.println("Insert log error: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Insert shipment into database
    // -----------------------------------------------
    public void insertShipment(String shipmentId, String origin, String destination) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO shipments VALUES (?, ?, ?, ?)"
            );
            ps.setString(1, shipmentId);
            ps.setString(2, origin);
            ps.setString(3, destination);
            ps.setString(4, "DELIVERED");
            ps.executeUpdate();
            System.out.println("Shipment saved to DB: " + shipmentId);

        } catch (Exception e) {
            System.out.println("Insert shipment error: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Display all vendors from database
    // -----------------------------------------------
    public void showVendors() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vendors");

            System.out.println("===== VENDORS FROM DATABASE =====");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("vendor_id") +
                        " | Name: " + rs.getString("name") +
                        " | Rating: " + rs.getDouble("rating"));
            }

        } catch (Exception e) {
            System.out.println("Show vendors error: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Display all orders from database
    // -----------------------------------------------
    public void showOrders() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM purchase_orders");

            System.out.println("===== ORDERS FROM DATABASE =====");
            while (rs.next()) {
                System.out.println("PO: " + rs.getString("po_id") +
                        " | Status: " + rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("Show orders error: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Display all deliveries from database
    // -----------------------------------------------
    public void showDeliveryLog() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM delivery_log");

            System.out.println("===== DELIVERY LOG FROM DATABASE =====");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " +
                        rs.getString("details") +
                        " | " + rs.getTimestamp("logged_at"));
            }

        } catch (Exception e) {
            System.out.println("Show log error: " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // Display all shipments from database
    // -----------------------------------------------
    public void showShipments() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shipments");

            System.out.println("===== SHIPMENTS FROM DATABASE =====");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("shipment_id") +
                        " | " + rs.getString("origin") +
                        " -> " + rs.getString("destination") +
                        " | Status: " + rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("Show shipments error: " + e.getMessage());
        }
    }
}
