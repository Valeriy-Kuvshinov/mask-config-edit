package src.configuration.bars;

import java.awt.*;

import src.utilities.*;

public class MaskEditBottombarTwo extends CustPanel {
    private Runnable onBackAction;

    public MaskEditBottombarTwo(Runnable onBackAction) {
        super(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 15, 15);
        this.onBackAction = onBackAction;
        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
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
        var centerText = new CustComponent("Review your changes before saving!", null, 42, 20, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_ONE);
        return centerText;
    }

    private CustComponent createRightButton() {
        var rightButton = new CustComponent("Save", 100, 42, 20, 10,
                Component.RIGHT_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
        rightButton.addButtonBehavior(() -> System.out.println("Start saving process"));
        return rightButton;
    }
}