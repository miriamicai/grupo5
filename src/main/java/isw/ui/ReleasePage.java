package isw.ui;

import isw.cliente.Cliente;
import isw.releases.Album;
import javax.swing.*;
import java.net.URL;
import java.util.HashMap;

public class ReleasePage extends JFrame{
    private Album album;
    private JLabel title, cover, releaseDate, avgRating, genres, rating, review;
    private JTextField ratingBox;
    private JTextArea reviewBox;
    private JButton saveChangesButton;
    private JButton cancelButton;
    private JLabel artist;
    private JLabel track_Count;
    private JLabel length;
    public HashMap<Integer, Integer> user_sessions;
    private Cliente c;

    public ReleasePage(Album album, Cliente c) {
        this.album = album;
        this.c = c;
        setTitle(album.getTitle());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeUIComponents();

        populateAlbumDetails(album);

        setVisible(true);
    }

    private void populateAlbumDetails(Album album) {
        // Set album details into the respective UI components
        title.setText(album.getTitle());
        artist.setText(album.getArtist());
        releaseDate.setText(album.getRelease() != null ? album.getRelease().toString() : "Unknown");
        track_Count.setText("Number of tracks: " + String.valueOf(album.getNumTracks()));
        ratingBox.setText(album.getPersonalRating() > 0 ? String.valueOf(album.getPersonalRating()) : "");
        // Assume you have a field to display genres
        genres.setText("Genres: " + album.getGenres());
        length.setText("Length: " + album.getLength());

        // Load the album cover image
        loadCoverImage(album.getCoverUrl());
    }

    private void initializeUIComponents() {
        // Initialize labels with default text
        title = new JLabel("Title: ");
        artist = new JLabel("Artist: ");
        releaseDate = new JLabel("Release Date: ");
        track_Count = new JLabel("Track Count: ");
        genres = new JLabel("Genres: ");
        length = new JLabel("Length: ");
        //cover = new JLabel("Cover not available");

        // Initialize input fields
        ratingBox = new JTextField(5); // Small text field for ratings
        reviewBox = new JTextArea(3, 20); // Text area for reviews

        // Initialize buttons
        saveChangesButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Use a layout manager to organize components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout

        // Add components to the panel
        panel.add(title);
        panel.add(artist);
        panel.add(releaseDate);
        panel.add(track_Count);
        //panel.add(avgRating);
        panel.add(genres);
        panel.add(length);
        panel.add(cover);
        panel.add(new JLabel("Your Rating:"));
        panel.add(ratingBox);
        panel.add(new JLabel("Your Review:"));
        panel.add(new JScrollPane(reviewBox)); // Add scroll for the review box
        panel.add(saveChangesButton);
        panel.add(cancelButton);

        // Add panel to the frame
        add(panel);
    }

    private void loadCoverImage(String coverUrl) {
        // Load the image into the JLabel
        try {
            ImageIcon icon = new ImageIcon(new URL(coverUrl));
            cover.setIcon(icon);
        } catch (Exception e) {
            cover.setText("Cover not available");
        }
    }

    private void onSave(int login){

        String rating = ratingBox.getText().trim();
        String review = reviewBox.getText().trim();

        /*if(rating.isEmpty() || review.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a rating or a review.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }*/

        //boolean nonZeroValue = loginSessions.values().stream().anyMatch(value -> value > 0);
        if(login > 0){
            String albumId = (album.getId() != null && !album.getId().isEmpty()) ? album.getId() : "NO_ID";
            c.logRelease(login, albumId, album.getTitle(),album.getArtist(), album.getRelease());
        }else{
            JOptionPane.showMessageDialog(this, "Please log in before logging a release.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    private void onCancel(){
        dispose();
    }
}