package isw.ui;

import javax.swing.*;

public class ProfilePage extends JFrame {
    private JPanel contentPane; // Main panel generated by the .form file
    private JLabel pfp, username, favoriteAlbums, recentRatings;
    private JPanel favoriteAlbumsPanel, recentRatingsPanel, accessButtons;
    private JButton profile, reviews, releases;

    public ProfilePage() {
        setTitle("Profile");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the content pane to the generated contentPane panel
        setContentPane(contentPane);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProfilePage::new);
    }
}
