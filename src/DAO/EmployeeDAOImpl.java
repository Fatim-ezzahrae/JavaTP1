package DAO;

import java.sql.*;
import java.util.ArrayList;

import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;

public class EmployeeDAOImpl implements EmployeeDAOI{
	
	private Connection conn;
	
	public EmployeeDAOImpl() {
		
		conn = DBConnection.getConnection();
		
	}

	@Override
	public Object[][] findById(int employeId) throws SQLException{
		String sql = "SELECT IdEmp, nom, prenom, email, tel, salaire, poste, role FROM Employe "
				+ "join Poste on Employe.idPoste = Poste.id "
				+ "join Role on Employe.idRole = Role.id WHERE idEmp = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, employeId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Object[][] empData = new Object[1][8]; // Assuming 1 row with 8 columns

	            empData[0][0] = rs.getInt("IdEmp");          // ID
	            empData[0][1] = rs.getString("nom");         // Nom
	            empData[0][2] = rs.getString("prenom");      // Prénom
	            empData[0][3] = rs.getString("email");       // Email
	            empData[0][4] = rs.getInt("salaire");        // Salaire
	            empData[0][5] = rs.getInt("tel");            // Téléphone
	            empData[0][6] = rs.getString("role");        // Role
	            empData[0][7] = rs.getString("poste");       // Poste

	            return empData;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return null; 
	}
	
	 int idPoste = -1;
	 int idRole = -1;

	@Override
	public void add(Employe employe) throws SQLException{
	
		String sql = "SELECT id FROM Poste where poste = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, employe.getPoste().toString());
			try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                idPoste = rs.getInt("id");
	            } else {
	                throw new SQLException("Poste not found: " + employe.getPoste().toString());
	            }
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
		String sql2 = "SELECT id FROM Role where role = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql2)) {
			
			stmt.setString(1, employe.getRole().toString());
			try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                idRole = rs.getInt("id");
	            } else {
	                throw new SQLException("Role not found: " + employe.getRole().toString());
	            }
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
			
		String sql3 = "Insert Into Employe (nom, prenom, email, tel, salaire, idPoste, idRole ) values (?,?,?,?,?,?,?)";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql3)) {
			
			stmt.setString(1, employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setInt(4, employe.getTel());
			stmt.setInt(5, employe.getSalaire());
			stmt.setInt(6, idPoste);
			stmt.setInt(7, idRole);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
	

	public void update(Employe employe, int emp_id) throws SQLException {
		
		String sql = "SELECT id FROM Poste where poste = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, employe.getPoste().toString());
			try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                idPoste = rs.getInt("id");
	            } else {
	                throw new SQLException("Poste not found: " + employe.getPoste().toString());
	            }
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
		String sql2 = "SELECT id FROM Role where role = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql2)) {
			
			stmt.setString(1, employe.getRole().toString());
			try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                idRole = rs.getInt("id");
	            } else {
	                throw new SQLException("Role not found: " + employe.getRole().toString());
	            }
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
		String sql3 = "UPDATE employe SET nom = ?, prenom = ?, email = ?, tel = ?, salaire = ?, idPoste = ?, idRole = ? where idEmp = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql3)) {
			
			stmt.setString(1, employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setInt(4, employe.getTel());
			stmt.setInt(5, employe.getSalaire());
			stmt.setInt(6, idPoste);
			stmt.setInt(7, idRole);
			stmt.setInt(8, emp_id);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw e;		
		}
		
	}
	
	public void delete(int emp_id) throws SQLException{
		
		String sql = "delete from employe where idEmp = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, emp_id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public Object[][] findAll() throws SQLException{
		
		Object[][] newData = getEmployes(); // Fetch new data
		return newData;
		
	}
	
	public ArrayList<String> getRoles() {
		
		ArrayList<String> roles = new ArrayList<>();
		
		String sql = "SELECT role FROM Role";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				roles.add(rs.getString("role"));
			}
			
		} catch (SQLException e) {
            e.printStackTrace();
        }
		
		return roles;
		
	}
	
	public ArrayList<String> getPostes() {
		
		ArrayList<String> postes = new ArrayList<>();
		
		String sql = "SELECT poste FROM Poste";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				postes.add(rs.getString("poste"));
			}
			
		} catch (SQLException e) {
            e.printStackTrace();
        }
		
		return postes;
		
	}
	
	public Object[][] getEmployes() {
		
		ArrayList<Employe> employes = new ArrayList<>();
		
		String sql = "SELECT idEmp, nom, prenom, email, salaire, tel, role, poste FROM Employe "
				+ "join Poste on Employe.idPoste = Poste.id "
				+ "join Role on Employe.idRole = Role.id";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Employe employe = new Employe(	
						rs.getInt("idEmp"),
		                rs.getString("nom"),
		                rs.getString("prenom"),
		                rs.getString("email"),
		                rs.getInt("salaire"),
		                rs.getInt("tel"),
		                Role.valueOf(rs.getString("role").toUpperCase()), 
		                Poste.valueOf(rs.getString("poste").toUpperCase()) 
		            );
				employes.add(employe);
			}
			
		} catch (SQLException e) {
            e.printStackTrace();
        }
		
		Object[][] data = new Object[employes.size()][8]; // 8 columns for the data
	    for (int i = 0; i < employes.size(); i++) {
	        Employe emp = employes.get(i);
	        data[i][0] = emp.getIdEmp();      // ID
	        data[i][1] = emp.getNom();        // Nom
	        data[i][2] = emp.getPrenom();     // Prénom
	        data[i][3] = emp.getEmail();      // Email
	        data[i][4] = emp.getSalaire();    // Salaire
	        data[i][5] = emp.getTel();        // Téléphone
	        data[i][6] = emp.getRole().toString(); // Role
	        data[i][7] = emp.getPoste().toString(); // Poste
	    }
	    
		
		return data;
		
	}

	
}
