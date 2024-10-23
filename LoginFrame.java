
        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        
        public class LoginFrame extends JFrame implements ActionListener {
        
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
        
            LoginFrame() {
                setLayoutManager();
                setLocationAndSize();
                addComponentsToContainer();
                addActionEvent();
        
                container.setBackground(new Color(32, 32, 32)); // Set your desired background color here
        
                // Setting text and button colors
                userLabel.setForeground(Color.WHITE);
                passwordLabel.setForeground(Color.WHITE);
                noaccountLabel.setForeground(Color.WHITE);
                logLabel.setForeground(Color.WHITE);
        
                loginButton.setOpaque(true);  // Make the button opaque
                loginButton.setContentAreaFilled(true);  // Allow the content area to be filled
                loginButton.setBorderPainted(false);  // Optional: Remove the border for a cleaner look
                loginButton.setBackground(Color.PINK);  // Set background color
                loginButton.setForeground(Color.WHITE);  // Set text color
        
                forgotButton.setForeground(new Color(32, 32, 32));  // White text for the forgot button
                signupButton.setForeground(new Color(32, 32, 32));
        
                ImageIcon logo = new ImageIcon("/Users/salomerivas/Desktop/COMILLAS/ING DE SOFTWARE/Java Login/soulmatelogosinfondo.png");
                logoLabel.setIcon(logo);
                logoLabel.setBounds(120, 65, 300, 240);  // Set the position and size of the logo
                container.add(logoLabel);
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
        
            public void addActionEvent() {
                loginButton.addActionListener(this);
                forgotButton.addActionListener(this);
                signupButton.addActionListener(this);
            }
        
            // Database connection method
            public Connection getConnection() {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogindb", "root", "root");  // Replace with your database details
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error connecting to the database.");
                    e.printStackTrace();
                }
                return connection;
            }
        
            // Overriding actionPerformed() method
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginButton) {
                    // Get user input
                    String username = userTextField.getText();
                    String password = new String(passwordField.getPassword());
        
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter all fields.");
                        return;
                    }
        
                    // Database validation
                    try (Connection connection = getConnection()) {
                        if (connection != null) {
                            String query = "SELECT * FROM userlogintable WHERE USERNAME=? AND PASSWORD=?";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1, username);
                            preparedStatement.setString(2, password);
                            ResultSet resultSet = preparedStatement.executeQuery();
        
                            if (resultSet.next()) {
                                JOptionPane.showMessageDialog(this, "Login Successful");
                            } else {
                                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error occurred during login.");
                        ex.printStackTrace();
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
            }
        
            public static void main(String[] args) {
                new LoginFrame().setVisible(true);
            }
        }
        