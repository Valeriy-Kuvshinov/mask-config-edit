package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import src.configuration.*;
import src.configuration.utilities.*;
import src.utilities.*;

public class VpsSetInputs extends CustPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public VpsSetInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        Map<String, Object> vpsSettings = MaskEditManager.getSettingsForCategory("VPS");
        var combinedPanel = createInputsPanel(vpsSettings);

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

    private CustPanel createInputsPanel(Map<String, Object> vpsSettings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        addInputRow(panel, gbc, 0, "output_vps_service", vpsSettings.get("output_vps_service"), 330, 32, "VPS Service");
        addInputRow(panel, gbc, 1, "service_expiration_date_vps", vpsSettings.get("service_expiration_date_vps"), 330,
                10, "MM/DD/YYYY");
        addInputRow(panel, gbc, 2, "output_vps_service_countryCode", vpsSettings.get("output_vps_service_countryCode"),
                90, 2, "Country Code");
        addInputRow(panel, gbc, 3, "output_vps_service_country", vpsSettings.get("output_vps_service_country"), 330, 32,
                "Country");
        addInputRow(panel, gbc, 4, "output_vps_service_server", vpsSettings.get("output_vps_service_server"), 330, 32,
                "Server");
        addSelectRow(panel, gbc, 5, "output_vps_service_preferred_transport",
                vpsSettings.get("output_vps_service_preferred_transport"), 112, new String[] { "tcp", "udp" });
        addInputRow(panel, gbc, 6, "output_vps_service_username", vpsSettings.get("output_vps_service_username"), 330,
                32, "Username");
        addInputRow(panel, gbc, 7, "output_vps_service_password", vpsSettings.get("output_vps_service_password"), 330,
                32, "Password");

        return panel;
    }

    private void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int inputWidth, int maxChars, String placeholder) {
        InputPanelUtils.addInputRow(panel, gbc, row, key, value, inputWidth, maxChars, placeholder, LIGHT_COLOR);
    }

    private void addSelectRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int selectWidth, String[] options) {
        InputPanelUtils.addSelectRow(panel, gbc, row, key, value, selectWidth, options, LIGHT_COLOR);
    }
}