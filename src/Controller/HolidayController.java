package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import DAO.HolidayDAOImpl;
import Model.Employe;
import Model.Holiday;
import Model.Holiday.HolidayType;
import Model.HolidayModel;
import View.HolidayView;

public class HolidayController {

	private HolidayModel model;
	private HolidayView view;
	
	private int selectedHolidayId = -1; // Default value when no row is selected
	
	public HolidayController(HolidayModel model, HolidayView view) {
		
		this.model = model;
		this.view = view;
		
		initializeController();
		
	}
	
	
	private void initializeController() {
		
		view.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String DDate = view.getDDebut().getText();
				String FDate = view.getDFin().getText();
				String employe = view.getEmploye();	
				
				try {
					
					int idEmp = model.getIdEmp(employe);
					int updatedHoliday = model.calculateHoliday(DDate, FDate, idEmp);
					
					if(updatedHoliday > 0) {
						
						HolidayType holidayType = view.getHolidayType();
						
						model.addHoliday(DDate, FDate, holidayType, employe);
						model.updateEmployeeHoliday(updatedHoliday, idEmp);
						JOptionPane.showMessageDialog(view, "Holiday added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
						view.refreshTable(model.getHoliday());
					}
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(view, "Failed to add holiday: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		view.getUpdateButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String DDate = view.getDDebut().getText();
				String FDate = view.getDFin().getText();				
				
				String employe = view.getEmploye();
				
				try {
					int idEmp = model.getIdEmp(employe);
					Holiday originalHoliday = model.getHolidayBySelectedId(selectedHolidayId);
					if(originalHoliday != null) {
						String ODDate = originalHoliday.getDDebut();
						String OFDate = originalHoliday.getDFin();
						
						HolidayType holidayType = view.getHolidayType();
						
						boolean isDateChanged = !ODDate.equals(DDate) || !OFDate.equals(FDate);
						
						Holiday holiday = new Holiday(DDate, FDate, holidayType, employe);
						
						if(isDateChanged) {
							int updatedLeftHoliday = model.getEmployeeHoliday(idEmp)
		                            + model.PeriodBtwTwoDates(ODDate, OFDate)
		                            - model.PeriodBtwTwoDates(DDate, FDate);
							model.updateHoliday(holiday, selectedHolidayId);
							model.updateEmployeeHoliday(updatedLeftHoliday, idEmp);
						} else {
							model.updateHoliday(holiday, selectedHolidayId);
						}
						JOptionPane.showMessageDialog(view, "Holiday updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
						view.refreshTable(model.getHoliday());
					} else {
		                JOptionPane.showMessageDialog(view, "No holiday selected to update!", "Error", JOptionPane.ERROR_MESSAGE);
		            }
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(view, "Failed to update holiday: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}	
				
			}
		});
		
		
		view.getDeleteButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String DDate = view.getDDebut().getText();
				String FDate = view.getDFin().getText();
				String employe = view.getEmploye();
				HolidayType holidayType = view.getHolidayType();
				
				try {
					
					model.deleteHoliday(new Holiday(selectedHolidayId, DDate, FDate, holidayType, employe));
					JOptionPane.showMessageDialog(view, "Holiday deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
					view.refreshTable(model.getHoliday());
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(view, "Failed to delete holiday: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		
		view.getJTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		view.getJTable().getSelectionModel().addListSelectionListener(e -> {
			if(!e.getValueIsAdjusting()) {
				int selectedRow = view.getJTable().getSelectedRow();
				
				if(selectedRow !=  -1) {
					
					selectedHolidayId = (int) view.getJTable().getValueAt(selectedRow, 0);
					
					String employe = (String) view.getJTable().getValueAt(selectedRow, 1);
					String DDate = (String) view.getJTable().getValueAt(selectedRow, 2);
					String FDate = (String) view.getJTable().getValueAt(selectedRow, 3);
					String Type = (String) view.getJTable().getValueAt(selectedRow, 4);					
				
		            view.getEmployeCombo().setSelectedItem(employe);
					view.getDDebut().setText(DDate);
					view.getDFin().setText(FDate);
					view.getHolidayTypeCombo().setSelectedItem(HolidayType.valueOf(Type));
				}
			}
		});
		
		
	}
	
}









