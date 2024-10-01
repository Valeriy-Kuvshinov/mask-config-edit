package src.introduction;

import java.util.function.*;
import javax.swing.*;
import java.awt.*;

import src.utilities.*;

public class MaskChoicePanel extends CustomPanel {
    private Consumer<String> createMaskHandler;
    private Consumer<String> editMaskHandler;
    private CustomPanel optionsPanel;
    private CustomLabel messageLabel;
    private CustomInput createMaskField;
    private CustomInput editMaskField;
    private CustomComponent backButton;
    private static final Color DARK_COLOR = new Color(30, 30, 30);

    public MaskChoicePanel(Consumer<String> createMaskHandler, Consumer<String> editMaskHandler, Runnable onBackClick) {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);
        this.createMaskHandler = createMaskHandler;
        this.editMaskHandler = editMaskHandler;
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
        messageLabel = new CustomLabel("Flash drive connected. Choose an option:", null, null,
                Component.CENTER_ALIGNMENT);

        // Create back button
        backButton = new CustomComponent("Back", 100, 60, 20, 10, Component.CENTER_ALIGNMENT, null, null);
        backButton.addButtonBehavior(onBackClick);

        optionsPanel = createOptionsPanel();
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(optionsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(backButton);
        mainPanel.add(Box.createVerticalGlue());

        add(wrapperPanel, BorderLayout.CENTER);
    }

    private CustomPanel createOptionsPanel() {
        var optionsPanel = new CustomPanel(new GridLayout(1, 2, 20, 0), DARK_COLOR, null, Component.CENTER_ALIGNMENT,
                0, 0, 0);

        // Create Mask option
        var createPanel = new CustomPanel(new BoxLayout(null, BoxLayout.Y_AXIS), Color.LIGHT_GRAY, null, null, 15, 15,
                15);

        var createLabelOne = new CustomLabel("Create Mask", DARK_COLOR, 20, Component.CENTER_ALIGNMENT);
        var createLabelTwo = new CustomLabel("Create new configuration file with default settings", DARK_COLOR, 16,
                Component.CENTER_ALIGNMENT);

        var createIcon = new ImageIcon("resources/images/new-flash.png");
        var createImageLabel = new CustomLabel(createIcon, Component.CENTER_ALIGNMENT);
        createImageLabel.setLabelSize(100, 100);

        createMaskField = new CustomInput(260, 54, 15, "New name", Component.CENTER_ALIGNMENT);
        createMaskField.setText("Mask-08");

        var createButton = new CustomComponent("Create", 100, 50, 20, 10, Component.CENTER_ALIGNMENT, null, null);

        createPanel.add(createLabelOne);
        createPanel.add(Box.createVerticalStrut(10));
        createPanel.add(createImageLabel);
        createPanel.add(Box.createVerticalStrut(10));
        createPanel.add(createMaskField);
        createPanel.add(Box.createVerticalStrut(20));
        createPanel.add(createLabelTwo);
        createPanel.add(Box.createVerticalStrut(20));
        createPanel.add(createButton);

        // Edit Mask option
        var editPanel = new CustomPanel(new BoxLayout(null, BoxLayout.Y_AXIS), Color.LIGHT_GRAY, null, null, 15, 15,
                15);

        var editLabelOne = new CustomLabel("Edit Mask", DARK_COLOR, 20, Component.CENTER_ALIGNMENT);
        var editLabelTwo = new CustomLabel("Edit a configuration file on the connected USB drive", DARK_COLOR, 16,
                Component.CENTER_ALIGNMENT);

        var editIcon = new ImageIcon("resources/images/old-flash.png");
        var editImageLabel = new CustomLabel(editIcon, Component.CENTER_ALIGNMENT);
        editImageLabel.setLabelSize(100, 100);

        editMaskField = new CustomInput(260, 54, 15, "Existing name", Component.CENTER_ALIGNMENT);
        editMaskField.setText("Mask-08");

        var editButton = new CustomComponent("Edit", 100, 50, 20, 10, Component.CENTER_ALIGNMENT, null, null);

        editPanel.add(editLabelOne);
        editPanel.add(Box.createVerticalStrut(10));
        editPanel.add(editImageLabel);
        editPanel.add(Box.createVerticalStrut(10));
        editPanel.add(editMaskField);
        editPanel.add(Box.createVerticalStrut(20));
        editPanel.add(editLabelTwo);
        editPanel.add(Box.createVerticalStrut(20));
        editPanel.add(editButton);

        optionsPanel.add(createPanel);
        optionsPanel.add(editPanel);

        createButton.addButtonBehavior(() -> createMaskHandler.accept(createMaskField.getText()));
        editButton.addButtonBehavior(() -> editMaskHandler.accept(editMaskField.getText()));

        return optionsPanel;
    }
}