package src.introduction;

import javax.swing.*;
import java.awt.*;

import src.utilities.*;
import src.utilities.gui.*;

public class FlashPanel extends CustPanel {
    public FlashPanel(Runnable onBackClick) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        initUI(onBackClick);
    }

    private void initUI(Runnable onBackClick) {
        var wrapperPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE,
                null, null, 0, 0, 0);
        wrapperPanel.add(createMainPanel(onBackClick));

        add(wrapperPanel, BorderLayout.CENTER);
    }

    private CustPanel createMainPanel(Runnable onBackClick) {
        var mainPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), ColorPalette.DARK_ONE,
                null, null, 0, 0, 0);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(new CustLabel("Welcome to User Configuration!", null,
                null, Component.CENTER_ALIGNMENT));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(new CustLabel("Please connect a flash drive...", null,
                null, Component.CENTER_ALIGNMENT));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(createConnectLabel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(createBackButton(onBackClick));
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private CustLabel createConnectLabel() {
        var connectIcon = new ImageIcon("resources/images/connect-flash.png");
        var connectLabel = new CustLabel(connectIcon, Component.CENTER_ALIGNMENT);
        connectLabel.setLabelSize(180, 180);
        return connectLabel;
    }

    private CustComponent createBackButton(Runnable onBackClick) {
        var backButton = new CustComponent("Back", 100, 60, 20, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
        backButton.addButtonBehavior(onBackClick);
        return backButton;
    }
}