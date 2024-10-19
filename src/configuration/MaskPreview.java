package src.configuration;

import java.awt.*;
import javax.swing.*;

import src.configuration.bars.*;
import src.configuration.general.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class MaskPreview extends CustPanel {
    private String maskName;
    private Runnable onBackAction;
    private CustPanel topSection;
    private CustPanel bottomSection;
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
        // Create a panel to hold the topbar, middlebar, and separators
        topSection = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        topSection.add(new MaskEditTopbar(maskName));

        var separatorOne = new CustSeparator(new Color(100, 0, 150), 1);
        topSection.add(separatorOne);

        add(topSection, BorderLayout.NORTH);

        // Create the settings preview component
        var previewComponent = createPreviewComponent();

        // Wrap the preview component in a panel to center it
        var wrapperPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        wrapperPanel.add(previewComponent, new GridBagConstraints());

        // Create the scroll pane
        scrollPane = new CustScroll(wrapperPanel);
        scrollPane.getViewport().setBackground(ColorPalette.DARK_ONE);

        add(scrollPane, BorderLayout.CENTER);

        // Create a bottom section panel
        bottomSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);

        var separatorTwo = new CustSeparator(new Color(100, 0, 150), 1);
        bottomSection.add(separatorTwo, BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombarTwo(onBackAction), BorderLayout.CENTER);

        add(bottomSection, BorderLayout.SOUTH);
    }

    private CustPanel createPreviewComponent() {
        var previewComponent = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 15, 20, 20);
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 15);

        for (String category : MaskSettings.CATEGORY_ORDER) {
            var categorySettings = previewSettings.getCategory(category);
            if (categorySettings != null) {
                previewComponent.add(new CustLabel(category, ColorPalette.LIGHT_ONE, 20,
                        Component.LEFT_ALIGNMENT), gbc);
                gbc.gridy++;

                switch (category) {
                    case "System":
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "interfaceNames_input_usb", "Input_computer", "Input_cellular", "Input_secureIphone",
                                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb",
                                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
                        });
                        break;
                    case "Tor":
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_tor_list_countryCodes", "output_tor_list_exit_nodes",
                                "output_tor_default_country"
                        });
                        break;
                    case "VPN":
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_vpn_service_list", "output_vpn_default_service",
                                "output_vpn_default_country", "output_vpn_default_server"
                        });
                        addNestedVpnSettings(previewComponent, gbc, categorySettings);
                        break;
                    case "VPS":
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_vps_service", "service_expiration_date_vps", "output_vps_service_countryCode",
                                "output_vps_service_country", "output_vps_service_server",
                                "output_vps_service_preferred_transport",
                                "output_vps_service_username", "output_vps_service_password"
                        });
                        break;
                    case "Proxy":
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "output_proxy_list_service", "output_proxy_default_service",
                                "output_proxy_default_country", "output_proxy_default_server"
                        });
                        addNestedProxySettings(previewComponent, gbc, categorySettings);
                        break;
                    case "Hotspot":
                        addOrderedSettings(previewComponent, gbc, categorySettings, new String[] {
                                "inputInterface_hotspot_hidden", "inputInterface_hotspot_ssid_length",
                                "inputInterface_hotspot_password_length", "inputInterface_hotspot_ssid_policy",
                                "inputInterface_hotspot_password_policy", "inputInterface_hotspot_ssid",
                                "inputInterface_hotspot_password"
                        });
                        break;
                }
                gbc.gridy++;
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
            String service = vpnServices[i];
            var nestedSettings = (CategorySettings) settings.getSetting(service);
            panel.add(new CustLabel(service, ColorPalette.LIGHT_ONE, 18,
                    Component.LEFT_ALIGNMENT), gbc);
            gbc.gridy++;
            gbc.insets.left += 20;

            String serviceNumber = String.valueOf(i + 1);
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
        }
    }

    private void addNestedProxySettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        String[] proxyServices = { "ProxyServiceOne", "ProxyServiceTwo", "ProxyServiceThree" };
        for (int i = 0; i < proxyServices.length; i++) {
            String service = proxyServices[i];
            var nestedSettings = (CategorySettings) settings.getSetting(service);
            panel.add(new CustLabel(service, ColorPalette.LIGHT_ONE, 18,
                    Component.LEFT_ALIGNMENT), gbc);
            gbc.gridy++;
            gbc.insets.left += 20;

            String serviceNumber = String.valueOf(i + 1);
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
        }
    }
}