package src.utilities;

import java.awt.*;
import javax.swing.*;
import src.utilities.gui.*;

public class GridBagHelper {
    private GridBagConstraints gbc;
    private CustPanel panel;

    public GridBagHelper(CustPanel panel, GridBagConstraints gbc) {
        this.panel = panel;
        this.gbc = gbc;
    }

    public void addComponent(Component component) {
        gbc.gridy++;
        panel.add(component, gbc);
    }

    public void addVerticalSpace(int height) {
        addComponent(Box.createRigidArea(new Dimension(0, height)));
    }

    public void addLabel(String text, Color textColor, Integer fontSize, Float alignment) {
        addComponent(new CustLabel(text, textColor, fontSize, alignment));
    }

    public void addCustComponent(String text, Integer width, Integer height, int paddingX, int paddingY,
            Color backgroundColor, Color textColor) {
        addComponent(new CustComponent(text, width, height, paddingX, paddingY,
                Component.CENTER_ALIGNMENT, backgroundColor, textColor));
    }
}