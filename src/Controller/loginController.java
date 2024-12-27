package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import Model.loginModel;
import View.EmployeeView;
import View.HolidayView;
import View.loginView;

public class loginController {

    private loginModel model;
    private loginView view;
    private EmployeeView employeeView;
    private HolidayView holidayView;
    private String userRole;
    private Runnable loginSuccessListener; // To notify when login is successful
    
    public loginController(loginModel model, loginView view, EmployeeView employeeView, HolidayView holidayView) {
        this.model = model;
        this.view = view;
        this.employeeView = employeeView;
        this.holidayView = holidayView;
        initializeController();
    }
    
    public void initializeController() {
        
        view.getLoginButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String user = view.getUser().getText();
                String pass = view.getPass().getText();

                try {
                    // Validate user and get the role
                    userRole = model.validateUserAndGetRole(user, pass);
                    
                    if (userRole != null) {
                        // Pass the role to EmployeeView and HolidayView
                        employeeView.setUserRole(userRole);
                        holidayView.setUserRole(userRole);
                        
                        // Notify login success
                        if (loginSuccessListener != null) {
                            loginSuccessListener.run(); // Notify that login is successful
                        }
                    } else {
                        JOptionPane.showMessageDialog(view, "Invalid login credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Login failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
     // Add action listener for "Add User" button
        view.getAddUserButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for a new username and password
                String newUser = JOptionPane.showInputDialog(view, "Enter new username:");
                String newPass = JOptionPane.showInputDialog(view, "Enter new password:");

                if (newUser != null && newPass != null) {
                    // Hash the password before storing it
                    String hashedPassword = BCrypt.hashpw(newPass, BCrypt.gensalt());

                    try {
                        // Call the model method to add the user to the database
                        boolean success = model.addUser(newUser, hashedPassword);

                        if (success) {
                            JOptionPane.showMessageDialog(view, "User added successfully!");
                        } else {
                            JOptionPane.showMessageDialog(view, "Failed to add user.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Error adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    
    // Set the listener for login success
    public void setLoginSuccessListener(Runnable loginSuccessListener) {
        this.loginSuccessListener = loginSuccessListener;
    }
}