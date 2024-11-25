package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionInputsPanel extends JPanel {

    private final int maxComponentCount = ConfigLoader.getInt("fip.maxCompCount");
    private CustomButton newButton;
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
        setPreferredSize(ConfigLoader.getDim("dim.fip"));
        setMaximumSize(ConfigLoader.getDim("dim.fip"));
        setMinimumSize(ConfigLoader.getDim("dim.fip"));
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setBackground(ConfigLoader.getColor("color.fip.background"));
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        newButton = new CustomButton("Add new");
        newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newButton.addActionListener(event -> addNewInput());

        add(newButton);
    }

    private void addNewInput() {
        FunctionTextInputComponent newInput = new FunctionTextInputComponent(this);
        inputComponents.add(newInput);
        float hue = (float) (inputComponents.size() - 1) / maxComponentCount;
        Color graphColor = Color.getHSBColor(hue, 0.9f, 1f);
        newInput.getGraph().setColor(graphColor);
        newInput.setBorderColor(graphColor);
        add(newInput, getComponentCount() - 1);  // Insert before the 'Add new' button
        drawingPane.addGraph(newInput.getGraph());
        if(inputComponents.size() == maxComponentCount)
            newButton.setVisible(false);
        revalidate();
        repaint();
    }

    public void deleteInput(FunctionTextInputComponent input) {
        drawingPane.removeGraph(input.getGraph());
        inputComponents.remove(input);
        if(inputComponents.size() == maxComponentCount - 1)
            newButton.setVisible(true);
        remove(input);
        revalidate();
        repaint();
    }

    public void deleteAll(){
        for(int i = 0; i < inputComponents.size(); ){
            deleteInput(inputComponents.get(0));
        }
    }

    public void setMultipleInputs(List<String> inputs){
        for(String s : inputs){
            addNewInput();
            FunctionTextInputComponent newInput = inputComponents.get(inputComponents.size()-1);
            newInput.getGraph().setValuesFromExpression(s);
            newInput.setExpression(s);

        }
    }

    public List<String> getAllInputs(){
        return inputComponents.stream().map(FunctionTextInputComponent::getExpression).toList();
    }

    public void updateAll() {
        inputComponents.forEach(input -> drawingPane.updateGraph(input.getGraph()));
    }

    public void updateInput(FunctionTextInputComponent input) {
        drawingPane.updateGraph(input.getGraph());
    }

    //for tests
    public boolean isAddNewButtonVisible() {
        return newButton.isVisible();
    }

}
