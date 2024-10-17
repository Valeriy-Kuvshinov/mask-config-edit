package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import org.json.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class ProxySetInputs extends CustPanel {
        private MaskEditManager manager;
        private Map<String, Object> currentSettings;
        private static final Color DARK_COLOR = new Color(30, 30, 30);
        private static final Color LIGHT_COLOR = new Color(220, 220, 220);

        public ProxySetInputs(MaskEditManager manager) {
                super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
                this.manager = manager;
                initUI();
        }

        private void initUI() {
                currentSettings = manager.getSettingsForCategory("Proxy");
                var combinedPanel = createInputsPanel(currentSettings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(Map<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);
                var row = 0;

                // General Proxy Settings
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "General Settings", LIGHT_COLOR);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_service",
                                InputPanelUtils.jsonArrayToString(settings.get("output_proxy_list_service")),
                                420, 64, "Proxy Services", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_default_service",
                                settings.get("output_proxy_default_service"),
                                330, 32, "Default Service", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_default_country",
                                settings.get("output_proxy_default_country"),
                                90, 2, "Default Country", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_default_server",
                                settings.get("output_proxy_default_server"),
                                330, 32, "Default Server", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);

                // Individual Proxy Settings
                String[] services = { "ProxyServiceOne", "ProxyServiceTwo", "ProxyServiceThree" };
                for (var i = 0; i < services.length; i++) {
                        row = addProxyServiceSettings(panel, gbc, row, settings, services[i], i + 1);
                }

                return panel;
        }

        private int addProxyServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
                        Map<String, Object> settings, String serviceName, int serviceNumber) {
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "Proxy " + serviceNumber + " Settings",
                                LIGHT_COLOR);

                var proxyServiceObj = settings.get(serviceName);
                if (!(proxyServiceObj instanceof JSONObject)) {
                        System.err.println("Expected JSONObject for " + serviceName + ", but got " +
                                        (proxyServiceObj != null ? proxyServiceObj.getClass().getName() : "null"));
                        return row;
                }
                JSONObject proxyService = (JSONObject) proxyServiceObj;
                var suffix = String.valueOf(serviceNumber);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix,
                                proxyService.optString("output_proxy_service_" + suffix),
                                420, 32, "Proxy Service " + suffix, LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "service_expiration_date_proxy_" + suffix,
                                proxyService.optString("service_expiration_date_proxy_" + suffix),
                                210, 10, "MM/DD/YYYY", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_countryCodes_" + suffix,
                                InputPanelUtils.jsonArrayToString(proxyService.optJSONArray(
                                                "output_proxy_list_countryCodes_" + suffix)),
                                90, 2, "Country Codes", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_ips_" + suffix,
                                InputPanelUtils.jsonArrayToString(proxyService.optJSONArray(
                                                "output_proxy_list_ips_" + suffix)),
                                420, 64, "IPs", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_ports_" + suffix,
                                InputPanelUtils.jsonArrayToString(proxyService.optJSONArray(
                                                "output_proxy_list_ports_" + suffix)),
                                420, 64, "Ports", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix + "_username",
                                proxyService.optString("output_proxy_service_" + suffix + "_username"),
                                420, 32, "Username", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix + "_password",
                                proxyService.optString("output_proxy_service_" + suffix + "_password"),
                                420, 32, "Password", LIGHT_COLOR,
                                manager, "Proxy", currentSettings);

                return row;
        }
}