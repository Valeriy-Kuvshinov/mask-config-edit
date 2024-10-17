package src.configuration;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import src.configuration.bars.*;
import src.configuration.inputs.utilities.InputPanelUtils;
import src.utilities.*;

public class MaskPreview extends CustPanel {
    private String maskName;
    private Runnable onBackAction;
    private CustPanel topSection;
    private CustPanel bottomSection;
    private CustPanel previewPanel;
    private CustScroll scrollPane;
    private Map<String, Map<String, Object>> previewSettings;
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public MaskPreview(String maskName, Map<String, Map<String, Object>> previewSettings, Runnable onBackAction) {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);
        this.maskName = maskName;
        this.previewSettings = previewSettings;
        this.onBackAction = onBackAction;
        initializeUI();
    }

    private void initializeUI() {
        // Create a panel to hold the topbar, middlebar, and separators
        topSection = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), DARK_COLOR, null, null, 0, 0, 0);
        topSection.add(new MaskEditTopbar(maskName));

        var separatorOne = new CustSeparator(new Color(100, 0, 150), 1);
        topSection.add(separatorOne);

        add(topSection, BorderLayout.NORTH);

        // Create a content panel to hold switchable panels
        previewPanel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        displaySettings();

        // Inserted contentPanel into a scrollable panel
        scrollPane = new CustScroll(previewPanel);
        scrollPane.getViewport().setBackground(DARK_COLOR);

        add(scrollPane, BorderLayout.CENTER);

        // Create a bottom section panel
        bottomSection = new CustPanel(new BorderLayout(), DARK_COLOR, null, null, 0, 0, 0);

        var separatorTwo = new CustSeparator(new Color(100, 0, 150), 1);
        bottomSection.add(separatorTwo, BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombarTwo(onBackAction), BorderLayout.CENTER);

        add(bottomSection, BorderLayout.SOUTH);
    }

    private void displaySettings() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        for (Map.Entry<String, Map<String, Object>> category : previewSettings.entrySet()) {
            previewPanel.add(new CustLabel(category.getKey(), LIGHT_COLOR, 20, Component.LEFT_ALIGNMENT), gbc);
            gbc.gridy++;

            for (Map.Entry<String, Object> setting : category.getValue().entrySet()) {
                String value = InputPanelUtils.jsonArrayToString(setting.getValue());
                previewPanel.add(
                        new CustLabel(setting.getKey() + ": " + value, LIGHT_COLOR, 16, Component.LEFT_ALIGNMENT), gbc);
                gbc.gridy++;
            }

            gbc.gridy++;
        }
    }
}