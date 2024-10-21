package src.utilities.gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import src.utilities.*;

public class CustInput extends JComponent {
    // Used as a customized input for forms in the app
    private JTextField textField = new JTextField("");
    private int cornerRadius = 15;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private String placeholder;
    private boolean isFocused = false;
    private static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 32);
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustInput(int width, int height, int maxChars, String placeholder, Float alignmentX) {
        this.placeholder = placeholder != null ? placeholder : "Insert something";

        this.backgroundColor = ColorPalette.LIGHT_ONE;
        this.borderColor = ColorPalette.DARK_ONE;
        this.textColor = ColorPalette.DARK_ONE;

        initInput(width, height, maxChars, alignmentX);
    }

    public interface TextChangeListener {
        void textChanged(String newText);
    }

    private void initInput(int width, int height, int maxChars, Float alignmentX) {
        setLayout(new BorderLayout());
        setOpaque(false);

        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        textField.setForeground(textColor);
        textField.setFont(DEFAULT_FONT);

        setMaxCharLimit(maxChars);
        add(textField, BorderLayout.CENTER);

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

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

    public void addTextChangeListener(TextChangeListener listener) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                listener.textChanged(getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2d.setColor(backgroundColor);
        g2d.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius));

        // Paint border
        g2d.setColor(isFocused ? ColorPalette.BLUE_ONE : borderColor);
        g2d.setStroke(new BasicStroke(2)); // Set border thickness
        g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius));

        g2d.dispose();
        super.paintComponent(g);
    }

    public void addActionListener(java.awt.event.ActionListener listener) {
        textField.addActionListener(listener);
    }
}