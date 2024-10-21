package src.configuration.inputs;

import java.awt.*;

import src.configuration.*;
import src.configuration.general.*;
import src.utilities.*;
import src.utilities.gui.*;

public class HotspotSetInputs extends CustPanel {
        private MaskEditManager manager;
        private CategorySettings currentSettings;

        public HotspotSetInputs(MaskEditManager manager) {
                super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 15, 15);
                this.manager = manager;
                initUI();
        }

        private void initUI() {
                currentSettings = manager.getSettingsForCategory("Hotspot");
                var combinedPanel = createInputsPanel(currentSettings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(CategorySettings settings) {
                var panel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);

                InputPanelUtils.addSectionHeader(panel, gbc, 0, "General Settings", ColorPalette.LIGHT_ONE);

                InputPanelUtils.addSelectRow(panel, gbc, 1, "inputInterface_hotspot_hidden",
                                settings.getSetting("inputInterface_hotspot_hidden"),
                                64, new String[] { "0", "1" }, ColorPalette.LIGHT_ONE,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 2, "inputInterface_hotspot_ssid_length",
                                settings.getSetting("inputInterface_hotspot_ssid_length"),
                                46, 1, "9", ColorPalette.LIGHT_ONE,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 3, "inputInterface_hotspot_password_length",
                                settings.getSetting("inputInterface_hotspot_password_length"),
                                46, 1, "9", ColorPalette.LIGHT_ONE,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addSelectRow(panel, gbc, 4, "inputInterface_hotspot_ssid_policy",
                                settings.getSetting("inputInterface_hotspot_ssid_policy"),
                                330, new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" },
                                ColorPalette.LIGHT_ONE, manager, "Hotspot", currentSettings);
                InputPanelUtils.addSelectRow(panel, gbc, 5, "inputInterface_hotspot_password_policy",
                                settings.getSetting("inputInterface_hotspot_password_policy"),
                                330, new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" },
                                ColorPalette.LIGHT_ONE, manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 6, "inputInterface_hotspot_ssid",
                                settings.getSetting("inputInterface_hotspot_ssid"),
                                330, 32, "SSID name", ColorPalette.LIGHT_ONE,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 7, "inputInterface_hotspot_password",
                                settings.getSetting("inputInterface_hotspot_password"),
                                330, 32, "Password", ColorPalette.LIGHT_ONE,
                                manager, "Hotspot", currentSettings);

                return panel;
        }
}