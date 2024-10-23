package src.introduction;

import javax.swing.*;
import java.awt.*;

import src.utilities.*;
import src.utilities.gui.*;

public class FlashPanel extends CustPanel {
    private CustScroll scrollPane;

    public FlashPanel(Runnable onBackClick) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        initUI(onBackClick);
    }

    private void initUI(Runnable onBackClick) {
        add(createScrollPane(createMainPanel(onBackClick)), BorderLayout.CENTER);
    }

    private CustScroll createScrollPane(CustPanel content) {
        var wrapperPanel = new CustPanel(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        wrapperPanel.add(content, BorderLayout.CENTER);

        scrollPane = new CustScroll(wrapperPanel);
        return scrollPane;
    }

    private CustPanel createMainPanel(Runnable onBackClick) {
        var mainPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, null, 15, 10, 20);
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = -1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 15, 5, 15);

        var helper = new GridBagHelper(mainPanel, gbc);

        helper.addLabel("Welcome to User Configuration!", null, null, Component.CENTER_ALIGNMENT);
        helper.addVerticalSpace(20);
        helper.addLabel("Please connect a flash drive...", null, null, Component.CENTER_ALIGNMENT);
        helper.addVerticalSpace(40);
        helper.addComponent(createConnectLabel());
        helper.addVerticalSpace(40);
        helper.addComponent(createBackButton(onBackClick));

        return mainPanel;
    }

    private CustLabel createConnectLabel() {
        var connectIcon = new ImageIcon("resources/images/connect-flash.png");
        var connectLabel = new CustLabel(connectIcon, Component.CENTER_ALIGNMENT);
        connectLabel.setLabelSize(180, 180);
        return connectLabel;
    }

    private CustComponent createBackButton(Runnable onBackClick) {
        var backButton = new CustComponent("Back", 100, 60, 20, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
        backButton.addButtonBehavior(onBackClick);
        return backButton;
    }
}