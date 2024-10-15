package src.configuration.inputs;

import java.awt.*;
import java.util.Map;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class SystemSetInputs extends CustPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public SystemSetInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        Map<String, Object> settings = MaskEditManager.getSettingsForCategory("System");
        var combinedPanel = createInputsPanel(settings);
        InputPanelUtils.initCommonUI(this, combinedPanel);
    }

    private CustPanel createInputsPanel(Map<String, Object> settings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        InputPanelUtils.addSectionHeader(panel, gbc, 0, "Inputs Settings", LIGHT_COLOR);

        // interfaceNames_input_usb label + input
        gbc.gridy++;
        gbc.gridwidth = 6;
        InputPanelUtils.addInputRow(panel, gbc, gbc.gridy, "interfaceNames_input_usb",
                settings.get("interfaceNames_input_usb"), 330, 16, "xxxxxxxxxxxxxxxx", LIGHT_COLOR);
        gbc.insets = new Insets(10, 15, 10, 15);

        // Input settings
        String[] inputSettings = {
                "Input_computer", "Input_cellular", "Input_secureIphone",
                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb"
        };
        addSettingsGroup(panel, gbc, settings, inputSettings, gbc.gridy + 1, 2, 3);

        InputPanelUtils.addSectionHeader(panel, gbc, 4, "Output Settings", LIGHT_COLOR);

        // Output settings
        String[] outputSettings = {
                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
        };
        addSettingsGroup(panel, gbc, settings, outputSettings, gbc.gridy + 1, 3, 3);

        return panel;
    }

    private void addSettingsGroup(CustPanel panel, GridBagConstraints gbc, Map<String, Object> settings,
            String[] settingKeys, int startRow, int rows, int cols) {
        for (var i = 0; i < settingKeys.length; i++) {
            var row = startRow + (i / cols);
            var col = i % cols;

            var key = settingKeys[i];
            var value = settings.get(key);

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

            var select = new CustSelect(64, 54, new String[] { "0", "1" }, value.toString(), Component.LEFT_ALIGNMENT);
            panel.add(select, gbc);
        }
    }
}