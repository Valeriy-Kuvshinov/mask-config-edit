package src.utilities;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CustSelect extends JComboBox<String> {
    // Used as a customized select for forms in the app
    private int cornerRadius = 15;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private boolean isFocused = false;

    // Default values
    protected static final Font DEFAULT_FONT = new Font("Roboto", Font.BOLD, 32);
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(220, 220, 220); // Light gray
    private static final Color DEFAULT_BORDER_COLOR = new Color(30, 30, 30); // Dark gray
    private static final Color DEFAULT_TEXT_COLOR = new Color(30, 30, 30); // Dark gray
    private static final Color FOCUS_BORDER_COLOR = new Color(0, 120, 215); // Blue-ish color for focus
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustSelect(int width, int height, String[] options, String defaultValue, Float alignmentX) {
        super(options);

        this.backgroundColor = DEFAULT_BACKGROUND_COLOR;
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.textColor = DEFAULT_TEXT_COLOR;

        initializeComponent(width, height, defaultValue, alignmentX);
    }

    private void initializeComponent(int width, int height, String defaultValue, Float alignmentX) {
        setOpaque(false);
        setFont(DEFAULT_FONT);
        setBackground(backgroundColor);
        setForeground(textColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 30));
        setSelectedItem(defaultValue);
        setRenderer(new PaddedListCellRenderer());
        setUI(new RoundedComboBoxUI());

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocused = false;
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        var g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create shape for background and border
        var roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1,
                cornerRadius, cornerRadius);

        // Paint background
        g2.setColor(backgroundColor);
        g2.fill(roundedRectangle);

        // Paint border
        g2.setColor(isFocused ? FOCUS_BORDER_COLOR : borderColor);
        g2.setStroke(new BasicStroke(2));
        g2.draw(roundedRectangle);

        // Draw the arrow
        var arrowWidth = 14;
        var arrowHeight = 14;
        var x = getWidth() - arrowWidth - 15;
        var y = (getHeight() - arrowHeight) / 2;
        g2.setColor(textColor);
        g2.fillPolygon(
                new int[] { x, x + arrowWidth / 2, x + arrowWidth },
                new int[] { y, y + arrowHeight, y },
                3);

        // Draw the selected item text
        var selectedItem = getSelectedItem().toString();
        var fm = g2.getFontMetrics();
        var textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2.setColor(textColor);
        g2.drawString(selectedItem, 10, textY);

        g2.dispose();
    }

    private class PaddedListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            var renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            renderer.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            renderer.setFont(DEFAULT_FONT);
            return renderer;
        }
    }

    private class RoundedComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            return new JButton() {
                @Override
                public int getWidth() {
                    return 0;
                }
            };
        }

        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Do nothing to prevent the default value painting
        }

        @Override
        protected ComboPopup createPopup() {
            return new RoundedComboPopup(comboBox);
        }
    }

    private class RoundedComboPopup extends BasicComboPopup {
        public RoundedComboPopup(JComboBox<Object> comboBox) {
            super(comboBox);
        }

        @Override
        protected JScrollPane createScroller() {
            var scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroller.setOpaque(false);
            scroller.getViewport().setOpaque(false);
            scroller.setBorder(null);
            return scroller;
        }

        @Override
        protected void configurePopup() {
            super.configurePopup();
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setBackground(backgroundColor);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            var g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
            g2.setColor(borderColor);
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}