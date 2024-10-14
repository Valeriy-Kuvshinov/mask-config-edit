package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import src.configuration.*;
import src.configuration.utilities.*;
import src.utilities.*;

public class VpnSetInputs extends CustPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public VpnSetInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        Map<String, Object> vpnSettings = MaskEditManager.getSettingsForCategory("VPN");
        var combinedPanel = createInputsPanel(vpnSettings);

        var centerPanel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(combinedPanel, centerGbc);

        add(centerPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private CustPanel createInputsPanel(Map<String, Object> vpnSettings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        var row = 0;

        // General VPN Settings
        addSectionHeader(panel, gbc, row++, "General Settings");

        addInputRow(panel, gbc, row++, "output_vpn_service_list",
                String.join(", ", (String[]) vpnSettings.get("output_vpn_service_list")),
                420, 64, "VPN Services");
        addInputRow(panel, gbc, row++, "output_vpn_default_service",
                vpnSettings.get("output_vpn_default_service"),
                330, 32, "Default Service");
        addInputRow(panel, gbc, row++, "output_vpn_default_country",
                vpnSettings.get("output_vpn_default_country"),
                90, 3, "Default Country");
        addInputRow(panel, gbc, row++, "output_vpn_default_server",
                vpnSettings.get("output_vpn_default_server"),
                330, 32, "Default Server");

        // Individual VPN Settings
        String[] vpnServices = { "VPNServiceOne", "VPNServiceTwo", "VPNServiceThree" };
        for (var i = 0; i < vpnServices.length; i++) {
            row = addVpnServiceSettings(panel, gbc, row, vpnSettings, vpnServices[i], i + 1);
        }

        return panel;
    }

    private int addVpnServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
            Map<String, Object> vpnSettings, String serviceName, int serviceNumber) {
        addSectionHeader(panel, gbc, row++, "VPN " + serviceNumber + " Settings");

        var vpnServiceObj = vpnSettings.get(serviceName);
        if (!(vpnServiceObj instanceof Map)) {
            System.err.println("Expected Map for " + serviceName + ", but got " +
                    (vpnServiceObj != null ? vpnServiceObj.getClass().getName() : "null"));
            return row;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> vpnService = (Map<String, Object>) vpnServiceObj;
        var suffix = String.valueOf(serviceNumber);

        addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix,
                vpnService.get("output_vpn_service_" + suffix),
                330, 32, "VPN Service " + suffix);
        addInputRow(panel, gbc, row++, "service_expiration_date_vpn_" + suffix,
                vpnService.get("service_expiration_date_vpn_" + suffix),
                210, 10, "MM/DD/YYYY");
        addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_countryCodes",
                InputPanelUtils.joinStringArray(vpnService.get("output_vpn_service_" + suffix + "_list_countryCodes")),
                420, 64, "Country Codes");
        addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_username",
                vpnService.get("output_vpn_service_" + suffix + "_username"),
                420, 32, "Username");
        addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_password",
                vpnService.get("output_vpn_service_" + suffix + "_password"),
                420, 32, "Password");
        addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_servers",
                InputPanelUtils.joinStringArray(vpnService.get("output_vpn_service_" + suffix + "_list_servers")),
                420, 20898, "Servers");

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