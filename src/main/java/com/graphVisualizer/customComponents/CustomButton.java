package com.graphVisualizer.customComponents;
import com.graphVisualizer.utils.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@code CustomButton} is a specialized {@code JButton} that provides a customized UI experience
 * with rounded corners, color transitions on hover and press events, and modern font styling.
 * @see JButton
 */
public class CustomButton extends JButton {
    /**
     * The default color for the button.
     * This color is loaded from the configuration file using the key "color.base".
     * @see ConfigLoader
     */
    protected Color baseColor = ConfigLoader.getColor("color.base");

    /**
     * The color used for the button when the mouse hovers over it.
     *
     * <p>This color is loaded from the application's configuration
     * using the key "color.hover" via the `ConfigLoader` class.
     * @see ConfigLoader
     */
    protected Color hoverColor = ConfigLoader.getColor("color.hover");

    /**
     * The color used when the button is pressed.
     * This color value is loaded via the {@link ConfigLoader#getColor(String)} method
     * using the key "color.pressed" from a configuration file.
     * @see ConfigLoader
     */
    protected Color pressedColor = ConfigLoader.getColor("color.pressed");

    /**
     * The color used for the text displayed on the button. This value is loaded from the
     * application's configuration file using the key "color.text".
     * @see ConfigLoader
     */
    protected Color textColor = ConfigLoader.getColor("color.text");

    /**
     * The radius for rounded corners of the {@code CustomButton}.
     * This value determines the curvature of the button's edges,
     * giving it a rounded appearance.
     * @see ConfigLoader
     */
    protected final int cornerRadius = 10;

    /**
     * Initializes the button with the given {@code text}.
     * @param text the text of the button
     */
    public CustomButton(String text) {
        super(text);
        initializeButton();
    }

    /**
     * Initialize the button with custom properties and behaviors such as removing default focus
     * ring, border, and background, setting text font and color, applying hover and press effect.
     *
     * <ul>
     * <li>Removes default focus ring, border, and content area fill for a modern, custom appearance.</li>
     * <li>Sets text color and font to "Segoe UI" with bold style and size 13.</li>
     * <li>Changes the cursor to a hand cursor when hovering over the button.</li>
     * <li>Adds mouse listeners to handle hover and press effects, changing background colors accordingly.</li>
     * </ul>
     */
    private void initializeButton() {
        setFocusPainted(false); // Remove focus ring
        setBorderPainted(false); // Remove default border
        setContentAreaFilled(false); // Remove default background
        setForeground(textColor); // Set text color
        setFont(new Font("Segoe UI", Font.BOLD, 13)); // Modern font
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        setOpaque(false); // Make background transparent

        // Add a hover and press effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setBackground(baseColor);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                setBackground(pressedColor);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                // If the mouse is still over the button, set to hover color
                if (contains(evt.getPoint())) {
                    setBackground(hoverColor);
                } else {
                    setBackground(baseColor);
                }
                repaint();
            }
        });

        setBackground(baseColor); // Set initial background
    }

    /**
     * Overrides the {@code paintComponent} method to customize the drawing of the button.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Enable antialiasing for smooth graphics
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the button background with rounded corners
        g2.setColor(getBackground());
        g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);

        // Dispose the graphics context to free resources
        g2.dispose();

        // Let the superclass handle the text rendering
        super.paintComponent(g);
    }

    /**
     * Calculates the preferred size of the button based on its text and padding.
     *
     * @return the preferred size as a Dimension object
     */
    @Override
    public Dimension getPreferredSize() {
        // Calculate preferred size based on text and padding
        FontMetrics fm = getFontMetrics(getFont());
        int hPadding = 40;
        int width = fm.stringWidth(getText()) + hPadding; // Add horizontal padding
        int vPadding = 5;
        int height = fm.getHeight() + vPadding;          // Add vertical padding
        return new Dimension(width, height);
    }

    /**
     * Sets the text of the button, ensuring the component is revalidated and repainted.
     *
     * @param text the new text to display on the button
     */
    @Override
    public void setText(String text) {
        super.setText(text);
        revalidate();
        repaint(); // Force repaint and layout recalculation
    }
}
