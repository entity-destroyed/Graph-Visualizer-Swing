package com.graphVisualizer.math;

import com.fathzer.soft.javaluator.StaticVariableSet;
import com.graphVisualizer.utils.ConfigLoader;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * The {@code Graph} class represents a graphical representation of a mathematical curve.
 * It provides functionality to generate and visualize the curve based on a given
 * mathematical expression, scaling, and configuration settings.
 */
public class Graph {
    /**
     * The interval between each calculated point on the graph.
     * This variable is used to determine the step size for iterating through the range of the graph.
     * Its value is loaded from a configuration file using the key "g.step".
     *
     * @see ConfigLoader
     */
    private double step = ConfigLoader.getDouble("g.step");

    /**
     * This variable represents the scaling factor for adjusting the graph's size and spacing.
     * It is initialized with a value retrieved from the application configuration using the key "g.scale".
     */
    private double scale = ConfigLoader.getDouble("g.scale");
    /**
     * The starting point of the graph curve calculation.
     * Initialized from the configuration property "g.begin".
     */
    private int begin = ConfigLoader.getInt("g.begin");
    /**
     * The {@code end} variable represents the endpoint value of the graph on the x-axis,
     * loaded from the application configuration.
     * This value determines the upper limit of the x-axis range for the {@code Graph}.
     */
    private int end = ConfigLoader.getInt("g.end");
    /**
     * Represents an array of pre-calculated values for plotting a graph curve.
     * The size of the array is determined by the range specified by the {@code begin} and {@code end} values,
     * divided by the {@code step} value. These values are loaded from configuration settings.
     *
     * @see Graph#calculateGraphCurve(int, int) for usage in plotting the graph.
     * @see Graph#setValuesFromExpression(String) for setting values based on an expression.
     */
    private final double[] values;
    /**
     * An array of {@code Line2D} objects representing the lines or segments that form the graph.
     * Each {@code Line2D} object in this array corresponds to a segment between two consecutive
     * calculated data points on the graph.
     */
    private final Line2D[] lines;
    /**
     * A boolean flag indicating whether the graph is currently visible.
     * This variable is used to control the rendering of the graph in the UI.
     */
    private boolean visible = true;
    /**
     * Represents the color used to draw the graph.
     * This color can be set and retrieved using the corresponding setter and getter methods.
     */
    private Color color;
    /**
     * Represents the mathematical expression used for evaluating the graph's curve.
     * This expression is evaluated against a range of values to generate the graph's data points.
     */
    private String expression;

    /**
     * Constructs a new Graph object. Initializes the 'values' array based on the
     * range from 'begin' to 'end' with a specified 'step' size, and initializes the 'lines'
     * array to store line segments representing the graph.
     * <p>
     * The 'lines' array is set such that each 'Line2D' object is initialized to represent
     * a line segment with default coordinates (0.0, 0.0, 0.0, 0.0).
     */
    public Graph() {
        values = new double[(int) Math.ceil((end - begin+2) / step)];
        lines = new Line2D[values.length - 1];
        for (int i = 0; i < values.length - 1; i++) {
            lines[i] = new Line2D.Double(0.0, 0.0, 0.0, 0.0);
        }
    }

    /**
     * Calculates and sets the position of the graph curve's line segments based on the given center coordinates.
     * The method iterates over the values array and updates the lines array to represent the curve.
     *
     * @param centerX the X coordinate of the graph's center
     * @param centerY the Y coordinate of the graph's center
     */
    public void calculateGraphCurve(int centerX, int centerY) {
        for (int i = 1; i < values.length - 1; i++) {
            lines[i].setLine(
                    begin * scale + (i - 1) * scale * step + centerX,
                    values[i - 1] * (-1) * scale + centerY,
                    begin * scale + (i) * scale * step + centerX,
                    values[i] * (-1) * scale + centerY);
        }
    }

    /**
     * Sets the values array for the graph based on the provided mathematical expression.
     * The expression is evaluated for a range of x values from 'begin' to the end of the values array,
     * with a step size specified by 'step'.
     *
     * @param expression a mathematical expression to be evaluated. The expression should be a valid mathematical
     *                   expression where 'x' is the variable.
     * @throws IllegalArgumentException if the provided expression is invalid or cannot be evaluated.
     */
    public void setValuesFromExpression(String expression) throws IllegalArgumentException {
        this.expression = expression;
        CustomEvaluator evaluator = new CustomEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<>();

        for (double x = begin, i = 0; i < values.length; x += step, i += 1) {
            variables.set("x", x);
            values[(int) i] = evaluator.evaluate(expression, variables);
        }
    }

    public void updateScaleAndRange(double scale, int centerX, int paneSize){
        this.scale = scale;
        //calculate range based on scale and center
        begin = (int) Math.floor((centerX)/-scale);
        end = (int) Math.ceil(begin + paneSize/scale);
        step = 2/(scale); //calculate step: multiply by two to avoid stacking lines that cause little bumps
        try{
            setValuesFromExpression(expression);
        }catch (NullPointerException | IllegalArgumentException ignored) {

        }
    }

    /**
     * Checks if the graph is currently visible.
     *
     * @return true if the graph is visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Retrieves the array of line segments that represent the graph.
     *
     * @return an array of Line2D objects representing the line segments of the graph.
     */
    public Line2D[] getLines() {
        return lines;
    }

    /**
     * Sets the visibility of the graph.
     *
     * @param v a boolean where true makes the graph visible and false makes it invisible
     */
    public void setVisible(boolean v) {
        visible = v;
    }

    /**
     * Returns the color associated with the graph.
     *
     * @return the current color of the graph
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the graph to the specified {@code Color} object.
     *
     * @param c the {@code Color} to set for the graph
     */
    public void setColor(Color c) {
        color = c;
    }

    /**
     * Retrieves the mathematical expression associated with the graph.
     *
     * @return the mathematical expression as a String.
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the mathematical expression for the graph.
     *
     * @param expression the mathematical expression to be evaluated and set
     *                   to the graph. The expression should be a valid
     *                   mathematical expression where 'x' is the variable.
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }
}
