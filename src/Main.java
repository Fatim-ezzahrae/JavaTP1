import Controller.EmployeeController;
import Controller.HolidayController;
import Controller.loginController;
import DAO.EmployeeDAOImpl;
import DAO.HolidayDAOImpl;
import DAO.loginDAOImpl;
import Model.EmployeModel;
import Model.HolidayModel;
import Model.loginModel;
import View.EmployeeView;
import View.HolidayView;
import View.MainView;
import View.loginView;

public class Main {

    public static void main(String[] args) {
        // Initialize login components
        loginDAOImpl dao = new loginDAOImpl();
        loginModel loginModel = new loginModel(dao);
        loginView loginView = new loginView();
        
        // Initialize the DAO for Employee
        EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
        EmployeModel employeModel = new EmployeModel(employeeDAO);
        EmployeeView employeeView = new EmployeeView(); // Create employee view
        
        // Initialize the DAO for Holiday
        HolidayDAOImpl holidayDAO = new HolidayDAOImpl();
        HolidayModel holidayModel = new HolidayModel(holidayDAO);
        HolidayView holidayView = new HolidayView(); // Create holiday view

        // Initialize the controllers
        loginController loginController = new loginController(loginModel, loginView, employeeView, holidayView);
        EmployeeController employeeController = new EmployeeController(employeModel, employeeView);
        HolidayController holidayController = new HolidayController(holidayModel, holidayView);
        
        // Show the login view first
        loginView.setVisible(true);
        
        // Initialize the MainView after successful login
        // Here, you'll switch to MainView after login success
        loginController.setLoginSuccessListener(new Runnable() {
            @Override
            public void run() {
                // Hide the login view
                loginView.setVisible(false);
                
                // Show the MainView with EmployeeView and HolidayView in tabs
                new MainView(employeeView, holidayView).setVisible(true);
            }
        });
    }
}
