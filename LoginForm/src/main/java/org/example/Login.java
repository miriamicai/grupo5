package org.example;

import javax.swing.*;
import java.awt.*;

public class Login extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton logInButton;
    private JButton forgotButton;
    private JButton signUpButton;
    private JPanel LoginPanel;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Login login = new Login(null);
    }
}
