package src.configuration.inputs;

import java.awt.*;

import src.configuration.*;
import src.configuration.general.*;
import src.configuration.inputs.utilities.*;
import src.utilities.*;

public class TorSetInputs extends CustPanel {
    private MaskEditManager manager;
    private CategorySettings currentSettings;

    public TorSetInputs(MaskEditManager manager) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 15, 15);
        this.manager = manager;
        initUI();
    }

    private void initUI() {
        currentSettings = manager.getSettingsForCategory("Tor");
        var combinedPanel = createInputsPanel(currentSettings);
        InputPanelUtils.initCommonUI(this, combinedPanel);
    }

    private CustPanel createInputsPanel(CategorySettings settings) {
        var panel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        InputPanelUtils.addSectionHeader(panel, gbc, 0, "General Settings", ColorPalette.LIGHT_ONE);

        InputPanelUtils.addInputRow(panel, gbc, 1, "output_tor_list_countryCodes",
                InputPanelUtils.jsonArrayToString(settings.getSetting("output_tor_list_countryCodes")),
                420, 32, "at, ch, de, fr...", ColorPalette.LIGHT_ONE,
                manager, "Tor", currentSettings);
        InputPanelUtils.addInputRow(panel, gbc, 2, "output_tor_list_exit_nodes",
                InputPanelUtils.jsonArrayToString(settings.getSetting("output_tor_list_exit_nodes")),
                420, 20, "0.0.0", ColorPalette.LIGHT_ONE,
                manager, "Tor", currentSettings);
        InputPanelUtils.addInputRow(panel, gbc, 3, "output_tor_default_country",
                settings.getSetting("output_tor_default_country"),
                120, 4, "00", ColorPalette.LIGHT_ONE,
                manager, "Tor", currentSettings);

        return panel;
    }
}