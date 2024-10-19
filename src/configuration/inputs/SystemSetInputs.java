package src.configuration.inputs;

import java.awt.*;

import src.configuration.*;
import src.configuration.general.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class SystemSetInputs extends CustPanel {
    private MaskEditManager manager;
    private CategorySettings currentSettings;

    public SystemSetInputs(MaskEditManager manager) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 15, 15);
        this.manager = manager;
        initializeUI();
    }

    private void initializeUI() {
        currentSettings = manager.getSettingsForCategory("System");
        var combinedPanel = createInputsPanel(currentSettings);
        InputPanelUtils.initCommonUI(this, combinedPanel);
    }

    private CustPanel createInputsPanel(CategorySettings settings) {
        var panel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        InputPanelUtils.addSectionHeader(panel, gbc, 0, "Inputs Settings", ColorPalette.LIGHT_ONE);

        // interfaceNames_input_usb label + input
        gbc.gridy++;
        gbc.gridwidth = 6;
        InputPanelUtils.addInputRow(panel, gbc, gbc.gridy, "interfaceNames_input_usb",
                settings.getSetting("interfaceNames_input_usb"), 330, 16, "xxxxxxxxxxxxxxxx",
                ColorPalette.LIGHT_ONE, manager, "System", currentSettings);
        gbc.insets = new Insets(10, 15, 10, 15);

        // Input settings
        String[] inputSettings = {
                "Input_computer", "Input_cellular", "Input_secureIphone",
                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb"
        };
        addSettingsGroup(panel, gbc, settings, inputSettings, gbc.gridy + 1, 2, 3);

        InputPanelUtils.addSectionHeader(panel, gbc, 4, "Output Settings", ColorPalette.LIGHT_ONE);

        // Output settings
        String[] outputSettings = {
                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
        };
        addSettingsGroup(panel, gbc, settings, outputSettings, gbc.gridy + 1, 3, 3);

        return panel;
    }

    private void addSettingsGroup(CustPanel panel, GridBagConstraints gbc, CategorySettings settings,
            String[] settingKeys, int startRow, int rows, int cols) {
        for (var i = 0; i < settingKeys.length; i++) {
            var row = startRow + (i / cols);
            var col = i % cols;

            var key = settingKeys[i];
            var value = settings.getSetting(key);

            // Label
            gbc.gridx = col * 2;
            gbc.gridy = row;
            gbc.gridwidth = 1;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            var label = new CustLabel(key, ColorPalette.LIGHT_ONE, 16, Component.LEFT_ALIGNMENT);
            panel.add(label, gbc);

            // Select
            gbc.gridx = col * 2 + 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;

            var select = new CustSelect(64, 54, new String[] { "0", "1" }, value.toString(), Component.LEFT_ALIGNMENT);
            select.addActionListener(e -> InputPanelUtils.updateSetting(manager,
                    "System", key, select.getSelectedItem()));
            panel.add(select, gbc);
        }
    }
}