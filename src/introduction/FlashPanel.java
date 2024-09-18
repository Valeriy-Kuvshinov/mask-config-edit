package src.introduction;

import javax.swing.*;
import java.awt.*;

import src.utilities.*;

public class FlashPanel extends JPanel {
    private CustomLabel messageLabel;
    private CustomLabel connectLabel;
    private static final Color DARK_COLOR = new Color(30, 30, 30);

    public FlashPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(DARK_COLOR);

        // Create a main panel to hold all components
        var mainPanel = new CustomPanel(null, DARK_COLOR, null, null);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // Create a wrapper panel to center the main panel
        var wrapperPanel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null);
        wrapperPanel.add(mainPanel);

        // Create labels
        var welcomeLabel = new CustomLabel("Welcome to User Configuration!", null, null, Component.CENTER_ALIGNMENT);
        messageLabel = new CustomLabel("Please connect a flash drive...", null, null, Component.CENTER_ALIGNMENT);

        ImageIcon connectIcon = new ImageIcon("resources/images/connect-flash.png");
        connectLabel = new CustomLabel(connectIcon, Component.CENTER_ALIGNMENT);
        connectLabel.setLabelSize(180, 180);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(connectLabel);
        mainPanel.add(Box.createVerticalGlue());

        add(wrapperPanel, BorderLayout.CENTER);
    }
}