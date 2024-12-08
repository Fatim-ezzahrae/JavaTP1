package DAO;

import java.sql.SQLException;
import Model.Employe;

public interface EmployeeDAOI {

	Object[][] findById(int employeeID) throws SQLException;
	
	void add(Employe employe) throws SQLException;
	
	void update(Employe employe, int emp_id) throws SQLException;
	
	void delete(int employe) throws SQLException;
	
	Object[][] findAll() throws SQLException;
}
