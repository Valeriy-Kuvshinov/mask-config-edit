package src.editing;

import java.io.*;
import java.util.*;

public class MaskFileManager {
    public static final Map<String, Map<String, Object>> DEFAULT_SETTINGS = new HashMap<>();

    static {
        // Input settings
        Map<String, Object> inputSettings = new HashMap<>();
        inputSettings.put("Input_computer", 1);
        inputSettings.put("Input_cellular", 1);
        inputSettings.put("Input_secureIphone", 1);
        DEFAULT_SETTINGS.put("Input", inputSettings);

        // Input Interface settings
        Map<String, Object> inputInterfaceSettings = new HashMap<>();
        inputInterfaceSettings.put("ethernet", 1);
        inputInterfaceSettings.put("wifi", 1);
        inputInterfaceSettings.put("usb", 1);
        DEFAULT_SETTINGS.put("InputInterface", inputInterfaceSettings);

        // Output Interface settings
        Map<String, Object> outputInterfaceSettings = new HashMap<>();
        outputInterfaceSettings.put("ethernet", 1);
        outputInterfaceSettings.put("wifi", 1);
        outputInterfaceSettings.put("cellularModem", 1);
        DEFAULT_SETTINGS.put("OutputInterface", outputInterfaceSettings);

        // Output settings
        Map<String, Object> outputSettings = new HashMap<>();
        outputSettings.put("tor", 1);
        outputSettings.put("proxy", 1);
        outputSettings.put("vpn", 1);
        outputSettings.put("vps", 1);
        outputSettings.put("force_tor", 0);
        DEFAULT_SETTINGS.put("Output", outputSettings);

        // Proxy settings
        Map<String, Object> proxySettings = new HashMap<>();
        proxySettings.put("ip", "45.9.190.11");
        proxySettings.put("port", 8888);
        proxySettings.put("username", "user");
        proxySettings.put("password", "password");
        proxySettings.put("default_service", "HighProxy1");
        proxySettings.put("default_country", "es");
        proxySettings.put("default_server", "172.102.143.32");
        proxySettings.put("list_services", Arrays.asList("HighProxy1", "HighProxy2"));
        proxySettings.put("list_countryCodes", Arrays.asList("es", "gb", "fr"));
        proxySettings.put("list_ips", Arrays.asList("206.232.14.155", "195.206.109.58", "333.333.333.333"));
        proxySettings.put("list_ports", Arrays.asList(21272, 22222, 33333));
        proxySettings.put("list_usernames", Arrays.asList("nirsabb36427", "nirsabb36427", "user3"));
        proxySettings.put("list_passwords", Arrays.asList("r5du8mpvep", "r5du8mpvep", "password3"));
        DEFAULT_SETTINGS.put("Proxy", proxySettings);

        // Tor settings
        Map<String, Object> torSettings = new HashMap<>();
        torSettings.put("list_countryCodes", Arrays.asList("at", "ch", "de", "fr", "nl", "no", "ro", "se"));
        torSettings.put("list_exit_nodes", new ArrayList<>());
        torSettings.put("default_country", 99);
        DEFAULT_SETTINGS.put("Tor", torSettings);

        // VPN settings
        Map<String, Object> vpnSettings = new HashMap<>();
        vpnSettings.put("default_service", "any");
        vpnSettings.put("default_country", "any");
        vpnSettings.put("default_server", "any");
        vpnSettings.put("service_list", Arrays.asList("nordvpn", "protonvpn", "expressvpn"));
        DEFAULT_SETTINGS.put("VPN", vpnSettings);

        // VPN 1 settings
        Map<String, Object> vpnServiceOneSettings = new HashMap<>();
        vpnServiceOneSettings.put("service", "nordvpn");
        vpnServiceOneSettings.put("expiration_date", "01/01/2025");
        vpnServiceOneSettings.put("list_countryCodes",
                Arrays.asList("at", "ch", "cz", "de", "es", "fr", "it", "nl", "pl", "ro", "uk", "se"));
        vpnServiceOneSettings.put("username", "eEX9A2ZxKd3kZcGYeepccFEg");
        vpnServiceOneSettings.put("password", "emJTGgyviYQduLUwvSZD6xk1");
        vpnServiceOneSettings.put("list_servers", readFromJson("resources/json/vpn_servers_one.json"));
        DEFAULT_SETTINGS.put("VPNServiceOne", vpnServiceOneSettings);

        // VPN 2 settings
        Map<String, Object> vpnServiceTwoSettings = new HashMap<>();
        vpnServiceTwoSettings.put("service", "protonvpn");
        vpnServiceTwoSettings.put("expiration_date", "02/02/2024");
        vpnServiceTwoSettings.put("list_countryCodes",
                Arrays.asList("at", "ch", "cz", "de", "es", "fr", "it", "nl", "pl", "ro", "se"));
        vpnServiceTwoSettings.put("username", "3gMlEux4oskmvYFX");
        vpnServiceTwoSettings.put("password", "n5oBE6FcFn12NlLoi9d7Ep9Vb0R10PJN");
        vpnServiceTwoSettings.put("list_servers", readFromJson("resources/json/vpn_servers_two.json"));
        DEFAULT_SETTINGS.put("VPNServiceTwo", vpnServiceTwoSettings);

        // VPN 2 settings
        Map<String, Object> vpnServiceThreeSettings = new HashMap<>();
        vpnServiceThreeSettings.put("service", "expressvpn");
        vpnServiceThreeSettings.put("expiration_date", "03/03/2025");
        vpnServiceThreeSettings.put("list_countryCodes",
                Arrays.asList("at", "ch", "cz", "de", "es", "fr", "hu", "it", "nl", "pt", "se"));
        vpnServiceThreeSettings.put("username", "tk5zulngd3ylvuyczvykl1bh");
        vpnServiceThreeSettings.put("password", "javcbkhkijp46wdkn7xapmqv");
        vpnServiceThreeSettings.put("list_servers", readFromJson("resources/json/vpn_servers_three.json"));
        DEFAULT_SETTINGS.put("VPNServiceThree", vpnServiceThreeSettings);
    }

    private static List<String> readFromJson(String resourcePath) {
        List<String> servers = new ArrayList<>();
        try (InputStream inputStream = MaskFileManager.class.getResourceAsStream(resourcePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String json = jsonContent.toString();
            int start = json.indexOf("[") + 1;
            int end = json.lastIndexOf("]");
            String serversString = json.substring(start, end);

            String[] serverArray = serversString.split(",");
            for (String server : serverArray) {
                servers.add(server.trim().replace("\"", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return servers;
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
