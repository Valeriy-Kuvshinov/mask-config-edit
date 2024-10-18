package src.configuration.inputs;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class VpnSetInputs extends CustPanel {
        private MaskEditManager manager;
        private LinkedHashMap<String, Object> currentSettings;

        public VpnSetInputs(MaskEditManager manager) {
                super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 15, 15);
                this.manager = manager;
                initUI();
        }

        private void initUI() {
                currentSettings = manager.getSettingsForCategory("VPN");
                var combinedPanel = createInputsPanel(currentSettings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(LinkedHashMap<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);
                var row = 0;

                // General VPN Settings
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "General Settings", ColorPalette.LIGHT_ONE);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_list",
                                InputPanelUtils.jsonArrayToString(settings.get("output_vpn_service_list")),
                                420, 64, "VPN Services", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_service",
                                settings.get("output_vpn_default_service"),
                                330, 32, "Default Service", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_country",
                                settings.get("output_vpn_default_country"),
                                90, 3, "Default Country", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_server",
                                settings.get("output_vpn_default_server"),
                                330, 32, "Default Server", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);

                // Individual VPN Settings
                String[] services = { "VPNServiceOne", "VPNServiceTwo", "VPNServiceThree" };
                for (var i = 0; i < services.length; i++) {
                        row = addVpnServiceSettings(panel, gbc, row, settings, services[i], i + 1);
                }

                return panel;
        }

        private int addVpnServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
                        LinkedHashMap<String, Object> settings, String serviceName, int serviceNumber) {
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "VPN " + serviceNumber + " Settings",
                                ColorPalette.LIGHT_ONE);

                var serviceObj = settings.get(serviceName);
                if (!(serviceObj instanceof LinkedHashMap)) {
                        System.err.println("Expected LinkedHashMap for " + serviceName + ", but got " +
                                        (serviceObj != null ? serviceObj.getClass().getName() : "null"));
                        return row;
                }
                @SuppressWarnings("unchecked")
                LinkedHashMap<String, Object> vpnService = (LinkedHashMap<String, Object>) serviceObj;
                var suffix = String.valueOf(serviceNumber);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix,
                                vpnService.get("output_vpn_service_" + suffix),
                                330, 32, "VPN Service " + suffix, ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "service_expiration_date_vpn_" + suffix,
                                vpnService.get("service_expiration_date_vpn_" + suffix),
                                210, 10, "MM/DD/YYYY", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_countryCodes",
                                InputPanelUtils.jsonArrayToString(
                                                vpnService.get("output_vpn_service_" + suffix + "_list_countryCodes")),
                                420, 64, "Country Codes", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_username",
                                vpnService.get("output_vpn_service_" + suffix + "_username"),
                                420, 32, "Username", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_password",
                                vpnService.get("output_vpn_service_" + suffix + "_password"),
                                420, 32, "Password", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_servers",
                                InputPanelUtils.jsonArrayToString(
                                                vpnService.get("output_vpn_service_" + suffix + "_list_servers")),
                                420, 20898, "Servers", ColorPalette.LIGHT_ONE,
                                manager, "VPN", currentSettings);

                return row;
        }
}