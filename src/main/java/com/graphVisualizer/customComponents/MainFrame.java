package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.GraphSerializer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final int windowWidth = 600;
    private final int windowHeight = 520;

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
        CustomButton backButton = new CustomButton("Back to menu");
        CustomButton saveButton = new CustomButton("Save graphs");

        //add components to empty scene
        emptyScene.add(functionInputsPanel, BorderLayout.WEST);
        emptyScene.add(drawingPane, BorderLayout.CENTER);
        JPanel optionsPanel = new JPanel();
        optionsPanel.add(backButton);
        optionsPanel.add(saveButton);
        emptyScene.add(optionsPanel, BorderLayout.SOUTH);
        emptyScene.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        //create menu buttons
        CustomButton emptySceneButton = new CustomButton("New empty pane");
        CustomButton loadSceneButton = new CustomButton("Load previous");

        //create menu panel
        JPanel menuPanel = new JPanel();
        basePanel.add(menuPanel, "Menu");
        menuPanel.add(emptySceneButton);
        menuPanel.add(loadSceneButton);

        //add action listeners to menu items
        emptySceneButton.addActionListener(e -> {
            cardLayout.show(basePanel,"EmptyScene");
        });
        loadSceneButton.addActionListener(e -> {
            functionInputsPanel.setMultipleInputs(GraphSerializer.readGraphsFromFile());
            functionInputsPanel.updateAll();
            cardLayout.show(basePanel,"EmptyScene");
        });
        backButton.addActionListener(e -> {
            cardLayout.show(basePanel, "Menu");
            functionInputsPanel.deleteAll();
        });
        saveButton.addActionListener(e ->
                GraphSerializer.writeGraphsToFile(functionInputsPanel.getAllInputs()));

        cardLayout.show(basePanel, "Menu");
        add(basePanel);
        setVisible(true);
    }
}
