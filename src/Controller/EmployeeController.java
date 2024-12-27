package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import DAO.EmployeeDAOImpl;
import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;
import Model.EmployeModel;
import View.EmployeeView;

public class EmployeeController {
	
	 private EmployeModel employeModel;
	private EmployeeView view;
	private int selectedEmployeeId = -1;
	
	public EmployeeController(EmployeModel employeModel, EmployeeView view) {
        this.employeModel = employeModel;
        this.view = view;

        initializeController();
    }
	
	//method to contain the action listener of add, update, delete, and list
	private void initializeController() {
	
		//add a new employee
		view.getAddButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (!view.validateFields()) return;			               
				
				Employe employe = extractEmployeeFromFields();
				
				try {
                    employeModel.addEmployee(employe);
                    Object[][] data = employeModel.getAllEmployees();
                    view.refreshTable(data);
                    JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Failed to add employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
			}						
		});
		
		//update the selected row of the JTable
		view.getUpdateButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
						
				if (!view.validateFields()) return;
				
				if (selectedEmployeeId == -1) {
					JOptionPane.showMessageDialog(view, "Please select an employee to update.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Employe employe = extractEmployeeFromFields();
				
	            try {
                    employeModel.updateEmployee(employe, selectedEmployeeId);
                    Object[][] data = employeModel.getAllEmployees();
                    view.refreshTable(data);
                    JOptionPane.showMessageDialog(view, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Failed to update employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
				
			}
			
		});
		
		view.getJTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		view.getJTable().getSelectionModel().addListSelectionListener(e -> {
			
			if (!e.getValueIsAdjusting()) {
				int selectedRow = view.getJTable().getSelectedRow();
				
				if (selectedRow != -1) {
					
					selectedEmployeeId = (int) view.getJTable().getValueAt(selectedRow, 0);
					
					String nom = (String) view.getJTable().getValueAt(selectedRow, 1);
		            String prenom = (String) view.getJTable().getValueAt(selectedRow, 2);
		            String email = (String) view.getJTable().getValueAt(selectedRow, 3);
		            String tel = (String) view.getJTable().getValueAt(selectedRow, 4);
		            int salaire = (int) view.getJTable().getValueAt(selectedRow, 5);
		            String role = (String) view.getJTable().getValueAt(selectedRow, 6);
		            String poste = (String) view.getJTable().getValueAt(selectedRow, 7);
		            
		            view.getNom().setText(nom);
		            view.getPrenom().setText(prenom);
		            view.getEmail().setText(email);
		            view.getTel().setText(String.valueOf(tel));
		            view.getSalaire().setText(String.valueOf(salaire));
		            view.getRole().setSelectedItem(role); // Select the role in the JComboBox
		            view.getPoste().setSelectedItem(poste); 
				}
			}
			
		});
		
		view.getDeleteButton().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if (selectedEmployeeId == -1) {
		        	JOptionPane.showMessageDialog(view, "Please select an employee to delete." , "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int confirm = JOptionPane.showConfirmDialog(view, 
		            "Are you sure you want to delete this employee?", 
		            "Confirm Delete", 
		            JOptionPane.YES_NO_OPTION);

		        if (confirm == JOptionPane.YES_OPTION) {
		            try {
		                Employe employe = employeModel.getEmployeeById(selectedEmployeeId);
		                employeModel.deleteEmployee(employe);
		                selectedEmployeeId = -1;
		                Object[][] data = employeModel.getAllEmployees();
		                view.refreshTable(data);
		                JOptionPane.showMessageDialog(view, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		            } catch (Exception ex) {
		            	JOptionPane.showMessageDialog(view, "Failed to delete employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});

		
		view.getListButton().addActionListener(new ActionListener() {

			@Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Object[][] data = employeModel.getAllEmployees();
                    view.refreshTable(data);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Failed to list employees: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
			
		});
		
		view.getFindIdButton().addActionListener(new ActionListener() {

			@Override
            public void actionPerformed(ActionEvent e) {
                int id = view.requestID();

                try {
                    Object[][] employe = employeModel.findById(id);
                    view.refreshTable(employe);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Failed to list employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
			
		});
	}
	
	private Employe extractEmployeeFromFields() throws IllegalArgumentException {
	    String nom = view.getNom().getText();
	    String prenom = view.getPrenom().getText();
	    String email = view.getEmail().getText();
	    String tel = view.getTel().getText().trim();
	    String salaireStr = view.getSalaire().getText();
	    Role role = (Role) view.getRole().getSelectedItem();
	    Poste poste = (Poste) view.getPoste().getSelectedItem();
	    int salaire = 0;
	    try {
            salaire = Integer.parseInt(salaireStr);  // Convert salaire to int
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid salary", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
	    return new Employe(nom, prenom, email, tel, salaire, role, poste);
	}


}
