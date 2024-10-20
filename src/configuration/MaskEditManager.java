package src.configuration;

import java.util.*;
import javax.swing.*;
import java.awt.*;

import src.configuration.bars.*;
import src.configuration.general.*;
import src.configuration.inputs.*;
import src.utilities.*;

public class MaskEditManager extends CustPanel {
    private String maskName;
    private Runnable onBackAction;
    private Runnable onPreviewAction;
    private CustPanel contentPanel;
    private CustScroll scrollPane;
    private Map<String, CustPanel> switchPanels = new HashMap<>();
    private MaskSettings loadedSettings;

    public MaskEditManager(String maskName, Runnable onBackAction, Runnable onPreviewAction, boolean isCreating) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        this.maskName = maskName;
        this.onBackAction = onBackAction;
        this.onPreviewAction = onPreviewAction;

        if (isCreating) {
            loadedSettings = MaskSettings.DEFAULT_SETTINGS.copy();
        } else {
            // TODO: Load existing settings from file on USB
            loadedSettings = MaskSettings.DEFAULT_SETTINGS.copy();
        }

        initializeSwitchPanels();
        initializeUI();
    }

    private void initializeSwitchPanels() {
        // Connect switchpanels to the edit manager for inputs 2 way binding
        switchPanels.put("System", new SystemSetInputs(this));
        switchPanels.put("Tor", new TorSetInputs(this));
        switchPanels.put("VPN", new VpnSetInputs(this));
        switchPanels.put("VPS", new VpsSetInputs(this));
        switchPanels.put("Proxy", new ProxySetInputs(this));
        switchPanels.put("Hotspot", new HotspotSetInputs(this));
    }

    private void initializeUI() {
        // Create a panel to hold the topbar, middlebar, and separators
        var topSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        topSection.add(new MaskEditTopbar(maskName), BorderLayout.NORTH);

        var middleSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);

        var separatorOne = new CustSeparator(ColorPalette.BLUE_TWO, 1);
        middleSection.add(separatorOne, BorderLayout.NORTH);
        middleSection.add(new MaskEditMiddlebar(this), BorderLayout.CENTER);

        var separatorTwo = new CustSeparator(ColorPalette.BLUE_TWO, 1);
        middleSection.add(separatorTwo, BorderLayout.SOUTH);
        topSection.add(middleSection, BorderLayout.CENTER);

        add(topSection, BorderLayout.NORTH);

        // Create a content panel to hold switchable panels
        contentPanel = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        contentPanel.add(switchPanels.get("System"), BorderLayout.CENTER); // Default of contentPanel

        // Inserted contentPanel into a scrollable panel
        scrollPane = new CustScroll(contentPanel);
        scrollPane.getViewport().setBackground(ColorPalette.DARK_ONE);

        add(scrollPane, BorderLayout.CENTER);

        // Create a bottom section panel
        var bottomSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);

        var separatorThree = new CustSeparator(ColorPalette.BLUE_TWO, 1);
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

    public CategorySettings getSettingsForCategory(String category) {
        return loadedSettings.getCategory(category);
    }

    // Handle settings nested inside categories and nested services
    public void updateSetting(String category, String key, Object value) {
        CategorySettings categorySettings = loadedSettings.getCategory(category);
        if (key.contains("_") && !isTopLevelKey(category, key)) {
            String[] parts = key.split("_", 2);
            String serviceKey = parts[0];
            String nestedKey = parts[1];
            CategorySettings nestedSettings = (CategorySettings) categorySettings.getSetting(serviceKey);
            if (nestedSettings == null) {
                nestedSettings = new CategorySettings();
                categorySettings.setSetting(serviceKey, nestedSettings);
            }
            nestedSettings.setSetting(nestedKey, value);
        } else {
            categorySettings.setSetting(key, value);
        }
    }

    private boolean isTopLevelKey(String category, String key) {
        CategorySettings categorySettings = loadedSettings.getCategory(category);
        return categorySettings.getSetting(key) != null;
    }

    public MaskSettings getLoadedSettings() {
        return loadedSettings;
    }

    public void setOnPreviewAction(Runnable onPreviewAction) {
        this.onPreviewAction = onPreviewAction;
    }

    public String getMaskName() {
        return this.maskName;
    }
}