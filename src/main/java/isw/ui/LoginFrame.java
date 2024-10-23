import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    Container container=getContentPane();
    JLabel logLabel=new JLabel("Log in to");
    JLabel userLabel=new JLabel("Email or username");
    JLabel passwordLabel=new JLabel("Password");
    JTextField userTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JButton loginButton=new JButton("Log In");
    JButton forgotButton=new JButton("Forgot your password?");
    JLabel noaccountLabel=new JLabel("Don't have an account?");
    JButton signupButton=new JButton("Sign up for Soulmate");

    JLabel logoLabel = new JLabel();
 
    LoginFrame()
    {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    

    container.setBackground(new Color(32, 32, 32)); // Set your desired background color here

        // 2. Set button colors

        userLabel.setForeground(Color.WHITE); 
        passwordLabel.setForeground(Color.WHITE); 
        noaccountLabel.setForeground(Color.WHITE); 
        logLabel.setForeground(Color.WHITE); 

        
        JButton loginButton = new JButton("Log In");
        loginButton.setOpaque(true);  // Make the button opaque
        loginButton.setContentAreaFilled(true);  // Allow the content area to be filled
        loginButton.setBorderPainted(false);  // Optional: Remove the border for a cleaner look
        loginButton.setBackground(Color.PINK);  // Set background color
        loginButton.setForeground(Color.WHITE);  // Set text color
        // White text for the login button

        forgotButton.setForeground(new Color(32, 32, 32)); // White text for the forgot button

        signupButton.setForeground(new Color(32, 32, 32));

        ImageIcon logo = new ImageIcon("/Users/salomerivas/Desktop/COMILLAS/ING DE SOFTWARE/Java Login/soulmatelogosinfondo.png");
        logoLabel.setIcon(logo);
        logoLabel.setBounds(120, 65, 300, 240);  // Set the position and size of the logo
        container.add(logoLabel);
    }

    public void setLayoutManager()
    {
        //Setting layout manager of Container to null
        container.setLayout(null);
    }

    public void setLocationAndSize()
    {
        Font defaultFont = userLabel.getFont();  // Get the current default font
        Font newFont = defaultFont.deriveFont(Font.BOLD, 30);  // Make it bold and increase size to 18
        logLabel.setFont(newFont);  // Apply the new font to the label

//Setting location and Size of each components using setBounds() method.
        logLabel.setBounds(180,20,200,30);
        userLabel.setBounds(185,310,200,30);
        userTextField.setBounds(150,340,180,30);
        passwordLabel.setBounds(215,370,100,30);
        passwordField.setBounds(150,400,180,30);
        loginButton.setBounds(95,450,300,30);
        forgotButton.setBounds(120,500,250,30);
        noaccountLabel.setBounds(60,540,200,30);
        signupButton.setBounds(230,540,200,30);
  
    }
    public void addComponentsToContainer()
    {
       //Adding each components to the Container
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

    public void addActionEvent()
   {
      //adding Action listener to components
       loginButton.addActionListener(this);
       forgotButton.addActionListener(this);
       signupButton.addActionListener(this);
   }
    //Overriding actionPerformed() method
    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            if (userText.equalsIgnoreCase("salomerivas") && pwdText.equalsIgnoreCase("1616")) {
                JOptionPane.showMessageDialog(this, "Login Successful");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        } else if (e.getSource() == forgotButton) {
            // Forgot password logic
            String email = JOptionPane.showInputDialog(this, "Enter your email to reset password:");
            if (email != null && !email.isEmpty()) {
                // Simulate sending an email
                JOptionPane.showMessageDialog(this, "A password reset link has been sent to " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Email is required to reset password.");
            }
        } else if (e.getSource() == signupButton) {
            // Sign-up logic: Open a new window for registration
            JFrame signUpFrame = new JFrame("Sign Up for Soulmate");
            signUpFrame.setBounds(100, 100, 300, 200);
            signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            signUpFrame.setVisible(true);

             }
        
            }
        }


