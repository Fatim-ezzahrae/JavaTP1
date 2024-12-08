package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String url = "jdbc:mysql://localhost:3306/gestionemp";
    private static final String user = "root";
    private static final String password = "";
    private static Connection conn = null; // Define the connection variable

    // Method to get the connection
    public static Connection getConnection() {
        if (conn == null) { // Check if connection is null
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish the connection
                conn = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error lors de la connexion");
            }
        }
        return conn;
    }
}
