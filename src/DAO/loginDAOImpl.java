package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

import Model.Employe.Role;

public class loginDAOImpl {
	
	private Connection conn;
	
	public loginDAOImpl() {
		conn = DBConnection.getConnection();
	}
	
	// Check if the user exists and the password matches the hashed password in the database
    public Role validateUserAndGetRole(String username, String password) {
        String sql = "SELECT login.idEmp, password, Role FROM login "
        		+ "join employe on employe.idEmp = login.idEmp"
        		+ " WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Retrieve the stored hashed password and the user's role
                String storedHash = rs.getString("password");
                String roleStr = rs.getString("Role");

                // Check if the input password matches the stored hashed password
                if (BCrypt.checkpw(password, storedHash)) {
                    // Convert the role string to the Role enum
                    try {
                        return Role.valueOf(roleStr.toUpperCase()); // Return the role as an enum
                    } catch (IllegalArgumentException e) {
                        // Handle invalid role string (if any)
                        System.out.println("Invalid role: " + roleStr);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if login fails or role is invalid
    }
    
    public boolean addUser(String username, String hashedPassword) {
        String sql = "INSERT INTO login (idEmp, username, password) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, 22);
            stmt.setString(2, username);
            stmt.setString(3, hashedPassword);
            int rowsInserted = stmt.executeUpdate();
            
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
