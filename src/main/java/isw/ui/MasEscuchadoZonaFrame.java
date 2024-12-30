package isw.ui;

import isw.dao.LastFmService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MasEscuchadoZonaFrame extends JFrame {

    public MasEscuchadoZonaFrame() {
        setTitle("Lo Más Escuchado en tu Zona");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Lo Más Escuchado en tu Zona", JLabel.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Obtener datos de Last.fm
        LastFmService lastFmService = new LastFmService();
        Map<String, Integer> topTracks = lastFmService.getTopTracks();

        // Crear gráfica
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : topTracks.entrySet()) {
            dataset.addValue(entry.getValue(), "Reproducciones", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Canciones Más Escuchadas",
                "Canción",
                "Reproducciones",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(750, 500));

        mainPanel.add(chartPanel, BorderLayout.CENTER);
        add(mainPanel);

        setVisible(true);
    }
}
