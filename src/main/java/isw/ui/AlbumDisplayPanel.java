package isw.ui;

import isw.releases.Album;

import javax.swing.*;
import java.awt.*;

public class AlbumDisplayPanel extends JPanel{
    private JLabel name, artist, cover;
    private JPanel jPanel;

    public AlbumDisplayPanel(Album album) {
        setLayout(new BorderLayout());

        // Use album data to populate the panel
        JLabel titleLabel = new JLabel(album.getTitle());
        JLabel artistLabel = new JLabel(album.getArtist());
        JLabel coverLabel = new JLabel(new ImageIcon(album.getCoverUrl())); // Assume coverUrl is a valid image URL

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
