package src.configuration.inputs;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class VpsSetInputs extends CustPanel {
        private static final Color DARK_COLOR = new Color(30, 30, 30);
        private static final Color LIGHT_COLOR = new Color(220, 220, 220);

        public VpsSetInputs() {
                super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
                initUI();
        }

        private void initUI() {
                Map<String, Object> settings = MaskEditManager.getSettingsForCategory("VPS");
                var combinedPanel = createInputsPanel(settings);
                InputPanelUtils.initCommonUI(this, combinedPanel);
        }

        private CustPanel createInputsPanel(Map<String, Object> settings) {
                var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);

                InputPanelUtils.addSectionHeader(panel, gbc, 0, "General Settings", LIGHT_COLOR);

                addInputRow(panel, gbc, 1, "output_vps_service", settings.get("output_vps_service"),
                                330, 32, "VPS Service");
                addInputRow(panel, gbc, 2, "service_expiration_date_vps", settings.get("service_expiration_date_vps"),
                                330, 10, "MM/DD/YYYY");
                addInputRow(panel, gbc, 3, "output_vps_service_countryCode",
                                settings.get("output_vps_service_countryCode"),
                                90, 2, "Country Code");
                addInputRow(panel, gbc, 4, "output_vps_service_country",
                                settings.get("output_vps_service_country"),
                                330, 32, "Country");
                addInputRow(panel, gbc, 5, "output_vps_service_server", settings.get("output_vps_service_server"),
                                330, 32, "Server");
                addSelectRow(panel, gbc, 6, "output_vps_service_preferred_transport",
                                settings.get("output_vps_service_preferred_transport"),
                                112, new String[] { "tcp", "udp" });
                addInputRow(panel, gbc, 7, "output_vps_service_username",
                                settings.get("output_vps_service_username"),
                                330, 32, "Username");
                addInputRow(panel, gbc, 8, "output_vps_service_password",
                                settings.get("output_vps_service_password"),
                                330, 32, "Password");

                return panel;
        }

        private void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
                        int inputWidth, int maxChars, String placeholder) {
                InputPanelUtils.addInputRow(panel, gbc, row, key, value, inputWidth, maxChars, placeholder,
                                LIGHT_COLOR);
        }

        private void addSelectRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
                        int selectWidth, String[] options) {
                InputPanelUtils.addSelectRow(panel, gbc, row, key, value, selectWidth, options, LIGHT_COLOR);
        }
}