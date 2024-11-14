package isw.ui;

import isw.cliente.Cliente;
import isw.controler.CustomerControler;
import isw.domain.Customer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


//ASOCIAR ESTA CLASE A BOTÓN DEL JVENTANALOGGED
public class UserProfileForm extends JFrame implements ActionListener {

    // Assuming user data is passed as parameters or fetched from a database or API
    private int numberOfPlaylists = 10;
    private int followersCount = 120;
    private int followingCount = 35;
    private String nombreUsuario;
    private ArrayList<String> topArtists = new ArrayList<>();
    private ArrayList<String> topTracks = new ArrayList<>();
    private ArrayList<String> playlists = new ArrayList<>();

    private ArrayList<String> nombresSeguidores;
    private ArrayList<String> nombresSeguidos;
    private Cliente cliente;


    public UserProfileForm(HashMap<String, Object> session, Cliente cliente) {

        //Cargar datos desde el servidor -> seguidores, seguidos, canciones, etc.
        nombreUsuario = cargarDatosDelCliente(session, cliente);

        setTitle("User Profile");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // Top Panel for Profile Info
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(new Color(18, 18, 18));

        // Profile header with "Profile" text above the user name
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(18, 18, 18));

        JLabel profileLabel = new JLabel("Profile");
        profileLabel.setForeground(Color.LIGHT_GRAY);
        profileLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        //mover a la derecha y añadir espacio
        profileLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        headerPanel.add(Box.createVerticalStrut(20)); // Space above the label
        headerPanel.add(profileLabel);

        //avatares
        JLabel avatarLabel = new JLabel();
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(Color.GRAY); // Placeholder color for avatar
        avatarLabel.setPreferredSize(new Dimension(100, 100));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setVerticalAlignment(SwingConstants.CENTER);
        avatarLabel.setBorder(createCircleBorder(Color.WHITE)); // Create circular border


        //añadir avatar
        headerPanel.add(profileLabel);
        headerPanel.add(Box.createVerticalStrut(10)); // Spacing between "Profile" and avatar
        headerPanel.add(avatarLabel);
        headerPanel.add(Box.createVerticalStrut(10)); // Spacing between avatar and username

        //nombre de usuario
        CustomerControler controler = null;
        JLabel profileNameLabel = new JLabel(nombreUsuario);
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        profileNameLabel.setForeground(Color.WHITE);

        headerPanel.add(profileNameLabel);

        // Profile info like number of playlists, followers, and following
        JLabel profileInfoLabel = new JLabel(numberOfPlaylists + " Saved Albums · " + followersCount + " Followers · " + followingCount + " Following");
        profileInfoLabel.setForeground(Color.LIGHT_GRAY);

        profilePanel.add(headerPanel, BorderLayout.NORTH);
        profilePanel.add(profileInfoLabel, BorderLayout.SOUTH);

        // Top Artists Panel
        JPanel artistsPanel = new JPanel();
        artistsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        artistsPanel.setBackground(new Color(18, 18, 18));

        JLabel topArtistsLabel = new JLabel("Favorite Albums");
        topArtistsLabel.setForeground(Color.LIGHT_GRAY);

        artistsPanel.add(topArtistsLabel);

        for (String artist : topArtists) {
            JPanel artistPanel = new JPanel();
            artistPanel.setPreferredSize(new Dimension(100, 120));
            artistPanel.setBackground(new Color(18, 18, 18));
            artistPanel.setLayout(new BorderLayout());

            JLabel artistLabel = new JLabel(artist, SwingConstants.CENTER);
            artistLabel.setForeground(Color.WHITE);

            // Mock profile image placeholder
            JLabel artistImage = new JLabel("Album Image", SwingConstants.CENTER);
            artistImage.setOpaque(true);
            artistImage.setBackground(Color.GRAY);

            artistPanel.add(artistImage, BorderLayout.CENTER);
            artistPanel.add(artistLabel, BorderLayout.SOUTH);
            artistsPanel.add(artistPanel);
        }

