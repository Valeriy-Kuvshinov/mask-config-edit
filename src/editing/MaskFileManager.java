package src.editing;

import java.io.*;
import java.util.*;

public class MaskFileManager {
    public static final Map<String, Map<String, Object>> DEFAULT_SETTINGS = new HashMap<>();

    static {
        // General System settings. 1 - enabled. 0 - disabled
        Map<String, Object> generalSystemSettings = new HashMap<>();
        // Input settings
        generalSystemSettings.put("Input_computer", 1);
        generalSystemSettings.put("Input_cellular", 1);
        generalSystemSettings.put("Input_secureIphone", 1);
        generalSystemSettings.put("InputInterface_ethernet", 1);
        generalSystemSettings.put("InputInterface_wifi", 1);
        generalSystemSettings.put("InputInterface_usb", 1);
        // Enforce the specific device
        generalSystemSettings.put("interfaceNames_input_usb", "227c369a1f027ece");
        // Output settings
        generalSystemSettings.put("OutputInterface_ethernet", 1);
        generalSystemSettings.put("OutputInterface_wifi", 1);
        generalSystemSettings.put("OutputInterface_cellularModem", 1);
        generalSystemSettings.put("Output_tor", 1);
        generalSystemSettings.put("Output_proxy", 1);
        generalSystemSettings.put("Output_vpn", 1);
        generalSystemSettings.put("Output_vps", 1);
        generalSystemSettings.put("Output_force_tor", 0);

        DEFAULT_SETTINGS.put("System", generalSystemSettings);

        // Subsystem: Anonymizers
        // Tor settings
        Map<String, Object> torSettings = new HashMap<>();
        torSettings.put("output_tor_list_countryCodes",
                new String[] { "at", "ch", "de", "fr", "nl", "no", "ro", "se" });
        torSettings.put("output_tor_list_exit_nodes", new String[] {});
        torSettings.put("output_tor_default_country", 99);

        DEFAULT_SETTINGS.put("Tor", torSettings);

        // VPN general settings
        Map<String, List<String>> fullVpnServersList = readVpnServersJson("/resources/json/vpn_servers_list.json");
        Map<String, Object> vpnSettings = new HashMap<>();
        vpnSettings.put("output_vpn_service_list", new String[] { "nordvpn", "protonvpn", "expressvpn" });
        vpnSettings.put("output_vpn_default_service", "nordvpn");
        vpnSettings.put("output_vpn_default_country", "any");
        vpnSettings.put("output_vpn_default_server", "any");

        // VPN 1 settings
        Map<String, Object> vpnServiceOneSettings = new HashMap<>();
        vpnServiceOneSettings.put("output_vpn_service_1", "nordvpn");
        vpnServiceOneSettings.put("service_expiration_date_vpn_1", "01/01/2025");
        vpnServiceOneSettings.put("output_vpn_service_1_list_countryCodes",
                new String[] { "at", "ch", "cz", "de", "es", "fr", "it", "nl", "pl", "ro", "uk", "se" });
        vpnServiceOneSettings.put("output_vpn_service_1_username", "eEX9A2ZxKd3kZcGYeepccFEg");
        vpnServiceOneSettings.put("output_vpn_service_1_password", "emJTGgyviYQduLUwvSZD6xk1");
        vpnServiceOneSettings.put("output_vpn_service_1_list_servers", fullVpnServersList.get("nordvpn"));

        // VPN 2 settings
        Map<String, Object> vpnServiceTwoSettings = new HashMap<>();
        vpnServiceTwoSettings.put("output_vpn_service_2", "protonvpn");
        vpnServiceTwoSettings.put("service_expiration_date_vpn_2", "02/02/2024");
        vpnServiceTwoSettings.put("output_vpn_service_2_list_countryCodes",
                new String[] { "at", "ch", "cz", "de", "es", "fr", "it", "nl", "pl", "ro", "se" });
        vpnServiceTwoSettings.put("output_vpn_service_2_username", "3gMlEux4oskmvYFX");
        vpnServiceTwoSettings.put("output_vpn_service_2_password", "n5oBE6FcFn12NlLoi9d7Ep9Vb0R10PJN");
        vpnServiceTwoSettings.put("output_vpn_service_2_list_servers", fullVpnServersList.get("protonvpn"));

        // VPN 3 settings
        Map<String, Object> vpnServiceThreeSettings = new HashMap<>();
        vpnServiceThreeSettings.put("output_vpn_service_3", "expressvpn");
        vpnServiceThreeSettings.put("service_expiration_date_vpn_3", "03/03/2025");
        vpnServiceThreeSettings.put("output_vpn_service_3_list_countryCodes",
                new String[] { "at", "ch", "cz", "de", "es", "fr", "hu", "it", "nl", "pt", "se" });
        vpnServiceThreeSettings.put("output_vpn_service_3_username", "tk5zulngd3ylvuyczvykl1bh");
        vpnServiceThreeSettings.put("output_vpn_service_3_password", "javcbkhkijp46wdkn7xapmqv");
        vpnServiceThreeSettings.put("output_vpn_service_3_list_servers", fullVpnServersList.get("expressvpn"));

        // Add VPN services settings to the main VPN settings
        vpnSettings.put("VPNServiceOne", vpnServiceOneSettings);
        vpnSettings.put("VPNServiceTwo", vpnServiceTwoSettings);
        vpnSettings.put("VPNServiceThree", vpnServiceThreeSettings);

        DEFAULT_SETTINGS.put("VPN", vpnSettings);

        // VPS settings
        Map<String, Object> vpsSettings = new HashMap<>();
        vpsSettings.put("output_vps_service", "awsmicrovps");
        vpsSettings.put("service_expiration_date_vps", "01/01/2024");
        vpsSettings.put("output_vps_service_countryCode", "fr");
        vpsSettings.put("output_vps_service_country", "France");
        vpsSettings.put("output_vps_service_server", "fr15.188.51.172");
        // TCP or UDP
        vpsSettings.put("output_vps_service_preferred_transport", "tcp");
        vpsSettings.put("output_vps_service_username", "user");
        vpsSettings.put("output_vps_service_password", "password");

        DEFAULT_SETTINGS.put("VPS", vpsSettings);

        // Proxy settings
        Map<String, Object> proxySettings = new HashMap<>();
        proxySettings.put("output_proxy_list_service", new String[] { "HighProxy1", "HighProxy2" });
        proxySettings.put("output_proxy_default_service", "HighProxy1");
        proxySettings.put("output_proxy_default_country", "es");
        proxySettings.put("output_proxy_default_server", "172.102.143.32");

        // Proxy 1 settings
        Map<String, Object> proxyServiceOneSettings = new HashMap<>();
        proxyServiceOneSettings.put("output_proxy_service_1", "HighProxy1");
        proxyServiceOneSettings.put("service_expiration_date_proxy_1", "01/01/2025");
        proxyServiceOneSettings.put("output_proxy_list_countryCodes_1", new String[] { "es" });
        proxyServiceOneSettings.put("output_proxy_list_ips_1", new String[] { "206.232.14.155,195" });
        proxyServiceOneSettings.put("output_proxy_list_ports_1", new String[] { "21272" });
        proxyServiceOneSettings.put("output_proxy_service_1_username", "nirsabb36427");
        proxyServiceOneSettings.put("output_proxy_service_1_password", "r5du8mpvep");

        // Proxy 2 settings
        Map<String, Object> proxyServiceTwoSettings = new HashMap<>();
        proxyServiceTwoSettings.put("output_proxy_service_2", "HighProxy2");
        proxyServiceTwoSettings.put("service_expiration_date_proxy_2", "02/02/2025");
        proxyServiceTwoSettings.put("output_proxy_list_countryCodes_2", new String[] { "gb" });
        proxyServiceTwoSettings.put("output_proxy_list_ips_2", new String[] { "195.206.109.58" });
        proxyServiceTwoSettings.put("output_proxy_list_ports_2", new String[] { "22222" });
        proxyServiceTwoSettings.put("output_proxy_service_2_username", "nirsabb36427");
        proxyServiceTwoSettings.put("output_proxy_service_2_password", "r5du8mpvep");

        // Proxy 3 settings
        Map<String, Object> proxyServiceThreeSettings = new HashMap<>();
        proxyServiceThreeSettings.put("output_proxy_service_3", "HighProxy3");
        proxyServiceThreeSettings.put("service_expiration_date_proxy_3", "03/03/2025");
        proxyServiceThreeSettings.put("output_proxy_list_countryCodes_3", new String[] { "fr" });
        proxyServiceThreeSettings.put("output_proxy_list_ips_3", new String[] { "333.333.333.333" });
        proxyServiceThreeSettings.put("output_proxy_list_ports_3", new String[] { "33333" });
        proxyServiceThreeSettings.put("output_proxy_service_3_username", "user3");
        proxyServiceThreeSettings.put("output_proxy_service_3_password", "password3");

        // Add Proxy service settings to the main Proxy settings
        proxySettings.put("ProxyServiceOne", proxyServiceOneSettings);
        proxySettings.put("ProxyServiceTwo", proxyServiceTwoSettings);
        proxySettings.put("ProxyServiceThree", proxyServiceThreeSettings);

        DEFAULT_SETTINGS.put("Proxy", proxySettings);

        // Hotspot settings
        Map<String, Object> hotspotSettings = new HashMap<>();
        // 1 for hidden, 0 for not hidden
        hotspotSettings.put("inputInterface_hotspot_hidden", 0);
        // ssid name. if it is "" or '' a random ssid will be generated according to
        // length and policy
        hotspotSettings.put("inputInterface_hotspot_ssid", "AnonMask-Dev-V11");
        // Number of characters. Must be >= 1
        hotspotSettings.put("inputInterface_hotspot_ssid_length", 8);
        // policy can be "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" .
        // always case sensitive and always mixed cases.
        hotspotSettings.put("inputInterface_hotspot_ssid_policy", "alphanum");
        // Provisioned password. Must be at least 8 characters long. if it is "" or '' a
        // random password will be generated according to length and policy
        hotspotSettings.put("inputInterface_hotspot_password", "@anonmask");
        // Number of characters. Must be >= 8
        hotspotSettings.put("inputInterface_hotspot_password_length", 9);
        // policy can be "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" .
        // always case sensitive and always mixed cases.
        hotspotSettings.put("inputInterface_hotspot_password_policy", "alphanum");

        DEFAULT_SETTINGS.put("Hotspot", hotspotSettings);
    }

