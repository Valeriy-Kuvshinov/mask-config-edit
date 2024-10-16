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
                initUI(onBackClick);
        }

        private void initUI(Runnable onBackClick) {
                var mainPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), DARK_COLOR, null, null, 0, 0, 0);
                var wrapperPanel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                wrapperPanel.add(mainPanel);

                mainPanel.add(Box.createVerticalGlue());
                mainPanel.add(new CustLabel("Welcome to User Configuration!", null, null,
                                Component.CENTER_ALIGNMENT));
                mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                mainPanel.add(new CustLabel("Flash drive connected. Choose an option:", null, null,
                                Component.CENTER_ALIGNMENT));
                mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
                mainPanel.add(createOptionsPanel());
                mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));

                var backButton = new CustComponent("Back", 100, 60, 20, 10,
                                Component.CENTER_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);
                backButton.addButtonBehavior(onBackClick);
                mainPanel.add(backButton);
                mainPanel.add(Box.createVerticalGlue());

                add(wrapperPanel, BorderLayout.CENTER);
        }

        private CustPanel createOptionsPanel() {
                var optionsPanel = new CustPanel(new GridLayout(1, 2, 20, 0), DARK_COLOR, null,
                                Component.CENTER_ALIGNMENT, 0, 0, 0);
                optionsPanel.add(createOptionPanel("Create Mask", "Create new configuration file with default settings",
                                "resources/images/new-flash.png", "Create", "New name", createMaskHandler));
                optionsPanel.add(createOptionPanel("Edit Mask", "Edit a configuration file on the connected USB drive",
                                "resources/images/old-flash.png", "Edit", "Existing name", editMaskHandler));
                return optionsPanel;
        }

        private CustPanel createOptionPanel(String title, String description, String iconPath,
                        String buttonText, String inputPlaceholder, Consumer<String> handler) {
                var panel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), Color.LIGHT_GRAY, null,
                                null, 15, 15, 15);

                panel.add(new CustLabel(title, DARK_COLOR, 20, Component.CENTER_ALIGNMENT));
                panel.add(Box.createVerticalStrut(10));

                var imageLabel = new CustLabel(new ImageIcon(iconPath), Component.CENTER_ALIGNMENT);
                imageLabel.setLabelSize(100, 100);
                panel.add(imageLabel);
                panel.add(Box.createVerticalStrut(10));

                var inputField = new CustInput(260, 54, 15, inputPlaceholder, Component.CENTER_ALIGNMENT);
                inputField.setText("Mask-08");
                panel.add(inputField);
                panel.add(Box.createVerticalStrut(20));

                panel.add(new CustLabel(description, DARK_COLOR, 16, Component.CENTER_ALIGNMENT));
                panel.add(Box.createVerticalStrut(20));

                var button = new CustComponent(buttonText, 100, 50, 20, 10,
                                Component.CENTER_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);
                panel.add(button);

                inputField.addTextChangeListener(newText -> updateButtonState(button, inputField, handler));
                updateButtonState(button, inputField, handler);

                return panel;
        }

        private void updateButtonState(CustComponent button, CustInput input, Consumer<String> handler) {
                boolean isValid = input.getText().length() >= 4;
                button.setBackgroundColor(isValid ? LIGHT_COLOR : GRAY_COLOR);
                button.setColor(isValid ? GRAY_COLOR : LIGHT_COLOR);
                button.removeButtonBehavior();
                if (isValid) {
                        button.addButtonBehavior(() -> handler.accept(input.getText()));
                }
        }
}