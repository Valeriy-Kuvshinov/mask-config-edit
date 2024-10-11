package src.configuration.inputs;

import java.awt.*;
import java.util.*;
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
        var combinedPanel = createInputsPanel(torSettings);

        // Create a new panel to center the combinedPanel horizontally
        var centerPanel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(combinedPanel, centerGbc);

        add(centerPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private CustomPanel createInputsPanel(Map<String, Object> torSettings) {
        var panel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        addRow(panel, gbc, 0, "output_tor_list_countryCodes", torSettings.get("output_tor_list_countryCodes"), 420, 32,
                "at, ch, de, fr...");
        addRow(panel, gbc, 1, "output_tor_list_exit_nodes", torSettings.get("output_tor_list_exit_nodes"), 420, 20,
                "0.0.0");
        addRow(panel, gbc, 2, "output_tor_default_country", torSettings.get("output_tor_default_country"), 120, 4,
                "00");

        return panel;
    }

    private void addRow(CustomPanel panel, GridBagConstraints gbc, int row, String key, Object value, int inputWidth,
            int maxChars, String placeholder) {
        // Reset insets for each new row
        gbc.insets = new Insets(10, 15, 10, 15);

        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        var label = new CustomLabel(key, LIGHT_COLOR, 16, Component.RIGHT_ALIGNMENT);
        panel.add(label, gbc);

        // Input
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 20, 10, 15);

        var input = new CustomInput(inputWidth, 54, maxChars, placeholder, Component.LEFT_ALIGNMENT);
        input.setText(value instanceof String[] ? String.join(", ", (String[]) value) : value.toString());
        panel.add(input, gbc);
    }
}