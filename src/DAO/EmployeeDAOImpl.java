package DAO;

import java.sql.*;
import java.util.ArrayList;

import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;

public class EmployeeDAOImpl implements GenericDAOI<Employe>{
	
	private Connection conn;
	
	public EmployeeDAOImpl() {
		
		conn = DBConnection.getConnection();
		
	}

	@Override
	public Object[][] findById(int employeId) throws Exception{
		String sql = "SELECT idEmp, nom, prenom, email, tel, salaire, Role, Poste FROM Employe WHERE idEmp = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, employeId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Object[][] empData = new Object[1][8]; // Assuming 1 row with 8 columns

	            empData[0][0] = rs.getInt("idEmp");          // ID
	            empData[0][1] = rs.getString("nom");         // Nom
	            empData[0][2] = rs.getString("prenom");      // Prénom
	            empData[0][3] = rs.getString("email");       // Email
	            empData[0][4] = rs.getString("tel");            // Téléphone
	            empData[0][5] = rs.getInt("salaire");        // Salaire	            
	            empData[0][6] = rs.getString("Role");        // Role
	            empData[0][7] = rs.getString("Poste");       // Poste

	            return empData;
				
			}
			
		} catch (SQLException e) {
			throw new Exception("Error in findById emp: " + e.getMessage(), e);
		}	
		
		return null; 
	}
	
	 int idPoste = -1;
	 int idRole = -1;

	@Override
	public void add(Employe employe) throws Exception{
				
		String sql = "Insert Into Employe (nom, prenom, email, tel, salaire, Poste, Role ) values (?,?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setString(4, employe.getTel());
			stmt.setInt(5, employe.getSalaire());
			stmt.setString(6, employe.getPoste().name());
			stmt.setString(7, employe.getRole().name());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new Exception("Error in add Emp: " + e.getMessage(), e);		
		}
	}
	

	public void update(Employe employe, int emp_id) throws Exception {
		
		String sql3 = "UPDATE employe SET nom = ?, prenom = ?, email = ?, tel = ?, salaire = ?, Poste = ?, Role = ? where idEmp = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql3)) {
			
			stmt.setString(1, employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setString(4, employe.getTel());
			stmt.setInt(5, employe.getSalaire());
			stmt.setString(6, employe.getPoste().name());
			stmt.setString(7, employe.getRole().name());
			stmt.setInt(8, emp_id);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new Exception("Error in update Emp: " + e.getMessage(), e);			
		}
		
	}
	
	public Object[][] findAll() throws Exception{
		
		Object[][] newData = getEmployes(); // Fetch new data
		return newData;
		
	}
		
	public Object[][] getEmployes() throws Exception {
		
		ArrayList<Employe> employes = new ArrayList<>();
		
		String sql = "SELECT idEmp, nom, prenom, email, tel, salaire, Role, Poste FROM Employe";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Employe employe = new Employe(	
						rs.getInt("idEmp"),
		                rs.getString("nom"),
		                rs.getString("prenom"),
		                rs.getString("email"),
		                rs.getString("tel"),
		                rs.getInt("salaire"),
		                rs.getString("Role"), 
		                rs.getString("Poste") 
		            );
				employes.add(employe);
			}
			
		} catch (SQLException e) {
			throw new Exception("Error in getEmployes: " + e.getMessage(), e);	
        }
		
		Object[][] data = new Object[employes.size()][8]; // 8 columns for the data
	    for (int i = 0; i < employes.size(); i++) {
	        Employe emp = employes.get(i);
	        data[i][0] = emp.getIdEmp();      // ID
	        data[i][1] = emp.getNom();        // Nom
	        data[i][2] = emp.getPrenom();     // Prénom
	        data[i][3] = emp.getEmail();      // Email
	        data[i][4] = emp.getTel();        // Téléphone
	        data[i][5] = emp.getSalaire();    // Salaire
	        data[i][6] = emp.getRoleSt(); // Role
	        data[i][7] = emp.getPosteSt(); // Poste
	    }
	    
		
		return data;
		
	}

	@Override
	public void delete(Employe employe) throws Exception {
		String sql = "delete from employe where idEmp = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, employe.getIdEmp());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error in delete emp: " + e.getMessage(), e);
		}
		
	}
	
	public Employe getEmployeById(int emp_id) throws Exception{
		
		String sql = "SELECT nom, prenom, email, tel, salaire, Role, Poste FROM Employe where idEmp = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, emp_id);			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {				
				Employe employe = new Employe(	
		                rs.getString("nom"),
		                rs.getString("prenom"),
		                rs.getString("email"),
		                rs.getString("tel"),
		                rs.getInt("salaire"),
		                rs.getString("Role"), 
		                rs.getString("Poste") 
		            );
				return employe;
			}			
		} catch (SQLException e) {
			throw new Exception("Error in getEmployesById: " + e.getMessage(), e);
		}
		return null;
		
	}

	
	
}
