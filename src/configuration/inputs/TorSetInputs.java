package src.configuration.inputs;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class TorSetInputs extends CustPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public TorSetInputs() {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        initUI();
    }

    private void initUI() {
        Map<String, Object> settings = MaskEditManager.getSettingsForCategory("Tor");
        var combinedPanel = createInputsPanel(settings);
        InputPanelUtils.initCommonUI(this, combinedPanel);
    }

    private CustPanel createInputsPanel(Map<String, Object> settings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        InputPanelUtils.addSectionHeader(panel, gbc, 0, "General Settings", LIGHT_COLOR);

        addInputRow(panel, gbc, 1, "output_tor_list_countryCodes",
                InputPanelUtils.joinStringArray(settings.get("output_tor_list_countryCodes")),
                420, 32, "at, ch, de, fr...");
        addInputRow(panel, gbc, 2, "output_tor_list_exit_nodes",
                InputPanelUtils.joinStringArray(settings.get("output_tor_list_exit_nodes")),
                420, 20, "0.0.0");
        addInputRow(panel, gbc, 3, "output_tor_default_country",
                settings.get("output_tor_default_country"),
                120, 4, "00");

        return panel;
    }

    private void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int inputWidth, int maxChars, String placeholder) {
        InputPanelUtils.addInputRow(panel, gbc, row, key, value, inputWidth, maxChars, placeholder, LIGHT_COLOR);
    }
}