package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.ConfigLoader;
import com.graphVisualizer.utils.GraphSerializer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final String MENU = "Menu";
    private final String EMPTY_SCENE = "EmptyScene";

    public MainFrame(){
        setTitle("Function Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ConfigLoader.getDim("dim.window"));

        //create card layout
        CardLayout cardLayout = new CardLayout();
        JPanel basePanel = new JPanel(cardLayout);

        //create JPanel and set layout
        JPanel emptyScene = new JPanel();

        emptyScene.setLayout(new BorderLayout(10,10));
        basePanel.add(emptyScene,EMPTY_SCENE);


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
        basePanel.add(menuPanel, MENU);
        menuPanel.add(emptySceneButton);
        menuPanel.add(loadSceneButton);

        //add action listeners to menu items
        emptySceneButton.addActionListener(e -> cardLayout.show(basePanel,EMPTY_SCENE));
        loadSceneButton.addActionListener(e -> {
            functionInputsPanel.setMultipleInputs(GraphSerializer.readGraphsFromFile());
            functionInputsPanel.updateAll();
            cardLayout.show(basePanel,EMPTY_SCENE);
        });
        backButton.addActionListener(e -> {
            cardLayout.show(basePanel, MENU);
            functionInputsPanel.deleteAll();
        });
        saveButton.addActionListener(e ->
                GraphSerializer.writeGraphsToFile(functionInputsPanel.getAllInputs()));

        cardLayout.show(basePanel, MENU);
        add(basePanel);
        setVisible(true);
    }
}
