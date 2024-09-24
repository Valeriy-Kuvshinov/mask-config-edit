package src.introduction;

import javax.swing.*;
import java.awt.*;

import src.utilities.*;

public class FlashPanel extends CustomPanel {
    private CustomLabel messageLabel;
    private CustomLabel connectLabel;
    private CustomComponent backButton;
    private static final Color DARK_COLOR = new Color(30, 30, 30);

    public FlashPanel(Runnable onBackClick) {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);
        initializeUI(onBackClick);
    }

    private void initializeUI(Runnable onBackClick) {
        // Create a main panel to hold all components
        var mainPanel = new CustomPanel(new BoxLayout(null, BoxLayout.Y_AXIS), DARK_COLOR, null, null, 0, 0, 0);

        // Create a wrapper panel to center the main panel
        var wrapperPanel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        wrapperPanel.add(mainPanel);

        // Create labels
        var welcomeLabel = new CustomLabel("Welcome to User Configuration!", null, null, Component.CENTER_ALIGNMENT);
        messageLabel = new CustomLabel("Please connect a flash drive...", null, null, Component.CENTER_ALIGNMENT);

        var connectIcon = new ImageIcon("resources/images/connect-flash.png");
        connectLabel = new CustomLabel(connectIcon, Component.CENTER_ALIGNMENT);
        connectLabel.setLabelSize(180, 180);

        // Create back button
        backButton = new CustomComponent("Back", 100, 60, 20, 10, Component.CENTER_ALIGNMENT, null, null);
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