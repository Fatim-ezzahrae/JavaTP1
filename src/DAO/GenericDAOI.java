package DAO;

import java.sql.SQLException;
import Model.Employe;

public interface GenericDAOI<T> {

	Object[][] findById(int employeeID) throws SQLException, Exception;
	
	void add(T employe) throws SQLException, Exception;
	
	void update(T employe, int emp_id) throws SQLException, Exception;
	
	void delete(T employe) throws SQLException, Exception;
	
	Object[][] findAll() throws SQLException, Exception;
	
	
}
