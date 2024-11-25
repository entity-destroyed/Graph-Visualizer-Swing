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
        SwingUtilities.invokeLater(() -> {
            assertNotNull(drawingPane);
            assertEquals(400, drawingPane.getWidth(), "Width should be 400.");
            assertEquals(400, drawingPane.getHeight(), "Height should be 400.");
        });
    }

    @Test
    void testAddGraph() {
        SwingUtilities.invokeLater(() -> {
            // Add a mock graph to the pane
            drawingPane.addGraph(mockGraph);

            // Verify that the graph is added to the graphList
            List<Graph> graphList = drawingPane.getGraphList();  // Assume a getter for the list in the DrawingPane class.
            assertEquals(1, graphList.size(), "Graph should be added to the list.");
            assertTrue(graphList.contains(mockGraph), "Graph list should contain the added graph.");
        });
    }

    @Test
    void testRemoveGraph() {
        SwingUtilities.invokeLater(() -> {
            // Add a mock graph and then remove it
            drawingPane.addGraph(mockGraph);
            drawingPane.removeGraph(mockGraph);

            // Verify that the graph is removed from the graphList
            List<Graph> graphList = drawingPane.getGraphList();  // Assume a getter for the list in the DrawingPane class.
            assertEquals(0, graphList.size(), "Graph should be removed from the list.");
        });
    }

    @Test
    void testUpdateGraph() {
        SwingUtilities.invokeLater(() -> {
            // Add the graph and then update it
            drawingPane.addGraph(mockGraph);
            drawingPane.updateGraph(mockGraph);

            // Verify that updateGraph calls the calculateGraphCurve method
            verify(mockGraph).calculateGraphCurve(anyInt(), anyInt());
        });
    }

    @Test
    void testPaintComponentGridLines() {
        SwingUtilities.invokeLater(() -> {
            // Simulate the paint component method call
            Graphics mockGraphics = mock(Graphics.class);
            Graphics2D mockGraphics2D = (Graphics2D) mockGraphics;
            when(mockGraphics.create()).thenReturn(mockGraphics2D);

            drawingPane.paintComponent(mockGraphics);

            // Verify that the grid lines are drawn (based on the gridLines list size)
            verify(mockGraphics2D, times(2)).draw(any(Line2D.class));  // Two center lines (vertical and horizontal)
        });
    }

    @Test
    void testPaintComponentGraphLines() {
        SwingUtilities.invokeLater(() -> {
            // Set up the graph mock to return visible and lines
            Line2D mockLine = mock(Line2D.class);
            when(mockGraph.isVisible()).thenReturn(true);
            when(mockGraph.getColor()).thenReturn(Color.RED);
            when(mockGraph.getLines()).thenReturn(new Line2D[]{mockLine});

            // Add the mock graph
            drawingPane.addGraph(mockGraph);

            // Simulate the paint component method call
            Graphics mockGraphics = mock(Graphics.class);
            Graphics2D mockGraphics2D = (Graphics2D) mockGraphics;
            when(mockGraphics.create()).thenReturn(mockGraphics2D);

            drawingPane.paintComponent(mockGraphics);

            // Verify that the graph line is drawn
            verify(mockGraphics2D).setColor(Color.RED);
            verify(mockGraphics2D).draw(mockLine);
        });
    }

    @Test
    void testFilterLineOutOfSightWithReflection() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Get the private method via reflection
                var method = DrawingPane.class.getDeclaredMethod("filterLineOutOfSight", Line2D.class);
                method.setAccessible(true);  // Make the method accessible

                // Create lines
                Line2D insideLine = new Line2D.Double(100, 100, 200, 200);  // Within bounds
                Line2D outsideLine = new Line2D.Double(-10, -10, -100, -100);  // Out of bounds

                // Invoke the private method and assert results
                assertTrue((Boolean) method.invoke(drawingPane, insideLine), "Line inside the bounds should be in sight.");
                assertFalse((Boolean) method.invoke(drawingPane, outsideLine), "Line outside the bounds should not be in sight.");
            } catch (Exception e) {
                fail("Reflection test failed: " + e.getMessage());
            }
        });
    }

    // To make testing easier, we assume we have a getter method in the DrawingPane class for graphList (not exposed in your original code).
    // You may need to add this to DrawingPane for test verification.
}
