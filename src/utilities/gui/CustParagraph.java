package src.utilities.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

import src.utilities.*;

public class CustParagraph extends JTextPane {
    // Customizable paragraph
    private static final String FONT_NAME = "Roboto";
    private static final int DEFAULT_FONT_SIZE = 16;
    private static final float DEFAULT_ALIGNMENT = Component.LEFT_ALIGNMENT;

    public CustParagraph(String boldText, String regularText, Color textColor, Integer fontSize, Float alignmentX) {
        initParagraph(boldText, regularText, textColor, fontSize, alignmentX);
    }

    private void initParagraph(String boldText, String regularText, Color textColor, Integer fontSize,
            Float alignmentX) {
        setEditable(false);
        setOpaque(false);
        setBorder(null);
        setAlignmentX(alignmentX != null ? alignmentX : DEFAULT_ALIGNMENT);

        var doc = getStyledDocument();
        var font = new Font(FONT_NAME, Font.PLAIN, fontSize != null ? fontSize : DEFAULT_FONT_SIZE);
        setFont(font);
        setForeground(textColor != null ? textColor : ColorPalette.LIGHT_ONE);

        var defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        var regularStyle = doc.addStyle("regular", defaultStyle);
        StyleConstants.setFontFamily(regularStyle, FONT_NAME);
        StyleConstants.setFontSize(regularStyle, fontSize != null ? fontSize : DEFAULT_FONT_SIZE);
        StyleConstants.setForeground(regularStyle, textColor != null ? textColor : ColorPalette.LIGHT_ONE);

        var boldStyle = doc.addStyle("bold", regularStyle);
        StyleConstants.setBold(boldStyle, true);

        try {
            doc.insertString(doc.getLength(), boldText, boldStyle);
            doc.insertString(doc.getLength(), regularText, regularStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        var viewportWidth = getViewportWidth();
        var maxWidth = viewportWidth - 30;
        maxWidth = Math.max(maxWidth, 100);

        setSize(new Dimension(maxWidth, 1));
        var d = super.getPreferredSize();
        d.width = Math.min(d.width, maxWidth);

        return d;
    }

    private int getViewportWidth() {
        var parent = getParent();
        while (parent != null && !(parent instanceof JViewport)) {
            parent = parent.getParent();
        }
        return (parent != null) ? parent.getWidth() : 600;
    }
}