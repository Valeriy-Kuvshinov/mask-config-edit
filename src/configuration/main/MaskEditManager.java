package src.configuration.main;

import java.util.*;
import java.awt.*;

import src.interfaces.*;
import src.configuration.bars.*;
import src.configuration.general.*;
import src.configuration.inputs.*;
import src.utilities.*;
import src.utilities.gui.*;

public class MaskEditManager extends CustPanel implements MaskEditor {
    private String maskName;
    private Runnable onBackAction;
    private Runnable onPreviewAction;
    private CustPanel mainPanel;
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

        initSwitchPanels();
        initUI();
    }

    private void initSwitchPanels() {
        // Connect switchpanels to the edit manager for inputs 2 way binding
        switchPanels.put("System", new SystemSetInputs(this));
        switchPanels.put("Tor", new TorSetInputs(this));
        switchPanels.put("VPN", new VpnSetInputs(this));
        switchPanels.put("VPS", new VpsSetInputs(this));
        switchPanels.put("Proxy", new ProxySetInputs(this));
        switchPanels.put("Hotspot", new HotspotSetInputs(this));
    }

    private void initUI() {
        add(createTopSection(), BorderLayout.NORTH);
        add(createScrollPane(), BorderLayout.CENTER);
        add(createBottomSection(), BorderLayout.SOUTH);
    }

    private CustPanel createTopSection() {
        var topSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        topSection.add(new MaskEditTopbar(maskName), BorderLayout.NORTH);
        topSection.add(createMiddleSection(), BorderLayout.CENTER);
        return topSection;
    }

    private CustPanel createMiddleSection() {
        var middleSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        middleSection.add(new CustSeparator(ColorPalette.BLUE_TWO, 1), BorderLayout.NORTH);
        middleSection.add(new MaskEditMiddlebar(this), BorderLayout.CENTER);
        middleSection.add(new CustSeparator(ColorPalette.BLUE_TWO, 1), BorderLayout.SOUTH);
        return middleSection;
    }

    private CustScroll createScrollPane() {
        mainPanel = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        mainPanel.add(switchPanels.get("System"), BorderLayout.CENTER); // Default switchable panel

        scrollPane = new CustScroll(mainPanel);
        return scrollPane;
    }

    private CustPanel createBottomSection() {
        var bottomSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        bottomSection.add(new CustSeparator(ColorPalette.BLUE_TWO, 1), BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombar("edit", onBackAction, onPreviewAction), BorderLayout.CENTER);
        return bottomSection;
    }

    // Swtich between panels, and render the new selected panel
    public void showPanel(String panelName) {
        mainPanel.removeAll();
        var selectedPanel = switchPanels.get(panelName);
        if (selectedPanel != null) {
            mainPanel.add(selectedPanel, BorderLayout.CENTER);
        }
        scrollPane.scrollToTop();
    }

    public CategorySettings getSettingsForCategory(String category) {
        return loadedSettings.getCategory(category);
    }

    // Handle settings nested inside categories
    public void updateSetting(String category, String key, Object value) {
        var categorySettings = loadedSettings.getCategory(category);
        categorySettings.setSetting(key, value);
    }

    @Override
    public MaskSettings getLoadedSettings() {
        return loadedSettings;
    }

    @Override
    public String getMaskName() {
        return this.maskName;
    }
}