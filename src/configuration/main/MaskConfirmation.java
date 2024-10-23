package src.configuration.main;

import java.awt.*;

import src.configuration.bars.*;
import src.configuration.general.*;
import src.utilities.*;
import src.utilities.gui.*;

public class MaskConfirmation extends CustPanel {
    private String maskName;
    private String flashDriveRoute = "";
    private Integer fileSize = 0;
    private Runnable onBackAction;

    public MaskConfirmation(String maskName, Runnable onBackAction) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        this.maskName = maskName;
        this.onBackAction = onBackAction;
        initUI();
    }

    private void initUI() {
        add(createScrollPane(createMainPanel()), BorderLayout.CENTER);
        add(createBottomSection(), BorderLayout.SOUTH);
    }

    private CustScroll createScrollPane(CustPanel content) {
        var wrapperPanel = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        wrapperPanel.add(content, BorderLayout.CENTER);

        var scrollPane = new CustScroll(wrapperPanel);
        return scrollPane;
    }

    private CustPanel createBottomSection() {
        var bottomSection = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        bottomSection.add(new CustSeparator(ColorPalette.BLUE_TWO, 1), BorderLayout.NORTH);
        bottomSection.add(new MaskEditBottombar("save", onBackAction, null), BorderLayout.CENTER);
        return bottomSection;
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

        helper.addLabel("Export " + maskName + " to flash drive", ColorPalette.LIGHT_ONE,
                32, Component.LEFT_ALIGNMENT);
        helper.addVerticalSpace(60);

        helper.addLabel("Available Flash Drive: " + flashDriveRoute, ColorPalette.LIGHT_ONE,
                32, Component.LEFT_ALIGNMENT);
        helper.addLabel("Expected size: " + fileSize + " MB", ColorPalette.LIGHT_ONE,
                32, Component.LEFT_ALIGNMENT);
        helper.addLabel("Date: " + DateTime.formattedDate, ColorPalette.LIGHT_ONE,
                32, Component.LEFT_ALIGNMENT);

        return mainPanel;
    }
}