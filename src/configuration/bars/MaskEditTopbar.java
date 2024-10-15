package src.configuration.bars;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.*;

import src.utilities.*;

public class MaskEditTopbar extends CustPanel {
    private String maskName;
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public MaskEditTopbar(String maskName) {
        super(new GridBagLayout(), DARK_COLOR, null, null, 0, 15, 10);
        this.maskName = maskName;
        initUI();
    }

    private void initUI() {
        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);

        // Name section (1/4)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        add(createNameSection(), gbc);

        // User Configuration section (2/4)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(createUserConfigSection(), gbc);

        // Date section (1/4)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        add(createDateSection(), gbc);
    }

    private JPanel createNameSection() {
        var panel = new CustPanel(new FlowLayout(FlowLayout.LEFT), DARK_COLOR, null, null, 0, 0, 0);

        panel.add(new CustComponent(maskName, null, 42, 20, 10,
                Component.LEFT_ALIGNMENT, LIGHT_COLOR, DARK_COLOR));
        return panel;
    }

    private JPanel createUserConfigSection() {
        var panel = new CustPanel(new FlowLayout(FlowLayout.CENTER), DARK_COLOR, null, null, 0, 0, 0);

        panel.add(new CustLabel("User-01 Configuration", LIGHT_COLOR, 32, Component.CENTER_ALIGNMENT));
        return panel;
    }

    private JPanel createDateSection() {
        var panel = new CustPanel(new FlowLayout(FlowLayout.RIGHT), DARK_COLOR, null, null, 0, 0, 0);

        var currentDate = LocalDate.now();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        var formattedDate = currentDate.format(formatter);

        panel.add(new CustComponent(formattedDate, null, 42, 20, 10,
                Component.RIGHT_ALIGNMENT, LIGHT_COLOR, DARK_COLOR));

        return panel;
    }
}