package src.utilities;

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

        wheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (scrollEnabled) {
                    JScrollBar verticalScrollBar = getVerticalScrollBar();
                    int notches = e.getWheelRotation();
                    int scrollAmount = notches * verticalScrollBar.getUnitIncrement() * 8;
                    int newValue = verticalScrollBar.getValue() + scrollAmount;

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
    }

    public void updateScrollBars() {
        revalidate();
        repaint();
        SwingUtilities.invokeLater(() -> {
            Component view = getViewport().getView();
            if (view != null) {
                int preferredHeight = view.getPreferredSize().height;
                int viewportHeight = getViewport().getHeight();
                boolean needsVerticalScrollBar = preferredHeight > viewportHeight;
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
        });
    }
}