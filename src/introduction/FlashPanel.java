package src.introduction;

import javax.swing.*;
import java.awt.*;

import src.utilities.*;

public class FlashPanel extends CustPanel {
    public FlashPanel(Runnable onBackClick) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        initiUI(onBackClick);
    }

    private void initiUI(Runnable onBackClick) {
        // Create a main panel to hold all components
        var mainPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), ColorPalette.DARK_ONE, null, null,
                0, 0, 0);

        var wrapperPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        wrapperPanel.add(mainPanel);

        var welcomeLabel = new CustLabel("Welcome to User Configuration!", null, null, Component.CENTER_ALIGNMENT);
        var messageLabel = new CustLabel("Please connect a flash drive...", null, null, Component.CENTER_ALIGNMENT);

        var connectIcon = new ImageIcon("resources/images/connect-flash.png");
        var connectLabel = new CustLabel(connectIcon, Component.CENTER_ALIGNMENT);
        connectLabel.setLabelSize(180, 180);

        var backButton = new CustComponent("Back", 100, 60, 20, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
        backButton.addButtonBehavior(onBackClick);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(connectLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(backButton);
        mainPanel.add(Box.createVerticalGlue());

        add(wrapperPanel, BorderLayout.CENTER);
    }
}