package src.configuration;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.*;

import src.utilities.*;

public class MaskEditingTopbar extends CustomPanel {
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private String maskName;

    public MaskEditingTopbar(String maskName) {
        super(new GridBagLayout(), DARK_COLOR, null, null);
        this.maskName = maskName;
        initializeUI();
    }

    private void initializeUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);

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
        CustomPanel panel = new CustomPanel(new FlowLayout(FlowLayout.LEFT), DARK_COLOR, null, null);
        panel.add(new CustomLabel("Name: ", LIGHT_COLOR, 24, Component.LEFT_ALIGNMENT));
        panel.add(new CustomComponent(maskName, null, 42, null, null,
                Component.LEFT_ALIGNMENT, LIGHT_COLOR, DARK_COLOR));
        return panel;
    }

    private JPanel createUserConfigSection() {
        CustomPanel panel = new CustomPanel(new FlowLayout(FlowLayout.CENTER), DARK_COLOR, null, null);
        panel.add(new CustomLabel("User-01 Configuration", LIGHT_COLOR, 32, Component.CENTER_ALIGNMENT));
        return panel;
    }

    private JPanel createDateSection() {
        CustomPanel panel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT), DARK_COLOR, null, null);
        panel.add(new CustomLabel("Date: ", LIGHT_COLOR, 24, Component.RIGHT_ALIGNMENT));

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String formattedDate = currentDate.format(formatter);

        CustomComponent dateComponent = new CustomComponent(formattedDate, null, 42, null, null,
                Component.RIGHT_ALIGNMENT, LIGHT_COLOR, DARK_COLOR);
        panel.add(dateComponent);
        return panel;
    }
}