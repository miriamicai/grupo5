package isw.ui;

import isw.releases.Album;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchResults extends JFrame {
    private JPanel jPanel;
    private JPanel releasePanel;
    private JLabel searchResultsLabel;

    public SearchResults(List<Album> albums){
        System.out.println("Number of albums found: " + albums.size()); // Debug print

        setTitle("Search Results");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /*jPanel = new JPanel(new BorderLayout());
        releasePanel = new JPanel(new GridLayout(0, 3, 10, 10));*/

        for (Album album : albums){
            AlbumDisplayPanel albumPanel = new AlbumDisplayPanel(album);
            /*albumPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {

                }
            });*/
            releasePanel.add(albumPanel);
        }

        releasePanel.revalidate();
        releasePanel.repaint();

        jPanel.add(releasePanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(releasePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}