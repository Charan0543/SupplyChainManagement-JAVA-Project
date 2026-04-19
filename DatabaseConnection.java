
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {

    // Database details - change password to your MySQL password
    static String url      = "jdbc:mysql://localhost:3306/supply_chain_db";
    static String user     = "root";
    static String password = "your_password"; // <-- change this

    static Connection con = null;

    // Method to get connection
    public static Connection getConnection() {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully.");

        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return con;
    }

    // Method to close connection
    public static void closeConnection() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Database connection closed.");
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

}
