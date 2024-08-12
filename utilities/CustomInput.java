package utilities;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CustomInput extends JComponent {
    private JTextField textField;
    private int arcWidth = 15;
    private int arcHeight = 15;
    private Color backgroundColor = new Color(220, 220, 220); // Light gray
    private Color borderColor = new Color(30, 30, 30); // Dark gray
    private Color textColor = new Color(30, 30, 30); // Dark gray
    private Dimension size;
    private int maxChars;
    private String placeholder;
    private static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 32);

    public CustomInput(String text, int width, int height, int maxChars, String placeholder) {
        setLayout(new BorderLayout());
        setOpaque(false);

        textField = new JTextField(text);
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setForeground(textColor);
        textField.setFont(DEFAULT_FONT);

        this.maxChars = maxChars;
        setMaxCharLimit();
        add(textField, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setSize(width, height);

        this.placeholder = placeholder;
        setupPlaceholder();
    }

    // Limit character amount in a field
    private void setMaxCharLimit() {
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
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + text.length()) - maxChars - length;
                if (overLimit <= 0)
                    super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    private void setupPlaceholder() {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
        // Initialize with placeholder
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public String getText() {
        String currentText = textField.getText();
        return currentText.equals(placeholder) ? "" : currentText;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setTextColor(Color color) {
        this.textColor = color;
        textField.setForeground(color);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
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

        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return size != null ? size : super.getPreferredSize();
    }

    public void addActionListener(java.awt.event.ActionListener listener) {
        textField.addActionListener(listener);
    }
}