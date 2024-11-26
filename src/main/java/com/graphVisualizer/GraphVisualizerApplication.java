package com.graphVisualizer;

import com.graphVisualizer.customComponents.MainFrame;

import javax.swing.*;

/**
 * The {@code GraphVisualizerApplication} class serves as the entry point for the graph visualization application.
 * It initializes the main application frame where users can interact with the graphical user interface.
 *
 * @see MainFrame
 */
public class GraphVisualizerApplication {

    /**
     * Initializes and starts the main application frame for the graph visualization application.
     * The method creates an instance of {@code MainFrame}, which serves as the main window of the application.
     *
     * @see MainFrame
     */
    public void startApplication() {
        MainFrame mainFrame = new MainFrame();
    }

    /**
     * The {@code main} method that serves as the entry point for the {@code GraphVisualizerApplication}.
     * It initializes and starts the application using {@code Swing}'s event dispatch thread.
     *
     * @param args command-line arguments passed to the application
     * @see SwingUtilities
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphVisualizerApplication app = new GraphVisualizerApplication();
            app.startApplication();
        });
    }
}
