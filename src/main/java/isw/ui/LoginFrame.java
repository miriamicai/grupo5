package isw.ui;

import isw.domain.AutentifCustomer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginFrame extends JFrame {

    Container container = getContentPane();
    JLabel logLabel = new JLabel("Log in to");
    JLabel userLabel = new JLabel("Email or username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Log In");
    JButton forgotButton = new JButton("Forgot your password?");
    JLabel noaccountLabel = new JLabel("Don't have an account?");
    JButton signupButton = new JButton("Sign up for Soulmate");

    JLabel logoLabel = new JLabel();

    public LoginFrame() {
        this.setTitle("Login Form");
        this.setVisible(true);
        this.setBounds(10, 10, 500, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);


        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        //addActionEvent();

        container.setBackground(new Color(32, 32, 32));

        //texto y botones
        userLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        noaccountLabel.setForeground(Color.WHITE);
        logLabel.setForeground(Color.WHITE);

        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorderPainted(false);
        loginButton.setBackground(Color.PINK);
        loginButton.setForeground(Color.WHITE);

        forgotButton.setForeground(new Color(32, 32, 32));
        signupButton.setForeground(new Color(32, 32, 32));

        //cargar el logo desde la url de GitHub
        try {
            URL imageUrl = new URL("https://raw.githubusercontent.com/miriamicai/grupo5/main/Fotos/soulmatelogosinfondo.png");
            ImageIcon logo = new ImageIcon(imageUrl);
            logoLabel.setIcon(logo);
            logoLabel.setBounds(120, 65, 300, 240);  //posición y tamaño del logo
            container.add(logoLabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando la imagen del logo.");
            e.printStackTrace();
        }

        // Lógica para iniciar sesión
        loginButton.addActionListener(e -> {
            String usuario = userTextField.getText();
            String password = new String(passwordField.getPassword());
            //lógica en domain.AutetifCustomer
            AutentifCustomer verif = new AutentifCustomer();
            boolean exito = verif.VerificarLogin(usuario, password);
            if (exito){
                JOptionPane.showMessageDialog(this, "¡Login exitoso!");
            }else{
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        });
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        Font defaultFont = userLabel.getFont();  // Get the current default font
        Font newFont = defaultFont.deriveFont(Font.BOLD, 30);  // Make it bold and increase size to 18
        logLabel.setFont(newFont);  // Apply the new font to the label

        // Setting location and size of each component
        logLabel.setBounds(180, 20, 200, 30);
        userLabel.setBounds(185, 310, 200, 30);
        userTextField.setBounds(150, 340, 180, 30);
        passwordLabel.setBounds(215, 370, 100, 30);
        passwordField.setBounds(150, 400, 180, 30);
        loginButton.setBounds(95, 450, 300, 30);
        forgotButton.setBounds(120, 500, 250, 30);
        noaccountLabel.setBounds(60, 550, 200, 30);
        signupButton.setBounds(230, 550, 200, 30);
    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(logLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(forgotButton);
        container.add(loginButton);
        container.add(noaccountLabel);
        container.add(signupButton);
    }

    /*
    public void addActionEvent() {
        loginButton.addActionListener(this);
        forgotButton.addActionListener(this);
        signupButton.addActionListener(this);
    }*/




    /* Overriding actionPerformed() method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Get user input
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all fields.");
            }
        } else if (e.getSource() == forgotButton) {
            // Forgot password logic
            String email = JOptionPane.showInputDialog(this, "Enter your email to reset password:");
            if (email != null && !email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A password reset link has been sent to " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Email is required to reset password.");
            }
        } else if (e.getSource() == signupButton) {
            // Open registration form (assuming it's another class)
            new RegistrationForm();  // Replace with actual form class
        }
    }*/

    public static void main(String[] args) {
        LoginFrame frame = new LoginFrame();
        /*frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10, 10, 500, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);*/
    }
}