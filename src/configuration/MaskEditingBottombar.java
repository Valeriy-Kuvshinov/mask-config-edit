package src.configuration;

import javax.swing.*;
import java.awt.*;

import src.utilities.*;

public class MaskEditingBottombar extends CustomPanel {
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private Runnable onBackAction;
    private CustomComponent leftButton;
    private CustomComponent centerText;
    private CustomComponent rightButton;

    public MaskEditingBottombar(Runnable onBackAction) {
        super(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        this.onBackAction = onBackAction;
        initializeUI();
    }

    private void initializeUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);

        // Left button section (1/4)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        add(createLeftButtonSection(), gbc);

        // Center text section (2/4)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(createCenterTextSection(), gbc);

        // Right button section (1/4)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        add(createRightButtonSection(), gbc);
    }

    private JPanel createLeftButtonSection() {
        CustomPanel panel = new CustomPanel(new FlowLayout(FlowLayout.LEFT), DARK_COLOR, null, null, 0, 0, 0);
        leftButton = new CustomComponent("Back", 100, 40, 20, 10, Component.LEFT_ALIGNMENT, LIGHT_COLOR, DARK_COLOR);
        leftButton.addButtonBehavior(onBackAction);
        panel.add(leftButton);
        return panel;
    }

    private JPanel createCenterTextSection() {
        CustomPanel panel = new CustomPanel(new FlowLayout(FlowLayout.CENTER), DARK_COLOR, null, null, 0, 0, 0);
        centerText = new CustomComponent("Current Status: Editing", null, 42, 20, 10, Component.CENTER_ALIGNMENT,
                LIGHT_COLOR, DARK_COLOR);
        panel.add(centerText);
        return panel;
    }

    private JPanel createRightButtonSection() {
        CustomPanel panel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT), DARK_COLOR, null, null, 0, 0, 0);
        rightButton = new CustomComponent("Save", 100, 40, 20, 10, Component.RIGHT_ALIGNMENT, LIGHT_COLOR, DARK_COLOR);
        rightButton.addButtonBehavior(() -> System.out.println("Save button clicked"));
        panel.add(rightButton);
        return panel;
    }
}