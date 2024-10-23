import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class RegistrationForm implements ActionListener {

    JFrame frame;
    JLabel signLabel = new JLabel("Sign up to");
    JLabel soulLabel = new JLabel("find your ...");
    JLabel logoLabel = new JLabel();  // JLabel for the logo

    String[] gender = {"Male", "Female"};
    JLabel nameLabel = new JLabel("Name");
    JLabel genderLabel = new JLabel("Gender");
    JLabel passwordLabel = new JLabel("Password");
    JLabel confirmPasswordLabel = new JLabel("Confirm Password");
    JLabel cityLabel = new JLabel("City");
    JLabel emailLabel = new JLabel("Email");

    JTextField nameTextField = new JTextField();
    JComboBox genderComboBox = new JComboBox(gender);
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    JTextField cityTextField = new JTextField();
    JTextField emailTextField = new JTextField();

    JButton registerButton = new JButton("Register");
    JButton resetButton = new JButton("Reset");

    // Constructor to set up the UI
    RegistrationForm() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();  // Add action listeners
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Registration Form");
        frame.setBounds(40, 40, 380, 600);
        frame.getContentPane().setBackground(new Color(32, 32, 32));
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        Font defaultFont = nameLabel.getFont();
        Font newFont = defaultFont.deriveFont(Font.BOLD, 30);
        signLabel.setFont(newFont);
        soulLabel.setFont(newFont);

        // Load the logo image
       /*ImageIcon logoIcon = new ImageIcon("/Users/salomerivas/Desktop/COMILLAS/ING DE SOFTWARE/Java Login/soulmatemini.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setBounds(30, 10, 80, 80);  */ 

        // Set bounds for each component
        signLabel.setBounds(120, 5, 180, 70);
        soulLabel.setBounds(120, 40, 180, 70);

        nameLabel.setBounds(35, 235, 100, 70);
        nameTextField.setBounds(180, 260, 165, 23);

        genderLabel.setBounds(35, 275, 100, 70);
        genderComboBox.setBounds(180, 300, 165, 23);

        passwordLabel.setBounds(35, 315, 100, 70);
        passwordField.setBounds(180, 340, 165, 23);

        confirmPasswordLabel.setBounds(35, 355, 140, 70);
        confirmPasswordField.setBounds(180, 380, 165, 23);

        cityLabel.setBounds(35, 395, 100, 70);
        cityTextField.setBounds(180, 420, 165, 23);

        emailLabel.setBounds(35, 435, 100, 70);
        emailTextField.setBounds(180, 460, 165, 23);    
       
        registerButton.setBounds(70, 525, 100, 35);
        resetButton.setBounds(220, 525, 100, 35);

        // Set label colors
        nameLabel.setForeground(Color.WHITE);
        signLabel.setForeground(Color.WHITE);
        soulLabel.setForeground(Color.WHITE);
        genderLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setForeground(Color.WHITE);
        cityLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        genderComboBox.setBackground(Color.WHITE);
    }

    public void addComponentsToFrame() {
        frame.add(logoLabel);  // Add the logo to the frame
        frame.add(nameLabel);
        frame.add(signLabel);
        frame.add(soulLabel);
        frame.add(genderLabel);
        frame.add(passwordLabel);
        frame.add(confirmPasswordLabel);
        frame.add(cityLabel);
        frame.add(emailLabel);
        frame.add(nameTextField);
        frame.add(genderComboBox);
        frame.add(passwordField);
        frame.add(confirmPasswordField);
        frame.add(cityTextField);
        frame.add(emailTextField);
        frame.add(registerButton);
        frame.add(resetButton);
    }

    public void actionEvent() {
        //Adding Action Listener to buttons
        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // Registration logic: collect form data
            String name = nameTextField.getText();
            String gender = genderComboBox.getSelectedItem().toString();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String city = cityTextField.getText();
            String email = emailTextField.getText();
    
            // Check if all fields are filled
            if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || city.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Simulate successful registration (you can add your database logic here)
                JOptionPane.showMessageDialog(frame, "Registration Successful! Welcome, " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
    
                // Clear fields after successful registration
                nameTextField.setText("");
                genderComboBox.setSelectedItem("Male");
                passwordField.setText("");
                confirmPasswordField.setText("");
                cityTextField.setText("");
                emailTextField.setText("");
            }
        } else if (e.getSource() == resetButton) {
            // Clear the fields when reset is clicked
            nameTextField.setText("");
            genderComboBox.setSelectedItem("Male");
            passwordField.setText("");
            confirmPasswordField.setText("");
            cityTextField.setText("");
            emailTextField.setText("");
        }
    }
    

    // Main method correctly placed within the class
    public static void main(String[] args) {
        new RegistrationForm();
    }
}


