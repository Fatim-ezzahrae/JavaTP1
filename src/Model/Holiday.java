package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Holiday {
	
	private String DDebut;
	private String DFin;
	private HolidayType type;
	private String employe;
	private int idEmp;
	private int idType;
	private int id;
	
	public Holiday(String DDebut, String DFin, HolidayType type, String employe) {
		this.DDebut = DDebut;
		this.DFin = DFin;
		this.type = type;
		this.employe = employe;
	}
	
	public Holiday(int id, String DDebut, String DFin, HolidayType type, String employe) {
		this.id = id;
		this.DDebut = DDebut;
		this.DFin = DFin;
		this.type = type;
		this.employe = employe;
	}
	
	public Holiday(String DDebut, String DFin, int idType, int idEmp) {
		this.DDebut = DDebut;
		this.DFin = DFin;
		this.idType = idType;
		this.idEmp = idEmp;
	}
	
	public enum HolidayType {
		Payé,
		Non_payé,
		Maladie
	}
	
	public String getDDebut() {
		return DDebut;
	}
	
	public String getDFin() {
		return DFin;
	}
	
	public HolidayType getType() {
		return type;
	}
	
	public String getEmploye() {
		return employe;
	}
	
	public int getHolidayId() {
		return id;
	}
	
}
