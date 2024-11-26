package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.ConfigLoader;
import com.graphVisualizer.utils.GraphSerializer;

import javax.swing.*;
import java.awt.*;


/**
 * This is the main frame of the application.
 */
public class MainFrame extends JFrame {

    /**
     * String constant for the Menu layout component
     */
    private final String MENU = "Menu";
    /**
     * String constant for the EmptyScene layout component
     */
    private final String EMPTY_SCENE = "EmptyScene";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel basePanel = new JPanel(cardLayout);
    private FunctionInputsPanel functionInputsPanel;

    /**
     * Parameterless constructor: creates the whole of the window, shows the menu
     */
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

    /**
     * Initializes the Menu panel with two buttons: <i>New empty pane</i>, and <i>Load previous</i>.
     */
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

    /**
     * Creates a panel containing the Graph Visualizer user interface (empty by default), and adds it to the base panel
     * providing accessibility for the user from the menu.
     * <p>
     * The panel it creates, contains a {@code FunctionInputsPanel} that manages the inputs through {@code FunctionTextInputComponent}s,
     * and a {@code DrawingPane} where the {@code Graph} of the functions are drawn to.
     *
     * @see FunctionInputsPanel
     * @see FunctionTextInputComponent
     * @see DrawingPane
     * @see com.graphVisualizer.math.Graph
     *
     */
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

    /**
     * Switches scenes in the card layout based on the {@code sceneName}.
     * @param sceneName
     */
    private void switchToScene(String sceneName) {
        cardLayout.show(basePanel, sceneName);
    }

    /**
     * Loads the saved functions from file, and feeds them into the {@code FunctionInputsPanel} where they get stored in a
     * {@code Graph} object contained by {@code FunctionTextInputComponent}s.
     *
     * @see FunctionInputsPanel
     * @see FunctionTextInputComponent
     * @see com.graphVisualizer.math.Graph
     */
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

    /**
     * Saves the present functions of the {@code FunctionInputsPanel} by writing them into the data file.
     *
     * @see FunctionInputsPanel
     */
    private void saveGraphs() {
        try {
            GraphSerializer.writeGraphsToFile(functionInputsPanel.getAllInputs());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving graphs: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Navigates back to the <i>Menu</i> in the card layout.
     */
    private void backToMenu() {
        functionInputsPanel.deleteAll();
        switchToScene(MENU);
    }
}
