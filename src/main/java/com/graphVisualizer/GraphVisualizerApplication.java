package com.graphVisualizer;

import com.graphVisualizer.customComponents.MainFrame;

import javax.swing.*;

public class GraphVisualizerApplication {

    public void startApplication() {
        MainFrame mainFrame = new MainFrame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphVisualizerApplication app = new GraphVisualizerApplication();
            app.startApplication();
        });
    }
}
