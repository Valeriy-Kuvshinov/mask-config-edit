package src.configuration.inputs;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class TorSetInputs extends CustPanel {
    private MaskEditManager manager;
    private Map<String, Object> currentSettings;
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public TorSetInputs(MaskEditManager manager) {
        super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
        this.manager = manager;
        initUI();
    }

    private void initUI() {
        currentSettings = manager.getSettingsForCategory("Tor");
        var combinedPanel = createInputsPanel(currentSettings);
        InputPanelUtils.initCommonUI(this, combinedPanel);
    }

    private CustPanel createInputsPanel(Map<String, Object> settings) {
        var panel = new CustPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        InputPanelUtils.addSectionHeader(panel, gbc, 0, "General Settings", LIGHT_COLOR);

        InputPanelUtils.addInputRow(panel, gbc, 1, "output_tor_list_countryCodes",
                InputPanelUtils.jsonArrayToString(settings.get("output_tor_list_countryCodes")),
                420, 32, "at, ch, de, fr...", LIGHT_COLOR,
                manager, "Tor", currentSettings);
        InputPanelUtils.addInputRow(panel, gbc, 2, "output_tor_list_exit_nodes",
                InputPanelUtils.jsonArrayToString(settings.get("output_tor_list_exit_nodes")),
                420, 20, "0.0.0", LIGHT_COLOR,
                manager, "Tor", currentSettings);
        InputPanelUtils.addInputRow(panel, gbc, 3, "output_tor_default_country",
                settings.get("output_tor_default_country"),
                120, 4, "00", LIGHT_COLOR,
                manager, "Tor", currentSettings);

        return panel;
    }
}