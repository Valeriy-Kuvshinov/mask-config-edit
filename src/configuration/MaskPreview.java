package src.configuration;

import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;

import src.configuration.bars.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class MaskPreview extends CustPanel {
    private String maskName;
    private Runnable onBackAction;
    private CustPanel topSection;
    private CustPanel bottomSection;
    private CustScroll scrollPane;
    private LinkedHashMap<String, LinkedHashMap<String, Object>> previewSettings;
    private static final List<String> CATEGORY_ORDER = List.of("System", "Tor", "VPN", "VPS", "Proxy", "Hotspot");

    public MaskPreview(String maskName, LinkedHashMap<String, LinkedHashMap<String, Object>> previewSettings,
            Runnable onBackAction) {
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
        gbc.insets = new Insets(5, 10, 5, 10);

        for (String category : CATEGORY_ORDER) {
            LinkedHashMap<String, Object> categorySettings = previewSettings.get(category);
            if (categorySettings != null && !categorySettings.isEmpty()) {
                previewComponent.add(new CustLabel(category, ColorPalette.LIGHT_ONE, 20,
                        Component.LEFT_ALIGNMENT), gbc);
                gbc.gridy++;

                for (Map.Entry<String, Object> entry : categorySettings.entrySet()) {
                    var key = entry.getKey();
                    var value = entry.getValue();

                    if (value instanceof LinkedHashMap) {
                        // Handle nested structures (VPN and Proxy services)
                        previewComponent.add(new CustLabel(key, ColorPalette.LIGHT_ONE, 18,
                                Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        gbc.insets.left += 20; // Indent nested items

                        @SuppressWarnings("unchecked")
                        LinkedHashMap<String, Object> nestedMap = (LinkedHashMap<String, Object>) value;
                        for (Map.Entry<String, Object> nestedEntry : nestedMap.entrySet()) {
                            var nestedValue = InputPanelUtils.jsonArrayToString(nestedEntry.getValue());
                            previewComponent.add(new CustLabel(nestedEntry.getKey() + ": " + nestedValue,
                                    ColorPalette.LIGHT_ONE, 16, Component.LEFT_ALIGNMENT), gbc);
                            gbc.gridy++;
                        }

                        gbc.insets.left -= 20; // Reset indent
                    } else {
                        var stringValue = InputPanelUtils.jsonArrayToString(value);
                        previewComponent.add(new CustLabel(key + ": " + stringValue,
                                ColorPalette.LIGHT_ONE, 16, Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                    }
                }
                gbc.gridy++;
            }
        }
        return previewComponent;
    }
}