package src.configuration.inputs;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

import src.configuration.*;
import src.utilities.*;

public class SystemSettingsInputs extends CustomPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public SystemSettingsInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        var systemSettings = MaskEditingManager.getSettingsForCategory("System");
        var combinedPanel = createInputsPanel(systemSettings);
        add(combinedPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private CustomPanel createInputsPanel(Map<String, Object> systemSettings) {
        var panel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        int row = 0;
        int col = 0;

        // Add single-digit inputs
        for (Map.Entry<String, Object> entry : systemSettings.entrySet()) {
            if (entry.getKey().equals("interfaceNames_input_usb")) {
                continue;
            }
            // Label
            gbc.gridx = col * 2;
            gbc.gridy = row;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            var label = new CustomLabel(entry.getKey(), LIGHT_COLOR, 16, Component.LEFT_ALIGNMENT);
            panel.add(label, gbc);

            // Input
            gbc.gridx = col * 2 + 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;
            var input = new CustomInput(40, 42, 1, "2", Component.LEFT_ALIGNMENT);
            input.setText(entry.getValue().toString());
            panel.add(input, gbc);

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
        // Add USB input in a new row
        row++; // Move to the next row

        // USB Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        var usbLabel = new CustomLabel("interfaceNames_input_usb", LIGHT_COLOR, 16, Component.LEFT_ALIGNMENT);
        panel.add(usbLabel, gbc);

        // USB Input
        gbc.gridx = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        var usbInput = new CustomInput(330, 42, 16, "xxxxxxxxxxxxxxxx", Component.LEFT_ALIGNMENT);
        usbInput.setText(systemSettings.get("interfaceNames_input_usb").toString());
        panel.add(usbInput, gbc);

        return panel;
    }
}