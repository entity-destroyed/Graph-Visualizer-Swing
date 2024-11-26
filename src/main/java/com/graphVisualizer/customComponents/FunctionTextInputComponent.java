package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;
import com.graphVisualizer.utils.ConfigLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Component for storing and managing an individual function.<br>
 * Instances of this class are contained in {@code FunctionInputsPanel}.
 * <p>
 *     This component is made out of three subcomponents:
 *     <ul>
 *         <li>{@code textField}: the input for the mathematical expression of the function</li>
 *         <li>{@code visibilityButton}: enables the user to hide the function temporary</li>
 *         <li>{@code deleteButton}: on click deletes the function and the component from the {@code
 *         FunctionInputPanel} as well as from the {@code DrawingPane}</li>
 *     </ul>
 * </p>
 *
 * @see #textField
 * @see #visibilityButton
 * @see #deleteButton
 * @see DrawingPane
 * @see FunctionInputsPanel
 */
public class FunctionTextInputComponent extends JPanel {

    /**The graph of the function.*/
    private final Graph graph;

    /**Text input field for the mathematical expression.*/
    private JTextField textField;

    /**For hiding or showing the graph of the function in the {@code DrawingPane}.
     * @see DrawingPane
     * @see #toggleVisibility(FunctionInputsPanel) */
    private CustomToggleButton visibilityButton;

    /**Deletes the function and the component from the {@code FunctionInputPanel}
     * as well as from the {@code DrawingPane}
     * @see DrawingPane*/
    private CustomButton deleteButton;

    /**
     * Initializes the component with respect to the {@code FunctionInputsPanel} it's in.
     * @param panel the {@code FunctionInputsPanel} that contains this component
     * @see FunctionInputsPanel
     */
    public FunctionTextInputComponent(FunctionInputsPanel panel) {
        this.graph = new Graph();
        initializeUI();
        setupEventHandlers(panel);
    }

    /**
     * Sets the layout, size, background and alignment for all three subcomponents.
     * @see #textField
     * @see #visibilityButton
     * @see #deleteButton
     */
    private void initializeUI() {
        setLayout(new GridBagLayout());
        setMaximumSize(ConfigLoader.getDim("dim.ftic"));
        setBackground(ConfigLoader.getColor("color.ftic.background"));
        setAlignmentY(Component.TOP_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        textField = new JTextField();
        Dimension textFieldDim = ConfigLoader.getDim("dim.ftic.textField");
        textField.setPreferredSize(textFieldDim);
        textField.setMinimumSize(textFieldDim);
        visibilityButton = new CustomToggleButton("Hide");
        visibilityButton.setPreferredSize(ConfigLoader.getDim("dim.ftic.visButton"));
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

    /**
     * Sets {@code ActionListeners} for the buttons, and {@code FocusListener} for the {@code textField}
     * @param panel the {@code FunctionInputsPanel} which has to be updated when the user entered the expression.
     * @see FunctionInputsPanel
     * @see #textField
     * @see #visibilityButton
     * @see #deleteButton
     */
    private void setupEventHandlers(FunctionInputsPanel panel) {
        // Evaluate on losing focus
        textField.addActionListener(event -> textField.transferFocus());
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                try{
                    graph.setValuesFromExpression(textField.getText());
                    textField.setBackground(Color.white);
                }catch(IllegalArgumentException iaex){
                    textField.setBackground(ConfigLoader.getColor("color.inputError"));
                }
                panel.updateInput(FunctionTextInputComponent.this);
            }
        });

        // Delete button handler
        deleteButton.addActionListener(event -> panel.deleteInput(FunctionTextInputComponent.this));

        // Visibility toggle handler
        visibilityButton.addActionListener(event -> toggleVisibility(panel));
    }

    /**
     * Sets the visibility of the {@code Graph} of the function
     * @param panel the {@code FunctionInputsPanel} which has to be updated when visibility changed.
     * @see FunctionInputsPanel
     * @see Graph
     */
    private void toggleVisibility(FunctionInputsPanel panel) {
        boolean isHidden = visibilityButton.isSelected();
        graph.setVisible(!isHidden);
        panel.updateInput(this);
        visibilityButton.setText(isHidden ? "Show" : "Hide");
    }

    /**
     * Sets the border {@code Color} of the {@code textField} component to the specified color.
     *
     * @param c the color to set the border to
     * @see #textField
     */
    public void setBorderColor(Color c){
        textField.setBorder(BorderFactory.createLineBorder(c,2));
    }

    /**
     * Retrieves the {@code Graph} associated with this component.
     *
     * @return the current {@code Graph} instance of this component.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Retrieves the current expression text from the {@code textField} component.
     *
     * @return the current expression as a {@code String}
     * @see #textField
     */
    public String getExpression(){
        return textField.getText();
    }

    /**
     * Sets the expression to this component and to the corresponding {@code Graph}
     * @param expression the expression to be set.
     * @see #graph
     * @see Graph
     * @see Graph#setExpression(String)
     * @see #textField
     */
    public void setExpression(String expression){
        textField.setText(expression);
        graph.setExpression(expression);
    }
}
