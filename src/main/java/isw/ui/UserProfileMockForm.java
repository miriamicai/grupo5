package isw.ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserProfileMockForm extends JFrame {

    private int numberOfPlaylists;
    private int followersCount;
    private int followingCount;
    private String nombreUsuario;
    private ArrayList<String> topArtists;
    private ArrayList<String> topTracks;
    private ArrayList<String> playlists;

    private int idLogged;
    private JPanel followersPanel;
    private JPanel followingPanel;

    public UserProfileMockForm(int idLogged) {
        this.idLogged = idLogged;

        // Datos inventados
        this.nombreUsuario = "UsuarioPrueba" + idLogged;
        this.numberOfPlaylists = 5;
        this.followersCount = 120;
        this.followingCount = 45;
        this.topArtists = new ArrayList<>(Arrays.asList("Artista1", "Artista2", "Artista3"));
        this.topTracks = new ArrayList<>(Arrays.asList("Canción1", "Canción2", "Canción3"));
        this.playlists = new ArrayList<>(Arrays.asList("Playlist1", "Playlist2", "Playlist3"));

        // Configuración del JFrame
        setTitle("Mock User Profile");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // Panel principal
        JPanel profilePanel = createProfilePanel();

        // Agregar solo el panel de perfil, eliminando la parte de followers/following
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(18, 18, 18));
        mainPanel.add(profilePanel);

        // Añadir paneles para artistas, canciones y playlists
        mainPanel.add(createTopArtistsPanel());
        mainPanel.add(createTopTracksPanel());
        mainPanel.add(createPlaylistsPanel());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createProfilePanel() {
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(new Color(18, 18, 18));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(18, 18, 18));

        JLabel profileLabel = new JLabel("Profile");
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

        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(avatarLabel);

        JLabel profileNameLabel = new JLabel(nombreUsuario);
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        profileNameLabel.setForeground(Color.WHITE);

        // Agregar los botones Followers y Following al lado del nombre del usuario
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setBackground(new Color(18, 18, 18));
        JButton followersButton = new JButton("Followers");
        followersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayFollowers();
            }
        });

        JButton followingButton = new JButton("Following");
        followingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayFollowing();
            }
        });

        buttonPanel.add(followersButton);
        buttonPanel.add(followingButton);

        headerPanel.add(profileNameLabel);
        headerPanel.add(buttonPanel);

        JLabel profileInfoLabel = new JLabel(numberOfPlaylists + " Saved Albums · " + followersCount + " Followers · " + followingCount + " Following");
        profileInfoLabel.setForeground(Color.LIGHT_GRAY);

        profilePanel.add(headerPanel, BorderLayout.NORTH);
        profilePanel.add(profileInfoLabel, BorderLayout.SOUTH);

        return profilePanel;
    }

    private JPanel createTopArtistsPanel() {
        JPanel artistsPanel = new JPanel();
        artistsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        artistsPanel.setBackground(new Color(18, 18, 18));

        JLabel topArtistsLabel = new JLabel("Favorite Artists");
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
            JLabel artistImage = new JLabel("Artist Image", SwingConstants.CENTER);
            artistImage.setOpaque(true);
            artistImage.setBackground(Color.GRAY);

            artistPanel.add(artistImage, BorderLayout.CENTER);
            artistPanel.add(artistLabel, BorderLayout.SOUTH);
            artistsPanel.add(artistPanel);
        }

        return artistsPanel;
    }

    private JPanel createTopTracksPanel() {
        JPanel tracksPanel = new JPanel(new GridLayout(5, 1));
        tracksPanel.setBackground(new Color(18, 18, 18));

        JLabel topTracksLabel = new JLabel("Top tracks this month");
        topTracksLabel.setForeground(Color.LIGHT_GRAY);

        for (String track : topTracks) {
            JLabel trackLabel = new JLabel(track);
            trackLabel.setForeground(Color.WHITE);
            tracksPanel.add(trackLabel);
        }

        return tracksPanel;
    }

    private JPanel createPlaylistsPanel() {
        JPanel playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playlistsPanel.setBackground(new Color(18, 18, 18));

        JLabel publicPlaylistsLabel = new JLabel("Favorite Playlists");
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

        return playlistsPanel;
    }

    private void displayFollowers() {
        // Datos de los seguidores (simulados)
        List<String> mockFollowers = Arrays.asList("Seguidor1", "Seguidor2", "Seguidor3", "Seguidor4", "Seguidor5",
                "Seguidor6", "Seguidor7", "Seguidor8", "Seguidor9", "Seguidor10",
                "Seguidor11", "Seguidor12");

        showFollowersOrFollowing("Seguidores", mockFollowers);
    }

    private void displayFollowing() {
        // Datos de los seguidos (simulados)
        List<String> mockFollowing = Arrays.asList("Seguido1", "Seguido2", "Seguido3", "Seguido4", "Seguido5",
                "Seguido6", "Seguido7", "Seguido8", "Seguido9", "Seguido10",
                "Seguido11", "Seguido12");

        showFollowersOrFollowing("Seguidos", mockFollowing);
    }





    private void showFollowersOrFollowing(String title, List<String> usersList) {
        // Color de fondo oscuro similar al de "Mi Perfil"
        Color darkBackgroundColor = new Color(30, 30, 30); // Un color oscuro

        // Crear panel principal con fondo oscuro similar al perfil
        JPanel followersOrFollowingPanel = new JPanel();
        followersOrFollowingPanel.setLayout(new BorderLayout());
        followersOrFollowingPanel.setBackground(darkBackgroundColor);

        // Color de fondo del perfil, para el botón "Volver a Mi Perfil"
        Color profileBackgroundColor = new Color(45, 45, 45); // Ajusta este valor según el color de tu perfil

        // Crear panel para el botón "Volver a Mi Perfil"
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(darkBackgroundColor);

        JButton backButton = new JButton("Volver a Mi Perfil");
        backButton.setBackground(profileBackgroundColor); // Fondo del perfil
        backButton.setForeground(Color.WHITE); // Texto blanco
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Regresar al perfil
                setContentPanel(createProfilePanel()); // Suponiendo que esta función crea el panel de tu perfil
            }
        });

        backButtonPanel.add(backButton);
        followersOrFollowingPanel.add(backButtonPanel, BorderLayout.NORTH);

        // Crear panel para el título (centrado, con fondo oscuro y texto blanco)
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrado
        titlePanel.setBackground(darkBackgroundColor);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Texto en blanco

        titlePanel.add(titleLabel);
        followersOrFollowingPanel.add(titlePanel, BorderLayout.CENTER);

        // Crear panel para la cuadrícula con márgenes (vacíos)
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 3, 10, 10)); // Cuadrícula de 4 filas x 3 columnas
        gridPanel.setBackground(darkBackgroundColor); // Fondo oscuro para la cuadrícula

        // Aplicar un borde vacío para los márgenes
        // Margen de 1.5mm a izquierda y derecha y 3mm abajo (aproximadamente 5px y 10px)
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));

        // Crear botones para cada usuario con fondo blanco y texto en negro
        for (String user : usersList) {
            JButton userButton = new JButton(user);
            userButton.setPreferredSize(new Dimension(120, 120)); // Hacer los botones cuadrados
            userButton.setBackground(Color.WHITE); // Fondo blanco
            userButton.setForeground(Color.BLACK); // Texto negro

            // Establecer bordes vacíos para los botones (margen adicional dentro de cada botón)
            userButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5)); // Margen adicional dentro de los botones

            // Agregar un listener para el evento
            userButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Perfil de: " + user, "Perfil", JOptionPane.INFORMATION_MESSAGE));

            gridPanel.add(userButton);
        }

        // Agregar cuadrícula de botones al panel principal
        followersOrFollowingPanel.add(gridPanel, BorderLayout.SOUTH);

        // Mostrar la ventana con la cuadrícula de seguidores o seguidos
        setContentPanel(followersOrFollowingPanel);
    }








    private void setContentPanel(JPanel panel) {
        // Cambia el contenido del panel central para mostrar la lista de seguidores o seguidos
        getContentPane().removeAll();
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public Border createCircleBorder(Color color) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(color);
                g.fillOval(x, y, width, height);
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserProfileMockForm mockForm = new UserProfileMockForm(1); // Cambiar ID para pruebas
            mockForm.setVisible(true);
        });
    }
}