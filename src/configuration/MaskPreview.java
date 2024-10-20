package src.configuration;

import java.awt.*;

import src.configuration.bars.*;
import src.configuration.general.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class MaskPreview extends CustPanel {
    private String maskName;
    private Runnable onBackAction;
    private CustScroll scrollPane;
    private MaskSettings previewSettings;

    public MaskPreview(String maskName, MaskSettings previewSettings, Runnable onBackAction) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        this.maskName = maskName;
        this.previewSettings = previewSettings;
        this.onBackAction = onBackAction;
        initializeUI();
    }

    private void initializeUI() {
        // Create a panel to hold the topbar and separators
        var topSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        topSection.add(new MaskEditTopbar(maskName), BorderLayout.NORTH);

        var separatorOne = new CustSeparator(ColorPalette.BLUE_TWO, 1);
        topSection.add(separatorOne, BorderLayout.SOUTH);

        add(topSection, BorderLayout.NORTH);

        // Create the settings preview component
        var previewComponent = createPreviewComponent();

        // Create a panel to hold the previewComponent
        var contentPanel = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        contentPanel.add(previewComponent, BorderLayout.CENTER);

        // Create the scroll pane
        scrollPane = new CustScroll(contentPanel);
        scrollPane.getViewport().setBackground(ColorPalette.DARK_ONE);

        add(scrollPane, BorderLayout.CENTER);

        // Create a bottom section panel
        var bottomSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);

        var separatorTwo = new CustSeparator(ColorPalette.BLUE_TWO, 1);
        bottomSection.add(separatorTwo, BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombarTwo(onBackAction), BorderLayout.CENTER);

        add(bottomSection, BorderLayout.SOUTH);
    }

    private CustPanel createPreviewComponent() {
        var previewComponent = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 15, 10, 20);
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 15);

        for (String category : MaskSettings.CATEGORY_ORDER) {
            var categorySettings = previewSettings.getCategory(category);
            if (categorySettings != null) {
                gbc.gridy++;

                switch (category) {
                    case "System":
                        previewComponent.add(new CustLabel("## Subsystem: 1 - enabled. 0 - disabled.",
                                ColorPalette.LIGHT_ONE, 20, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;

                        previewComponent.add(new CustLabel("# Inputs:",
                                ColorPalette.LIGHT_ONE, 18, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "interfaceNames_input_usb", "Input_computer", "Input_cellular", "Input_secureIphone",
                                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb"
                        });

                        addEmptyLine(previewComponent, gbc);

                        previewComponent.add(new CustLabel("# Outputs:",
                                ColorPalette.LIGHT_ONE, 18, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
                        });
                        break;
                    case "Tor":
                        previewComponent.add(new CustLabel("## Subsystem: Anonymizers",
                                ColorPalette.LIGHT_ONE, 20, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        previewComponent.add(new CustLabel("# Tor",
                                ColorPalette.LIGHT_ONE, 18, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_tor_list_countryCodes", "output_tor_list_exit_nodes",
                                "output_tor_default_country"
                        });
                        break;
                    case "VPN":
                        previewComponent.add(new CustLabel("# VPN",
                                ColorPalette.LIGHT_ONE, 18, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_vpn_service_list", "output_vpn_default_service",
                                "output_vpn_default_country", "output_vpn_default_server"
                        });
                        addNestedVpnSettings(previewComponent, gbc, categorySettings);
                        break;
                    case "VPS":
                        previewComponent.add(new CustLabel("# VPS",
                                ColorPalette.LIGHT_ONE, 18, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_vps_service", "service_expiration_date_vps", "output_vps_service_countryCode",
                                "output_vps_service_country", "output_vps_service_server",
                                "output_vps_service_preferred_transport",
                                "output_vps_service_username", "output_vps_service_password"
                        });
                        break;
                    case "Proxy":
                        previewComponent.add(new CustLabel("# Proxy",
                                ColorPalette.LIGHT_ONE, 18, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_proxy_list_service", "output_proxy_default_service",
                                "output_proxy_default_country", "output_proxy_default_server"
                        });
                        addNestedProxySettings(previewComponent, gbc, categorySettings);
                        break;
                    case "Hotspot":
                        previewComponent.add(new CustLabel("# Hotspot",
                                ColorPalette.LIGHT_ONE, 18, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "inputInterface_hotspot_hidden", "inputInterface_hotspot_ssid_length",
                                "inputInterface_hotspot_password_length", "inputInterface_hotspot_ssid_policy",
                                "inputInterface_hotspot_password_policy", "inputInterface_hotspot_ssid",
                                "inputInterface_hotspot_password"
                        });
                        break;
                }
                addEmptyLine(previewComponent, gbc);
            }
        }
        return previewComponent;
    }

    private void addOrderedSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings, String[] keys) {
        for (String key : keys) {
            var value = settings.getSetting(key);
            var stringValue = InputPanelUtils.jsonArrayToString(value);
            panel.add(new CustLabel(key + ": " + stringValue,
                    ColorPalette.LIGHT_ONE, 16, Component.LEFT_ALIGNMENT), gbc);
            gbc.gridy++;
        }
    }

    private void addNestedVpnSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        String[] vpnServices = { "VPNServiceOne", "VPNServiceTwo", "VPNServiceThree" };
        for (int i = 0; i < vpnServices.length; i++) {
            var nestedSettings = (CategorySettings) settings.getSetting(vpnServices[i]);

            if (i == 0) {
                addEmptyLine(panel, gbc);
            }

            panel.add(new CustLabel("# VPN service " + (i + 1) + ":", ColorPalette.LIGHT_ONE, 18,
                    Component.LEFT_ALIGNMENT), gbc);
            gbc.gridy++;
            gbc.insets.left += 20;

            var serviceNumber = String.valueOf(i + 1);
            String[] serviceKeys = {
                    "output_vpn_service_" + serviceNumber,
                    "service_expiration_date_vpn_" + serviceNumber,
                    "output_vpn_service_" + serviceNumber + "_list_countryCodes",
                    "output_vpn_service_" + serviceNumber + "_list_servers",
                    "output_vpn_service_" + serviceNumber + "_username",
                    "output_vpn_service_" + serviceNumber + "_password"
            };
            addOrderedSettings(panel, gbc, nestedSettings, serviceKeys);

            gbc.insets.left -= 20;

            if (i < vpnServices.length - 1) {
                addEmptyLine(panel, gbc);
            }
        }
    }

    private void addNestedProxySettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        String[] proxyServices = { "ProxyServiceOne", "ProxyServiceTwo", "ProxyServiceThree" };
        for (int i = 0; i < proxyServices.length; i++) {
            var nestedSettings = (CategorySettings) settings.getSetting(proxyServices[i]);

            if (i == 0) {
                addEmptyLine(panel, gbc);
            }

            panel.add(new CustLabel("# Proxy service " + (i + 1) + ":", ColorPalette.LIGHT_ONE, 18,
                    Component.LEFT_ALIGNMENT), gbc);
            gbc.gridy++;
            gbc.insets.left += 20;

            var serviceNumber = String.valueOf(i + 1);
            String[] serviceKeys = {
                    "output_proxy_service_" + serviceNumber,
                    "service_expiration_date_proxy_" + serviceNumber,
                    "output_proxy_list_countryCodes_" + serviceNumber,
                    "output_proxy_list_ips_" + serviceNumber,
                    "output_proxy_list_ports_" + serviceNumber,
                    "output_proxy_service_" + serviceNumber + "_username",
                    "output_proxy_service_" + serviceNumber + "_password"
            };
            addOrderedSettings(panel, gbc, nestedSettings, serviceKeys);

            gbc.insets.left -= 20;

            if (i < proxyServices.length - 1) {
                addEmptyLine(panel, gbc);
            }
        }
    }

    private void addEmptyLine(CustPanel panel, GridBagConstraints gbc) {
        panel.add(new CustLabel(" ", ColorPalette.LIGHT_ONE, 16, Component.LEFT_ALIGNMENT), gbc);
        gbc.gridy++;
    }
}