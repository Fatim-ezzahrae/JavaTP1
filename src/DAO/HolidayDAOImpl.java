package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import Model.Employe;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayDAOImpl implements GenericDAOI<Holiday>{

	private Connection conn;
	
	public HolidayDAOImpl() {
		
		conn = DBConnection.getConnection();
	}
	
	@Override
	public Object[][] findById(int employeeID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	int idType = -1;
	
	@Override
	public void add(Holiday employe) throws SQLException {
		
		String sql = "SELECT id from typeholiday where type = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, employe.getType().toString());
			try(ResultSet rs = stmt.executeQuery()) {
				if(rs.next()) {
					idType = rs.getInt("id");
				} else {
	                throw new SQLException("Type not found: " + employe.getType().toString());
	            }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		String sql1 = "INSERT INTO holiday(DDebut, DFin, idType, idEmp) VALUES (?, ?, ?, ?)";
		try(PreparedStatement stmt = conn.prepareStatement(sql1)) {
			
			stmt.setString(1,employe.getDDebut());
			stmt.setString(2,employe.getDFin());
			stmt.setInt(3, idType);
			stmt.setInt(4, getIdEmp(employe.getEmploye()));
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
	}

	@Override
	public void update(Holiday employe, int emp_id) throws SQLException {
		String sql = "SELECT id from typeholiday where type = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, employe.getType().toString());
			try(ResultSet rs = stmt.executeQuery()) {
				if(rs.next()) {
					idType = rs.getInt("id");
				} else {
	                throw new SQLException("Type not found: " + employe.getType().toString());
	            }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql1 = "UPDATE holiday SET idEmp = ?, idType = ?, DDebut = ?, DFin = ? WHERE id = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql1)) {
			
			stmt.setInt(1, getIdEmp(employe.getEmploye()));
			stmt.setInt(2, idType);
			stmt.setString(3, employe.getDDebut());
			stmt.setString(4, employe.getDFin());
			stmt.setInt(5, emp_id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Holiday employe) throws SQLException {
		String sql = "DELETE FROM holiday WHERE id = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, employe.getHolidayId());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
	        throw new SQLException("Failed to delete holiday with ID: " + employe.getHolidayId(), e);
		}
		
	}

	@Override
	public Object[][] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	// employees names for the JComboBox
	public ArrayList<String> getEmployees() {
		
		ArrayList<String> employes = new ArrayList<>();
		
		String sql = "SELECT nom, prenom from Employe";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				
                employes.add(nom + " " + prenom);
			}						
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return employes;
	}
	
	//holiday types for the JComboBox
	public ArrayList<HolidayType> getHolidayType() {
		
		ArrayList<HolidayType> types = new ArrayList<>();
		
		String sql = "SELECT type from typeholiday";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String type = rs.getString("type").trim();
				try {
					types.add(HolidayType.valueOf(type));
				} catch (IllegalArgumentException e) {
					System.err.println("Invalid holiday type in database: " + type);
				}
			}			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return types;		
	}
	
	//holidays list for the Jtable
	public Object[][] getHoliday() {
		
		String sql = "SELECT holiday.id, CONCAT(employe.nom, ' ', employe.prenom) AS emp, employe.idEmp, "
		           + "holiday.DDebut, holiday.DFin, typeholiday.type "
		           + "FROM holiday "
		           + "JOIN employe ON employe.idEmp = holiday.idEmp "
		           + "JOIN typeholiday ON typeholiday.id = holiday.idType";

		
		ArrayList<Object[]> holidayList = new ArrayList<>();
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Object[] row = new Object[6];  // 6 because there are 6 columns
				row[0] = rs.getInt("id");
				row[1] = rs.getString("emp");
	            row[2] = rs.getString("DDebut");
	            row[3] = rs.getString("DFin");
	            row[4] = rs.getString("type");
	            row[5] = rs.getInt("idEmp");
	            
	            
		        
		        holidayList.add(row);
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return holidayList.toArray(new Object[0][0]);
	}
	
	//to get the number of holiday days left for the employee with this id
	public int getEmployeeHoliday(int idEmp) {
		
		String sql = "select holiday from employe where idEmp = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, idEmp);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
	            int holidayLeft = rs.getInt("holiday");
	            return holidayLeft;
	        }
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	//update the holiday of the employee after getting one
	public void updateEmployeeHoliday(int leftHoliday, int idEmp) {
		
		String sql = "UPDATE Employe SET holiday = ? WHERE idEmp = ?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, leftHoliday);
			stmt.setInt(2, idEmp);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	int id = -1;
	public int getIdEmp(String fullName) {
		
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
	
	public Holiday getHolidayBySelecteId(int id) {
		
		String sql = "SELECT DDebut, DFin, idEmp, idType FROM holiday WHERE id = ?";
		Holiday holiday = null;
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				holiday = new Holiday(
						rs.getString("DDebut"),
						rs.getString("DFin"),
						rs.getInt("idType"),
						rs.getInt("idEmp")
						);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return holiday;
	}
	
	public static void main(String[] args) {
		HolidayDAOImpl dao = new HolidayDAOImpl();
		System.out.println(dao.getIdEmp("Jane Smith"));
	}
	
	
}

























