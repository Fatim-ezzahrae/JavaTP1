package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import DAO.EmployeeDAOImpl;
import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;
import View.EmployeeView;

public class EmployeeContoller {
	
	private EmployeeDAOImpl employeeDAO;
	private EmployeeView view;
	private int selectedEmployeeId = -1;
	
	public EmployeeContoller(EmployeeDAOImpl  employeeDAO, EmployeeView view) {
		this.employeeDAO = employeeDAO;
		this.view = view;
		
		initializeController();
	}
	
	//method to contain the action listener of add, update, delete, and list
	private void initializeController() {
	
		//add a new employee
		view.getAddButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
								
				String nom = view.getNom().getText();
				String prenom = view.getPrenom().getText();
				String email = view.getEmail().getText();
				String telStr = view.getTel().getText();
				String salaireStr = view.getSalaire().getText();
				Role role = Role.valueOf(view.getRole());
				Poste poste = Poste.valueOf(view.getPoste());
				
				int tel = 0;
				int salaire = 0;
				
				try {
	                tel = Integer.parseInt(telStr);  // Convert tel to int
	            } catch (NumberFormatException ex) {
	                // Handle invalid tel input (optional: display an error message)
	                System.out.println("Invalid telephone number");
	                return; // Exit the method if the input is invalid
	            }

	            try {
	                salaire = Integer.parseInt(salaireStr);  // Convert salaire to int
	            } catch (NumberFormatException ex) {
	                // Handle invalid salaire input (optional: display an error message)
	                System.out.println("Invalid salary");
	                return; // Exit the method if the input is invalid
	            }
				
				Employe employe = new Employe(nom, prenom, email, salaire, tel, role, poste);
				
				try {
					employeeDAO.add(employe);
					Object[][] data = employeeDAO.findAll();
					view.refreshTable(data);
					JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
	                // If an exception is thrown, it indicates a failure
	                JOptionPane.showMessageDialog(view, "Failed to add employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
			}						
		});
		
		//update the selected row of the JTable
		view.getUpdateButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
						
				if (selectedEmployeeId == -1) {
					JOptionPane.showMessageDialog(view, "Please select an employee to update.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String nom = view.getNom().getText();
				String prenom = view.getPrenom().getText();
				String email = view.getEmail().getText();
				String telStr = view.getTel().getText();
				String salaireStr = view.getSalaire().getText();
				Role role = Role.valueOf(view.getRole());
				Poste poste = Poste.valueOf(view.getPoste());
				
				int tel = 0;
				int salaire = 0;
				
				try {
	                tel = Integer.parseInt(telStr);  // Convert tel to int
	            } catch (NumberFormatException ex) {
	                // Handle invalid tel input (optional: display an error message)
	                System.out.println("Invalid telephone number");
	                return; // Exit the method if the input is invalid
	            }

	            try {
	                salaire = Integer.parseInt(salaireStr);  // Convert salaire to int
	            } catch (NumberFormatException ex) {
	                // Handle invalid salaire input (optional: display an error message)
	                System.out.println("Invalid salary");
	                return; // Exit the method if the input is invalid
	            }
	            
	            Employe employe = new Employe(nom, prenom, email, salaire, tel, role, poste);
				
				try {
					employeeDAO.update(employe, selectedEmployeeId);
					Object[][] data = employeeDAO.findAll();
					view.refreshTable(data);
					JOptionPane.showMessageDialog(view, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
	                // If an exception is thrown, it indicates a failure
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
		            int tel = (int) view.getJTable().getValueAt(selectedRow, 4);
		            int salaire = (int) view.getJTable().getValueAt(selectedRow, 5);
		            String role = (String) view.getJTable().getValueAt(selectedRow, 6);
		            String poste = (String) view.getJTable().getValueAt(selectedRow, 7);
		            
		            view.getNom().setText(nom);
		            view.getPrenom().setText(prenom);
		            view.getEmail().setText(email);
		            view.getTel().setText(String.valueOf(tel));
		            view.getSalaire().setText(String.valueOf(salaire));
		            view.getRoleComboBox().setSelectedItem(role); // Select the role in the JComboBox
		            view.getPosteComboBox().setSelectedItem(poste); 
				}
			}
			
		});
		
		view.getDeleteButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int id = (selectedEmployeeId == -1) ? view.requestID() : selectedEmployeeId;

			    if (id != -1) {
			        try {
			            employeeDAO.delete(id);
			            Object[][] data = employeeDAO.findAll();
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
					Object[][] data = employeeDAO.findAll();
					view.refreshTable(data);
				} catch(Exception e1) {
					 JOptionPane.showMessageDialog(view, "Failed to list employee: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		
		view.getFindIdButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int id = view.requestID();
				
				try {
					Object[][] employe = employeeDAO.findById(id);
					view.refreshTable(employe);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(view, "Failed to list employee: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
	}
	


}
