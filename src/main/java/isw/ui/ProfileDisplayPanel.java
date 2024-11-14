package isw.ui;

import isw.cliente.Cliente;
import isw.domain.Customer;
//import isw.domain.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileDisplayPanel extends JPanel{
    private JPanel profileDisplayPanel;
    private JLabel usernameLabel;
    private JLabel nameLabel;
    private JLabel pfpLabel;
    private JButton followButton;

    public ProfileDisplayPanel(Customer customer){
        Cliente cliente = new Cliente();
        setLayout(new BorderLayout());

        String username = customer.getNombreUsuario();
        String nombre = customer.getNombre();
        String apellido1 = customer.getApellido1();
        String apellido2 = customer.getApellido2();
        int followerId = customer.getId();
        ImageIcon pfp;
        pfp = new ImageIcon("src/main/resources/default-profile-icon-8-207272221.jpg");

        JLabel usernameLabel = new JLabel(username);
        JLabel nameLabel = new JLabel(nombre + " " + apellido1 + " " + apellido2);
        JLabel pfpLabel = new JLabel(pfp);

        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int loggedInUserId = UserSession.getInstance().getUserId();
                cliente.establishConnection(3, followerId);
            }
        });

        add(usernameLabel, BorderLayout.NORTH);
        add(nameLabel, BorderLayout.CENTER);
        add(pfpLabel, BorderLayout.SOUTH);
        add(followButton, BorderLayout.EAST);
    }

    //Constructor sin argumentos
    public ProfileDisplayPanel(){
        setLayout(new BorderLayout());
        JLabel usernameLabel = new JLabel("Username");
        JLabel nameLabel = new JLabel("Name");
        JLabel pfpLabel = new JLabel("Profile picture");

        add(usernameLabel, BorderLayout.NORTH);
        add(nameLabel, BorderLayout.CENTER);
        add(pfpLabel, BorderLayout.SOUTH);
    }

    private void initComponents(){}
}