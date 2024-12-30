package isw.ui;

import isw.dao.LastFmService;
import isw.releases.Album;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class UltimasNovedadesFrame extends JFrame {

    private final LastFmService lastFmService = new LastFmService();

    public UltimasNovedadesFrame() {
        setTitle("Últimas Novedades Musicales");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Últimas Novedades Musicales", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel albumsPanel = new JPanel();
        albumsPanel.setLayout(new GridLayout(0, 3, 15, 15)); // 3 columns for better visualization
        albumsPanel.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(albumsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Fetch albums and populate the panel
        List<Album> albums = lastFmService.searchAlbum("new releases");
        if (albums != null && !albums.isEmpty()) {
            for (Album album : albums) {
                albumsPanel.add(createAlbumCard(album));
            }
        } else {
            JLabel noDataLabel = new JLabel("No se encontraron novedades.", JLabel.CENTER);
            noDataLabel.setForeground(Color.WHITE);
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            albumsPanel.add(noDataLabel);
        }

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createAlbumCard(Album album) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.DARK_GRAY);
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Placeholder while the image loads
        JLabel coverLabel = new JLabel("Cargando...", JLabel.CENTER);
        coverLabel.setForeground(Color.LIGHT_GRAY);

        // Album details
        JLabel titleLabel = new JLabel(album.getTitle(), JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel artistLabel = new JLabel("Por: " + album.getArtist(), JLabel.CENTER);
        artistLabel.setForeground(Color.LIGHT_GRAY);
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add components to card
        card.add(coverLabel, BorderLayout.CENTER);
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        detailsPanel.setBackground(Color.DARK_GRAY);
        detailsPanel.add(titleLabel);
        detailsPanel.add(artistLabel);
        card.add(detailsPanel, BorderLayout.SOUTH);

        // Load image asynchronously
        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() {
                try {
                    URL imageUrl = new URL(album.getCoverUrl());
                    return new ImageIcon(imageUrl);
                } catch (Exception e) {
                    System.err.println("Error al cargar la imagen: " + e.getMessage());
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    ImageIcon coverIcon = get();
                    if (coverIcon != null) {
                        Image scaledImage = coverIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        coverLabel.setIcon(new ImageIcon(scaledImage));
                        coverLabel.setText(""); // Clear placeholder text
                    } else {
                        coverLabel.setText("Sin Imagen");
                    }
                } catch (Exception e) {
                    coverLabel.setText("Error al cargar");
                    e.printStackTrace();
                }
            }
        }.execute();

        return card;
    }

}
