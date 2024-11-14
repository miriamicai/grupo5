package isw.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;

import java.util.List;

import isw.dao.MusicBrainzService;
import isw.dao.SpotifyAuth;
import isw.releases.Album;
import isw.enums.SearchTypes;


public class JVentana extends JFrame {
    protected JButton btnIniciarSesion;
    protected JButton btnRegistro;
    protected JButton btnSpotify;
    protected JButton btnDatosSpotify;
    protected JButton btnSalir;

    protected JPanel topPanel; //para que lo pueda acceder JVentanaLogged

    public JVentana() {
        // Configuración de la ventana principal
        setTitle("Página Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        MusicBrainzService musicBrainzService = new MusicBrainzService();

        // Panel izquierdo para el logo
        JPanel panelIzq = new JPanel();
        panelIzq.setLayout(new BorderLayout());
        panelIzq.setBackground(Color.BLACK);

        // logo
        ImageIcon logoInicial = new ImageIcon("src/main/resources/soulmatelogosinfondo.png");
        Image imagenEscala = logoInicial.getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH);
        ImageIcon iconoEscala = new ImageIcon(imagenEscala);
        JLabel etiquetaImagen = new JLabel(iconoEscala);
        etiquetaImagen.setPreferredSize(new Dimension(400, 600));

        panelIzq.add(etiquetaImagen, BorderLayout.NORTH);
        add(panelIzq, BorderLayout.WEST);


        // Panel central y derecho combinado
        JPanel panelCenDer = new JPanel();
        panelCenDer.setLayout(new GridLayout(4, 1, 10, 10));
        panelCenDer.setBackground(Color.BLACK);

        // Crear botones
        JButton btnNovedades = createImageButton("Últimas novedades", "src/main/resources/imagen1.jpg", 180, 100);
        JButton btnExplorarArtistas = createImageButton("Explorar nuevos artistas", "src/main/resources/imagen1.jpg", 180, 100);
        JButton btnCantantesFavoritos = createImageButton("Tus cantantes favoritos", "src/main/resources/imagen1.jpg", 180, 100);
        JButton btnMasEscuchado = createImageButton("Lo más escuchado en tu zona", "src/main/resources/imagen1.jpg", 180, 100);

        panelCenDer.add(btnNovedades);
        panelCenDer.add(btnExplorarArtistas);
        panelCenDer.add(btnCantantesFavoritos);
        panelCenDer.add(btnMasEscuchado);
        add(panelCenDer, BorderLayout.CENTER);

        // Panel superior
        topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.BLACK);

        JTextField searchField = new JTextField(20);
        JButton btnBuscar = createStyledButton("Buscar");

        topPanel.add(searchField);
        topPanel.add(btnBuscar);

        // Botones de inicio de sesión
        this.btnIniciarSesion = createStyledButton("Iniciar Sesión");
        this.btnRegistro = createStyledButton("Registro");
        this.btnSpotify = createStyledButton("Vincular con tu cuenta de Spotify");
        this.btnDatosSpotify = createStyledButton("Ver datos de tu cuenta Spotify");
        this.btnSalir = createStyledButton("Salir");

        topPanel.add(btnIniciarSesion);
        topPanel.add(btnRegistro);
        topPanel.add(btnSpotify);
        topPanel.add(btnSalir);

        add(topPanel, BorderLayout.NORTH);

        // Acción del botón "Iniciar Sesión"
        btnIniciarSesion.addActionListener(e -> {
            new LoginFrame();
        });

        // Acción del botón "Registro"
        btnRegistro.addActionListener(e -> {
            new RegistrationForm();
        });

        //Acción del botón "Buscar"
        btnBuscar.addActionListener(e -> {
            List<Album> albums = null;
            try {
                albums = musicBrainzService.searchAlbum(searchField.getText());
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
            showSearchResults(albums);
        });

        //Acción del botón "Vincular con cuenta de Spotify"
        btnSpotify.addActionListener(e -> {
            SpotifyAuth.requestNewAuthorizationCode();
        });

        // Acción del botón "Salir"
        btnSalir.addActionListener(e -> {
            System.exit(0);
        });

        // Efectos hover
        addHoverEffect(btnNovedades);
        addHoverEffect(btnExplorarArtistas);
        addHoverEffect(btnCantantesFavoritos);
        addHoverEffect(btnMasEscuchado);

    }

    public void showAccountInfoButton() {
        JPanel topPanel = (JPanel) btnSpotify.getParent();
        topPanel.remove(btnSpotify); // Remove the Spotify link button
        topPanel.add(btnDatosSpotify); // Add the account info button
        topPanel.revalidate();
        topPanel.repaint();

        // Set up the action for the new button to show account info
        btnDatosSpotify.addActionListener(e -> {
            if (SpotifyAuth.accessToken != null) {
                SpotifyAuth.getUserAccountInfo(SpotifyAuth.accessToken);
            } else {
                System.out.println("Access token is not available.");
            }
        });
    }


    //acceso a top panel delde JVentanaLogged
    protected JPanel getTopPanel() {
        return topPanel;
    }

    // Resto de los métodos para crear botones
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

    private JButton createImageButton(String text, String imagePath, int buttonWidth, int buttonHeight) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };

        button.setFocusPainted(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.TOP);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        return button;
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            private Color originalColor = button.getForeground();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(originalColor);
            }
        });
    }

    public void showSearchResults(List<Album> albums) {
        if (albums.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No results found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            SearchResults resultsWindow = new SearchResults(albums, SearchTypes.ALBUM);
            resultsWindow.setVisible(true);
        }
    }





    public static void main(String[] args) {
        JVentana ventanaPpal = new JVentana();
        ventanaPpal.setVisible(true);
    }

}