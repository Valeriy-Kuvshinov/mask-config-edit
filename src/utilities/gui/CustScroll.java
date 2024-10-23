package src.utilities.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustScroll extends JScrollPane {
    private boolean scrollEnabled = true;
    private MouseWheelListener wheelListener;

    public CustScroll(Component view) {
        super(view);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(null);
        getViewport().setBackground(null);

        wheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (scrollEnabled) {
                    var verticalScrollBar = getVerticalScrollBar();
                    var notches = e.getWheelRotation();
                    var scrollAmount = notches * verticalScrollBar.getUnitIncrement() * 8;
                    var newValue = verticalScrollBar.getValue() + scrollAmount;

                    // Ensure the new value is within the scrollbar's min and max
                    newValue = Math.max(verticalScrollBar.getMinimum(),
                            Math.min(newValue, verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount()));

                    verticalScrollBar.setValue(newValue);
                } else {
                    e.consume();
                }
            }
        };
        // Add the wheel listener to the viewport and the scroll pane
        getViewport().addMouseWheelListener(wheelListener);
        addMouseWheelListener(wheelListener);
        scrollToTop();
    }

    public void updateScrollBars() {
        revalidate();
        repaint();
        SwingUtilities.invokeLater(() -> {
            var view = getViewport().getView();
            if (view != null) {
                var preferredHeight = view.getPreferredSize().height;
                var viewportHeight = getViewport().getHeight();
                var needsVerticalScrollBar = preferredHeight > viewportHeight;
                setVerticalScrollBarPolicy(needsVerticalScrollBar ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                        : JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                setScrollEnabled(needsVerticalScrollBar);
                if (needsVerticalScrollBar) {
                    getVerticalScrollBar().setMaximum(preferredHeight);
                }
            }
        });
    }

    public void setScrollEnabled(boolean enabled) {
        this.scrollEnabled = enabled;
        getVerticalScrollBar().setEnabled(enabled);
        if (enabled) {
            getViewport().addMouseWheelListener(wheelListener);
            addMouseWheelListener(wheelListener);
        } else {
            getViewport().removeMouseWheelListener(wheelListener);
            removeMouseWheelListener(wheelListener);
        }
    }

    public void scrollToTop() {
        SwingUtilities.invokeLater(() -> {
            getVerticalScrollBar().setValue(0);
            updateScrollBars();
        });
    }
}