package isw.ui;

import isw.dao.LastFmService;
import isw.releases.Album;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class AlbumDisplayPanel extends JPanel{
    private JLabel name, artist, cover;
    private JPanel TotalAlbumPanel;
    private JButton logButton;
    public Album dAlbum;

    public AlbumDisplayPanel(Album album){
        setLayout(new BorderLayout());

        String title = album.getTitle() != null ? album.getTitle() : "Unknown Title";
        String artist = album.getArtist() != null ? album.getArtist() : "Unknown Artist";

        ImageIcon coverIcon;
        try {
            // Attempt to create ImageIcon from a URL if coverUrl is a web URL
            coverIcon = new ImageIcon(new URL(album.getCoverUrl()));
        } catch (MalformedURLException e) {
            // If coverUrl is not a valid URL, load a local placeholder image
            coverIcon = new ImageIcon(album.getCoverUrl()); // Assumes coverUrl is a local path
        }

        // Use album data to populate the panel
        JLabel titleLabel = new JLabel(title);
        JLabel artistLabel = new JLabel(artist);
        JLabel coverLabel = new JLabel(coverIcon); // Assume coverUrl is a valid image URL
        JButton logButton = new JButton("Add");

        add(titleLabel, BorderLayout.NORTH);
        add(artistLabel, BorderLayout.CENTER);
        add(coverLabel, BorderLayout.SOUTH);
        add(logButton, BorderLayout.EAST);

        LastFmService fmService = new LastFmService();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
                JOptionPane.showMessageDialog(
                        AlbumDisplayPanel.this,
                        "You clicked on: " + title + " by " + artist,
                        "Album Clicked",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dAlbum = fmService.getAlbumDetails(album.getId());
                if (dAlbum == null) {
                    //JOptionPane.showMessageDialog(null, "Album details not available.", "Error", JOptionPane.ERROR_MESSAGE);
                    dAlbum = fmService.getAlbumDetails(album.getArtist(), album.getTitle());
                    if (dAlbum == null){
                        JOptionPane.showMessageDialog(null, "Album details not available.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                createReleasePage(dAlbum); // Proceed only if album is valid
            }
        });
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


    public void createReleasePage(Album album){
        new ReleasePage(album);
        //releaseWindow.setVisible(true);
    }
}

