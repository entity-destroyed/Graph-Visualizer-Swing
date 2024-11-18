package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawingPane extends JPanel {

    private final int width = 400;
    private final int height = 400;
    private final int centerX;
    private final int centerY;
    private List<Graph> graphList = new ArrayList<>();
    private List<Line2D> gridLines = new ArrayList<>();

    public DrawingPane(){
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setDoubleBuffered(true);
        centerX = width/2;
        centerY = height/2;
        setBackground(new Color(1,1,1, 0.35f));
        initGridLines();

    }

    private void initGridLines(){
        // Add center vertical line
        gridLines.add(new Line2D.Double(centerX, 0, centerX, height));
        // Add center horizontal line
        gridLines.add(new Line2D.Double(0, centerY, width, centerY));
    }

    public void addGraph(Graph graph){
        graphList.add(graph);
        repaint();
    }

    public void updateGraph(Graph graph){
        graph.calculateGraphCurve(centerX, centerY);
        repaint();
    }

    public void removeGraph(Graph graph){
        graphList.remove(graph);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw grid lines
        g2.setColor(new Color(80, 80, 80, 255));
        for (Line2D line : gridLines) {
            g2.draw(line);
        }

        // Draw graph lines
        graphList.stream()
                .filter(Graph::isVisible)
                .forEach(graph -> {
                    g2.setColor(graph.getColor());
                    Arrays.stream(graph.getLines()).forEach(g2::draw);
        });
    }

}
