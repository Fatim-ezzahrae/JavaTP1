package Model;

import DAO.loginDAOImpl;
import Model.Employe.Role;

public class loginModel {

	private loginDAOImpl dao;
	
	public loginModel(loginDAOImpl dao) {
		this.dao = dao;
	}
	
	// Validates if the provided username and password match a valid user
    public String validateUserAndGetRole(String username, String password) throws Exception {
        // Check if the user exists and password matches, and fetch the role
        Role userRole = dao.validateUserAndGetRole(username, password);

        if (userRole == null) {
            // Throw exception if invalid login
            throw new Exception("Invalid username or password");
        }

        return userRole.name();  // Return the user's role
    }
}
