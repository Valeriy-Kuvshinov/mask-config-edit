package src.configuration.inputs;

import java.awt.*;

import src.configuration.*;
import src.configuration.general.*;
import src.utilities.*;
import src.utilities.gui.*;

public class VpnSetInputs extends CustPanel {
        private MaskEditManager manager;
        private CategorySettings currentSettings;

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

        private CustPanel createInputsPanel(CategorySettings settings) {
                var panel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);
                var row = 0;

                // General VPN Settings
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "General Settings", ColorPalette.LIGHT_ONE);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_list",
                                InputPanelUtils.jsonArrayToString(settings.getSetting("output_vpn_service_list")),
                                420, 64, "VPN Services", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_service",
                                settings.getSetting("output_vpn_default_service"),
                                330, 32, "Default Service", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_country",
                                settings.getSetting("output_vpn_default_country"),
                                90, 3, "Default Country", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_default_server",
                                settings.getSetting("output_vpn_default_server"),
                                330, 32, "Default Server", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);

                // Individual VPN Settings
                String[] services = { "VPNServiceOne", "VPNServiceTwo", "VPNServiceThree" };
                for (var i = 0; i < services.length; i++) {
                        row = addServiceSettings(panel, gbc, row, settings, services[i], i + 1);
                }

                return panel;
        }

        private int addServiceSettings(CustPanel panel, GridBagConstraints gbc, int row,
                        CategorySettings settings, String serviceName, int serviceNumber) {
                InputPanelUtils.addSectionHeader(panel, gbc, row++, "VPN " + serviceNumber + " Settings",
                                ColorPalette.LIGHT_ONE);

                var suffix = String.valueOf(serviceNumber);

                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix,
                                settings.getSetting("output_vpn_service_" + suffix),
                                330, 32, "VPN Service " + suffix, ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "service_expiration_date_vpn_" + suffix,
                                settings.getSetting("service_expiration_date_vpn_" + suffix),
                                210, 10, "MM/DD/YYYY", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_countryCodes",
                                InputPanelUtils.jsonArrayToString(settings.getSetting(
                                                "output_vpn_service_" + suffix + "_list_countryCodes")),
                                420, 64, "Country Codes", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_username",
                                settings.getSetting("output_vpn_service_" + suffix + "_username"),
                                420, 32, "Username", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_password",
                                settings.getSetting("output_vpn_service_" + suffix + "_password"),
                                420, 32, "Password", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);
                InputPanelUtils.addInputRow(panel, gbc, row++, "output_vpn_service_" + suffix + "_list_servers",
                                InputPanelUtils.jsonArrayToString(settings.getSetting(
                                                "output_vpn_service_" + suffix + "_list_servers")),
                                420, 20898, "Servers", ColorPalette.LIGHT_ONE,
                                manager, "VPN", null);

                return row;
        }
}