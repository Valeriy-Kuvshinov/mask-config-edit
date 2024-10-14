package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import src.configuration.*;
import src.configuration.utilities.*;
import src.utilities.*;

public class ProxySetInputs extends CustPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public ProxySetInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        Map<String, Object> proxySettings = MaskEditManager.getSettingsForCategory("Proxy");
        var combinedPanel = createInputsPanel(proxySettings);

        // Create a new panel to center the combinedPanel horizontally
        var centerPanel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(combinedPanel, centerGbc);

        add(centerPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private CustPanel createInputsPanel(Map<String, Object> proxySettings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        var row = 0;

        // General Proxy Settings
        addSectionHeader(panel, gbc, row++, "General Settings");

        addInputRow(panel, gbc, row++, "output_proxy_list_service",
                String.join(", ", (String[]) proxySettings.get("output_proxy_list_service")),
                420, 64, "Proxy Services");
        addInputRow(panel, gbc, row++, "output_proxy_default_service",
                proxySettings.get("output_proxy_default_service"),
                330, 32, "Default Service");
        addInputRow(panel, gbc, row++, "output_proxy_default_country",
                proxySettings.get("output_proxy_default_country"),
                90, 2, "Default Country");
        addInputRow(panel, gbc, row++, "output_proxy_default_server",
                proxySettings.get("output_proxy_default_server"),
                330, 32, "Default Server");

        // Individual Proxy Settings
        String[] proxyServices = { "ProxyServiceOne", "ProxyServiceTwo", "ProxyServiceThree" };
        for (var i = 0; i < proxyServices.length; i++) {
            row = addProxyServiceSettings(panel, gbc, row, proxySettings, proxyServices[i], i + 1);
        }

        return panel;
    }

    private int addProxyServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
            Map<String, Object> proxySettings, String serviceName, int serviceNumber) {
        addSectionHeader(panel, gbc, row++, "Proxy " + serviceNumber + " Settings");

        var proxyServiceObj = proxySettings.get(serviceName);
        if (!(proxyServiceObj instanceof Map)) {
            System.err.println("Expected Map for " + serviceName + ", but got " +
                    (proxyServiceObj != null ? proxyServiceObj.getClass().getName() : "null"));
            return row;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> proxyService = (Map<String, Object>) proxyServiceObj;
        var suffix = String.valueOf(serviceNumber);

        addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix,
                proxyService.get("output_proxy_service_" + suffix),
                420, 32, "Proxy Service " + suffix);
        addInputRow(panel, gbc, row++, "service_expiration_date_proxy_" + suffix,
                proxyService.get("service_expiration_date_proxy_" + suffix),
                210, 10, "MM/DD/YYYY");
        addInputRow(panel, gbc, row++, "output_proxy_list_countryCodes_" + suffix,
                InputPanelUtils.joinStringArray(proxyService.get("output_proxy_list_countryCodes_" + suffix)),
                90, 2, "Country Codes");
        addInputRow(panel, gbc, row++, "output_proxy_list_ips_" + suffix,
                InputPanelUtils.joinStringArray(proxyService.get("output_proxy_list_ips_" + suffix)),
                420, 64, "IPs");
        addInputRow(panel, gbc, row++, "output_proxy_list_ports_" + suffix,
                InputPanelUtils.joinStringArray(proxyService.get("output_proxy_list_ports_" + suffix)),
                420, 64, "Ports");
        addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix + "_username",
                proxyService.get("output_proxy_service_" + suffix + "_username"),
                420, 32, "Username");
        addInputRow(panel, gbc, row++, "output_proxy_service_" + suffix + "_password",
                proxyService.get("output_proxy_service_" + suffix + "_password"),
                420, 32, "Password");

        return row;
    }

    private void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int inputWidth, int maxChars, String placeholder) {
        InputPanelUtils.addInputRow(panel, gbc, row, key, value, inputWidth, maxChars, placeholder, LIGHT_COLOR);
    }

    private void addSectionHeader(CustPanel panel, GridBagConstraints gbc, int row, String headerText) {
        InputPanelUtils.addSectionHeader(panel, gbc, row, headerText, LIGHT_COLOR);
    }
}