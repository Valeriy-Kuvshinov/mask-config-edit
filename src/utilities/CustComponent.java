package src.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CustComponent extends JComponent {
    // Used for creating customized bland or button components
    private String text;
    private int cornerRadius = 15;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private Color originalBackgroundColor;
    private Color originalTextColor;
    private java.awt.event.MouseAdapter buttonBehavior;

    // Default values
    protected static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 24);
    private static final Color DEFAULT_BORDER_COLOR = new Color(30, 30, 30); // Dark gray
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustComponent(String text, Integer width, Integer height, int paddingX, int paddingY,
            Float alignmentX, Color backgroundColor, Color textColor) {
        this.text = text;

        this.backgroundColor = backgroundColor;
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.textColor = textColor;

        // Ensure button colors are not lost during hovering
        this.originalBackgroundColor = backgroundColor;
        this.originalTextColor = textColor;

        initializeComponent(width, height, paddingX, paddingY, alignmentX);
    }

    private void initializeComponent(Integer width, Integer height, int paddingX, int paddingY, Float alignmentX) {
        setOpaque(false);
        setForeground(this.textColor);
        setFont(DEFAULT_FONT);
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Calculate the preferred size based on the text content or use specific width
        var fm = getFontMetrics(getFont());
        var textWidth = fm.stringWidth(text);
        var textHeight = fm.getHeight();

        // assign values based on padding and text, if width / height are not specified
        var finalWidth = (width != null) ? width : textWidth + paddingX;
        var finalHeight = (height != null) ? height : textHeight + paddingY;

        // Set the preferred size,
        var preferredSize = new Dimension(finalWidth, finalHeight);
        setPreferredSize(preferredSize);
        setMinimumSize(preferredSize);
        setMaximumSize(preferredSize);

        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    public String getText() {
        return text;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        setBackground(color);
        repaint();
    }

    public void setColor(Color color) {
        this.textColor = color;
        setForeground(color);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2d.setColor(backgroundColor);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        // Paint border
        g2d.setColor(borderColor);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        // Paint text
        if (!text.trim().isEmpty()) {
            g2d.setColor(textColor);
            g2d.setFont(getFont());
            var fm = g2d.getFontMetrics();
            var textWidth = fm.stringWidth(text);
            var textHeight = fm.getHeight();
            var x = (getWidth() - textWidth) / 2;
            var y = (getHeight() - textHeight) / 2 + fm.getAscent();
            g2d.drawString(text, x, y);
        }
        g2d.dispose();
    }

    public void addButtonBehavior(Runnable clickAction) {
        removeButtonBehavior(); // Remove existing behavior if any
        buttonBehavior = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickAction.run();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackgroundColor(originalTextColor);
                setColor(originalBackgroundColor);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackgroundColor(originalBackgroundColor);
                setColor(originalTextColor);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        };
        addMouseListener(buttonBehavior);
    }

    public void removeButtonBehavior() {
        if (buttonBehavior != null) {
            removeMouseListener(buttonBehavior);
            buttonBehavior = null;
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}