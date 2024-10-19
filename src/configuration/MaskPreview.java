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

                for (String key : categorySettings.getSettingKeys()) {
                    var value = categorySettings.getSetting(key);

                    if (value instanceof CategorySettings) {
                        // Handle nested structures (VPN and Proxy services)
                        previewComponent.add(new CustLabel(key, ColorPalette.LIGHT_ONE, 18,
                                Component.LEFT_ALIGNMENT), gbc);
                        gbc.gridy++;
                        gbc.insets.left += 20; // Indent nested items

                        var nestedSettings = (CategorySettings) value;
                        for (String nestedKey : nestedSettings.getSettingKeys()) {
                            var nestedValue = nestedSettings.getSetting(nestedKey);
                            var stringValue = InputPanelUtils.jsonArrayToString(nestedValue);
                            previewComponent.add(new CustLabel(nestedKey + ": " + stringValue,
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