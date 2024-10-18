package src.configuration.inputs;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class VpsSetInputs extends CustPanel {
        private MaskEditManager manager;
        private LinkedHashMap<String, Object> currentSettings;

        public VpsSetInputs(MaskEditManager manager) {
                super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 15, 15);
                this.manager = manager;
                initUI();
        }

        private void initUI() {
                currentSettings = manager.getSettingsForCategory("VPS");
                var combinedPanel = createInputsPanel(currentSettings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(LinkedHashMap<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);

                InputPanelUtils.addSectionHeader(panel, gbc, 0, "General Settings", ColorPalette.LIGHT_ONE);

                InputPanelUtils.addInputRow(panel, gbc, 1, "output_vps_service", settings.get("output_vps_service"),
                                330, 32, "VPS Service", ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 2, "service_expiration_date_vps",
                                settings.get("service_expiration_date_vps"),
                                330, 10, "MM/DD/YYYY", ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 3, "output_vps_service_countryCode",
                                settings.get("output_vps_service_countryCode"),
                                90, 2, "Country Code", ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 4, "output_vps_service_country",
                                settings.get("output_vps_service_country"),
                                330, 32, "Country", ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 5, "output_vps_service_server",
                                settings.get("output_vps_service_server"),
                                330, 32, "Server", ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);
                InputPanelUtils.addSelectRow(panel, gbc, 6, "output_vps_service_preferred_transport",
                                settings.get("output_vps_service_preferred_transport"),
                                112, new String[] { "tcp", "udp" }, ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 7, "output_vps_service_username",
                                settings.get("output_vps_service_username"),
                                330, 32, "Username", ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, 8, "output_vps_service_password",
                                settings.get("output_vps_service_password"),
                                330, 32, "Password", ColorPalette.LIGHT_ONE,
                                manager, "VPS", currentSettings);

                return panel;
        }
}