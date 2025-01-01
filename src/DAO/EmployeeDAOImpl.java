package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;

public class EmployeeDAOImpl implements GenericDAOI<Employe>,  DataImportExport<Employe>{
	
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

	public List<Employe> ListEmployes() throws Exception {
		
		List<Employe> employes = new ArrayList<>();
		
		String sql = "SELECT nom, prenom, email, tel, salaire, Role, Poste FROM Employe";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
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
				employes.add(employe);
			}
			
		} catch (SQLException e) {
			throw new Exception("Error in getEmployes: " + e.getMessage(), e);	
        }
		return employes;
		
	}
	
	int id = -1;
	public int getEmpId(String fullName) {
		String[] nameParts = fullName.split(" ", 2); // Split into two parts: first and rest
	    String nom = nameParts[0]; // The first part is the last name (nom)
	    String prenom = nameParts.length > 1 ? nameParts[1] : "";
	    String sql = "SELECT idEmp from employe where nom = ? and prenom = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("idEmp");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
		
	}
	
	public boolean addUser(int id, String username, String hashedPassword) {
        String sql = "INSERT INTO login (idEmp, username, password) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, id);
            stmt.setString(2, username);
            stmt.setString(3, hashedPassword);
            int rowsInserted = stmt.executeUpdate();
            
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
	public void importData(String filePath) throws IOException {
		String sql = "Insert Into Employe (nom, prenom, email, tel, salaire, Poste, Role ) values (?,?,?,?,?,?,?)";
		System.out.println("Importing data from: " + filePath);

		try(BufferedReader reader = new BufferedReader(new FileReader(filePath)); PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    System.out.println("File successfully opened.");
		    String line = reader.readLine();
		    System.out.println("First Line: " + line);
		    if (line == null) {
		        System.out.println("The file is empty.");
		    }
		    while((line = reader.readLine()) != null) {
		        System.out.println("Read line: " + line);
		        String[] data = line.split(",");
		        System.out.println("Data length: " + data.length); 
		        if(data.length == 7) {
		            System.out.println("Processing line: " + line);
		            pstmt.setString(1, data[0].trim());
		            pstmt.setString(2, data[1].trim());
		            pstmt.setString(3, data[2].trim());
		            pstmt.setString(4, data[3].trim());
		            pstmt.setString(5, data[4].trim());
		            pstmt.setString(6, data[5].trim());
		            pstmt.setString(7, data[6].trim());
		            pstmt.addBatch();
		        } else {
		            System.out.println("Skipping line due to incorrect number of data fields");
		        }
		    }
		    pstmt.executeBatch();
		    System.out.println("Employees imported successfully!");            
		} catch (IOException | SQLException e) {
		    e.printStackTrace();    
		}
	}

	@Override
	public void exportData(String filePath, List<Employe> data) throws IOException {
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			
			writer.write("Nom, Prenom, Email, Tel, Salaire, Poste, Role");
			writer.newLine();
			for (Employe employe : data) {
				String line = String.format("%s, %s, %s, %s, %d, %s, %s",
						employe.getNom(),
						employe.getPrenom(),
						employe.getEmail(),
						employe.getTel(),
						employe.getSalaire(),
						employe.getPosteSt(),
						employe.getRoleSt()
						);
				writer.write(line);
				writer.newLine();
			}
			
		} 
		
	}

	public ArrayList<Employe> ListEmployes_NomPre() throws Exception {
		
		ArrayList<Employe> employes = new ArrayList<>();
		
		String sql = "SELECT nom, prenom FROM Employe";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {				
				Employe employe = new Employe(rs.getString("nom"), rs.getString("prenom"));
	            employes.add(employe);
			}
			
		} catch (SQLException e) {
			throw new Exception("Error in getEmployes: " + e.getMessage(), e);	
        }
		return employes;
		
	}
	
	
}
