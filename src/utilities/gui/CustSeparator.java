package src.utilities.gui;

import javax.swing.*;
import java.awt.*;

public class CustSeparator extends JPanel {
    private Color lineColor;
    private int thickness;

    public CustSeparator(Color lineColor, int thickness) {
        this.lineColor = lineColor;
        this.thickness = thickness;
        setOpaque(false);
        setPreferredSize(new Dimension(1, thickness));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g.create();
        g2d.setColor(lineColor);
        g2d.fillRect(0, 0, getWidth(), thickness);
        g2d.dispose();
    }
}