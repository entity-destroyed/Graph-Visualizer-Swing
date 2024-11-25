package com.graphVisualizer.customComponents;

import com.graphVisualizer.utils.ConfigLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomToggleButton extends CustomButton {

    private boolean selected = false; // Initial state: not selected
    private final Color selectedColor = ConfigLoader.getColor("color.hover"); // Color when selected
    private final Color unselectedColor = ConfigLoader.getColor("color.base"); // Default color for unselected state

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

    // Override the paintComponent method to handle different states visually
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

    // Getter and Setter for the selected state
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        // Update the background color when toggled
        setBackground(selected ? selectedColor : unselectedColor);

        // Force revalidation to recalculate the preferred size and trigger a layout update
        revalidate();
        repaint();
    }
}
