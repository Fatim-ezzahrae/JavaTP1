package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class loginView extends JFrame {

    JTextField user = new JTextField();
    JLabel userLabel = new JLabel("Username:");
    JPasswordField password = new JPasswordField();
    JLabel passLabel = new JLabel("Password:");
    JButton logInButton = new JButton("LogIn");

    public loginView() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set preferred size for text fields to make them thinner
        user.setPreferredSize(new Dimension(300, 10));
        password.setPreferredSize(new Dimension(300, 10));

        // Panel for login credentials
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(userLabel);
        panel.add(user);
        panel.add(passLabel);
        panel.add(password);

        add(panel, BorderLayout.CENTER);

        // Panel for buttons (LogIn and Add User)
        JPanel buttons = new JPanel();
        buttons.add(logInButton);

        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
    }

    public JTextField getUser() {
        return user;
    }

    public JPasswordField getPass() {
        return password;
    }

    public JButton getLoginButton() {
        return logInButton;
    }

    public static void main(String[] args) {
        new loginView();
    }
}
