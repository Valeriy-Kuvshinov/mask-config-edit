package src.configuration.main;

import java.awt.*;

import src.utilities.*;
import src.utilities.gui.*;

public class MaskFinale extends CustPanel {
    private String maskName;
    private String flashDriveRoute;
    private boolean exportSuccess;
    private String errorMessage;
    private Runnable onBackToChoiceAction;

    public MaskFinale(String maskName, String flashDriveRoute, boolean exportSuccess, String errorMessage,
            Runnable onBackToChoiceAction) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        this.maskName = maskName;
        this.flashDriveRoute = flashDriveRoute;
        this.exportSuccess = exportSuccess;
        this.errorMessage = errorMessage;
        this.onBackToChoiceAction = onBackToChoiceAction;
        initUI();
    }

    private void initUI() {
        add(createMainPanel(), BorderLayout.CENTER);
    }

    private CustPanel createMainPanel() {
        var mainPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 15, 10, 20);
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 15, 5, 15);

        var helper = new GridBagHelper(mainPanel, gbc);

        if (exportSuccess) {
            helper.addLabel("Configuration Export Successful", ColorPalette.LIGHT_ONE,
                    32, Component.LEFT_ALIGNMENT);
            helper.addVerticalSpace(60);
            helper.addLabel("Mask file '" + maskName + ".cfg' has been exported to:", ColorPalette.LIGHT_ONE,
                    32, Component.LEFT_ALIGNMENT);
            helper.addLabel(flashDriveRoute, ColorPalette.LIGHT_ONE,
                    32, Component.LEFT_ALIGNMENT);
        } else {
            helper.addLabel("Configuration Export Failed", ColorPalette.LIGHT_ONE,
                    32, Component.LEFT_ALIGNMENT);
            helper.addVerticalSpace(60);
            helper.addLabel("Error: " + errorMessage, ColorPalette.LIGHT_ONE,
                    32, Component.LEFT_ALIGNMENT);
        }
        helper.addVerticalSpace(60);

        var returnButton = new CustComponent("Return to mask editing selection?", null, 42, 20, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
        returnButton.addButtonBehavior(onBackToChoiceAction);
        helper.addComponent(returnButton);

        return mainPanel;
    }
}