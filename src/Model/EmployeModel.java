package Model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.EmployeeDAOImpl;

public class EmployeModel {

    private EmployeeDAOImpl employeDAO;

    public EmployeModel(EmployeeDAOImpl employeDAO) {
        this.employeDAO = employeDAO;
    }

    // Get all employees as an Object[][] for UI rendering
    public Object[][] getAllEmployees() throws Exception {
        return employeDAO.findAll();
    }

    // Add a new employee with validation
    public void addEmployee(Employe employe) throws Exception {
        validateEmployee(employe);

        employeDAO.add(employe);
    }

    public List<Employe> listEmployes() {
    	try {
			return employeDAO.ListEmployes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    // Update an existing employee
    public void updateEmployee(Employe employe, int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID.");
        }
        validateEmployee(employe);

        employeDAO.update(employe, id);
    }

    // Delete an employee 
    public void deleteEmployee(Employe employe) throws Exception {
        if (employe == null || employe.getIdEmp() <= 0) {
            throw new IllegalArgumentException("Invalid employee object or ID.");
        }

        try {
            employeDAO.delete(employe);
        } catch (SQLException e) {
            throw new Exception("Error while deleting employee: " + e.getMessage(), e);
        }
    }
    
    public void importData(String filePath) throws IOException {
    	employeDAO.importData(filePath);
    }
    
    public void exportData(String filePath, List<Employe> data) throws IOException {
    	employeDAO.exportData(filePath, data);
    }

    // Validate employee data
    private void validateEmployee(Employe employe) throws Exception {
        if (employe == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (employe.getNom() == null || employe.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty.");
        }
        if (employe.getPrenom() == null || employe.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee first name cannot be null or empty.");
        }
        if (employe.getEmail() == null || !employe.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        if (employe.getTel() == null || 
            !employe.getTel().matches("^0[67][0-9]{8}$")) {
            throw new IllegalArgumentException("Phone number must be 10 digits and start with 06 or 07.");
        }
        if (employe.getSalaire() <= 0) {
            throw new IllegalArgumentException("Salary must be a positive number.");
        }
    }

    
 // Get an employee by ID
    public Employe getEmployeeById(int emp_id) throws Exception {
        if (emp_id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID.");
        }

        try {
            Employe employe = employeDAO.getEmployeById(emp_id);
            if (employe == null) {
                throw new Exception("Employee not found with ID: " + emp_id);
            }
            return employe;
        } catch (SQLException e) {
            throw new Exception("Error while retrieving employee: " + e.getMessage(), e);
        }
    }
    
    public int getEmpId(String fullName) {
    	return employeDAO.getEmpId(fullName);
    }
    
	public boolean addUser(int id, String username, String hashedPassword) {
		return employeDAO.addUser(id, username, hashedPassword);
	}
    
    public ArrayList<Employe> ListEmployes_NomPre() throws Exception {
    	return employeDAO.ListEmployes_NomPre();
    }
    
    public Object[][] findById(int employeId) throws Exception{
    	return employeDAO.findById(employeId);
    }
    
    private boolean checkFieExists(File file) {
    	if(!file.exists()) {
    		throw new IllegalArgumentException("Le fichier n'existe pas: " + file.getPath());
    	}
    	return true;
    }
    
    private boolean checkIsFile(File file) {
    	if(!file.isFile()) {
    		throw new IllegalArgumentException("Le chemin spécifié n'est pas un fichier : " + file.getPath());
    	}
    	return true;
    }
    
    private boolean checkIsReadable (File file) {
    	if(!file.canRead()) {
    		throw new IllegalArgumentException("Le fichier n'est pas lisible: " + file.getPath());
    	}
    	return true;
    }
    
}
