package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;
import com.graphVisualizer.utils.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Component for storing and managing functions wrapped in {@code FunctionTextInputComponent}s.
 * <p>
 *     This is a box, that has the inputs listed from top to bottom. If the number of stored functions
 *     is less than the limit, the last component in the list is the {@code newButton}.
 * </p>
 * @see FunctionTextInputComponent
 */
public class FunctionInputsPanel extends JPanel {

    /**The maximum number of functions in the panel*/
    private final int maxFunctionCount = ConfigLoader.getInt("fip.maxFuncCount");

    /**Button for adding a new input
     * @see #addNewInput() */
    private CustomButton newButton;

    /**The pane on which functions' {@code Graphs} are being drawn to.
     * @see com.graphVisualizer.math.Graph
     * @see DrawingPane#addGraph(Graph) */
    private final DrawingPane drawingPane;

    /**List of the {@code FunctionTextInputComponent}s
     * @see FunctionTextInputComponent*/
    private final List<FunctionTextInputComponent> inputComponents;

    /**Initializes the panel with the given {@code DrawingPane}
     * @param drawingPane the pane we draw functions onto.
     * @see DrawingPane*/
    public FunctionInputsPanel(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
        this.inputComponents = new ArrayList<>();

        initializeGUI();
    }

    /**
     * Sets the alignment, background, border for the panel, and the {@code ActionListener} for the {@code newButton}.
     * <br>
     * Also adds the {@code newButton} to the empty panel.
     * @see #newButton
     */
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

    /**
     * Gets called when the user presses the {@code newButton}.
     * Creates a {@code FunctionTextInputComponent} and adds the {@code Graph} to the {@code DrawingPane}
     * with a calculated color based on the index .<br>
     * At this point the function itself is empty, because the user have not yet provided an assignment.
     * @see #newButton
     * @see com.graphVisualizer.math.Graph
     * @see DrawingPane
     */
    private void addNewInput() {
        FunctionTextInputComponent newInput = new FunctionTextInputComponent(this);
        inputComponents.add(newInput);
        float hue = (float) (inputComponents.size() - 1) / maxFunctionCount;
        Color graphColor = Color.getHSBColor(hue, 0.9f, 1f);
        newInput.getGraph().setColor(graphColor);
        newInput.setBorderColor(graphColor);
        add(newInput, getComponentCount() - 1);  // Insert before the 'Add new' button
        drawingPane.addGraph(newInput.getGraph());
        if(inputComponents.size() == maxFunctionCount)
            newButton.setVisible(false);
        revalidate();
        repaint();
    }

    /**
     * Gets called when the {@code deleteButton} of the {@code FunctionTextInputComponent} is pressed.
     * <p>
     * Removes the provided {@code input} component from the list, and from the {@code DrawingPane}.
     * @param input the {@code FunctionTextInputComponent that gets deleted}.
     * @see DrawingPane
     * @see FunctionTextInputComponent
     */
    public void deleteInput(FunctionTextInputComponent input) {
        drawingPane.removeGraph(input.getGraph());
        inputComponents.remove(input);
        if(inputComponents.size() == maxFunctionCount - 1)
            newButton.setVisible(true);
        remove(input);
        revalidate();
        repaint();
    }

    /**
     * Deletes all {@code FunctionTextInputComponents} from the panel and from the {@code DrawingPane}.
     * @see FunctionTextInputComponent
     * @see DrawingPane
     */
    public void deleteAll(){
        for(int i = 0; i < inputComponents.size(); ){
            deleteInput(inputComponents.get(0));
        }
    }

    /**
     * Creates number of inputs equal to the number of elements in the {@code inputs} List, and sets their
     * function and {@code Graph} based on the expressions in the {@code input}.
     * @param inputs the mathematical expressions for the functions
     * @see com.graphVisualizer.math.Graph
     */
    public void setMultipleInputs(List<String> inputs){
        for(String s : inputs){
            addNewInput();
            FunctionTextInputComponent newInput = inputComponents.get(inputComponents.size()-1);
            newInput.getGraph().setValuesFromExpression(s);
            newInput.setExpression(s);

        }
    }

    /**
     * Getter for the present inputs in the panel.
     * @return the List of the mathematical expressions of the functions in the pane.
     */
    public List<String> getAllInputs(){
        return inputComponents.stream().map(FunctionTextInputComponent::getExpression).toList();
    }

    /**
     * Refreshes all the functions' {@code Graphs} in the {@code DrawingPane}
     * @see DrawingPane
     * @see com.graphVisualizer.math.Graph
     */
    public void updateAll() {
        inputComponents.forEach(input -> drawingPane.updateGraph(input.getGraph()));
    }

    /**
     * Refreshes the {@code input} function's {@code Graph} in the {@code DrawingPane}
     * @param input the function to be updated
     * @see DrawingPane
     * @see com.graphVisualizer.math.Graph
     */
    public void updateInput(FunctionTextInputComponent input) {
        drawingPane.updateGraph(input.getGraph());
    }

    /**
     * For tests.
     * @return true if the {@code newButton} is visible
     * @see #newButton
     */
    public boolean isAddNewButtonVisible() {
        return newButton.isVisible();
    }

}
