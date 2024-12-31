package isw.ui;

import isw.dao.LastFmServiceUI;
import isw.releases.Artist;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ExplorarArtistasFrame extends JFrame {

    private final LastFmServiceUI lastFmService = new LastFmServiceUI();

    public ExplorarArtistasFrame() {
        setTitle("Explorar Artistas");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Explorar Nuevos Artistas", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel artistsPanel = new JPanel();
        artistsPanel.setLayout(new GridLayout(0, 3, 15, 15)); // 3 columns for better visualization
        artistsPanel.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(artistsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Fetch artists and populate the panel
        List<Artist> artists = lastFmService.searchArtists();
        if (artists != null && !artists.isEmpty()) {
            for (Artist artist : artists) {
                artistsPanel.add(createArtistCard(artist));
            }
        } else {
            JLabel noDataLabel = new JLabel("No se encontraron artistas.", JLabel.CENTER);
            noDataLabel.setForeground(Color.WHITE);
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            artistsPanel.add(noDataLabel);
        }

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createArtistCard(Artist artist) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.DARK_GRAY);
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Artist image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon artistIcon = new ImageIcon(new URL(artist.getImageUrl()));
            Image scaledImage = artistIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imageLabel.setText("Sin Imagen");
            imageLabel.setForeground(Color.LIGHT_GRAY);
        }

        // Artist details
        JLabel nameLabel = new JLabel(artist.getName(), JLabel.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        card.add(imageLabel, BorderLayout.CENTER);
        JPanel detailsPanel = new JPanel(new GridLayout(1, 1));
        detailsPanel.setBackground(Color.DARK_GRAY);
        detailsPanel.add(nameLabel);
        card.add(detailsPanel, BorderLayout.SOUTH);

        return card;
    }
}