package isw.ui;

import isw.dao.LastFmServiceUI;
import isw.releases.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MusicTriviaGameFrame extends JFrame {

    private LastFmServiceUI lastFmService;
    private List<Song> songs;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JButton[] answerButtons;
    private JLabel scoreLabel;

    public MusicTriviaGameFrame() {
        lastFmService = new LastFmServiceUI();
        songs = lastFmService.getNewSongs(); // Obtener canciones desde Last.fm

        setTitle("Adivina la Canción");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configurar el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Título del juego
        JLabel titleLabel = new JLabel("Adivina la Canción", JLabel.CENTER);
        titleLabel.setForeground(new Color(255, 105, 180)); // Color rosa
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Pregunta
        questionLabel = new JLabel("Cargando pregunta...", JLabel.CENTER);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(questionLabel, BorderLayout.CENTER);

        // Opciones de respuesta
        JPanel answerPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        answerPanel.setBackground(Color.BLACK);
        answerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.setBackground(new Color(64, 64, 64)); // Fondo oscuro
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addActionListener(new AnswerButtonListener());
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(255, 105, 180)); // Rosa al pasar el mouse
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(64, 64, 64)); // Vuelve al color oscuro
                }
            });
            answerButtons[i] = button;
            answerPanel.add(button);
        }
        mainPanel.add(answerPanel, BorderLayout.SOUTH);

        // Puntuación
        scoreLabel = new JLabel("Puntuación: 0", JLabel.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(scoreLabel, BorderLayout.EAST);

        add(mainPanel);

        setVisible(true);
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= songs.size()) {
            endGame();
            return;
        }

        // Obtener canción actual
        Song currentSong = songs.get(currentQuestionIndex);

        // Configurar pregunta
        questionLabel.setText("¿Quién canta la canción: " + currentSong.getTitle() + "?");

        // Configurar opciones
        Random random = new Random();
        int correctAnswerIndex = random.nextInt(4); // Índice de la respuesta correcta
        answerButtons[correctAnswerIndex].setText(currentSong.getArtist());

        // Rellenar las otras opciones con artistas aleatorios
        for (int i = 0; i < 4; i++) {
            if (i != correctAnswerIndex) {
                String randomArtist = songs.get(random.nextInt(songs.size())).getArtist();
                while (randomArtist.equals(currentSong.getArtist())) {
                    randomArtist = songs.get(random.nextInt(songs.size())).getArtist();
                }
                answerButtons[i].setText(randomArtist);
            }
        }
    }

    private void checkAnswer(String selectedArtist) {
        Song currentSong = songs.get(currentQuestionIndex);
        if (selectedArtist.equals(currentSong.getArtist())) {
            score += 10; // Sumar puntos
            JOptionPane.showMessageDialog(this, "¡Correcto!", "Respuesta", JOptionPane.INFORMATION_MESSAGE);
        } else {
            score -= 5; // Restar puntos
            JOptionPane.showMessageDialog(this, "Incorrecto. La respuesta correcta era: " + currentSong.getArtist(), "Respuesta", JOptionPane.ERROR_MESSAGE);
        }

        currentQuestionIndex++;
        scoreLabel.setText("Puntuación: " + score);
        loadQuestion();
    }

    private void endGame() {
        JOptionPane.showMessageDialog(this, "¡Juego terminado! Puntuación final: " + score, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Cerrar la ventana
    }

    private class AnswerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String selectedArtist = clickedButton.getText();
            checkAnswer(selectedArtist);
        }
    }

    public static void main(String[] args) {
        new MusicTriviaGameFrame();
    }
}