    // VPN servers stored in JSON due to the amount of them, here is how we can read
    // the json file that contain the vpn servers list (for each of the 3 vpns)
    private static Map<String, List<String>> readVpnServersJson(String resourcePath) {
        Map<String, List<String>> vpnServers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(MaskFileManager.class.getResourceAsStream(resourcePath)))) {
            String line;
            String currentVpn = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.endsWith(": [")) {
                    currentVpn = line.substring(1, line.indexOf("\"", 1));
                    vpnServers.put(currentVpn, new ArrayList<>());
                } else if (line.startsWith("\"") && line.endsWith("\",")) {
                    vpnServers.get(currentVpn).add(line.substring(1, line.length() - 2));
                } else if (line.startsWith("\"") && line.endsWith("\"")) {
                    vpnServers.get(currentVpn).add(line.substring(1, line.length() - 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vpnServers;
    }

    // Method to get a specific setting
    public static Object getSetting(String category, String key) {
        Map<String, Object> categorySettings = DEFAULT_SETTINGS.get(category);
        if (categorySettings != null) {
            return categorySettings.get(key);
        }
        return null;
    }

    // Method to set a specific setting
    public static void setSetting(String category, String key, Object value) {
        DEFAULT_SETTINGS.computeIfAbsent(category, k -> new HashMap<>()).put(key, value);
    }
}