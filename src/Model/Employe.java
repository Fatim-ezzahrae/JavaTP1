package Model;

import java.sql.Connection;

public class Employe {

    private Connection conn;

    private String nom;
    private String email;
    private int salaire;
    private String prenom;
    private int idEmp;
    private String tel; // Changed from int to String
    private Role role;
    private Poste poste;
    private String roleSt;
    private String posteSt;
    private int holiday;

    public Employe(String nom, String prenom, String email, String tel, int salaire, Role role, Poste poste) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
        this.tel = tel; // Updated
        this.role = role;
        this.poste = poste;
        this.holiday = 25;
    }

    public Employe(String nom, String prenom, String email, String tel, int salaire, String role, String poste) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
        this.tel = tel; // Updated
        this.roleSt = role;
        this.posteSt = poste;
        this.holiday = 25;
    }

    public Employe(int idEmp, String nom, String prenom, String email, String tel, int salaire, String role, String poste) {
        this.idEmp = idEmp;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
        this.tel = tel; // Updated
        this.roleSt = role;
        this.posteSt = poste;
        this.holiday = 25;
    }

    public Employe(int idEmp, String nom, String prenom, String email, String tel, int salaire, Role role, Poste poste) {
        this.idEmp = idEmp;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.salaire = salaire;
        this.tel = tel; // Updated
        this.role = role;
        this.poste = poste;
    }

    public Employe(int idEmp, String nom, String prenom) {
        this.idEmp = idEmp;
        this.nom = nom;
        this.prenom = prenom;
    }

    public enum Poste {
        INGENIEUR_ETUDE_ET_DEVELOPPEMENT,
        TEAM_LEADER,
        PILOTE
    }

    public enum Role {
        ADMIN,
        EMPLOYE, 
        MANAGER
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getTel() { return tel; } // Updated
    public int getSalaire() { return salaire; }
    public int getIdEmp() { return idEmp; }
    public Role getRole() { return role; }
    public String getRoleSt() { return roleSt; }
    public Poste getPoste() { return poste; }
    public String getPosteSt() { return posteSt; }

    @Override
    public String toString() {
        return nom + " " + prenom; // Customize as needed
    }
}
