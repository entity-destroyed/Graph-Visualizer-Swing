package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;

import javax.swing.*;
import java.awt.*;

public class FunctionTextInputComponent extends JPanel {

    private Graph graph;
    private JTextField textField;
    private JToggleButton visibilityButton;
    private JButton deleteButton;

    public FunctionTextInputComponent(FunctionInputsPanel panel) {
        this.graph = new Graph();
        initializeUI();
        setupEventHandlers(panel);
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        textField = new JTextField(10);
        visibilityButton = new JToggleButton("Hide");
        deleteButton = new JButton("Delete");

        textField.setToolTipText("Enter function, e.g., =2x+3");

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
                graph.setValuesFromExpression(textField.getText());
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

    public Graph getGraph() {
        return graph;
    }
}
