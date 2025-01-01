package View;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.HolidayDAOImpl;
import Model.Employe;
import Model.Holiday.HolidayType;


public class HolidayView extends JPanel{

	HolidayDAOImpl dao = new HolidayDAOImpl();
	
	
	JButton addButton = new JButton("Ajouter");
	JButton updateButton = new JButton("Modifier");
	JButton deleteButton = new JButton("Supprimer");
	JButton importButton = new JButton("Importer avec congé");
	JButton exportButton = new JButton("Exporter avec congé");
	
	JTextField dateDebut = new JTextField("yyyy-MM-dd");
	JTextField dateFin = new JTextField("yyyy-MM-dd");
	JComboBox<String> employees;
	JComboBox<HolidayType> typeHoliday;
	
	JTable tbl;
	DefaultTableModel tableModel; // default Jtable so I can refresh it without recreating it
	String[] tableColumns = {"ID", "Employé", "Date de début", "Date de fin", "Type", "idEmp"};
	
	public HolidayView() {
		
		
		
		
		ArrayList<String> employes = dao.getEmployees();
		employees = new JComboBox<>(employes.toArray(new String[0]));
		
		ArrayList<HolidayType> types = dao.getHolidayType();
		typeHoliday = new JComboBox<>(types.toArray(new HolidayType[0]));
		
		JPanel data = new JPanel();
		data.setLayout(new BorderLayout());
		
		JPanel entries = new JPanel();
		entries.setLayout(new GridLayout(4,2));
		
		entries.add(new JLabel("Nom de l'employé:"));
		entries.add(employees);
		entries.add(new JLabel("Type:"));
		entries.add(typeHoliday);
		entries.add(new JLabel("Date de début:"));
		entries.add(dateDebut);
		entries.add(new JLabel("Date de fin:"));
		entries.add(dateFin);
		
		data.add(entries, BorderLayout.CENTER);
		
		
		tableModel = new DefaultTableModel(dao.getHoliday(), tableColumns);
		tbl = new JTable(tableModel);
	    tbl.setFillsViewportHeight(true);
	    
	    //hide the idEmp row
	    hideEmpColumn();
	    
		JScrollPane scroll = new JScrollPane(tbl);	
		tbl.setPreferredScrollableViewportSize(new Dimension(700, 150));  // Adjust as needed
		JPanel table = new JPanel();
		table.add(scroll);
		
		data.add(table, BorderLayout.SOUTH);
		
		add(data, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(addButton);
		buttons.add(updateButton);
		buttons.add(deleteButton);
		buttons.add(importButton);
		buttons.add(exportButton);
		
		add(buttons, BorderLayout.SOUTH);
		
	}
	
	// refresh the JTable
	public void refreshTable(Object[][] newData) {
	    
	    tableModel.setDataVector(newData, tableColumns);
	    hideEmpColumn();
	}
	
	public void hideEmpColumn() {
		tbl.getColumnModel().getColumn(5).setMinWidth(0);
	    tbl.getColumnModel().getColumn(5).setMaxWidth(0);
	    tbl.getColumnModel().getColumn(5).setPreferredWidth(0);
	    tbl.getColumnModel().getColumn(5).setResizable(false);
	}
	
	public void setUserRole(String userRole) {
	    // Check the role and hide the buttons accordingly
	    if (userRole.equals("MANAGER")) {
	        addButton.setVisible(true);
	        updateButton.setVisible(true);
	        deleteButton.setVisible(true);
	        importButton.setVisible(true);
	        exportButton.setVisible(true);
	    } else {
	        addButton.setVisible(false);
	        updateButton.setVisible(false);
	        deleteButton.setVisible(false);
	        importButton.setVisible(false);
	        exportButton.setVisible(false);
	    }
	}
	
	public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
	
	public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
	
	public String getEmploye() {
		return (String) employees.getSelectedItem();
	}
	
	public JComboBox<String> getEmployeCombo() {
		return employees;
	}
	
	public HolidayType getHolidayType() {
		return (HolidayType) typeHoliday.getSelectedItem();
	}
	
	public JComboBox<HolidayType> getHolidayTypeCombo() {
		return typeHoliday;
	}
	
	public JTextField getDDebut() {
		return dateDebut;
	}
	
	public JTextField getDFin() {
		return dateFin;
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
	
	public JButton getImportButton() {
        return importButton;
    }
	
	public JButton getExportButton() {
        return exportButton;
    }
	
	public JTable getJTable() {
		return tbl;
	}
}
