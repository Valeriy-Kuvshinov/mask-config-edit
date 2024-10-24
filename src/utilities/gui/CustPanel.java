package src.utilities.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

import src.utilities.*;

public class CustPanel extends JPanel {
    // Used as a customized panel for holding various GUI stuff
    private int borderRadius;
    private Color backgroundColor;
    private Color borderColor;
    private int paddingX;
    private int paddingY;
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustPanel(LayoutManager layout, Color backgroundColor, Color borderColor, Float alignmentX,
            int borderRadius, int paddingX, int paddingY) {
        super(layout instanceof BoxLayout ? null : layout);
        setOpaque(false);
        this.backgroundColor = backgroundColor != null ? backgroundColor : ColorPalette.LIGHT_ONE;
        this.borderColor = borderColor != null ? borderColor : ColorPalette.DARK_ONE;
        this.borderRadius = borderRadius;
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);

        if (layout instanceof BoxLayout) {
            setLayout(new BoxLayout(this, ((BoxLayout) layout).getAxis()));
        }

        setBorder(BorderFactory.createEmptyBorder(this.paddingY, this.paddingX, this.paddingY, this.paddingX));
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setPadding(Integer paddingX, Integer paddingY) {
        this.paddingX = paddingX != null ? paddingX : this.paddingX;
        this.paddingY = paddingY != null ? paddingY : this.paddingY;
        setBorder(BorderFactory.createEmptyBorder(this.paddingY, this.paddingX, this.paddingY, this.paddingX));
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
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius));

        // Paint border
        g2d.setColor(borderColor);
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius));

        g2d.dispose();
    }
}