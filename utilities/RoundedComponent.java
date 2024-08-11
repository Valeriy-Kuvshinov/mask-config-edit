package utilities;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class RoundedComponent extends JComponent {
    private String text;
    private int arcWidth = 15;
    private int arcHeight = 15;
    private Color backgroundColor = Color.WHITE;
    private Color borderColor = Color.BLACK;

    public RoundedComponent(String text) {
        this.text = text;
        setOpaque(false);
    }

    public void setText(String text) {
        this.text = text;
        repaint();
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
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2d.setColor(backgroundColor);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));

        // Paint border
        g2d.setColor(borderColor);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));

        // Paint text
        g2d.setColor(getForeground());
        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - fm.getDescent());

        g2d.dispose();
    }
}