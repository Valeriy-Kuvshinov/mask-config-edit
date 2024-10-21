package src.configuration.bars;

import java.awt.*;

import src.utilities.*;
import src.utilities.gui.*;

public class MaskEditBottombar extends CustPanel {
    private Runnable onBackAction;
    private Runnable onRunAction;
    private CustComponent centerText;
    private String mode;

    public MaskEditBottombar(String mode, Runnable onBackAction, Runnable onRunAction) {
        super(new GridBagLayout(), ColorPalette.DARK_TWO, null, null, 0, 15, 15);
        this.mode = mode;
        this.onBackAction = onBackAction;
        this.onRunAction = onRunAction;
        initUI();
    }

    private void initUI() {
        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 5);

        // Left button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(createLeftButton(), gbc);

        // Center text
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(createCenterText(), gbc);

        // Right button
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(createRightButton(), gbc);
    }

    private CustComponent createLeftButton() {
        var leftButton = new CustComponent("Back", 100, 42, 20, 10,
                Component.LEFT_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
        leftButton.addButtonBehavior(onBackAction);
        return leftButton;
    }

    private CustComponent createCenterText() {
        var centerMessage = (mode.equals("edit")) ? "Enjoy Editing!" : "Confirm changes before saving!";
        centerText = new CustComponent(centerMessage, null, 42, 20, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_ONE);
        return centerText;
    }

    private CustComponent createRightButton() {
        var buttonText = (mode.equals("edit")) ? "Preview" : "Proceed";
        var rightButton = new CustComponent(buttonText, null, 42, 20, 10,
                Component.RIGHT_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);

        if (onRunAction != null) {
            rightButton.addButtonBehavior(onRunAction);
        } else {
            rightButton.addButtonBehavior(() -> System.out.println(buttonText + "ing..."));
        }

        return rightButton;
    }
}