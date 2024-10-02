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

    private CustomPanel createInputsPanel(Map<String, Object> systemSettings) {
        var panel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // USB input
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        var usbLabel = new CustomLabel("interfaceNames_input_usb", LIGHT_COLOR, 16, Component.LEFT_ALIGNMENT);
        panel.add(usbLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 4;
        var usbInput = new CustomInput(330, 54, 16, "xxxxxxxxxxxxxxxx", Component.LEFT_ALIGNMENT);
        usbInput.setText(systemSettings.get("interfaceNames_input_usb").toString());
        panel.add(usbInput, gbc);

        // Input settings
        String[] inputSettings = {
                "Input_computer", "Input_cellular", "Input_secureIphone",
                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb"
        };
        addSettingsGroup(panel, gbc, systemSettings, inputSettings, 1, 2, 3);

        // Output settings
        String[] outputSettings = {
                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
        };
        addSettingsGroup(panel, gbc, systemSettings, outputSettings, 3, 3, 3);

        return panel;
    }

    private void addSettingsGroup(CustomPanel panel, GridBagConstraints gbc, Map<String, Object> systemSettings,
            String[] settingKeys, int startRow, int rows, int cols) {
        for (var i = 0; i < settingKeys.length; i++) {
            var row = startRow + (i / cols);
            var col = i % cols;

            var key = settingKeys[i];
            var value = systemSettings.get(key);

            // Label
            gbc.gridx = col * 2;
            gbc.gridy = row;
            gbc.gridwidth = 1;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            var label = new CustomLabel(key, LIGHT_COLOR, 16, Component.LEFT_ALIGNMENT);
            panel.add(label, gbc);

            // Select
            gbc.gridx = col * 2 + 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;

            String[] options = { "0", "1" };
            var select = new CustomSelect(64, 54, options, value.toString(), Component.LEFT_ALIGNMENT);
            panel.add(select, gbc);
        }
    }
}