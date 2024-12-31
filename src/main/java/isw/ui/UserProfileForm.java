package isw.ui;

import isw.cliente.Cliente;
import isw.domain.Customer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class UserProfileForm extends JFrame implements ActionListener {

    private int numberOfPlaylists = 10;
    private int followersCount = 120;
    private int followingCount = 35;
    private String nombreUsuario;
    private ArrayList<String> topArtists = new ArrayList<>();
    private ArrayList<String> topTracks = new ArrayList<>();
    private ArrayList<String> playlists = new ArrayList<>();

    private Cliente cliente;
    private int idLogged;
    public Color fondo = new Color(18, 18, 18);

    public UserProfileForm(int idLogged, HashMap<String, Object> session, Cliente cliente) {
        this.cliente = cliente;
        this.idLogged = idLogged;

        ArrayList<Customer> seguidores = (ArrayList<Customer>) cliente.getSeguidores(idLogged);
        ArrayList<Customer> seguidos = (ArrayList<Customer>) cliente.getSeguidos(idLogged);

        setTitle("Perfil");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(fondo);
        this.setVisible(true);

        // Top Panel for Profile Info
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(fondo);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(18, 18, 18));

        JLabel profileLabel = new JLabel("Perfil");
        profileLabel.setForeground(Color.LIGHT_GRAY);
        profileLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(profileLabel);

        JLabel avatarLabel = new JLabel();
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(Color.GRAY);
        avatarLabel.setPreferredSize(new Dimension(100, 100));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setVerticalAlignment(SwingConstants.CENTER);
        avatarLabel.setBorder(createCircleBorder(Color.WHITE));

        headerPanel.add(profileLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(avatarLabel);
        headerPanel.add(Box.createVerticalStrut(10));

        //String nombreUsuario = getUsuario(idLogged).getNombre();
        JLabel profileNameLabel = new JLabel(String.valueOf(session.get("usuario")));
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        profileNameLabel.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setBackground(new Color(18, 18, 18));

        JButton followersButton = new JButton("Seguidores");
        followersButton.addActionListener(e -> openFollowersWindow(seguidores));

        JButton followingButton = new JButton("Seguidos");
        followingButton.addActionListener(e -> openFollowingWindow(seguidos));

        buttonPanel.add(followersButton);
        buttonPanel.add(followingButton);

        headerPanel.add(profileNameLabel);
        headerPanel.add(buttonPanel);

        JLabel profileInfoLabel = new JLabel(numberOfPlaylists + " Saved Albums · " + followersCount + " Followers · " + followingCount + " Following");
        profileInfoLabel.setForeground(Color.LIGHT_GRAY);

        profilePanel.add(headerPanel, BorderLayout.NORTH);
        profilePanel.add(profileInfoLabel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(18, 18, 18));
        mainPanel.add(profilePanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    private Customer getUsuario(int idLogged) {
        Customer customer = cliente.getCustomer(idLogged);
        return customer;
    }

    private void openFollowersWindow(ArrayList<Customer> seguidores) {
        JFrame followersFrame = new JFrame("Seguidores");
        followersFrame.setSize(600, 400);
        followersFrame.setLayout(new BorderLayout());

        JPanel gridPanel = createGridPanel(seguidores);
        followersFrame.add(gridPanel, BorderLayout.CENTER);

        JButton backButton = createBackButton(followersFrame);
        followersFrame.add(backButton, BorderLayout.SOUTH);

        followersFrame.setVisible(true);
    }

    private void openFollowingWindow(ArrayList<Customer> seguidos) {
        JFrame followingFrame = new JFrame("Seguidos");
        followingFrame.setSize(600, 400);
        followingFrame.setLayout(new BorderLayout());

        JPanel gridPanel = createGridPanel(seguidos);
        followingFrame.add(gridPanel, BorderLayout.CENTER);

        JButton backButton = createBackButton(followingFrame);
        followingFrame.add(backButton, BorderLayout.SOUTH);

        followingFrame.setVisible(true);
    }

    private JPanel createGridPanel(ArrayList<Customer> users) {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 3, 10, 10));
        gridPanel.setBackground(new Color(18, 18, 18));

        for (Customer user : users) {
            JButton userButton = new JButton(user.getNombre());
            userButton.setPreferredSize(new Dimension(120, 120));
            userButton.setBackground(Color.WHITE);
            userButton.setForeground(Color.BLACK);
            userButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Perfil de: " + user.getNombre(), "Perfil", JOptionPane.INFORMATION_MESSAGE));
            gridPanel.add(userButton);
        }

        return gridPanel;
    }

    private JButton createBackButton(JFrame currentFrame) {
        JButton backButton = new JButton("Volver a Mi Perfil");
        backButton.setBackground(new Color(45, 45, 45));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> currentFrame.dispose());
        return backButton;
    }

    public Border createCircleBorder(Color color) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(color);
                g.drawOval(x, y, width - 1, height - 1);
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Funcionalidad de los botones
    }
}
