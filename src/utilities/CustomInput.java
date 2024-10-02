package src.utilities;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CustomInput extends JComponent {
    // Used as a customized input for forms in the app
    private JTextField textField = new JTextField("");
    private int cornerRadius = 15;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private String placeholder;
    private boolean isFocused = false;

    // Default values
    protected static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 32);
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(220, 220, 220); // Light gray
    private static final Color DEFAULT_BORDER_COLOR = new Color(30, 30, 30); // Dark gray
    private static final Color DEFAULT_TEXT_COLOR = new Color(30, 30, 30); // Dark gray
    private static final Color FOCUS_BORDER_COLOR = new Color(0, 120, 215); // Blue-ish color for focus
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustomInput(int width, int height, int maxChars, String placeholder, Float alignmentX) {
        this.placeholder = placeholder != null ? placeholder : "Insert something";

        this.backgroundColor = DEFAULT_BACKGROUND_COLOR;
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.textColor = DEFAULT_TEXT_COLOR;

        initializeComponent(width, height, maxChars, alignmentX);
    }

    private void initializeComponent(int width, int height, int maxChars, Float alignmentX) {
        setLayout(new BorderLayout());
        setOpaque(false);

        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        textField.setForeground(textColor);
        textField.setFont(DEFAULT_FONT);

        setMaxCharLimit(maxChars);
        add(textField, BorderLayout.CENTER);

        var preferredSize = new Dimension(width, height);
        setPreferredSize(preferredSize);
        setMinimumSize(preferredSize);
        setMaximumSize(preferredSize);

        setupPlaceholder();
        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocused = true;
                repaint();
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocused = false;
                repaint();
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    // Limit character amount in a field
    private void setMaxCharLimit(int maxChars) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= maxChars)
                    super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                var currentLength = fb.getDocument().getLength();
                var overLimit = (currentLength + text.length()) - maxChars - length;
                if (overLimit <= 0)
                    super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    // Initialize with placeholder
    private void setupPlaceholder() {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
    }

    public String getText() {
        String currentText = textField.getText();
        return currentText.equals(placeholder) ? "" : currentText;
    }

    public void setText(String text) {
        textField.setText(text);
        textField.setForeground(textColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2d.setColor(backgroundColor);
        g2d.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius));

        // Paint border
        g2d.setColor(isFocused ? FOCUS_BORDER_COLOR : borderColor);
        g2d.setStroke(new BasicStroke(2)); // Set border thickness
        g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius));

        g2d.dispose();
        super.paintComponent(g);
    }

    public void addActionListener(java.awt.event.ActionListener listener) {
        textField.addActionListener(listener);
    }
}