package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CustomComponent extends JComponent {
    private String text;
    private int arcWidth = 15;
    private int arcHeight = 15;
    private Color backgroundColor = new Color(220, 220, 220); // Light gray
    private Color borderColor = new Color(30, 30, 30); // Dark gray
    private Color textColor = new Color(30, 30, 30); // Dark gray
    private Dimension size;
    protected static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 24);

    public CustomComponent(String text) {
        this.text = text;
        setOpaque(false);
        setForeground(textColor);
        setFont(DEFAULT_FONT);
        initializeComponent();
    }

    private void initializeComponent() {
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
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

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setTextColor(Color color) {
        this.textColor = color;
        setForeground(color);
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
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    @Override
    public Dimension getPreferredSize() {
        if (size != null)
            return size;
        FontMetrics fm = getFontMetrics(getFont());
        int width = fm.stringWidth(text) + 20;
        int height = fm.getHeight() + 10;
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2d.setColor(backgroundColor);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));

        // Paint border
        g2d.setColor(borderColor);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));

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