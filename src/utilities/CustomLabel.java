package src.utilities;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {
    private static final String FONT_NAME = "Roboto";
    private static final int DEFAULT_FONT_SIZE = 24;
    private static final Color DEFAULT_COLOR = new Color(220, 220, 220); // Light color
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    // For text
    public CustomLabel(String text, Color textColor, Integer fontSize, Float alignmentX) {
        super(text, SwingConstants.LEFT);
        initializeLabel(textColor, fontSize, alignmentX);
        repaint();
    }

    // For images
    public CustomLabel(ImageIcon icon, Float alignmentX) {
        super(icon);
        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);
    }

    private void initializeLabel(Color textColor, Integer fontSize, Float alignmentX) {
        setFont(new Font(FONT_NAME, Font.BOLD, fontSize != null ? fontSize : DEFAULT_FONT_SIZE));
        setForeground(textColor != null ? textColor : DEFAULT_COLOR);
        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);
    }

    public void setColor(Color color) {
        setForeground(color);
    }

    public void setFontSize(int size) {
        setFont(new Font(FONT_NAME, Font.BOLD, size));
    }

    // New method to set label size while preserving aspect ratio
    public void setLabelSize(int maxWidth, int maxHeight) {
        if (getIcon() instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) getIcon();
            int imgWidth = icon.getIconWidth();
            int imgHeight = icon.getIconHeight();
            double scale = Math.min((double) maxWidth / imgWidth, (double) maxHeight / imgHeight);
            int width = (int) (imgWidth * scale);
            int height = (int) (imgHeight * scale);
            setPreferredSize(new Dimension(width, height));
            setMaximumSize(new Dimension(width, height));
            setMinimumSize(new Dimension(width, height));
        }
    }

    // Override paintComponent to scale the image
    @Override
    protected void paintComponent(Graphics g) {
        if (getIcon() instanceof ImageIcon) {
            Image img = ((ImageIcon) getIcon()).getImage();
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        } else {
            super.paintComponent(g);
        }
    }
}