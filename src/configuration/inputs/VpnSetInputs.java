package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import org.json.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class VpnSetInputs extends CustPanel {
        private static final Color DARK_COLOR = new Color(30, 30, 30);
        private static final Color LIGHT_COLOR = new Color(220, 220, 220);

        public VpnSetInputs() {
                super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
                initUI();
        }

        private void initUI() {
                Map<String, Object> settings = MaskEditManager.getSettingsForCategory("VPN");
                var combinedPanel = createInputsPanel(settings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(Map<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);
                var row = 0;

                // General VPN Settings
                addSectionHeader(panel, gbc, row++, "General Settings");

                addInputRow(panel, gbc, row++, "output_vpn_service_list",
                                InputPanelUtils.jsonArrayToString(settings.get("output_vpn_service_list")),
                                420, 64, "VPN Services");
                addInputRow(panel, gbc, row++, "output_vpn_default_service",
                                settings.get("output_vpn_default_service"),
                                330, 32, "Default Service");
                addInputRow(panel, gbc, row++, "output_vpn_default_country",
                                settings.get("output_vpn_default_country"),
                                90, 3, "Default Country");
                addInputRow(panel, gbc, row++, "output_vpn_default_server",
                                settings.get("output_vpn_default_server"),
                                330, 32, "Default Server");

                // Individual VPN Settings
                String[] services = { "VPNServiceOne", "VPNServiceTwo", "VPNServiceThree" };
                for (var i = 0; i < services.length; i++) {
                        row = addVpnServiceSettings(panel, gbc, row, settings, services[i], i + 1);
                }

                return panel;
        }

        private int addVpnServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
                        Map<String, Object> settings, String serviceName, int serviceNumber) {
                addSectionHeader(panel, gbc, row++, "VPN " + serviceNumber + " Settings");

                var serviceObj = settings.get(serviceName);
                if (!(serviceObj instanceof JSONObject)) {
                        System.err.println("Expected JSONObject for " + serviceName + ", but got " +
                                        (serviceObj != null ? serviceObj.getClass().getName() : "null"));
                        return row;
                }
                JSONObject vpnService = (JSONObject) serviceObj;
                var suffix = String.valueOf(serviceNumber);

                addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix,
                                vpnService.optString("output_vpn_service_" + suffix),
                                330, 32, "VPN Service " + suffix);
                addInputRow(panel, gbc, row++, "service_expiration_date_vpn_" + suffix,
                                vpnService.optString("service_expiration_date_vpn_" + suffix),
                                210, 10, "MM/DD/YYYY");
                addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_countryCodes",
                                InputPanelUtils.jsonArrayToString(vpnService
                                                .optJSONArray("output_vpn_service_" + suffix + "_list_countryCodes")),
                                420, 64, "Country Codes");
                addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_username",
                                vpnService.optString("output_vpn_service_" + suffix + "_username"),
                                420, 32, "Username");
                addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_password",
                                vpnService.optString("output_vpn_service_" + suffix + "_password"),
                                420, 32, "Password");
                addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_servers",
                                InputPanelUtils.jsonArrayToString(vpnService
                                                .optJSONArray("output_vpn_service_" + suffix + "_list_servers")),
                                420, 20898, "Servers");

                return row;
        }

        private void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
                        int inputWidth, int maxChars, String placeholder) {
                InputPanelUtils.addInputRow(panel, gbc, row, key, value, inputWidth, maxChars, placeholder,
                                LIGHT_COLOR);
        }

        private void addSectionHeader(CustPanel panel, GridBagConstraints gbc, int row, String headerText) {
                InputPanelUtils.addSectionHeader(panel, gbc, row, headerText, LIGHT_COLOR);
        }
}