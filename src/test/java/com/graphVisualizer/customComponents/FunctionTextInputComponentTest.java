package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.ConfigLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    void testSetTextFieldBorderColor() {
        Color testColor = Color.BLUE;
        SwingUtilities.invokeLater(() -> {
            component.setTextFieldBorderColor(testColor);
            assertEquals(testColor, ((javax.swing.border.LineBorder) component.getTextFieldBorder()).getLineColor(),
                    "Border color should match the set color.");
        });
    }

    @Test
    void testGraphVisibilityToggle(){
        assertTrue(component.getGraph().isVisible(), "Graph should initially be visible.");

        // Simulate clicking the visibility button
        CustomToggleButton visibilityButton = (CustomToggleButton) getChildComponentByName(component, CustomToggleButton.class);
        assertNotNull(visibilityButton, "Visibility button should be present.");
        assertFalse(visibilityButton.isSelected(), "Toggle should be unchecked before toggling visibility.");

        // Simulate a mouse press and release
        dispatchMouseEvent(visibilityButton, MouseEvent.MOUSE_PRESSED);
        dispatchMouseEvent(visibilityButton, MouseEvent.MOUSE_RELEASED);
        visibilityButton.doClick();

        // Validate state changes
        assertTrue(visibilityButton.isSelected(), "Toggle should be checked after toggling visibility.");
        assertFalse(component.getGraph().isVisible(), "Graph should be invisible after toggling visibility.");
        verify(mockPanel).updateInput(component);
    }

    @Test
    void testDeleteButtonAction(){
        // Perform action by clicking the delete button
        for (ActionListener actionListener : ((CustomButton) component.getComponent(2)).getActionListeners()) {
            actionListener.actionPerformed(new ActionEvent(component.getComponent(2), ActionEvent.ACTION_PERFORMED, null));
        }

        // Verify that deleteInput is called once on the FunctionInputsPanel mock
        Mockito.verify(mockPanel, Mockito.times(1)).deleteInput(component);
    }

    @Test
    void testFocusLostValidInput() {
        String expression = "2x+3";
        JTextField textField = (JTextField) getChildComponentByName(component, JTextField.class);
        assertNotNull(textField, "Text field should be present.");

        component.setExpression(expression);
        FocusEvent focusEvent = new FocusEvent(textField, FocusEvent.FOCUS_LOST);
        textField.grabFocus();
        textField.getFocusListeners()[0].focusLost(focusEvent);
        textField.transferFocus();

        assertEquals(expression, component.getGraph().getExpression(), "Graph should update with valid input.");
        assertEquals(Color.WHITE, textField.getBackground(), "Background should be white for valid input.");
    }

    @Test
    public void testFocusLostInvalidInput() {
        String invalidExpression = "invalid_expr";

        component.setExpression(invalidExpression);

        FocusEvent focusEvent = new FocusEvent(component.getComponent(0), FocusEvent.FOCUS_LOST);
        for (FocusListener focusListener : component.getComponent(0).getFocusListeners()) {
            focusListener.focusLost(focusEvent);
        }

        // Verify that the background color is set to the error color
        Color expectedErrorColor = ConfigLoader.getColor("color.inputError");
        Color actualBackgroundColor = component.getComponent(0).getBackground();

        assertEquals(expectedErrorColor, actualBackgroundColor,"The background color should be the error color upon invalid input.");

        // Verify that updateInput is called once on the FunctionInputsPanel mock
        Mockito.verify(mockPanel, Mockito.times(1)).updateInput(component);
    }

    // Helper method to dispatch a mouse event to a component
    private void dispatchMouseEvent(Component component, int eventType) {
        MouseEvent event = new MouseEvent(component, eventType, System.currentTimeMillis(), 0, 0, 0, 1, false);
        component.dispatchEvent(event);
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
