import Controller.EmployeeContoller;
import DAO.EmployeeDAOImpl;
import View.EmployeeView;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
		EmployeeView view = new EmployeeView();
		new EmployeeContoller(employeeDAO, view);
	}

}
