package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrawingPaneTest {

    private DrawingPane drawingPane;
    private Graph mockGraph;

    @BeforeEach
    void setUp() {
        // Create a new instance of DrawingPane before each test
        drawingPane = new DrawingPane();

        // Mock Graph class
        mockGraph = mock(Graph.class);
    }

    @Test
    void testConstructor() {
        assertNotNull(drawingPane);
        Dimension size = drawingPane.getPreferredSize();
        assertEquals(400, size.getWidth(), "Width should be 400 (from config).");
        assertEquals(400, size.getHeight(), "Height should be 400 (from config).");
    }

    @Test
    void testAddGraph() {
        drawingPane.addGraph(mockGraph);
        List<Graph> graphList = drawingPane.getGraphList();
        assertEquals(1, graphList.size(), "Graph should be added to the list.");
        assertTrue(graphList.contains(mockGraph), "Graph list should contain the added graph.");
    }

    @Test
    void testRemoveGraph() {
        drawingPane.addGraph(mockGraph);
        drawingPane.removeGraph(mockGraph);
        List<Graph> graphList = drawingPane.getGraphList();
        assertEquals(0, graphList.size(), "Graph should be removed from the list.");
    }

    @Test
    void testUpdateGraph() {
        drawingPane.addGraph(mockGraph);
        drawingPane.updateGraph(mockGraph);
        verify(mockGraph).calculateGraphCurve(anyInt(), anyInt());
    }

    @Test
    void testGridLinesInitialization() {
        List<Line2D> gridLines = drawingPane.getGridLines();
        assertEquals(2, gridLines.size(), "Grid lines should contain 2 lines.");
    }

}