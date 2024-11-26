package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;
import com.graphVisualizer.utils.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code DrawingPane} class is a custom {@code JPanel} that is used to draw {@code Graphs}.
 * It initializes the size of the panel based on configuration values and allows
 * for adding, updating, and removing graphs.
 * @see JPanel
 * @see Graph
 */
public class DrawingPane extends JPanel {

    private final int width = ConfigLoader.getInt("dim.dp.width");
    private final int height = ConfigLoader.getInt("dim.dp.height");
    private final int centerX;
    private final int centerY;
    private final List<Graph> graphList = new ArrayList<>();
    private final List<Line2D> gridLines = new ArrayList<>();

    /**
     * Initializes the drawing pane with specific configurations.
     */
    public DrawingPane(){
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setDoubleBuffered(true);
        centerX = width/2;
        centerY = height/2;
        setBackground(ConfigLoader.getColor("color.dp.background"));
        initGridLines();

    }

    /**
     * Function to draw the grid lines onto the {@code DrawingPane}.
     */
    private void initGridLines(){
        // Add center vertical line
        gridLines.add(new Line2D.Double(centerX, 0, centerX, height));
        // Add center horizontal line
        gridLines.add(new Line2D.Double(0, centerY, width, centerY));
    }

    /**
     * Adds a {@code Graph} to the {@code DrawingPane}.
     * @param graph the {@code Graph} to be added
     */
    public void addGraph(Graph graph){
        graphList.add(graph);
        repaint();
    }

    /**
     * Updates the curve of the {@code Graph}.
     * @param graph the {@code Graph} to be updated
     */
    public void updateGraph(Graph graph){
        graph.calculateGraphCurve(centerX, centerY);
        repaint();
    }

    /**
     * @param graph the {@code Graph} to be removed
     */
    public void removeGraph(Graph graph){
        graphList.remove(graph);
        repaint();
    }

    /**
     * Determines if the {@code Line} is out of bounds
     * @param line the {@code Line} to be checked
     * @return true if the line does not fit the {@code DrawingPane};
     * @see Line2D
     */
    private boolean filterLineOutOfSight(Line2D line){
        return (this.contains((int) Math.round(line.getP1().getX()),(int) Math.round(line.getP1().getY())) ||
                this.contains((int) Math.round(line.getP2().getX()),(int) Math.round(line.getP2().getY())));
    }

    /**
     * Overrides the paintComponent method to draw the grid and {@code Graph} lines onto the {@code DrawingPane}.
     *
     * @param g the Graphics object used for drawing
     * @see Graph
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw grid lines
        g2.setColor(ConfigLoader.getColor("color.grid"));
        for (Line2D line : gridLines) {
            g2.draw(line);
        }

        // Draw graph lines
        graphList.stream()
                .filter(Graph::isVisible)
                .forEach(graph -> {
                    g2.setColor(graph.getColor());
                    Arrays.stream(graph.getLines()).filter(this::filterLineOutOfSight).forEach(g2::draw);
                });
    }


    /**
     * Retrieves the list of all {@code Graph} objects currently contained within the {@code DrawingPane}.
     *
     * @return a list of Graph objects present in the DrawingPane
     * @see Graph
     */
    public List<Graph> getGraphList() {
        return graphList;
    }
}
