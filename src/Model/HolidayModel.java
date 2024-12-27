package Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import DAO.HolidayDAOImpl;
import Model.Holiday.HolidayType;

public class HolidayModel {

	private HolidayDAOImpl dao;
	
	public HolidayModel(HolidayDAOImpl dao) {
		this.dao = dao;
	}
	
	public int PeriodBtwTwoDates(String DDate, String FDate) throws Exception {
		int period = (int) ChronoUnit.DAYS.between(LocalDate.parse(DDate), LocalDate.parse(FDate));
		if(period < 0) {
			throw new Exception("Negative duration");
		}
		return period;
	}
	
	public int HolidayPeriod(String DDate, String FDate, int holidayLeft) {
		
		int period = (int) ChronoUnit.DAYS.between(LocalDate.parse(DDate), LocalDate.parse(FDate));
		
		return 	holidayLeft - period;
	}
	
	public ArrayList<String> getEmployees() {
		return dao.getEmployees();
	}
	
	public ArrayList<HolidayType> getHolidayType() {
		return dao.getHolidayType();
	}
	
	public Object[][] getHoliday() {
		return dao.getHoliday();
	}
	
	public int calculateHoliday(String DDate, String FDate, int idEmp) throws Exception {
		int EmpHoliday = dao.getEmployeeHoliday(idEmp);
		if (EmpHoliday == -1) {
	        throw new Exception("Failed to retrieve remaining holiday balance.");
	    }
		
		int updatedHoliday = HolidayPeriod(DDate, FDate, EmpHoliday);
		if (updatedHoliday < 0) {
	        throw new Exception("Holiday period exceeds the permitted limit.");
	    }
		return updatedHoliday;
	}
	
	public int getEmployeeHoliday(int idEmp) throws Exception {
		int EmpHoliday = dao.getEmployeeHoliday(idEmp);
		if (EmpHoliday == -1) {
	        throw new Exception("Failed to retrieve remaining holiday balance.");
	    }
		return EmpHoliday;
	}
	
	public void updateEmployeeHoliday(int leftHoliday, int idEmp) {
		dao.updateEmployeeHoliday(leftHoliday, idEmp);
	}
	
	public void addHoliday(String DDate, String FDate, HolidayType holidayType, String employe) throws SQLException {		
		dao.add(new Holiday(DDate, FDate, holidayType, employe));		
	}
	
	public void updateHoliday(Holiday holiday, int id) throws SQLException {
		dao.update(holiday, id);
	}
	
	public void deleteHoliday(Holiday employe) throws SQLException {
		dao.delete(employe);
	}
	
	public int getIdEmp(String fullName) throws Exception { 
		int idEmp = dao.getIdEmp(fullName);
		if (idEmp == -1) {
	        throw new Exception("Invalid employee selection.");
	    }
	    return idEmp;
	}
	
	public Holiday getHolidayBySelectedId(int id) {
		return dao.getHolidayBySelecteId(id);
	}
	
	public static void main(String[] args) {
		HolidayDAOImpl dao = new HolidayDAOImpl();
		HolidayModel md = new HolidayModel(dao);
		int rs;
		try {
			System.out.println(md.getIdEmp("Jane Smith"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}



















