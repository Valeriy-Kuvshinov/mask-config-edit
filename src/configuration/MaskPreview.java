package src.configuration;

import java.awt.*;

import src.configuration.bars.*;
import src.configuration.general.*;
import src.configuration.inputs.*;
import src.utilities.*;
import src.utilities.gui.*;

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
        initUI();
    }

    private void initUI() {
        add(createTopSection(), BorderLayout.NORTH);
        add(createScrollPane(createPreviewComponent()), BorderLayout.CENTER);
        add(createBottomSection(), BorderLayout.SOUTH);
    }

    private CustPanel createTopSection() {
        var topSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        topSection.add(new MaskEditTopbar(maskName), BorderLayout.NORTH);
        topSection.add(new CustSeparator(ColorPalette.BLUE_TWO, 1), BorderLayout.SOUTH);
        return topSection;
    }

    private CustScroll createScrollPane(CustPanel content) {
        var contentPanel = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        contentPanel.add(content, BorderLayout.CENTER);
        scrollPane = new CustScroll(contentPanel);
        scrollPane.getViewport().setBackground(ColorPalette.DARK_ONE);
        return scrollPane;
    }

    private CustPanel createBottomSection() {
        var bottomSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        bottomSection.add(new CustSeparator(ColorPalette.BLUE_TWO, 1), BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombarTwo(onBackAction), BorderLayout.CENTER);
        return bottomSection;
    }

    private CustPanel createPreviewComponent() {
        var previewComponent = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 15, 10, 20);
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 15);

        for (var category : MaskSettings.CATEGORY_ORDER) {
            var categorySettings = previewSettings.getCategory(category);
            if (categorySettings != null) {
                addCategorySettings(previewComponent, gbc, category, categorySettings);
                addEmptyLine(previewComponent, gbc);
            }
        }
        return previewComponent;
    }

    private void addCategorySettings(CustPanel panel, GridBagConstraints gbc, String category,
            CategorySettings settings) {
        switch (category) {
            case "System":
                addSystemSettings(panel, gbc, settings);
                break;
            case "Tor":
                addTorSettings(panel, gbc, settings);
                break;
            case "VPN":
                addVpnSettings(panel, gbc, settings);
                break;
            case "VPS":
                addVpsSettings(panel, gbc, settings);
                break;
            case "Proxy":
                addProxySettings(panel, gbc, settings);
                break;
            case "Hotspot":
                addHotspotSettings(panel, gbc, settings);
                break;
        }
    }

    private void addSystemSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        addSectionHeader(panel, gbc, "## Subsystem: 1 - enabled. 0 - disabled.", 20);
        addSectionHeader(panel, gbc, "# Inputs:", 18);
        addOrderedSettings(panel, gbc, settings, new String[] {
                "interfaceNames_input_usb", "Input_computer", "Input_cellular", "Input_secureIphone",
                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb"
        });
        addEmptyLine(panel, gbc);
        addSectionHeader(panel, gbc, "# Outputs:", 18);
        addOrderedSettings(panel, gbc, settings, new String[] {
                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
        });
    }

    private void addTorSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        addSectionHeader(panel, gbc, "## Subsystem: Anonymizers", 20);
        addSectionHeader(panel, gbc, "# Tor", 18);
        addOrderedSettings(panel, gbc, settings, new String[] {
                "output_tor_list_countryCodes", "output_tor_list_exit_nodes", "output_tor_default_country"
        });
    }

    private void addVpnSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        addSectionHeader(panel, gbc, "# VPN", 18);
        addOrderedSettings(panel, gbc, settings, new String[] {
                "output_vpn_service_list", "output_vpn_default_service",
                "output_vpn_default_country", "output_vpn_default_server"
        });
        for (var i = 1; i <= 3; i++) {
            addEmptyLine(panel, gbc);
            addSectionHeader(panel, gbc, "# VPN Service " + i, 18);
            addOrderedSettings(panel, gbc, settings, new String[] {
                    "output_vpn_service_" + i, "service_expiration_date_vpn_" + i,
                    "output_vpn_service_" + i + "_list_countryCodes", "output_vpn_service_" + i + "_list_servers",
                    "output_vpn_service_" + i + "_username", "output_vpn_service_" + i + "_password"
            });
        }
    }

    private void addVpsSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        addSectionHeader(panel, gbc, "# VPS", 18);
        addOrderedSettings(panel, gbc, settings, new String[] {
                "output_vps_service", "service_expiration_date_vps", "output_vps_service_countryCode",
                "output_vps_service_country", "output_vps_service_server", "output_vps_service_preferred_transport",
                "output_vps_service_username", "output_vps_service_password"
        });
    }

    private void addProxySettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        addSectionHeader(panel, gbc, "# Proxy", 18);
        addOrderedSettings(panel, gbc, settings, new String[] {
                "output_proxy_list_service", "output_proxy_default_service",
                "output_proxy_default_country", "output_proxy_default_server"
        });
        for (var i = 1; i <= 3; i++) {
            addEmptyLine(panel, gbc);
            addSectionHeader(panel, gbc, "# Proxy Service " + i, 18);
            addOrderedSettings(panel, gbc, settings, new String[] {
                    "output_proxy_service_" + i, "service_expiration_date_proxy_" + i,
                    "output_proxy_list_countryCodes_" + i, "output_proxy_list_ips_" + i,
                    "output_proxy_list_ports_" + i, "output_proxy_service_" + i + "_username",
                    "output_proxy_service_" + i + "_password"
            });
        }
    }

    private void addHotspotSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings) {
        addSectionHeader(panel, gbc, "# Hotspot", 18);
        addOrderedSettings(panel, gbc, settings, new String[] {
                "inputInterface_hotspot_hidden", "inputInterface_hotspot_ssid_length",
                "inputInterface_hotspot_password_length", "inputInterface_hotspot_ssid_policy",
                "inputInterface_hotspot_password_policy", "inputInterface_hotspot_ssid",
                "inputInterface_hotspot_password"
        });
    }

    private void addSectionHeader(CustPanel panel, GridBagConstraints gbc, String text, int fontSize) {
        panel.add(new CustLabel(text, ColorPalette.LIGHT_ONE, fontSize, Component.LEFT_ALIGNMENT), gbc);
        gbc.gridy++;
    }

    private void addOrderedSettings(CustPanel panel, GridBagConstraints gbc, CategorySettings settings, String[] keys) {
        for (var key : keys) {
            var value = settings.getSetting(key);
            var stringValue = InputPanelUtils.jsonArrayToString(value);
            panel.add(new CustLabel(key + ": " + stringValue,
                    ColorPalette.LIGHT_ONE, 16, Component.LEFT_ALIGNMENT), gbc);
            gbc.gridy++;
        }
    }

    private void addEmptyLine(CustPanel panel, GridBagConstraints gbc) {
        panel.add(new CustLabel(" ", ColorPalette.LIGHT_ONE, 16, Component.LEFT_ALIGNMENT), gbc);
        gbc.gridy++;
    }
}