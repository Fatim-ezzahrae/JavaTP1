package View;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.EmployeeDAOImpl;
import Model.Employe.Poste;
import Model.Employe.Role;
import Model.EmployeModel;

public class EmployeeView extends JPanel{
	
	JButton addButton = new JButton("Ajouter");
	JButton updateButton = new JButton("Modifier");
	JButton deleteButton = new JButton("Supprimer");
	JButton listButton = new JButton("Afficher");
	JButton findIdButton = new JButton("Trouver");
	JTextField nom = new JTextField(20);
	JTextField prenom = new JTextField(20);
	JTextField email = new JTextField(20);
	JTextField tel = new JTextField(20);
	JTextField salaire = new JTextField(20);
	JTable tbl;
	DefaultTableModel tableModel; // default Jtable so I can refresh it without recreating it
	String[] tableColumns = {"ID", "Nom", "Prénom", "Email", "tel", "Salaire",  "Role", "Poste"};
	JComboBox<Role> rolesCombo;
	JComboBox<Poste> postesCombo;
	
	public EmployeeView() {
		rolesCombo = new JComboBox<>(Role.values());
		postesCombo = new JComboBox<>(Poste.values());
		
		tableModel = new DefaultTableModel(new Object[][] {}, tableColumns);
		tbl = new JTable(tableModel);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    tbl.setFillsViewportHeight(true);
		
		
		
		JPanel data = new JPanel();
		data.setLayout(new BorderLayout());
		
		JPanel entries = new JPanel();
		entries.setLayout(new GridLayout(7,2));
		entries.add(new JLabel("Nom:"));
		entries.add(nom);
		entries.add(new JLabel("Prénom:"));
		entries.add(prenom);
		entries.add(new JLabel("Email:"));
		entries.add(email);
		entries.add(new JLabel("Téléphone:"));
		entries.add(tel);
		entries.add(new JLabel("Salaire:"));
		entries.add(salaire);
		entries.add(new JLabel("Role:"));
		entries.add(rolesCombo);
		entries.add(new JLabel("Poste:"));
		entries.add(postesCombo);
		
		data.add(entries, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(tbl);	
		tbl.setPreferredScrollableViewportSize(new Dimension(800, 200));  // Adjust as needed
		applyTableColumnWidths();
		JPanel table = new JPanel();
		table.add(scroll);
		data.add(table, BorderLayout.SOUTH);
		
		add(data, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(addButton);
		buttons.add(updateButton);
		buttons.add(deleteButton);
		buttons.add(listButton);
		buttons.add(findIdButton);
		
		add(buttons, BorderLayout.SOUTH);
	}	
	
	// refresh the JTable
	public void refreshTable(Object[][] newData) {
	    
	    tableModel.setDataVector(newData, tableColumns);
	    
	    applyTableColumnWidths();
	}

	private void applyTableColumnWidths() {
	    tbl.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID: very small
	    tbl.getColumnModel().getColumn(1).setPreferredWidth(80);  // Nom
	    tbl.getColumnModel().getColumn(2).setPreferredWidth(80);  // Prénom
	    tbl.getColumnModel().getColumn(3).setPreferredWidth(160); // Email
	    tbl.getColumnModel().getColumn(4).setPreferredWidth(80);  // Tel
	    tbl.getColumnModel().getColumn(5).setPreferredWidth(60);  // Salaire
	    tbl.getColumnModel().getColumn(6).setPreferredWidth(100); // Role
	    tbl.getColumnModel().getColumn(7).setPreferredWidth(200); // Poste
	}
	
	public int requestID() {
		JLabel Id = new JLabel("ID:");
		JTextField field = new JTextField(20);
		JPanel panel = new JPanel();
		panel.add(Id);
		panel.add(field);
		
		// Show a custom dialog with the panel
        int option = JOptionPane.showConfirmDialog(null, panel, "Input ID", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
        	String input = field.getText().trim();
        	
        	if(!input.isEmpty()) {
        		try {
        			int id = Integer.parseInt(input);
        			return id;
        		} catch(NumberFormatException e) {
        			e.printStackTrace();
        			return -1;
        		}
        	}else {
        		JOptionPane.showMessageDialog(null, "ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        		return -1;
        	}
        } else {
        	return -1;
        }
	}
	
	public boolean validateFields() {
	    if (nom.getText().isEmpty() || prenom.getText().isEmpty() || email.getText().isEmpty() || tel.getText().isEmpty() || salaire.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    try {
	        Integer.parseInt(tel.getText());
	        Integer.parseInt(salaire.getText());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Telephone and Salaire must be numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    return true;
	}

	public void setUserRole(String userRole) {
	    // Check the role and hide the buttons accordingly
	    if (userRole.equals("MANAGER")) {
	        addButton.setVisible(true);
	        updateButton.setVisible(true);
	        deleteButton.setVisible(true);
	    } else {
	        addButton.setVisible(false);
	        updateButton.setVisible(false);
	        deleteButton.setVisible(false);
	    }
	}

	public JButton getAddButton() {
        return addButton;
    }
	
	public JButton getUpdateButton() {
        return updateButton;
    }
	
	public JButton getDeleteButton() {
        return deleteButton;
    }
	
	public JButton getListButton() {
        return listButton;
    }
	
	public JButton getFindIdButton() {
        return findIdButton;
    }
	
	public JTextField getNom() {
		return nom;
	}
	
	public JTextField getPrenom() {
		return prenom;
	}
	
	public JTextField getEmail() {
		return email;
	}
	
	public JTextField getTel() {
		return tel;
	}
	
	public JTextField getSalaire() {
		return salaire;
	}
	
	public JComboBox<Role> getRole() {
	    return rolesCombo;
	}

	public JComboBox<Poste> getPoste() {
	    return postesCombo;
	}
	
	public JTable getJTable() {
		return tbl;
	}
	
	
}
