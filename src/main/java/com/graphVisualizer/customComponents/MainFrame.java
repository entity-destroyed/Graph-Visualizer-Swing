package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.ConfigLoader;
import com.graphVisualizer.utils.GraphSerializer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final String MENU = "Menu";
    private final String EMPTY_SCENE = "EmptyScene";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel basePanel = new JPanel(cardLayout);
    private FunctionInputsPanel functionInputsPanel;

    public MainFrame(){
        setTitle("Function Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ConfigLoader.getDim("dim.window"));

        initMenuPanel();
        initEmptyScenePanel();

        cardLayout.show(basePanel, MENU);
        add(basePanel);
        setVisible(true);
    }

    private void initMenuPanel() {
        JPanel menuPanel = new JPanel(new GridBagLayout());
        CustomButton emptySceneButton = new CustomButton("New empty pane");
        CustomButton loadSceneButton = new CustomButton("Load previous");

        emptySceneButton.addActionListener(e -> switchToScene(EMPTY_SCENE));
        loadSceneButton.addActionListener(e -> loadGraphs());

        menuPanel.add(emptySceneButton);
        menuPanel.add(loadSceneButton);
        basePanel.add(menuPanel, MENU);
    }

    private void initEmptyScenePanel() {
        JPanel emptyScene = new JPanel(new BorderLayout(10, 10));
        DrawingPane drawingPane = new DrawingPane();
        functionInputsPanel = new FunctionInputsPanel(drawingPane);

        JPanel optionsPanel = new JPanel();
        CustomButton backButton = new CustomButton("Back to menu");
        CustomButton saveButton = new CustomButton("Save graphs");

        backButton.addActionListener(e -> backToMenu());
        saveButton.addActionListener(e -> saveGraphs());

        optionsPanel.add(backButton);
        optionsPanel.add(saveButton);

        emptyScene.add(functionInputsPanel, BorderLayout.WEST);
        emptyScene.add(drawingPane, BorderLayout.CENTER);
        emptyScene.add(optionsPanel, BorderLayout.SOUTH);
        emptyScene.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        basePanel.add(emptyScene, EMPTY_SCENE);
    }

    private void switchToScene(String sceneName) {
        cardLayout.show(basePanel, sceneName);
    }

    private void loadGraphs() {
        try {
            functionInputsPanel.setMultipleInputs(GraphSerializer.readGraphsFromFile());
            functionInputsPanel.updateAll();
            switchToScene(EMPTY_SCENE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading graphs: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveGraphs() {
        try {
            GraphSerializer.writeGraphsToFile(functionInputsPanel.getAllInputs());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving graphs: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToMenu() {
        functionInputsPanel.deleteAll();
        switchToScene(MENU);
    }
}
