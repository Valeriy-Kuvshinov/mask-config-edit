package utilities;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {
    private static final String FONT_NAME = "Roboto";
    private static final int DEFAULT_FONT_SIZE = 24;
    private static final Color DEFAULT_COLOR = new Color(220, 220, 220); // Light color

    public CustomLabel(String text) {
        super(text, SwingConstants.CENTER);
        initializeLabel();
    }

    public CustomLabel(String text, Color textColor) {
        super(text, SwingConstants.CENTER);
        initializeLabel();
        setForeground(textColor);
    }

    public CustomLabel(String text, int fontSize) {
        super(text, SwingConstants.CENTER);
        initializeLabel();
        setFontSize(fontSize);
    }

    public CustomLabel(String text, Color textColor, int fontSize) {
        super(text, SwingConstants.CENTER);
        initializeLabel();
        setForeground(textColor);
        setFontSize(fontSize);
    }

    private void initializeLabel() {
        setFont(new Font(FONT_NAME, Font.BOLD, DEFAULT_FONT_SIZE));
        setForeground(DEFAULT_COLOR);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setTextWithColor(String text, Color color) {
        setText(text);
        setForeground(color);
    }

    public void setFontSize(int size) {
        setFont(new Font(FONT_NAME, Font.BOLD, size));
    }

    // You can add more custom methods here if needed
}