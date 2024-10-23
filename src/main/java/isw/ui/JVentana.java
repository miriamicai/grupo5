package isw.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import isw.cliente.Cliente;
import isw.domain.Customer;

public class JVentana extends JFrame {

    public JVentana() {
        // Configuración de la ventana principal
        setTitle("Página Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.BLACK);

        JTextField searchField = new JTextField(20);
        JButton btnBuscar = createStyledButton("Buscar");

        topPanel.add(searchField);
        topPanel.add(btnBuscar);

        // Botones de inicio de sesión
        JButton btnIniciarSesion = createStyledButton("Iniciar Sesión");
        JButton btnRegistro = createStyledButton("Registro");
        JButton btnSalir = createStyledButton("Salir");

        topPanel.add(btnIniciarSesion);
        topPanel.add(btnRegistro);
        topPanel.add(btnSalir);

        add(topPanel, BorderLayout.NORTH);

        // Acción del botón "Iniciar Sesión"
        btnIniciarSesion.addActionListener(e -> {
            new LoginWindow(JVentana.this);
        });

        // Acción del botón "Registro"
        btnRegistro.addActionListener(e -> {
            new RegistrationForm();
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

    // Resto de los métodos para crear botones
    private JButton createStyledButton(String text) {
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

    public static void main(String[] args) {
        JVentana ventanaPpal = new JVentana();
        ventanaPpal.setVisible(true);
    }
}

class LoginWindow extends JFrame {
    public LoginWindow(JFrame parent) {
        // Configurar la ventana de login
        setTitle("Iniciar Sesión");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Sección de usuario y contraseña
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        add(lblUsuario, gbc);

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setBackground(new Color(50, 50, 50));
        txtUsuario.setForeground(Color.WHITE);
        gbc.gridx = 1;
        add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setForeground(Color.WHITE);
        add(lblContraseña, gbc);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setBackground(new Color(50, 50, 50));
        txtPassword.setForeground(Color.WHITE);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(50, 150, 50));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // Separación visual entre las secciones
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        gbc.gridy = 3;
        add(separator, gbc);

        // Sección de ID del cliente y recuperación de información
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblId = new JLabel("ID Cliente:");
        lblId.setForeground(Color.WHITE);
        add(lblId, gbc);

        JTextField txtId = new JTextField(15);
        txtId.setBackground(new Color(50, 50, 50));
        txtId.setForeground(Color.WHITE);
        gbc.gridx = 1;
        add(txtId, gbc);

        JButton btnRecibirInfo = new JButton("Recibir Información");
        btnRecibirInfo.setBackground(new Color(50, 150, 50));
        btnRecibirInfo.setForeground(Color.WHITE);
        btnRecibirInfo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnRecibirInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(btnRecibirInfo, gbc);

        // Cuadro de texto para mostrar la información recibida
        JTextArea txtAreaResultado = new JTextArea(5, 20);
        txtAreaResultado.setLineWrap(true);
        txtAreaResultado.setWrapStyleWord(true);
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setBackground(new Color(50, 50, 50));
        txtAreaResultado.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(txtAreaResultado);
        gbc.gridy = 6;
        add(scrollPane, gbc);

        // Lógica para iniciar sesión
        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());
            if (usuario.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(this, "¡Login exitoso!");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        });

        // Lógica para recibir información con el ID del cliente
        btnRecibirInfo.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            String nombreCliente = recuperarInformacion(id);
            txtAreaResultado.setText("Cliente: " + nombreCliente);
        });

        setVisible(true);
    }

    public String recuperarInformacion(int id) {
        Cliente cliente = new Cliente();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getCustomer";
        session.put("id", id);
        session = cliente.sentMessage(context, session);
        Customer cu = (Customer) session.get("Customer");
        String nombre;
        if (cu == null) {
            nombre = "Error - No encontrado en la base de datos";
        } else {
            nombre = cu.getName();
        }
        return nombre;
    }
}
