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

        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(160, 400));
        setMaximumSize(new Dimension(160, 400));
        setMinimumSize(new Dimension(160, 400));
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setBackground(new Color(178, 255, 146));
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        newButton = new JButton("Add new");
        newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newButton.addActionListener(event -> addNewInput());

        add(newButton);
    }

    private void addNewInput() {
        FunctionTextInputComponent newInput = new FunctionTextInputComponent(this);
        inputComponents.add(newInput);
        float hue = (float) (inputComponents.size() - 1) / 5;
        Color graphColor = Color.getHSBColor(hue, 0.9f, 1f);
        newInput.getGraph().setColor(graphColor);
        newInput.setBorderColor(graphColor);
        add(newInput, getComponentCount() - 1);  // Insert before the 'Add new' button
        drawingPane.addGraph(newInput.getGraph());
        if(inputComponents.size() == 5)
            newButton.setVisible(false);
        revalidate();
        repaint();
    }

    public void deleteInput(FunctionTextInputComponent input) {
        drawingPane.removeGraph(input.getGraph());
        inputComponents.remove(input);
        if(inputComponents.size() == 4)
            newButton.setVisible(true);
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
