package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.ConfigLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@code CustomToggleButton} is an extension of {@code CustomButton} that supports a toggling state.
 * It changes its background color between a selected and an unselected state when clicked.
 * The colors are configured using the {@code ConfigLoader} utility.
 *
 * This class adds a mouse listener to toggle the button's state on user interaction and overrides
 * the {@code paintComponent} method to visually represent the current state of the button.
 *
 * The initial state of the button is not selected.
 *
 * @see CustomButton
 * @see ConfigLoader
 */
public class CustomToggleButton extends CustomButton {

    /**
     * Indicates whether the button is in the selected state.
     * This variable determines the state toggling of the {@code CustomToggleButton}.
     * It changes between {@code true} and {@code false} when the button is clicked,
     * affecting the visual representation and behavior of the button.
     */
    private boolean selected = false;

    /**
     * Represents the background color of the button
     * when it is in the selected state. This color is loaded from the configuration
     * file using the {@code ConfigLoader} utility with the key "color.hover".
     * @see ConfigLoader
     */
    private final Color selectedColor = ConfigLoader.getColor("color.hover");

    /**
     * Used for the unselected state of the {@code CustomToggleButton}.
     * This color is initialized using the {@code ConfigLoader} utility to fetch the color value
     * from the application's configuration file.
     * @see ConfigLoader
     */
    private final Color unselectedColor = ConfigLoader.getColor("color.base"); // Default color for unselected state

    /**
     * Constructs a {@code CustomToggleButton} with specified text.
     * Adds a mouse listener to toggle the selection state of the button when clicked.
     *
     * @param text the text to be displayed on the button
     */
    public CustomToggleButton(String text) {
        super(text); // Call the constructor of the parent class

        // Add mouse listener to toggle the button on click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Toggle the selected state
                setSelected(!selected);
                repaint(); // Repaint to reflect the new state
            }
        });

        // Set initial color based on the state
        setBackground(unselectedColor);
        revalidate();
        repaint();
    }

    /**
     * Overrides the {@code paintComponent} method to customize the drawing of the toggle button.
     * Enables antialiasing for smoother graphics, sets the background color based on the selection state,
     * and draws the button with rounded corners.
     *
     * @param g the {@code Graphics} context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Enable antialiasing for smoother graphics
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color based on the selected state
        if (selected) {
            g2.setColor(selectedColor); // Color when selected
        } else {
            g2.setColor(unselectedColor); // Color when unselected
        }

        // Draw the button background with rounded corners
        g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius); // Corner radius = 20

        // Dispose the graphics context
        g2.dispose();

        // Call the superclass method to paint the text
        super.paintComponent(g);
    }

    /**
     * Returns the selected state of this toggle button.
     *
     * @return {@code true} if the button is selected; {@code false} otherwise
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected state of this toggle button.
     *
     * @param selected the new selected state of the button
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        // Update the background color when toggled
        setBackground(selected ? selectedColor : unselectedColor);

        // Force revalidation to recalculate the preferred size and trigger a layout update
        revalidate();
        repaint();
    }
}
