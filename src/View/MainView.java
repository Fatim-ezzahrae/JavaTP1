package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainView extends JFrame{

	private JTabbedPane tabbedPane;
	
	public MainView(EmployeeView viewEmp, HolidayView viewHol) {
		
		setTitle("Application de gestion");
		setSize(850, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Employees", viewEmp);
		tabbedPane.addTab("Congées", viewHol);
		
		add(tabbedPane);
		
		setVisible(true);
		
	}
		
}
