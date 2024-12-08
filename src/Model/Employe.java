package Model;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DAO.DBConnection;

public class Employe {

	private Connection conn;
	
	private String nom;
	private String email;
	private int salaire;
	private String prenom;
	private int idEmp;
	private int tel;
	private Role role;
	private Poste poste;
	
	public Employe(String nom, String prenom, String email, int salaire, int tel, Role role, Poste poste) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.salaire = salaire;
		this.tel = tel;
		this.role = role;
		this.poste = poste;
	}
	
	public Employe(int idEmp, String nom, String prenom, String email, int salaire, int tel, Role role, Poste poste) {
        this.idEmp = idEmp;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.salaire = salaire;
        this.tel = tel;
        this.role = role;
        this.poste = poste;
    }
	
	public enum Poste {
		INGENIEUR_ETUDE_ET_DEVELOPPEMENT,
		TEAM_LEADER,
		PILOTE
	}
	
	public enum Role {
		ADMIN,
		EMPLOYE
	}
	
	public Employe() {
		this.conn = DBConnection.getConnection();
		 
		if (conn != null) {
	        System.out.println("Database connected successfully!");
	    } else {
	        System.out.println("Failed to connect to the database.");
	        JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	}
	
	
	public String getNom() { return nom; }
	public String getPrenom() { return prenom; }
	public String getEmail() { return email; }
	public Integer getTel() { return tel; }
	public Integer getSalaire() { return salaire; }
	public Integer getIdEmp() { return idEmp; }
	public Role getRole() { return role; }
	public Poste getPoste() { return poste; }
}
