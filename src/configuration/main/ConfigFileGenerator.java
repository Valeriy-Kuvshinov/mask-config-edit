package src.configuration.main;

import java.io.*;
import java.nio.file.*;

import src.configuration.general.*;
import src.configuration.inputs.*;

public class ConfigFileGenerator {
    private final MaskSettings settings;
    private final String maskName;

    public ConfigFileGenerator(MaskSettings settings, String maskName) {
        this.settings = settings;
        this.maskName = maskName;
    }

    private void addCategoryToConfig(StringBuilder config, String category, CategorySettings settings) {
        switch (category) {
            case "System":
                addSystemConfig(config, settings);
                break;
            case "Tor":
                addTorConfig(config, settings);
                break;
            case "VPN":
                addVpnConfig(config, settings);
                break;
            case "VPS":
                addVpsConfig(config, settings);
                break;
            case "Proxy":
                addProxyConfig(config, settings);
                break;
            case "Hotspot":
                addHotspotConfig(config, settings);
                break;
        }
        config.append("\n");
    }

    // Add similar methods for each category as in MaskPreview
    private void addSystemConfig(StringBuilder config, CategorySettings settings) {
        config.append("## Subsystem: 1 - enabled. 0 - disabled.\n");
        config.append("# Inputs:\n");
        addOrderedSettings(config, settings, new String[] {
                "interfaceNames_input_usb", "Input_computer", "Input_cellular", "Input_secureIphone",
                "InputInterface_ethernet", "InputInterface_wifi", "InputInterface_usb"
        });
        config.append("\n# Outputs:\n");
        addOrderedSettings(config, settings, new String[] {
                "OutputInterface_ethernet", "OutputInterface_wifi", "OutputInterface_cellularModem",
                "Output_tor", "Output_proxy", "Output_vpn", "Output_vps", "Output_force_tor"
        });
    }

    private void addTorConfig(StringBuilder config, CategorySettings settings) {
        config.append("\n## Subsystem: Anonymizers\n");
        config.append("\n# Tor:\n");
        addOrderedSettings(config, settings, new String[] {
                "output_tor_list_countryCodes", "output_tor_list_exit_nodes", "output_tor_default_country"
        });
    }

    private void addVpnConfig(StringBuilder config, CategorySettings settings) {
        config.append("# VPN\n");
        addOrderedSettings(config, settings, new String[] {
                "output_vpn_service_list", "output_vpn_default_service",
                "output_vpn_default_country", "output_vpn_default_server"
        });
        // VPN Service configurations (1-3)
        for (int i = 1; i <= 3; i++) {
            config.append("\n# VPN Service ").append(i).append("\n");
            addOrderedSettings(config, settings, new String[] {
                    "output_vpn_service_" + i, "service_expiration_date_vpn_" + i,
                    "output_vpn_service_" + i + "_list_countryCodes", "output_vpn_service_" + i + "_username",
                    "output_vpn_service_" + i + "_password", "output_vpn_service_" + i + "_list_servers"
            });
        }
    }

    private void addVpsConfig(StringBuilder config, CategorySettings settings) {
        config.append("# VPS\n");
        addOrderedSettings(config, settings, new String[] {
                "output_vps_service", "service_expiration_date_vps",
                "output_vps_service_countryCode", "output_vps_service_country",
                "output_vps_service_server", "output_vps_service_preferred_transport",
                "output_vps_service_username", "output_vps_service_password"
        });
    }

    private void addProxyConfig(StringBuilder config, CategorySettings settings) {
        config.append("# Proxy\n");
        addOrderedSettings(config, settings, new String[] {
                "output_proxy_list_service", "output_proxy_default_service",
                "output_proxy_default_country", "output_proxy_default_server"
        });
        // Proxy Service configurations (1-3)
        for (int i = 1; i <= 3; i++) {
            config.append("\n# Proxy Service ").append(i).append("\n");
            addOrderedSettings(config, settings, new String[] {
                    "output_proxy_service_" + i, "service_expiration_date_proxy_" + i,
                    "output_proxy_list_countryCodes_" + i, "output_proxy_list_ips_" + i,
                    "output_proxy_list_ports_" + i, "output_proxy_service_" + i + "_username",
                    "output_proxy_service_" + i + "_password"
            });
        }
    }

    private void addHotspotConfig(StringBuilder config, CategorySettings settings) {
        config.append("# Hotspot:\n");
        addOrderedSettings(config, settings, new String[] {
                "inputInterface_hotspot_hidden", "inputInterface_hotspot_ssid_length",
                "inputInterface_hotspot_password_length", "inputInterface_hotspot_ssid_policy",
                "inputInterface_hotspot_password_policy", "inputInterface_hotspot_ssid",
                "inputInterface_hotspot_password"
        });
    }

    private void addOrderedSettings(StringBuilder config, CategorySettings settings, String[] keys) {
        for (var key : keys) {
            var value = settings.getSetting(key);
            var stringValue = InputPanelUtils.jsonArrayToString(value);
            config.append(key).append(": ").append(stringValue).append("\n");
        }
    }

    public int getConfigSizeInBytes() {
        return generateConfig().getBytes().length;
    }

    public double getConfigSizeInKB() {
        return Math.round((getConfigSizeInBytes() / 1024.0) * 10.0) / 10.0;
    }

    public String generateConfig() {
        var config = new StringBuilder();

        for (var category : MaskSettings.CATEGORY_ORDER) {
            var categorySettings = settings.getCategory(category);
            if (categorySettings != null) {
                addCategoryToConfig(config, category, categorySettings);
            }
        }
        return config.toString();
    }

    public void saveToFile(String path) throws IOException {
        var config = generateConfig();
        var filePath = path + File.separator + maskName + ".cfg";
        Files.writeString(Path.of(filePath), config);
    }
}