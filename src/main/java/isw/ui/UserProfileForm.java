package isw.ui;

import isw.cliente.Cliente;
import isw.domain.Customer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class UserProfileForm extends JFrame implements ActionListener {

    protected int numberOfPlaylists = 10;
    protected String nombreUsuario;
    protected ArrayList<String> topArtists = new ArrayList<>();
    protected ArrayList<String> topTracks = new ArrayList<>();
    protected ArrayList<String> playlists = new ArrayList<>();

    protected Cliente cliente;
    protected int idLogged;
    protected HashMap<String, Object> session;
    public Color fondo = new Color(18, 18, 18);

    private JButton addFollowingButton; //no quiero que lo herede UserProfileOtros
    protected JLabel profileInfoLabel;

    public UserProfileForm(int idLogged, HashMap<String, Object> session, Cliente cliente) {
        this.cliente = cliente;
        this.idLogged = idLogged;
        this.session = session;
        this.nombreUsuario = getUsuario(idLogged).getNombre();

        setTitle("Perfil");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(fondo);
        this.setVisible(true);

        // Top Panel for Profile Info
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(fondo);

        JLabel profileLabel = new JLabel("Perfil");
        profileLabel.setForeground(Color.LIGHT_GRAY);
        profileLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel avatarLabel = new JLabel();
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(Color.GRAY);
        avatarLabel.setPreferredSize(new Dimension(100, 100));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setVerticalAlignment(SwingConstants.CENTER);
        avatarLabel.setBorder(createCircleBorder(Color.WHITE));

        JLabel profileNameLabel = new JLabel(nombreUsuario);
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        profileNameLabel.setForeground(Color.WHITE);

        profileInfoLabel = new JLabel();
        profileInfoLabel.setForeground(Color.LIGHT_GRAY);
        actualizarProfileInfoLabel();


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(fondo);

        JButton followersButton = createStyledButton("Seguidores");
        followersButton.addActionListener(e -> openFollowersWindow());

        JButton followingButton = createStyledButton("Seguidos");
        followingButton.addActionListener(e -> openFollowingWindow());

        buttonPanel.add(followersButton);
        buttonPanel.add(followingButton);

        //añadir botón de añadir seguidos mediante método:
        addFollowingButton(buttonPanel);


        profilePanel.add(Box.createVerticalStrut(20)); // Espaciado
        profilePanel.add(profileLabel);
        profilePanel.add(Box.createVerticalStrut(10)); // Espaciado
        profilePanel.add(avatarLabel);
        profilePanel.add(Box.createVerticalStrut(10)); // Espaciado
        profilePanel.add(profileNameLabel);
        profilePanel.add(Box.createVerticalStrut(10)); // Espaciado
        profilePanel.add(profileInfoLabel);
        profilePanel.add(Box.createVerticalStrut(10)); // Espaciado
        profilePanel.add(buttonPanel);
        profilePanel.add(Box.createVerticalStrut(20)); // Espaciado

        add(profilePanel, BorderLayout.NORTH);

        // Extra: artistas, canciones y álbumes
        JPanel ArtistasPanel = new JPanel();
        ArtistasPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ArtistasPanel.setBackground(new Color(18, 18, 18));

        JLabel topArtistsLabel = new JLabel("Favorite Albums");
        topArtistsLabel.setForeground(Color.LIGHT_GRAY);

        ArtistasPanel.add(topArtistsLabel);

        for (String artist : topArtists) {
            JPanel artistPanel = new JPanel();
            artistPanel.setPreferredSize(new Dimension(100, 120));
            artistPanel.setBackground(fondo);
            artistPanel.setLayout(new BorderLayout());

            JLabel artistLabel = new JLabel(artist, SwingConstants.CENTER);
            artistLabel.setForeground(Color.WHITE);

            // Mock profile image placeholder
            JLabel artistImage = new JLabel("Album Image", SwingConstants.CENTER);
            artistImage.setOpaque(true);
            artistImage.setBackground(Color.GRAY);

            artistPanel.add(artistImage, BorderLayout.CENTER);
            artistPanel.add(artistLabel, BorderLayout.SOUTH);
            ArtistasPanel.add(artistPanel);
        }

        // Top Tracks Panel
        JPanel tracksPanel = new JPanel(new GridLayout(5, 1));
        tracksPanel.setBackground(fondo);

        JLabel topTracksLabel = new JLabel("Top tracks this month");
        topTracksLabel.setForeground(Color.LIGHT_GRAY);

        // Public Playlists Panel
        JPanel playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playlistsPanel.setBackground(fondo);

        JLabel publicPlaylistsLabel = new JLabel("Favorite Songs");
        publicPlaylistsLabel.setForeground(Color.LIGHT_GRAY);
        playlistsPanel.add(publicPlaylistsLabel);

        for (String playlist : playlists) {
            JPanel playlistPanel = new JPanel();
            playlistPanel.setPreferredSize(new Dimension(100, 120));
            playlistPanel.setBackground(fondo);
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
        mainPanel.setBackground(fondo);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(ArtistasPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(tracksPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(playlistsPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    protected int getFollowersCount() {
        ArrayList<Customer> seguidores = getSeguidores(idLogged);
        if (seguidores == null) {
            return 0; //devuelve 0 si no se encontraron seguidores
        }
        return seguidores.size();
    }

    protected int getFollowingCount() {
        ArrayList<Customer> seguidos = getSeguidos(idLogged);
        if (seguidos == null) {
            return 0; //devuelve 0 si no se encontraron seguidores
        }
        return seguidos.size();
    }

    protected Customer getUsuario(int idLogged) {
        return cliente.getCustomer(idLogged);
    }

    protected ArrayList<Customer> getTodosUsuarios() {
        return cliente.getCustomers();
    }

    protected void openFollowersWindow() {
        ArrayList<Customer> seguidores = getSeguidores(idLogged);
        createUserGridWindow("Seguidores", seguidores);
    }

    protected void openFollowingWindow() {
        ArrayList<Customer> seguidos = getSeguidos(idLogged);
        createUserGridWindow("Seguidos", seguidos);
    }

    protected void actualizarProfileInfoLabel() {
        profileInfoLabel.setText(numberOfPlaylists + " Saved Albums · " + getFollowersCount() + " Seguidores · " + getFollowingCount() + " Seguidos");
    }


    protected void addFollowingButton(JPanel buttonPanel) {
        addFollowingButton = createStyledButton("Añadir Seguidos");
        addFollowingButton.addActionListener(e -> openAddFollowingWindow());
        buttonPanel.add(addFollowingButton);
    }

    private void openAddFollowingWindow() {
        ArrayList<Customer> seguidos = getSeguidos(idLogged);
        ArrayList<Customer> noSeguidos = getNoConnectedUsers(seguidos);
        createUserGridWindow("Añadir Seguidos", noSeguidos);
    }

    protected ArrayList<Customer> getSeguidores(int id) {
        return (ArrayList<Customer>) cliente.getSeguidores(id);
    }

    protected ArrayList<Customer> getSeguidos(int id) {
        return (ArrayList<Customer>) cliente.getSeguidos(id);
    }

    private ArrayList<Customer> getNoConnectedUsers(ArrayList<Customer> connectedUsers) {
        ArrayList<Customer> todosUsuarios = new ArrayList<>(getTodosUsuarios());
        todosUsuarios.removeIf(user -> user.getId() == idLogged || connectedUsers.contains(user));
        return todosUsuarios;
    }

    protected void createUserGridWindow(String title, ArrayList<Customer> users) {
        JFrame frame = new JFrame(title);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 3, 10, 10));
        gridPanel.setBackground(fondo);

        for (Customer user : users) {
            JButton userButton = createStyledButton(user.getNombre());
            userButton.setPreferredSize(new Dimension(120, 120));
            userButton.setBackground(Color.WHITE);
            userButton.setForeground(Color.BLACK);
            if (title == "Añadir Seguidos") {
                userButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Conexión creada con: " + user.getNombre(), "Conexión", JOptionPane.INFORMATION_MESSAGE);
                    // Creo la nueva conexión a través del cliente
                    cliente.establishConnection(idLogged, user.getId());
                    actualizarProfileInfoLabel();
                });
            } else {
                userButton.addActionListener(e -> {
                    new UserProfileOtros(user.getId(), session, cliente); //se muestra el perfil de otra persona
                });
            }
            gridPanel.add(userButton);
        }

        frame.add(gridPanel, BorderLayout.CENTER);

        JButton backButton = createBackButton(frame);
        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    protected JButton createBackButton(JFrame currentFrame) {
        JButton backButton = createStyledButton("Volver a Mi Perfil");
        backButton.setBackground(new Color(45, 45, 45));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> currentFrame.dispose());
        return backButton;
    }

    protected JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(64, 64, 64));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(96, 96, 96));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(64, 64, 64));
            }
        });
        return button;
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
