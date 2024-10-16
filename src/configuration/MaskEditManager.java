package src.configuration;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import org.json.*;
import java.nio.file.*;

import src.configuration.bars.*;
import src.configuration.inputs.*;
import src.utilities.*;

public class MaskEditManager extends CustPanel {
    private String maskName;
    private Runnable onBackAction;
    private CustPanel topSection;
    private CustPanel bottomSection;
    private CustPanel contentPanel;
    private CustScroll scrollPane;
    private Map<String, JPanel> switchPanels = new HashMap<>();
    private static final Map<String, Map<String, Object>> DEFAULT_SETTINGS = new HashMap<>();
    private static final String DEFAULT_SETTINGS_PATH = "/resources/json/default_settings.json";
    private static final Color DARK_COLOR = new Color(30, 30, 30);

    public MaskEditManager(String maskName, Runnable onBackAction) {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);
        this.maskName = maskName;
        this.onBackAction = onBackAction;
        loadDefaultSettings();
        initializeUI();
    }

    private void initializeUI() {
        // Create a panel to hold the topbar, middlebar, and separators
        topSection = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), DARK_COLOR, null, null, 0, 0, 0);
        topSection.add(new MaskEditTopbar(maskName));

        var separatorOne = new CustSeparator(new Color(100, 0, 150), 1);
        topSection.add(separatorOne);
        topSection.add(new MaskEditMiddlebar(this));

        var separatorTwo = new CustSeparator(new Color(100, 0, 150), 1);
        topSection.add(separatorTwo);

        add(topSection, BorderLayout.NORTH);

        // Create a content panel to hold switchable panels
        contentPanel = new CustPanel(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);

        // Create all settings panels
        switchPanels.put("System", new SystemSetInputs());
        switchPanels.put("TOR", new TorSetInputs());
        switchPanels.put("VPS", new VpsSetInputs());
        switchPanels.put("VPN", new VpnSetInputs());
        switchPanels.put("Proxy", new ProxySetInputs());
        switchPanels.put("Hotspot", new HotspotSetInputs());

        contentPanel.add(switchPanels.get("System"), BorderLayout.CENTER); // Default of contentPanel

        // Create a CustomScroll and add the contentPanel to it
        scrollPane = new CustScroll(contentPanel);
        scrollPane.getViewport().setBackground(DARK_COLOR);

        add(scrollPane, BorderLayout.CENTER);

        // Create a bottom section panel
        bottomSection = new CustPanel(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);

        var separatorThree = new CustSeparator(new Color(100, 0, 150), 1);
        bottomSection.add(separatorThree, BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombar(onBackAction), BorderLayout.CENTER);

        add(bottomSection, BorderLayout.SOUTH);
    }

    // Swtich between panels, and render the new selected panel
    public void showPanel(String panelName) {
        contentPanel.removeAll();
        var selectedPanel = switchPanels.get(panelName);
        if (selectedPanel != null) {
            contentPanel.add(selectedPanel, BorderLayout.CENTER);
        }
        scrollPane.scrollToTop();
        SwingUtilities.invokeLater(() -> {
            scrollPane.updateScrollBars();
        });
    }

    private void loadDefaultSettings() {
        try {
            String content = new String(
                    Files.readAllBytes(Paths.get(getClass().getResource(DEFAULT_SETTINGS_PATH).toURI())));
            JSONObject jsonObject = new JSONObject(content);

            for (String key : jsonObject.keySet()) {
                JSONObject categoryObject = jsonObject.getJSONObject(key);
                Map<String, Object> categoryMap = new HashMap<>();

                for (String settingKey : categoryObject.keySet()) {
                    Object value = categoryObject.get(settingKey);
                    categoryMap.put(settingKey, value);
                }

                DEFAULT_SETTINGS.put(key, categoryMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading default settings: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Map<String, Object> getSettingsForCategory(String category) {
        return DEFAULT_SETTINGS.getOrDefault(category, new HashMap<>());
    }
}