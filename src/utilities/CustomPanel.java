package src.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CustomPanel extends JPanel {
    private int cornerRadius = 15;
    private Color backgroundColor;
    private Color borderColor;

    // Default values
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(220, 220, 220); // Light gray
    private static final Color DEFAULT_BORDER_COLOR = new Color(30, 30, 30); // Dark gray
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustomPanel(LayoutManager layout) {
        this(layout, DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR, DEFAULT_ALIGNMENT);
    }

    public CustomPanel(LayoutManager layout, Color backgroundColor, Color borderColor, Float alignmentX) {
        super(layout != null ? layout : new FlowLayout());
        setOpaque(false);
        this.backgroundColor = backgroundColor != null ? backgroundColor : DEFAULT_BACKGROUND_COLOR;
        this.borderColor = borderColor != null ? borderColor : DEFAULT_BORDER_COLOR;
        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2d.setColor(backgroundColor);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        // Paint border
        g2d.setColor(borderColor);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        g2d.dispose();
    }
}