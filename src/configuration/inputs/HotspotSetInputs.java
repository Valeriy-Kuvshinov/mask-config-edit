package src.configuration.inputs;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class HotspotSetInputs extends CustPanel {
        private MaskEditManager manager;
        private Map<String, Object> currentSettings;
        private static final Color DARK_COLOR = new Color(30, 30, 30);
        private static final Color LIGHT_COLOR = new Color(220, 220, 220);

        public HotspotSetInputs(MaskEditManager manager) {
                super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
                this.manager = manager;
                initUI();
        }

        private void initUI() {
                currentSettings = manager.getSettingsForCategory("Hotspot");
                var combinedPanel = createInputsPanel(currentSettings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(Map<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);

                InputPanelUtils.addSectionHeader(panel, gbc, 0, "General Settings", LIGHT_COLOR);

                InputPanelUtils.addSelectRow(panel, gbc, 1, "inputInterface_hotspot_hidden",
                                settings.get("inputInterface_hotspot_hidden"),
                                64, new String[] { "0", "1" }, LIGHT_COLOR,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 2, "inputInterface_hotspot_ssid_length",
                                settings.get("inputInterface_hotspot_ssid_length"),
                                46, 1, "9", LIGHT_COLOR,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 3, "inputInterface_hotspot_password_length",
                                settings.get("inputInterface_hotspot_password_length"),
                                46, 1, "9", LIGHT_COLOR,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 4, "inputInterface_hotspot_ssid",
                                settings.get("inputInterface_hotspot_ssid"),
                                330, 32, "SSID name", LIGHT_COLOR,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 5, "inputInterface_hotspot_password",
                                settings.get("inputInterface_hotspot_password"),
                                330, 32, "Password", LIGHT_COLOR,
                                manager, "Hotspot", currentSettings);
                InputPanelUtils.addSelectRow(panel, gbc, 6, "inputInterface_hotspot_ssid_policy",
                                settings.get("inputInterface_hotspot_ssid_policy"),
                                330, new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" },
                                LIGHT_COLOR, manager, "Hotspot", currentSettings);
                InputPanelUtils.addSelectRow(panel, gbc, 7, "inputInterface_hotspot_password_policy",
                                settings.get("inputInterface_hotspot_password_policy"),
                                330, new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" },
                                LIGHT_COLOR, manager, "Hotspot", currentSettings);

                return panel;
        }
}