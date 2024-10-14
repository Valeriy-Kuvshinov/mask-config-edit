package src.configuration.inputs;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

import src.configuration.*;
import src.utilities.*;

public class SystemSetInputs extends CustPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public SystemSetInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        var systemSettings = MaskEditManager.getSettingsForCategory("System");
        var combinedPanel = createInputsPanel(systemSettings);

        // Create a new panel to center the combinedPanel horizontally
        var centerPanel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(combinedPanel, centerGbc);

        add(centerPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private CustPanel createInputsPanel(Map<String, Object> systemSettings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Inputs Section Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new CustLabel("Inputs Settings", LIGHT_COLOR, 24, Component.LEFT_ALIGNMENT), gbc);

        // interfaceNames label + input
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new CustLabel("interfaceNames_input_usb", LIGHT_COLOR, 16, Component.LEFT_ALIGNMENT), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 6;
        var usbInput = new CustInput(330, 54, 16, "xxxxxxxxxxxxxxxx", Component.LEFT_ALIGNMENT);
        usbInput.setText(systemSettings.get("interfaceNames_input_usb").toString());
        panel.add(usbInput, gbc);

        // Input settings
        String[] inputSettings = {
                "Input_computer", "Input_cellular", "Input_secureIphone",
                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb"
        };
        addSettingsGroup(panel, gbc, systemSettings, inputSettings, gbc.gridy + 1, 2, 3);

        // Outputs Section Label
        gbc.gridx = 0;
        gbc.gridy += 3;
        gbc.gridwidth = 6;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new CustLabel("Outputs Settings", LIGHT_COLOR, 24, Component.LEFT_ALIGNMENT), gbc);

        // Output settings
        String[] outputSettings = {
                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
        };
        addSettingsGroup(panel, gbc, systemSettings, outputSettings, gbc.gridy + 1, 3, 3);

        return panel;
    }

    private void addSettingsGroup(CustPanel panel, GridBagConstraints gbc, Map<String, Object> systemSettings,
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
            var label = new CustLabel(key, LIGHT_COLOR, 16, Component.LEFT_ALIGNMENT);
            panel.add(label, gbc);

            // Select
            gbc.gridx = col * 2 + 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;

            String[] options = { "0", "1" };
            var select = new CustSelect(64, 54, options, value.toString(), Component.LEFT_ALIGNMENT);
            panel.add(select, gbc);
        }
    }
}