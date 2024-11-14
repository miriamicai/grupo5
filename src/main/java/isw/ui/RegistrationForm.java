package isw.ui;

import isw.cliente.Cliente;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class RegistrationForm implements ActionListener {

    JFrame frame;
    JLabel signLabel = new JLabel("Sign up to");
    JLabel soulLabel = new JLabel("find your ...");
    JLabel logoLabel = new JLabel();  // JLabel for the logo

    String[] gender = {"Male", "Female"};
    JLabel nameLabel = new JLabel("Name");
    JLabel usernameLabel = new JLabel("Username: ");
    JLabel genderLabel = new JLabel("Gender");
    JLabel passwordLabel = new JLabel("Password");
    JLabel confirmPasswordLabel = new JLabel("Confirm Password");
    JLabel cityLabel = new JLabel("City");
    JLabel emailLabel = new JLabel("Email");

    JTextField usernameTextField = new JTextField();
    JTextField nameTextField = new JTextField();
    JComboBox genderComboBox = new JComboBox(gender);
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    JTextField cityTextField = new JTextField();
    JTextField emailTextField = new JTextField();

    JButton registerButton = new JButton("Register");
    JButton resetButton = new JButton("Reset");

    RegistrationForm() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Registration Form");
        frame.setBounds(40, 40, 380, 600);
        frame.getContentPane().setBackground(new Color(32, 32, 32));
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        Font defaultFont = nameLabel.getFont();
        Font newFont = defaultFont.deriveFont(Font.BOLD, 30);
        signLabel.setFont(newFont);
        soulLabel.setFont(newFont);

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon("src/main/resources/soulmatelogosinfondo.png");
        logoLabel.setIcon(logoIcon);  // Set the icon for the logo

        // Set bounds for each component
        logoLabel.setBounds(110, 0, 50, 50);  // Adjust size and location as needed
        signLabel.setBounds(110, 100, 180, 70);
        soulLabel.setBounds(105, 140, 180, 70);
        usernameLabel.setBounds(20, 200, 100,70);
        nameLabel.setBounds(20, 220, 100, 70);
        genderLabel.setBounds(20, 250, 100, 70);
        passwordLabel.setBounds(20, 300, 100, 70);
        confirmPasswordLabel.setBounds(20, 350, 140, 70);
        cityLabel.setBounds(20, 400, 100, 70);
        emailLabel.setBounds(20, 450, 100, 70);

        // Set bounds for text fields
        usernameTextField.setBounds(180, 200, 165, 23);
        nameTextField.setBounds(180, 220, 165, 23);
        genderComboBox.setBounds(180, 270, 165, 23);
        passwordField.setBounds(180, 320, 165, 23);
        confirmPasswordField.setBounds(180, 370, 165, 23);
        cityTextField.setBounds(180, 420, 165, 23);
        emailTextField.setBounds(180, 470, 165, 23);
        registerButton.setBounds(70, 520, 100, 35);
        resetButton.setBounds(220, 520, 100, 35);

        // Set label colors
        usernameLabel.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        signLabel.setForeground(Color.WHITE);
        soulLabel.setForeground(Color.WHITE);
        genderLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setForeground(Color.WHITE);
        cityLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
    }

    public void addComponentsToFrame() {
        frame.add(logoLabel);  // Add the logo to the frame
        frame.add(usernameLabel);
        frame.add(nameLabel);
        frame.add(signLabel);
        frame.add(soulLabel);
        frame.add(genderLabel);
        frame.add(passwordLabel);
        frame.add(confirmPasswordLabel);
        frame.add(cityLabel);
        frame.add(emailLabel);
        frame.add(usernameTextField);
        frame.add(nameTextField);
        frame.add(genderComboBox);
        frame.add(passwordField);
        frame.add(confirmPasswordField);
        frame.add(cityTextField);
        frame.add(emailTextField);
        frame.add(registerButton);
        frame.add(resetButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add functionality for the buttons here
        if(e.getSource() == registerButton){
            Cliente cliente = new Cliente();
            String username = usernameTextField.getText();
            String name = nameTextField.getText();
            String gender = genderComboBox.getSelectedItem().toString();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String city = cityTextField.getText();
            String email = emailTextField.getText();

            if(username.isEmpty()||name.isEmpty() || password.isEmpty() ||
                    confirmPassword.isEmpty() || city.isEmpty() || email.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Por favor rellena todos los campos.", "Formulatio incompleto", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Contraseñas no coinciden..", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                cliente.registerUser(username, name, email, password);
                JOptionPane.showMessageDialog(frame, "¡Registrado con éxito!", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource()==resetButton) {
            usernameTextField.setText("");
            nameTextField.setText("");
            genderComboBox.setSelectedIndex(0);
            passwordField.setText("");
            confirmPasswordField.setText("");
            cityTextField.setText("");
            emailTextField.setText("");
        }
    }

    /*public static void main(String[] args) {
        new RegistrationForm();
    }*/
}
