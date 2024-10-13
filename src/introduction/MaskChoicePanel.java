package src.introduction;

import java.util.function.*;
import javax.swing.*;
import java.awt.*;

import src.utilities.*;

public class MaskChoicePanel extends CustPanel {
        private Consumer<String> createMaskHandler;
        private Consumer<String> editMaskHandler;
        private static final Color DARK_COLOR = new Color(30, 30, 30);
        private static final Color GRAY_COLOR = new Color(50, 50, 50);
        private static final Color LIGHT_COLOR = new Color(220, 220, 220);

        public MaskChoicePanel(Consumer<String> createMaskHandler, Consumer<String> editMaskHandler,
                        Runnable onBackClick) {
                super(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);
                this.createMaskHandler = createMaskHandler;
                this.editMaskHandler = editMaskHandler;
                initializeUI(onBackClick);
        }

        private void initializeUI(Runnable onBackClick) {
                // Create a main panel to hold all components
                var mainPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), DARK_COLOR, null, null, 0, 0, 0);

                // Create a wrapper panel to center the main panel
                var wrapperPanel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                wrapperPanel.add(mainPanel);

                // Create labels
                var welcomeLabel = new CustLabel("Welcome to User Configuration!", null, null,
                                Component.CENTER_ALIGNMENT);
                var messageLabel = new CustLabel("Flash drive connected. Choose an option:", null, null,
                                Component.CENTER_ALIGNMENT);

                // Create back button
                var backButton = new CustComponent("Back", 100, 60, 20, 10,
                                Component.CENTER_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);
                backButton.addButtonBehavior(onBackClick);

                var optionsPanel = createOptionsPanel();
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

        private CustPanel createOptionsPanel() {
                var optionsPanel = new CustPanel(new GridLayout(1, 2, 20, 0), DARK_COLOR, null,
                                Component.CENTER_ALIGNMENT, 0, 0, 0);

                // Create Mask option
                var createPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), Color.LIGHT_GRAY, null, null,
                                15, 15, 15);

                var createLabelOne = new CustLabel("Create Mask", DARK_COLOR, 20, Component.CENTER_ALIGNMENT);
                var createLabelTwo = new CustLabel("Create new configuration file with default settings", DARK_COLOR,
                                16, Component.CENTER_ALIGNMENT);

                var createIcon = new ImageIcon("resources/images/new-flash.png");
                var createImageLabel = new CustLabel(createIcon, Component.CENTER_ALIGNMENT);
                createImageLabel.setLabelSize(100, 100);

                var createButton = new CustComponent("Create", 100, 50, 20, 10,
                                Component.CENTER_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);

                // Create mask field with relevant checks
                var createMaskField = new CustInput(260, 54, 15, "New name", Component.CENTER_ALIGNMENT);
                createMaskField.setText("Mask-08");
                createMaskField.addTextChangeListener(newText -> updateButtonState(
                                createButton, createMaskField, createMaskHandler));

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
                var editPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), Color.LIGHT_GRAY, null, null, 15,
                                15, 15);

                var editLabelOne = new CustLabel("Edit Mask", DARK_COLOR, 20, Component.CENTER_ALIGNMENT);
                var editLabelTwo = new CustLabel("Edit a configuration file on the connected USB drive", DARK_COLOR,
                                16, Component.CENTER_ALIGNMENT);

                var editIcon = new ImageIcon("resources/images/old-flash.png");
                var editImageLabel = new CustLabel(editIcon, Component.CENTER_ALIGNMENT);
                editImageLabel.setLabelSize(100, 100);

                var editButton = new CustComponent("Edit", 100, 50, 20, 10,
                                Component.CENTER_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);

                // Edit mask field with relevant checks
                var editMaskField = new CustInput(260, 54, 15, "Existing name", Component.CENTER_ALIGNMENT);
                editMaskField.setText("Mask-08");
                editMaskField.addTextChangeListener(newText -> updateButtonState(
                                editButton, editMaskField, editMaskHandler));

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

                // Initial button state update
                updateButtonState(createButton, createMaskField, createMaskHandler);
                updateButtonState(editButton, editMaskField, editMaskHandler);

                return optionsPanel;
        }

        private void updateButtonState(CustComponent button, CustInput input, Consumer<String> handler) {
                var isValid = input.getText().length() >= 4;

                if (isValid) {
                        button.setBackgroundColor(LIGHT_COLOR);
                        button.setColor(GRAY_COLOR);
                        button.removeButtonBehavior();
                        button.addButtonBehavior(() -> handler.accept(input.getText()));
                } else {
                        button.setBackgroundColor(GRAY_COLOR);
                        button.setColor(LIGHT_COLOR);
                        button.removeButtonBehavior();
                }
        }
}