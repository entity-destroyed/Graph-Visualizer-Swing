package com.graphVisualizer.customComponents;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private int windowWidth = 600;
    private int windowHeight = 460;

    public MainFrame(){
        setTitle("Function Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth,windowHeight);

        //create card layout
        CardLayout cardLayout = new CardLayout();
        JPanel basePanel = new JPanel(cardLayout);

        //create JPanel and set layout
        JPanel emptyScene = new JPanel();
        //emptyScene.setLayout(new BoxLayout(emptyScene, BoxLayout.X_AXIS));

        emptyScene.setLayout(new BorderLayout(10,10));
        basePanel.add(emptyScene,"EmptyScene");


        //create components
        DrawingPane drawingPane = new DrawingPane();
        FunctionInputsPanel functionInputsPanel = new FunctionInputsPanel(drawingPane);
        JButton backButton = new JButton("Back to menu");
        JButton saveButton = new JButton("Save graphs");

        //add components to empty scene
        emptyScene.add(functionInputsPanel, BorderLayout.WEST);
        emptyScene.add(drawingPane, BorderLayout.CENTER);
        JPanel optionsPanel = new JPanel();
        optionsPanel.add(backButton);
        optionsPanel.add(saveButton);
        emptyScene.add(optionsPanel, BorderLayout.SOUTH);
        emptyScene.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        //create menu buttons
        JButton emptySceneButton = new JButton("New empty pane");
        JButton loadSceneButton = new JButton("Load previous");

        //create menu panel
        JPanel menuPanel = new JPanel();
        basePanel.add(menuPanel, "Menu");
        menuPanel.add(emptySceneButton);
        menuPanel.add(loadSceneButton);

        //add action listeners to menu items
        emptySceneButton.addActionListener(e -> cardLayout.show(basePanel,"EmptyScene"));
        loadSceneButton.addActionListener(e -> {
            //TODO load scene button action listener -> serialization
        });
        backButton.addActionListener(e -> cardLayout.show(basePanel, "Menu"));
        saveButton.addActionListener(e -> {
            //TODO serialization
        });

        cardLayout.show(basePanel, "Menu");
        add(basePanel);
        setVisible(true);
    }
}
