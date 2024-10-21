package src.introduction;

import java.util.function.*;
import javax.swing.*;
import java.awt.*;

import src.utilities.*;
import src.utilities.gui.*;

public class MaskChoicePanel extends CustPanel {
        private Consumer<String> createMaskHandler;
        private Consumer<String> editMaskHandler;

        public MaskChoicePanel(Consumer<String> createMaskHandler, Consumer<String> editMaskHandler,
                        Runnable onBackClick) {
                super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
                this.createMaskHandler = createMaskHandler;
                this.editMaskHandler = editMaskHandler;
                initUI(onBackClick);
        }

        private void initUI(Runnable onBackClick) {
                add(createWrapperPanel(onBackClick), BorderLayout.CENTER);
        }

        private CustPanel createWrapperPanel(Runnable onBackClick) {
                var wrapperPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
                wrapperPanel.add(createMainPanel(onBackClick));
                return wrapperPanel;
        }

        private CustPanel createMainPanel(Runnable onBackClick) {
                var mainPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), ColorPalette.DARK_ONE,
                                null, null, 0, 0, 0);

                mainPanel.add(Box.createVerticalGlue());
                mainPanel.add(new CustLabel("Welcome to User Configuration!", null,
                                null, Component.CENTER_ALIGNMENT));
                mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                mainPanel.add(new CustLabel("Flash drive connected. Choose an option:", null,
                                null, Component.CENTER_ALIGNMENT));
                mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
                mainPanel.add(createOptionsPanel());
                mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
                mainPanel.add(createBackButton(onBackClick));
                mainPanel.add(Box.createVerticalGlue());

                return mainPanel;
        }

        private CustPanel createOptionsPanel() {
                var optionsPanel = new CustPanel(new GridLayout(1, 2, 20, 0), ColorPalette.DARK_ONE,
                                null, Component.CENTER_ALIGNMENT, 0, 0, 0);
                optionsPanel.add(createOptionPanel("Create Mask", "Create new configuration file with default settings",
                                "resources/images/new-flash.png", "Create", "New name", createMaskHandler));
                optionsPanel.add(createOptionPanel("Edit Mask", "Edit a configuration file on the connected USB drive",
                                "resources/images/old-flash.png", "Edit", "Existing name", editMaskHandler));
                return optionsPanel;
        }

        private CustPanel createOptionPanel(String title, String description, String iconPath,
                        String buttonText, String inputPlaceholder, Consumer<String> handler) {
                var panel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), ColorPalette.LIGHT_TWO,
                                null, null, 15, 15, 15);

                panel.add(new CustLabel(title, ColorPalette.DARK_ONE, 20, Component.CENTER_ALIGNMENT));
                panel.add(Box.createVerticalStrut(10));
                panel.add(createImageLabel(iconPath));
                panel.add(Box.createVerticalStrut(10));
                var inputField = createInputField(inputPlaceholder);
                panel.add(inputField);
                panel.add(Box.createVerticalStrut(20));
                panel.add(new CustLabel(description, ColorPalette.DARK_ONE, 16, Component.CENTER_ALIGNMENT));
                panel.add(Box.createVerticalStrut(20));
                var button = new CustComponent(buttonText, 100, 50, 20, 10,
                                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
                panel.add(button);

                inputField.addTextChangeListener(newText -> updateButtonState(button, inputField, handler));
                updateButtonState(button, inputField, handler);

                return panel;
        }

        private CustLabel createImageLabel(String iconPath) {
                var imageLabel = new CustLabel(new ImageIcon(iconPath), Component.CENTER_ALIGNMENT);
                imageLabel.setLabelSize(100, 100);
                return imageLabel;
        }

        private CustInput createInputField(String placeholder) {
                var inputField = new CustInput(260, 54, 15, placeholder, Component.CENTER_ALIGNMENT);
                inputField.setText("Mask-08");
                return inputField;
        }

        private CustComponent createBackButton(Runnable onBackClick) {
                var backButton = new CustComponent("Back", 100, 60, 20, 10,
                                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
                backButton.addButtonBehavior(onBackClick);
                return backButton;
        }

        private void updateButtonState(CustComponent button, CustInput input, Consumer<String> handler) {
                var isValid = input.getText().length() >= 4;
                button.setBackgroundColor(isValid ? ColorPalette.LIGHT_ONE : ColorPalette.DARK_TWO);
                button.setColor(isValid ? ColorPalette.DARK_TWO : ColorPalette.LIGHT_ONE);
                button.removeButtonBehavior();
                if (isValid) {
                        button.addButtonBehavior(() -> handler.accept(input.getText()));
                }
        }
}