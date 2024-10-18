package src.configuration.inputs;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class ProxySetInputs extends CustPanel {
        private MaskEditManager manager;
        private LinkedHashMap<String, Object> currentSettings;

        public ProxySetInputs(MaskEditManager manager) {
                super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 15, 15);
                this.manager = manager;
                initUI();
        }

        private void initUI() {
                currentSettings = manager.getSettingsForCategory("Proxy");
                var combinedPanel = createInputsPanel(currentSettings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(LinkedHashMap<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);
                var row = 0;

                // General Proxy Settings
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "General Settings", ColorPalette.LIGHT_ONE);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_service",
                                InputPanelUtils.jsonArrayToString(settings.get("output_proxy_list_service")),
                                420, 64, "Proxy Services", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_default_service",
                                settings.get("output_proxy_default_service"),
                                330, 32, "Default Service", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_default_country",
                                settings.get("output_proxy_default_country"),
                                90, 2, "Default Country", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_default_server",
                                settings.get("output_proxy_default_server"),
                                330, 32, "Default Server", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);

                // Individual Proxy Settings
                String[] services = { "ProxyServiceOne", "ProxyServiceTwo", "ProxyServiceThree" };
                for (var i = 0; i < services.length; i++) {
                        row = addProxyServiceSettings(panel, gbc, row, settings, services[i], i + 1);
                }

                return panel;
        }

        private int addProxyServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
                        LinkedHashMap<String, Object> settings, String serviceName, int serviceNumber) {
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "Proxy " + serviceNumber + " Settings",
                                ColorPalette.LIGHT_ONE);

                var proxyServiceObj = settings.get(serviceName);
                if (!(proxyServiceObj instanceof LinkedHashMap)) {
                        System.err.println("Expected LinkedHashMap for " + serviceName + ", but got " +
                                        (proxyServiceObj != null ? proxyServiceObj.getClass().getName() : "null"));
                        return row;
                }
                @SuppressWarnings("unchecked")
                LinkedHashMap<String, Object> proxyService = (LinkedHashMap<String, Object>) proxyServiceObj;
                var suffix = String.valueOf(serviceNumber);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix,
                                proxyService.get("output_proxy_service_" + suffix),
                                420, 32, "Proxy Service " + suffix, ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "service_expiration_date_proxy_" + suffix,
                                proxyService.get("service_expiration_date_proxy_" + suffix),
                                210, 10, "MM/DD/YYYY", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_countryCodes_" + suffix,
                                InputPanelUtils.jsonArrayToString(
                                                proxyService.get("output_proxy_list_countryCodes_" + suffix)),
                                90, 2, "Country Codes", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_ips_" + suffix,
                                InputPanelUtils.jsonArrayToString(proxyService.get("output_proxy_list_ips_" + suffix)),
                                420, 64, "IPs", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_list_ports_" + suffix,
                                InputPanelUtils.jsonArrayToString(
                                                proxyService.get("output_proxy_list_ports_" + suffix)),
                                420, 64, "Ports", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix + "_username",
                                proxyService.get("output_proxy_service_" + suffix + "_username"),
                                420, 32, "Username", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix + "_password",
                                proxyService.get("output_proxy_service_" + suffix + "_password"),
                                420, 32, "Password", ColorPalette.LIGHT_ONE,
                                manager, "Proxy", currentSettings);

                return row;
        }
}