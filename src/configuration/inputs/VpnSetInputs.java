package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import org.json.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class VpnSetInputs extends CustPanel {
        private MaskEditManager manager;
        private Map<String, Object> currentSettings;
        private static final Color DARK_COLOR = new Color(30, 30, 30);
        private static final Color LIGHT_COLOR = new Color(220, 220, 220);

        public VpnSetInputs(MaskEditManager manager) {
                super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
                this.manager = manager;
                initUI();
        }

        private void initUI() {
                currentSettings = manager.getSettingsForCategory("VPN");
                var combinedPanel = createInputsPanel(currentSettings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(Map<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);
                var row = 0;

                // General VPN Settings
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "General Settings", LIGHT_COLOR);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_list",
                                InputPanelUtils.jsonArrayToString(settings.get("output_vpn_service_list")),
                                420, 64, "VPN Services", LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_service",
                                settings.get("output_vpn_default_service"),
                                330, 32, "Default Service", LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_country",
                                settings.get("output_vpn_default_country"),
                                90, 3, "Default Country", LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_server",
                                settings.get("output_vpn_default_server"),
                                330, 32, "Default Server", LIGHT_COLOR,
                                manager, "VPN", currentSettings);

                // Individual VPN Settings
                String[] services = { "VPNServiceOne", "VPNServiceTwo", "VPNServiceThree" };
                for (var i = 0; i < services.length; i++) {
                        row = addVpnServiceSettings(panel, gbc, row, settings, services[i], i + 1);
                }

                return panel;
        }

        private int addVpnServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
                        Map<String, Object> settings, String serviceName, int serviceNumber) {
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "VPN " + serviceNumber + " Settings", LIGHT_COLOR);

                var serviceObj = settings.get(serviceName);
                if (!(serviceObj instanceof JSONObject)) {
                        System.err.println("Expected JSONObject for " + serviceName + ", but got " +
                                        (serviceObj != null ? serviceObj.getClass().getName() : "null"));
                        return row;
                }
                JSONObject vpnService = (JSONObject) serviceObj;
                var suffix = String.valueOf(serviceNumber);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix,
                                vpnService.optString("output_vpn_service_" + suffix),
                                330, 32, "VPN Service " + suffix, LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "service_expiration_date_vpn_" + suffix,
                                vpnService.optString("service_expiration_date_vpn_" + suffix),
                                210, 10, "MM/DD/YYYY", LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_countryCodes",
                                InputPanelUtils.jsonArrayToString(vpnService.optJSONArray(
                                                "output_vpn_service_" + suffix + "_list_countryCodes")),
                                420, 64, "Country Codes", LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_username",
                                vpnService.optString("output_vpn_service_" + suffix + "_username"),
                                420, 32, "Username", LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_password",
                                vpnService.optString("output_vpn_service_" + suffix + "_password"),
                                420, 32, "Password", LIGHT_COLOR,
                                manager, "VPN", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_servers",
                                InputPanelUtils.jsonArrayToString(vpnService.optJSONArray(
                                                "output_vpn_service_" + suffix + "_list_servers")),
                                420, 20898, "Servers", LIGHT_COLOR,
                                manager, "VPN", currentSettings);

                return row;
        }
}