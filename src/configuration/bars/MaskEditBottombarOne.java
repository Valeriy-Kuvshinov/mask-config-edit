package src.configuration.bars;

import java.awt.*;

import src.utilities.*;

public class MaskEditBottombarOne extends CustPanel {
    private Runnable onBackAction;
    private Runnable onPreviewAction;
    private CustComponent centerText;
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color GRAY_COLOR = new Color(50, 50, 50);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public MaskEditBottombarOne(Runnable onBackAction, Runnable onPreviewAction) {
        super(new GridBagLayout(), DARK_COLOR, null, null, 0, 15, 15);
        this.onBackAction = onBackAction;
        this.onPreviewAction = onPreviewAction;
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
                Component.LEFT_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);
        leftButton.addButtonBehavior(onBackAction);
        return leftButton;
    }

    private CustComponent createCenterText() {
        centerText = new CustComponent("Enjoy editing!", null, 42, 20, 10,
                Component.CENTER_ALIGNMENT, LIGHT_COLOR, DARK_COLOR);
        return centerText;
    }

    private CustComponent createRightButton() {
        var rightButton = new CustComponent("Preview", null, 42, 20, 10,
                Component.RIGHT_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);
        rightButton.addButtonBehavior(onPreviewAction);
        return rightButton;
    }
}