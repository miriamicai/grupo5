package isw.ui;

import isw.releases.Album;

import javax.swing.*;
import java.awt.*;

public class AlbumDisplayPanel extends JPanel{
    private JLabel name, artist, cover;
    private JPanel TotalAlbumPanel;

    public AlbumDisplayPanel(Album album){
        setLayout(new BorderLayout());

        String title = album.getTitle() != null ? album.getTitle() : "Unknown Title";
        String artist = album.getArtist() != null ? album.getArtist() : "Unknown Artist";
        ImageIcon coverIcon = album.getCoverUrl() != null ? new ImageIcon(album.getCoverUrl()) : new ImageIcon("src/main/resources/Marquee_moon_album_cover.jpg"); // Placeholder image

        // Use album data to populate the panel
        JLabel titleLabel = new JLabel(title);
        JLabel artistLabel = new JLabel(artist);
        JLabel coverLabel = new JLabel(coverIcon); // Assume coverUrl is a valid image URL

        add(titleLabel, BorderLayout.NORTH);
        add(artistLabel, BorderLayout.CENTER);
        add(coverLabel, BorderLayout.SOUTH);
    }

    // No-argument constructor required by the .form file
    public AlbumDisplayPanel() {
        setLayout(new BorderLayout());

        // Add default or placeholder components
        JLabel titleLabel = new JLabel("Title");
        JLabel artistLabel = new JLabel("Artist");
        JLabel coverLabel = new JLabel("Cover Image");

        add(titleLabel, BorderLayout.NORTH);
        add(artistLabel, BorderLayout.CENTER);
        add(coverLabel, BorderLayout.SOUTH);
    }

    private void initComponents(){}
}
