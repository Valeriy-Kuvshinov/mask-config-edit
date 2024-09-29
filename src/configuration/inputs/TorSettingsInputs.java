package src.configuration.inputs;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

import src.configuration.*;
import src.utilities.*;

public class TorSettingsInputs extends CustomPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public TorSettingsInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        Map<String, Object> torSettings = MaskEditingManager.getSettingsForCategory("Tor");

        var mainPanel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        addRow(mainPanel, gbc, 0, "output_tor_list_countryCodes", torSettings, 420, 32, "at, ch, de, fr...");
        addRow(mainPanel, gbc, 1, "output_tor_list_exit_nodes", torSettings, 420, 20, "0.0.0");
        addRow(mainPanel, gbc, 2, "output_tor_default_country", torSettings, 80, 4, "00");

        add(mainPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private void addRow(CustomPanel panel, GridBagConstraints gbc, int row, String key, Map<String, Object> settings,
            int inputWidth, int maxChars, String placeholder) {
        // Reset insets for each new row
        gbc.insets = new Insets(0, 0, 10, 0);

        // Empty column (left)
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(Box.createHorizontalStrut(10), gbc);

        // Label
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        var label = new CustomLabel(key, LIGHT_COLOR, 16, Component.RIGHT_ALIGNMENT);
        panel.add(label, gbc);

        // Input
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 100, 10, 0);

        var input = new CustomInput(inputWidth, 42, maxChars, placeholder, Component.LEFT_ALIGNMENT);
        var value = settings.get(key);
        input.setText(value instanceof String[] ? String.join(", ", (String[]) value) : value.toString());
        panel.add(input, gbc);

        // Empty column (right)
        gbc.gridx = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(Box.createHorizontalStrut(10), gbc);
    }
}