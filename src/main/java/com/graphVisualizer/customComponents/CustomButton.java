package com.graphVisualizer.customComponents;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    protected Color baseColor = new Color(45, 137, 239); // Default button color
    protected Color hoverColor = new Color(33, 102, 178); // Hover color
    protected Color pressedColor = new Color(25, 82, 148); // Pressed color
    protected Color textColor = Color.WHITE; // Text color
    protected int cornerRadius = 10; // Radius for rounded corners

    public CustomButton(String text) {
        super(text);
        initializeButton();
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing for smooth graphics
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

    @Override
    protected void paintBorder(Graphics g) {
        // Optionally, you can draw a border here if desired
        // For a clean design, we'll omit the border
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate preferred size based on text and padding
        FontMetrics fm = getFontMetrics(getFont());
        int width = fm.stringWidth(getText()) + 40; // Add horizontal padding
        int height = fm.getHeight() + 5;          // Add vertical padding
        return new Dimension(width, height);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        revalidate();
        repaint(); // Force repaint and layout recalculation
    }


    // Getters and setters for customization
    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
        setBackground(baseColor);
    }

    public Color getBaseColor(){
        return baseColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
    }
}
