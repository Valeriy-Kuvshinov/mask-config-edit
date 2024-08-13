package src.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CustomComponent extends JComponent {
    private String text;
    private int cornerRadius = 15;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private Dimension size;
    private float alignmentX;

    // Default values
    protected static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 24);
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(220, 220, 220); // Light gray
    private static final Color DEFAULT_BORDER_COLOR = new Color(30, 30, 30); // Dark gray
    private static final Color DEFAULT_TEXT_COLOR = new Color(30, 30, 30); // Dark gray
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustomComponent(Integer width, Integer height, Color backgroundColor) {
        this("", backgroundColor, DEFAULT_TEXT_COLOR, width, height, DEFAULT_ALIGNMENT);
    }

    public CustomComponent(String text, Color backgroundColor, Color textColor,
            Integer width, Integer height, Float alignmentX) {
        this.text = text;
        this.backgroundColor = backgroundColor != null ? backgroundColor : DEFAULT_BACKGROUND_COLOR;
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.textColor = textColor != null ? textColor : DEFAULT_TEXT_COLOR;
        this.size = new Dimension(
                width != null ? width : DEFAULT_WIDTH,
                height != null ? height : DEFAULT_HEIGHT);
        this.alignmentX = alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT;

        setOpaque(false);
        setForeground(this.textColor);
        setFont(DEFAULT_FONT);
        initializeComponent();
    }

    private void initializeComponent() {
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setAlignmentX(alignmentX);
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

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        Dimension newSize = new Dimension(width, height);
        setPreferredSize(newSize);
        setMinimumSize(newSize);
        setMaximumSize(newSize);
        this.size = newSize;
        revalidate();
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

        // Paint text
        if (!text.trim().isEmpty()) {
            g2d.setColor(textColor);
            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            g2d.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - fm.getDescent());
        }
        g2d.dispose();
    }

    public void addButtonBehavior(Runnable clickAction) {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickAction.run();
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackgroundColor(Color.LIGHT_GRAY);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackgroundColor(Color.WHITE);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}