        // Top Tracks Panel
        JPanel tracksPanel = new JPanel(new GridLayout(5, 1));
        tracksPanel.setBackground(new Color(18, 18, 18));

        JLabel topTracksLabel = new JLabel("Top tracks this month");
        topTracksLabel.setForeground(Color.LIGHT_GRAY);

        /*for (String track : topTracks) {
            JLabel trackLabel = new JLabel(track);
            trackLabel.setForeground(Color.WHITE);
            tracksPanel.add(trackLabel);
        }*/


        //mostrar listas de followers y
        /*
        for (String follower : nombresSeguidores) {
            //LLAMAR A FUNCIÓN PARA QUE ME DÉ LOS NOMBRES DE USUARIO
            JLabel trackLabel = new JLabel(follower);
            trackLabel.setForeground(Color.WHITE);
            tracksPanel.add(trackLabel);
        }

        for (String following : nombresSeguidos) {
            JLabel trackLabel = new JLabel(following);
            trackLabel.setForeground(Color.WHITE);
            tracksPanel.add(trackLabel);
        }*/


        // Public Playlists Panel
        JPanel playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playlistsPanel.setBackground(new Color(18, 18, 18));

        JLabel publicPlaylistsLabel = new JLabel("Favorite Songs");
        publicPlaylistsLabel.setForeground(Color.LIGHT_GRAY);
        playlistsPanel.add(publicPlaylistsLabel);

        for (String playlist : playlists) {
            JPanel playlistPanel = new JPanel();
            playlistPanel.setPreferredSize(new Dimension(100, 120));
            playlistPanel.setBackground(new Color(18, 18, 18));
            playlistPanel.setLayout(new BorderLayout());

            JLabel playlistLabel = new JLabel(playlist, SwingConstants.CENTER);
            playlistLabel.setForeground(Color.WHITE);

            // Mock playlist cover placeholder
            JLabel playlistImage = new JLabel("Cover", SwingConstants.CENTER);
            playlistImage.setOpaque(true);
            playlistImage.setBackground(Color.GRAY);

            playlistPanel.add(playlistImage, BorderLayout.CENTER);
            playlistPanel.add(playlistLabel, BorderLayout.SOUTH);
            playlistsPanel.add(playlistPanel);
        }

        // Adding Panels to Main Container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(18, 18, 18));
        mainPanel.add(profilePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(artistsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(tracksPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(playlistsPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
        
    }



    // Custom method to create circular borders (for avatar)
    public Border createCircleBorder(Color color) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(color);
                g.fillOval(x, y, width, height);
            }
        };
    }


    private String cargarDatosDelCliente(HashMap<String, Object> session, Cliente cliente) {
        this.cliente = cliente; //uso cliente para hacer las peticiones
        HashMap<String, Object> response = cliente.sentMessage("/getUserProfile", session);
        nombreUsuario = null;

        if (response != null) {
            Customer customer = (Customer) response.get("Customer");
            if (customer != null) {
                this.nombreUsuario = customer.getNombreUsuario();
                this.numberOfPlaylists = (int) response.getOrDefault("numberOfPlaylists", 0);
                this.followersCount = (int) response.getOrDefault("followersCount", 0);
                this.followingCount = (int) response.getOrDefault("followingCount", 0);
                this.topArtists = (ArrayList<String>) response.getOrDefault("topArtists", new ArrayList<>());
                this.topTracks = (ArrayList<String>) response.getOrDefault("topTracks", new ArrayList<>());
                this.playlists = (ArrayList<String>) response.getOrDefault("playlists", new ArrayList<>());
                this.nombresSeguidores = (ArrayList<String>) response.getOrDefault("nombresSeguidores", new ArrayList<>());
                this.nombresSeguidos = (ArrayList<String>) response.getOrDefault("nombresSeguidos", new ArrayList<>());
            } else {
                JOptionPane.showMessageDialog(this, "No se ha recuperado el perfil del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al comunicarse con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return nombreUsuario;
    }



    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserProfileForm userProfileForm = new UserProfileForm(1);
            userProfileForm.setVisible(true);
        });
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        //funcionalidad de los botones
    }
}
