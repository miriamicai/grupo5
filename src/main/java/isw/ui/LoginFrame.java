package isw.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    private Container container;
    private JLabel logLabel;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton forgotButton;
    private JLabel noaccountLabel;
    private JButton signupButton;
    private JLabel imageLabel;  // Label para la imagen

    public LoginFrame() {
        // Configuración del JFrame
        setTitle("Formulario de Login");
        setBounds(10, 10, 400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);
        container.setBackground(Color.BLACK);  // Establecer el fondo negro

        // Crear componentes
        // Imagen (Asegúrate de que la ruta de la imagen sea correcta)
        ImageIcon imageIcon = new ImageIcon("src/main/resources/soulmatelogosinfondo.png");  // Ruta a tu imagen
        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(100, 20, 200, 100);  // Tamaño y posición de la imagen

        logLabel = new JLabel("Log in to");
        logLabel.setForeground(Color.WHITE);  // Texto blanco para resaltar sobre fondo negro
        logLabel.setBounds(150, 130, 200, 30);

        userLabel = new JLabel("Email or username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 180, 150, 30);

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 230, 100, 30);

        userTextField = new JTextField();
        userTextField.setBounds(200, 180, 150, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 230, 150, 30);

        loginButton = new JButton("Log In");
        loginButton.setBounds(50, 280, 300, 30);
        loginButton.addActionListener(this);

        forgotButton = new JButton("Forgot your password?");
        forgotButton.setBounds(50, 330, 300, 30);
        forgotButton.addActionListener(this);

        noaccountLabel = new JLabel("Don't have an account?");
        noaccountLabel.setForeground(Color.WHITE);  // Texto blanco
        noaccountLabel.setBounds(50, 380, 200, 30);

        signupButton = new JButton("Sign up for Soulmate");
        signupButton.setBounds(200, 380, 150, 30);
        signupButton.addActionListener(this);

        // Añadir componentes al container
        container.add(imageLabel);  // Añadir la imagen
        container.add(logLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(loginButton);
        container.add(forgotButton);
        container.add(noaccountLabel);
        container.add(signupButton);

        setVisible(true);  // Asegúrate de que la ventana sea visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String pwdText = new String(passwordField.getPassword());

            // Lógica básica de validación de login
            if (userText.equalsIgnoreCase("salomerivas") && pwdText.equals("1616")) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                this.dispose();  // Cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        } else if (e.getSource() == forgotButton) {
            String email = JOptionPane.showInputDialog(this, "Enter your email to reset password:");
            if (email != null && !email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A password reset link has been sent to " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Email is required to reset password.");
            }
        } else if (e.getSource() == signupButton) {
            // Lógica de registro (abre otra ventana de registro)
            JOptionPane.showMessageDialog(this, "Sign Up window (not implemented)");
        }
    }

    public static void main(String[] args) {
        new LoginFrame();  // Crear y mostrar la ventana de login
    }
}
