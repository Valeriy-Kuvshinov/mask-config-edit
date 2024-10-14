package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import src.configuration.*;
import src.configuration.utilities.*;
import src.utilities.*;

public class TorSetInputs extends CustPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public TorSetInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initializeUI();
    }

    private void initializeUI() {
        Map<String, Object> torSettings = MaskEditManager.getSettingsForCategory("Tor");
        var combinedPanel = createInputsPanel(torSettings);

        // Create a new panel to center the combinedPanel horizontally
        var centerPanel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(combinedPanel, centerGbc);

        add(centerPanel, BorderLayout.NORTH);
        add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    private CustPanel createInputsPanel(Map<String, Object> torSettings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        addInputRow(panel, gbc, 0, "output_tor_list_countryCodes",
                InputPanelUtils.joinStringArray(torSettings.get("output_tor_list_countryCodes")),
                420, 32, "at, ch, de, fr...");
        addInputRow(panel, gbc, 1, "output_tor_list_exit_nodes",
                InputPanelUtils.joinStringArray(torSettings.get("output_tor_list_exit_nodes")),
                420, 20, "0.0.0");
        addInputRow(panel, gbc, 2, "output_tor_default_country",
                torSettings.get("output_tor_default_country"),
                120, 4, "00");

        return panel;
    }

    private void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int inputWidth, int maxChars, String placeholder) {
        InputPanelUtils.addInputRow(panel, gbc, row, key, value, inputWidth, maxChars, placeholder, LIGHT_COLOR);
    }
}