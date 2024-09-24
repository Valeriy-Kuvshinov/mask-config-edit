package src.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CustomComponent extends JComponent {
    // Used for creating customized bland or button components
    private String text;
    private int cornerRadius = 15;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private float alignmentX;
    private Integer width;
    private Integer height;
    private int paddingX;
    private int paddingY;

    // Default values
    protected static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 24);
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(220, 220, 220); // Light gray
    private static final Color DEFAULT_BORDER_COLOR = new Color(30, 30, 30); // Dark gray
    private static final Color DEFAULT_TEXT_COLOR = new Color(30, 30, 30); // Dark gray
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustomComponent(String text, Integer width, Integer height, int paddingX, int paddingY,
            Float alignmentX, Color backgroundColor, Color textColor) {
        this.text = text;
        this.width = width;
        this.height = height;
        this.paddingX = paddingX;
        this.paddingY = paddingY;

        this.backgroundColor = backgroundColor != null ? backgroundColor : DEFAULT_BACKGROUND_COLOR;
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.textColor = textColor != null ? textColor : DEFAULT_TEXT_COLOR;
        this.alignmentX = alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT;

        initializeComponent();
    }

    private void initializeComponent() {
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
        var finalWidth = (this.width != null) ? this.width : textWidth + this.paddingX;
        var finalHeight = (this.height != null) ? this.height : textHeight + this.paddingY;

        // Set the preferred size,
        var preferredSize = new Dimension(finalWidth, finalHeight);
        setPreferredSize(preferredSize);
        setMinimumSize(preferredSize);
        setMaximumSize(preferredSize);

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

    public void setColor(Color color) {
        this.textColor = color;
        setForeground(color);
        repaint();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.width = width;
        this.height = height;

        var newSize = new Dimension(width, height);
        setPreferredSize(newSize);
        setMinimumSize(newSize);
        setMaximumSize(newSize);
        revalidate();
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
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickAction.run();
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackgroundColor(Color.DARK_GRAY);
                setColor(DEFAULT_BACKGROUND_COLOR);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
                setColor(DEFAULT_TEXT_COLOR);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}