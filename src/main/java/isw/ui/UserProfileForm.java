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

    private int idLogged;


    public UserProfileForm(int idLogged) {

        cliente = new Cliente();
        //Almacena id del crack que haya iniciado sesión
        this.idLogged = idLogged;
        cargarDatosDelCliente(cliente);

        //Cargar datos desde el servidor -> seguidores, seguidos, canciones, etc.
        //nombreUsuario = cargarDatosDelCliente(session, cliente);

        setTitle("User Profile");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // Top Panel for Profile Info
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(new Color(18, 18, 18));

        // Profile header with "Profile" text above the username
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


        //mostrar listas de seguidores y seguidos
        // Mostrar seguidores
        JPanel seguidoresPanel = new JPanel();
        seguidoresPanel.setLayout(new BoxLayout(seguidoresPanel, BoxLayout.Y_AXIS));
        seguidoresPanel.setBackground(new Color(18, 18, 18));

        JLabel seguidoresLabel = new JLabel("Followers");
        seguidoresLabel.setForeground(Color.LIGHT_GRAY);
        seguidoresPanel.add(seguidoresLabel);

        for (String seguidor : nombresSeguidores) {
            JLabel seguidorLabel = new JLabel(seguidor);
            seguidorLabel.setForeground(Color.WHITE);
            seguidoresPanel.add(seguidorLabel);
        }

        // Mostrar seguidos
        JPanel seguidosPanel = new JPanel();
        seguidosPanel.setLayout(new BoxLayout(seguidosPanel, BoxLayout.Y_AXIS));
        seguidosPanel.setBackground(new Color(18, 18, 18));

        JLabel seguidosLabel = new JLabel("Following");
        seguidosLabel.setForeground(Color.LIGHT_GRAY);
        seguidosPanel.add(seguidosLabel);

        for (String seguido : nombresSeguidos) {
            JLabel seguidoLabel = new JLabel(seguido);
            seguidoLabel.setForeground(Color.WHITE);
            seguidosPanel.add(seguidoLabel);
        }

        // Agregar estos paneles a tu interfaz principal
        topTracksLabel.add(seguidoresPanel);
        topTracksLabel.add(Box.createVerticalStrut(20));
        topTracksLabel.add(seguidosPanel);








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



    private String cargarDatosDelCliente(Cliente cliente) {
        this.cliente = cliente; // Uso cliente para hacer las peticiones

        // Obtener los seguidores y seguidos
        //HashMap<String, Object> seguidoresResponse = cliente.getFollowers(idLogged);
        //HashMap<String, Object> seguidosResponse = cliente.sentMessage("/getSeguidos", session);
        cliente.getFollowers(idLogged);
        ArrayList<Customer> listaSeguidores = cliente.getSeguidoresList();

        // Inicializar las listas
        this.nombresSeguidores = new ArrayList<>();
        this.nombresSeguidos = new ArrayList<>();

        // Extraer los nombres de los seguidores
        /*ArrayList<Customer> seguidoresList = (ArrayList<Customer>) seguidoresResponse.get("Seguidores");
        if (seguidoresList != null) {
            for (Customer seguidor : seguidoresList) {
                this.nombresSeguidores.add(seguidor.getNombreUsuario());
            }
        }*/

        // Extraer los nombres de los seguidos
        /*ArrayList<Customer> seguidosList = (ArrayList<Customer>) seguidosResponse.get("Seguidos");
        if (seguidosList != null) {
            for (Customer seguido : seguidosList) {
                this.nombresSeguidos.add(seguido.getNombreUsuario());
            }
        }*/
        for(Customer c : listaSeguidores){
            this.nombresSeguidores.add(c.getNombre());
        }

        // Deberías tener más lógica aquí para completar el resto de la carga de datos, como el nombre de usuario.
        return this.nombreUsuario;
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