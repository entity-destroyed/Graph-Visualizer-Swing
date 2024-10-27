package com.graphVisualizer.customComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionInputsPanel extends JPanel {

    private JButton newButton;
    private final DrawingPane drawingPane;
    private final List<FunctionTextInputComponent> inputComponents;

    public FunctionInputsPanel(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
        this.inputComponents = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initializeUI();
    }

    private void initializeUI() {
        setMinimumSize(new Dimension(160, getHeight()));
        setBackground(new Color(240, 240, 240));  // Replace `defaultBackground` with an example color
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        newButton = new JButton("Add new");
        newButton.addActionListener(event -> addNewInput());

        add(newButton);
    }

    private void addNewInput() {
        FunctionTextInputComponent newInput = new FunctionTextInputComponent(this);
        inputComponents.add(newInput);
        add(newInput, getComponentCount() - 1);  // Insert before the 'Add new' button
        drawingPane.addGraph(newInput.getGraph());
        revalidate();
        repaint();
    }

    public void deleteInput(FunctionTextInputComponent input) {
        drawingPane.removeGraph(input.getGraph());
        inputComponents.remove(input);
        remove(input);
        revalidate();
        repaint();
    }

    public void updateAll() {
        inputComponents.forEach(input -> drawingPane.updateGraph(input.getGraph()));
    }

    public void updateInput(FunctionTextInputComponent input) {
        drawingPane.updateGraph(input.getGraph());
    }
}
