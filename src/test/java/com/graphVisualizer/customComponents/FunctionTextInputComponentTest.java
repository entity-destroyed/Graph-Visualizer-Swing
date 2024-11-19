package com.graphVisualizer.customComponents;

import com.graphVisualizer.math.Graph;
import com.graphVisualizer.customComponents.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FunctionTextInputComponentTest {

    private FunctionInputsPanel mockPanel;
    private FunctionTextInputComponent component;

    @BeforeEach
    void setUp() {
        mockPanel = mock(FunctionInputsPanel.class);
        component = new FunctionTextInputComponent(mockPanel);
    }

    @Test
    void testSetAndGetExpression() {
        String expression = "2x+3";
        component.setExpression(expression);
        assertEquals(expression, component.getExpression(), "Expression should match the set value.");
    }

    @Test
    void testSetBorderColor() {
        Color testColor = Color.BLUE;
        SwingUtilities.invokeLater(() -> {
            component.setBorderColor(testColor);
            assertEquals(testColor, ((javax.swing.border.LineBorder) component.getBorder()).getLineColor(),
                    "Border color should match the set color.");
        });
    }

    @Test
    void testGraphVisibilityToggle() {
        assertTrue(component.getGraph().isVisible(), "Graph should initially be visible.");

        // Simulate clicking the visibility button
        JButton visibilityButton = (JButton) getChildComponentByName(component, CustomToggleButton.class);
        assertNotNull(visibilityButton, "Visibility button should be present.");
        visibilityButton.doClick();

        assertFalse(component.getGraph().isVisible(), "Graph should be invisible after toggling visibility.");
        verify(mockPanel).updateInput(component);
    }

    @Test
    void testDeleteButtonAction() {
        // Simulate clicking the delete button
        JButton deleteButton = (JButton) getChildComponentByName(component, CustomButton.class);
        assertNotNull(deleteButton, "Delete button should be present.");
        deleteButton.doClick();

        verify(mockPanel).deleteInput(component);
    }

    @Test
    void testFocusLostValidInput() {
        String expression = "2x+3";
        JTextField textField = (JTextField) getChildComponentByName(component, JTextField.class);
        assertNotNull(textField, "Text field should be present.");

        textField.setText(expression);
        FocusEvent focusEvent = new FocusEvent(textField, FocusEvent.FOCUS_LOST);
        textField.getFocusListeners()[0].focusLost(focusEvent);

        assertEquals(expression, component.getGraph().getExpression(), "Graph should update with valid input.");
        assertEquals(Color.WHITE, textField.getBackground(), "Background should be white for valid input.");
        verify(mockPanel).updateInput(component);
    }

    @Test
    void testFocusLostInvalidInput() {
        String invalidExpression = "invalid";
        JTextField textField = (JTextField) getChildComponentByName(component, JTextField.class);
        assertNotNull(textField, "Text field should be present.");

        textField.setText(invalidExpression);
        FocusEvent focusEvent = new FocusEvent(textField, FocusEvent.FOCUS_LOST);
        textField.getFocusListeners()[0].focusLost(focusEvent);

        assertThrows(IllegalArgumentException.class, () -> component.getGraph().setValuesFromExpression(invalidExpression),
                "Invalid expression should throw IllegalArgumentException.");
        assertEquals(new Color(255, 57, 79), textField.getBackground(), "Background should indicate error for invalid input.");
        verify(mockPanel).updateInput(component);
    }

    // Helper method to get a child component by type
    private Component getChildComponentByName(Container parent, Class<?> childClass) {
        for (Component component : parent.getComponents()) {
            if (childClass.isInstance(component)) {
                return component;
            }
        }
        return null;
    }
}
