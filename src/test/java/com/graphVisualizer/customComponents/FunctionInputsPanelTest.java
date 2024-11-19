package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FunctionInputsPanelTest {

    private FunctionInputsPanel panel;
    private DrawingPane mockDrawingPane;

    @BeforeEach
    void setUp() {
        // Mocking DrawingPane
        mockDrawingPane = mock(DrawingPane.class);

        // Initialize FunctionInputsPanel
        SwingUtilities.invokeLater(() -> panel = new FunctionInputsPanel(mockDrawingPane));
    }

    @Test
    void testInitializeGUI() {
        SwingUtilities.invokeLater(() -> {
            assertNotNull(panel);
            assertEquals(0, panel.getAllInputs().size(), "Initially, there should be no inputs.");
        });
    }

    @Test
    void testAddNewInput() {
        SwingUtilities.invokeLater(() -> {
            // Simulate clicking the "Add new" button
            JButton addNewButton = (JButton) panel.getComponent(0);
            addNewButton.doClick();  // Programmatically trigger the button's action

            // Validate that an input was added
            assertEquals(1, panel.getAllInputs().size(), "Should have one input after adding.");

            // Validate interaction with the mock DrawingPane
            verify(mockDrawingPane).addGraph(any(Graph.class));
        });
    }

    @Test
    void testAddMultipleInputs() {
        SwingUtilities.invokeLater(() -> {
            panel.setMultipleInputs(List.of("x+1", "x^2", "sin(x)"));
            List<String> inputs = panel.getAllInputs();

            assertEquals(3, inputs.size(), "Should contain three inputs.");
            assertEquals("x+1", inputs.get(0));
            assertEquals("x^2", inputs.get(1));
            assertEquals("sin(x)", inputs.get(2));

            verify(mockDrawingPane, times(3)).addGraph(any(Graph.class));
        });
    }

    @Test
    void testDeleteInput() {
        SwingUtilities.invokeLater(() -> {
            panel.setMultipleInputs(List.of("x+1", "x^2"));
            List<String> inputsBefore = panel.getAllInputs();

            assertEquals(2, inputsBefore.size(), "Should have two inputs before deletion.");
            panel.deleteAll();
            List<String> inputsAfter = panel.getAllInputs();

            assertEquals(0, inputsAfter.size(), "Should have no inputs after deletion.");
            verify(mockDrawingPane, times(2)).removeGraph(any(Graph.class));
        });
    }

    @Test
    void testMaxInputsVisibility() {
        SwingUtilities.invokeLater(() -> {
            panel.setMultipleInputs(List.of("1", "2", "3", "4", "5"));

            assertFalse(panel.isAddNewButtonVisible(), "Add new button should be hidden when max inputs are reached.");

            panel.deleteAll();
            assertTrue(panel.isAddNewButtonVisible(), "Add new button should be visible after all inputs are deleted.");
        });
    }
}
