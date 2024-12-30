package isw.ui;

import isw.dao.LastFmService;
import isw.releases.Song;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CantantesFavoritosFrame extends JFrame {

    private final LastFmService lastFmService = new LastFmService();

    public CantantesFavoritosFrame() {
        setTitle("Nuevas Canciones Populares");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Título principal
        JLabel titleLabel = new JLabel("Canciones Populares", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel para mostrar las canciones
        JPanel songsPanel = new JPanel();
        songsPanel.setLayout(new GridLayout(0, 1, 10, 10)); // 1 columna, espacio entre filas
        songsPanel.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(songsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Obtener canciones y llenar el panel
        List<Song> songs = lastFmService.getNewSongs();
        if (songs != null && !songs.isEmpty()) {
            for (Song song : songs) {
                songsPanel.add(createSongCard(song));
            }
        } else {
            JLabel noDataLabel = new JLabel("No se encontraron nuevas canciones.", JLabel.CENTER);
            noDataLabel.setForeground(Color.WHITE);
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            songsPanel.add(noDataLabel);
        }

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createSongCard(Song song) {
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(2, 1)); // Dos filas: Título y artista
        card.setBackground(Color.DARK_GRAY);
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Etiqueta para el título de la canción
        JLabel titleLabel = new JLabel(song.getTitle(), JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Etiqueta para el artista
        JLabel artistLabel = new JLabel("Por: " + song.getArtist(), JLabel.CENTER);
        artistLabel.setForeground(Color.LIGHT_GRAY);
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        card.add(titleLabel);
        card.add(artistLabel);

        return card;
    }
}
