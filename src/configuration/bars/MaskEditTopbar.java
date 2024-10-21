package src.configuration.bars;

import java.awt.*;
import java.time.*;
import java.time.format.*;

import src.utilities.*;
import src.utilities.gui.*;

public class MaskEditTopbar extends CustPanel {
    private String maskName;

    public MaskEditTopbar(String maskName) {
        super(new GridBagLayout(), ColorPalette.DARK_TWO, null, null, 0, 15, 10);
        this.maskName = maskName;
        initUI();
    }

    private void initUI() {
        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);

        // Left section (mask name)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(createLeftSection(), gbc);

        // Center section (User Configuration)
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(createCenterSection(), gbc);

        // Right section (date)
        gbc.gridx = 2;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(createRightSection(), gbc);
    }

    private CustComponent createLeftSection() {
        return new CustComponent(maskName, null, 42, 20, 10,
                Component.LEFT_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
    }

    private CustComponent createCenterSection() {
        return new CustComponent("User-01 Configuration", null, 42, 32, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
    }

    private CustComponent createRightSection() {
        var currentDate = LocalDate.now();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        var formattedDate = currentDate.format(formatter);
        return new CustComponent(formattedDate, null, 42, 20, 10,
                Component.RIGHT_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
    }
}