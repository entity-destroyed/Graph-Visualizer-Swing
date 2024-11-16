package com.graphVisualizer;

import com.graphVisualizer.customComponents.DrawingPane;
import com.graphVisualizer.customComponents.FunctionInputsPanel;

import javax.swing.*;

public class GraphVisualizerApplication {

    private int windowWidth = 600;
    private int windowHeight = 420;

    public void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Graph Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);

        // Create a panel for layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); // Horizontal layout

        // Create components
        DrawingPane drawingPane = new DrawingPane();
        FunctionInputsPanel functionPanel = new FunctionInputsPanel(drawingPane);

        // Add components to the main panel
        mainPanel.add(functionPanel);
        mainPanel.add(drawingPane);

        // Set padding and spacing
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Add the main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Finalize and display the window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphVisualizerApplication app = new GraphVisualizerApplication();
            app.createAndShowGUI();
        });
    }
}
