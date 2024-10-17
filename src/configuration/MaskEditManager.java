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
    private Runnable onPreviewAction;
    private CustPanel topSection;
    private CustPanel bottomSection;
    private CustPanel contentPanel;
    private CustScroll scrollPane;
    private Map<String, JPanel> switchPanels = new HashMap<>();
    private Map<String, Map<String, Object>> loadedSettings = new HashMap<>();
    private static final Map<String, Map<String, Object>> DEFAULT_SETTINGS = new HashMap<>();
    private static final String DEFAULT_SETTINGS_PATH = "/resources/json/default_settings.json";
    private static final Color DARK_COLOR = new Color(30, 30, 30);

    public MaskEditManager(String maskName, Runnable onBackAction, Runnable onPreviewAction, boolean isCreating) {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);
        this.maskName = maskName;
        this.onBackAction = onBackAction;
        this.onPreviewAction = onPreviewAction;

        loadDefaultSettings();

        if (isCreating) {
            loadedSettings = new HashMap<>(DEFAULT_SETTINGS);
        } else {
            // TODO: Load existing settings from file or database
            loadedSettings = new HashMap<>(DEFAULT_SETTINGS);
        }

        initializeSwitchPanels();
        initializeUI();
    }

    private void initializeSwitchPanels() {
        // Connect switchpanels to the edit manager for inputs 2 way binding
        switchPanels.put("System", new SystemSetInputs(this));
        switchPanels.put("TOR", new TorSetInputs(this));
        switchPanels.put("VPS", new VpsSetInputs(this));
        switchPanels.put("VPN", new VpnSetInputs(this));
        switchPanels.put("Proxy", new ProxySetInputs(this));
        switchPanels.put("Hotspot", new HotspotSetInputs(this));
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

        contentPanel.add(switchPanels.get("System"), BorderLayout.CENTER); // Default of contentPanel

        // Inserted contentPanel into a scrollable panel
        scrollPane = new CustScroll(contentPanel);
        scrollPane.getViewport().setBackground(DARK_COLOR);

        add(scrollPane, BorderLayout.CENTER);

        // Create a bottom section panel
        bottomSection = new CustPanel(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);

        var separatorThree = new CustSeparator(new Color(100, 0, 150), 1);
        bottomSection.add(separatorThree, BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombarOne(onBackAction, onPreviewAction), BorderLayout.CENTER);

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

    public Map<String, Object> getSettingsForCategory(String category) {
        return loadedSettings.computeIfAbsent(category,
                k -> new HashMap<>(DEFAULT_SETTINGS.getOrDefault(k, new HashMap<>())));
    }

    public void updateSettings(String category, Map<String, Object> settings) {
        loadedSettings.put(category, settings);
    }

    public Map<String, Map<String, Object>> getLoadedSettings() {
        return loadedSettings;
    }

    public void setOnPreviewAction(Runnable onPreviewAction) {
        this.onPreviewAction = onPreviewAction;
    }

    public String getMaskName() {
        return this.maskName;
    }
}