package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;

import javax.swing.*;
import java.awt.*;

public class FunctionTextInputComponent extends JPanel {

    private Graph graph;
    private JTextField textField;
    private CustomToggleButton visibilityButton;
    private CustomButton deleteButton;

    public FunctionTextInputComponent(FunctionInputsPanel panel) {
        this.graph = new Graph();
        initializeUI();
        setupEventHandlers(panel);
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        setMaximumSize(new Dimension(170,80));
        setBackground(new Color(94, 255, 94));
        setAlignmentY(Component.TOP_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(142,20));
        textField.setMinimumSize(new Dimension(142,20));
        visibilityButton = new CustomToggleButton(" Hide ");
        visibilityButton.setPreferredSize(new Dimension(68,20));
        deleteButton = new CustomButton("Delete");

        textField.setToolTipText("Enter function, e.g., 2x+3");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(visibilityButton, gbc);

        gbc.gridx = 1;
        add(deleteButton, gbc);
    }

    private void setupEventHandlers(FunctionInputsPanel panel) {
        // Evaluate on losing focus
        textField.addActionListener(event -> textField.transferFocus());
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                try{
                    graph.setValuesFromExpression(textField.getText());
                    textField.setBackground(Color.white);
                }catch(IllegalArgumentException iaex){
                    textField.setBackground(new Color(255, 57, 79));
                }
                panel.updateInput(FunctionTextInputComponent.this);
            }
        });

        // Delete button handler
        deleteButton.addActionListener(event -> panel.deleteInput(FunctionTextInputComponent.this));

        // Visibility toggle handler
        visibilityButton.addActionListener(event -> toggleVisibility(panel));
    }

    private void toggleVisibility(FunctionInputsPanel panel) {
        boolean isHidden = visibilityButton.isSelected();
        graph.setVisible(!isHidden);
        panel.updateInput(this);
        visibilityButton.setText(isHidden ? "Show" : "Hide");
    }

    public void setBorderColor(Color c){
        textField.setBorder(BorderFactory.createLineBorder(c,2));
    }

    public Graph getGraph() {
        return graph;
    }

    public String getExpression(){
        return textField.getText();
    }
    public void setExpression(String expression){
        textField.setText(expression);
    }
}
