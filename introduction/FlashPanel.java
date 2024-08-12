package introduction;

import javax.swing.*;
import java.awt.*;

import utilities.CustomLabel;
import utilities.CustomComponent;
import utilities.CustomInput;
import utilities.CustomPanel;

public class FlashPanel extends JPanel {
    private WelcomeProcess welcomeProcess;
    private CustomLabel messageLabel;
    private JPanel optionsPanel;
    private CustomInput createMaskField;
    private CustomInput editMaskField;
    private static final Color DARK_COLOR = new Color(30, 30, 30);

    public FlashPanel(WelcomeProcess process) {
        this.welcomeProcess = process;
        setLayout(new BorderLayout());
        setBackground(DARK_COLOR);

        // Create a main panel to hold all components
        var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(DARK_COLOR);
        mainPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // Create a wrapper panel to center the main panel
        var wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(DARK_COLOR);
        wrapperPanel.add(mainPanel);

        // Create labels
        var welcomeLabel = new CustomLabel("Welcome to User Configuration!");
        messageLabel = new CustomLabel("Please connect a flash drive...");

        optionsPanel = createOptionsPanel();
        optionsPanel.setVisible(false);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(optionsPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(wrapperPanel, BorderLayout.CENTER);

        startFlashDriveCheck();
    }

    private JPanel createOptionsPanel() {
        var optionsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        optionsPanel.setBackground(DARK_COLOR);

        // Create Mask option
        var createPanel = new CustomPanel(null);
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.Y_AXIS));
        createPanel.setBackgroundColor(Color.LIGHT_GRAY);
        createPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        var createLabel = new CustomLabel("Create Mask");
        createLabel.setFontSize(20);
        createLabel.setForeground(DARK_COLOR);
        createLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        createMaskField = new CustomInput("", 250, 40, 12, "Insert name");
        createMaskField.setAlignmentX(Component.CENTER_ALIGNMENT);

        var createButton = new CustomComponent("Create");
        createButton.setSize(100, 50);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createPanel.add(createLabel);
        createPanel.add(Box.createVerticalStrut(10));
        createPanel.add(createMaskField);
        createPanel.add(Box.createVerticalStrut(20));
        createPanel.add(createButton);

        // Edit Mask option
        var editPanel = new CustomPanel(null);
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.setBackgroundColor(Color.LIGHT_GRAY);
        editPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        var editLabel = new CustomLabel("Edit Mask");
        editLabel.setFontSize(20);
        editLabel.setForeground(DARK_COLOR);
        editLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        editMaskField = new CustomInput("", 250, 40, 12, "Insert name");
        editMaskField.setAlignmentX(Component.CENTER_ALIGNMENT);

        var editButton = new CustomComponent("Edit");
        editButton.setSize(100, 50);
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        editPanel.add(editLabel);
        editPanel.add(Box.createVerticalStrut(10));
        editPanel.add(editMaskField);
        editPanel.add(Box.createVerticalStrut(20));
        editPanel.add(editButton);

        optionsPanel.add(createPanel);
        optionsPanel.add(editPanel);

        createButton.addButtonBehavior(() -> createMask());
        editButton.addButtonBehavior(() -> editMask());

        return optionsPanel;
    }

    private void startFlashDriveCheck() {
        // This is a placeholder for the actual flash drive detection logic
        // You would implement the actual detection mechanism here
        var timer = new Timer(2000, e -> {
            // Simulating flash drive detection after 2 seconds
            flashDriveConnected();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void flashDriveConnected() {
        messageLabel.setText("Flash drive connected. Choose an option:");
        optionsPanel.setVisible(true);
        revalidate();
        repaint();
    }

    private void createMask() {
        String maskName = createMaskField.getText();
        System.out.println("Creating mask: " + maskName);
        // TODO: Implement actual mask creation logic
    }

    private void editMask() {
        String maskName = editMaskField.getText();
        System.out.println("Editing mask: " + maskName);
        // TODO: Implement actual mask editing logic
    }
}