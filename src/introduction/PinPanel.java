package src.introduction;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import src.utilities.*;
import src.utilities.gui.*;

public class PinPanel extends CustPanel {
    private CustComponent[] pinDigits;
    private CustPanel buttonsPanel;
    private CustLabel messageLabel;
    private Runnable onPinVerifiedCallback;
    private StringBuilder currentPin = new StringBuilder();

    public PinPanel(Runnable onPinVerifiedCallback) {
        super(new BorderLayout(), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        this.onPinVerifiedCallback = onPinVerifiedCallback;
        initUI();
    }

    private void initUI() {
        var wrapperPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE,
                null, null, 0, 0, 0);
        wrapperPanel.add(createMainPanel());

        add(wrapperPanel, BorderLayout.CENTER);
    }

    private CustPanel createMainPanel() {
        var mainPanel = new CustPanel(new BoxLayout(null, BoxLayout.Y_AXIS), ColorPalette.DARK_ONE,
                null, null, 0, 0, 0);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(new CustLabel("Welcome to User Configuration!", null,
                null, Component.CENTER_ALIGNMENT));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createMessageLabel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(createPinDisplayPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(createButtonsPanel());
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private CustLabel createMessageLabel() {
        var initialMessage = getStoredPin() == null ? "Create your PIN" : "Enter your PIN";
        messageLabel = new CustLabel(initialMessage, null, null, Component.CENTER_ALIGNMENT);
        return messageLabel;
    }

    private CustPanel createPinDisplayPanel() {
        var pinDisplayPanel = new CustPanel(new FlowLayout(FlowLayout.CENTER, 10, 0), ColorPalette.DARK_ONE, null,
                Component.CENTER_ALIGNMENT, 0, 0, 0);
        pinDigits = new CustComponent[6];
        for (var i = 0; i < 6; i++) {
            pinDigits[i] = new CustComponent(" ", 55, 55, 20, 10,
                    null, ColorPalette.LIGHT_ONE, ColorPalette.DARK_ONE);
            pinDisplayPanel.add(pinDigits[i]);
        }
        return pinDisplayPanel;
    }

    private CustPanel createButtonsPanel() {
        buttonsPanel = new CustPanel(new GridLayout(4, 3, 10, 10), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        createPinButtons();
        return buttonsPanel;
    }

    // Create PIN Buttons
    private void createPinButtons() {
        buttonsPanel = new CustPanel(new GridBagLayout(), ColorPalette.DARK_ONE, null, Component.CENTER_ALIGNMENT,
                0, 0, 0);

        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] buttonLabels = {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "0", "-", "X"
        };
        for (var i = 0; i < buttonLabels.length; i++) {
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            addPinButton(buttonLabels[i], gbc);
        }
    }

    private void addPinButton(String label, GridBagConstraints gbc) {
        var button = new CustComponent(label, 65, 65, 20, 10,
                null, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
        button.addButtonBehavior(() -> handleButtonClick(label));
        buttonsPanel.add(button, gbc);
    }

    private void handleButtonClick(String command) {
        switch (command) {
            case "-":
                if (currentPin.length() > 0) {
                    currentPin.deleteCharAt(currentPin.length() - 1);
                    updatePinDisplay();
                }
                break;
            case "X":
                currentPin.setLength(0);
                updatePinDisplay();
                break;
            default:
                if (currentPin.length() < 6 && !"-".equals(command) && !"X".equals(command)) {
                    currentPin.append(command);
                    updatePinDisplay();
                    if (currentPin.length() == 6)
                        verifyPin();
                }
                break;
        }
    }

    private void updatePinDisplay() {
        for (var i = 0; i < 6; i++) {
            pinDigits[i].setText(i < currentPin.length() ? "*" : " ");
        }
    }

    private void verifyPin() {
        var enteredPin = currentPin.toString();
        var storedPin = getStoredPin();

        if (storedPin == null) {
            setStoredPin(enteredPin);
            messageLabel.setText("New PIN created successfully!");
            if (onPinVerifiedCallback != null)
                onPinVerifiedCallback.run();
        } else if (enteredPin.equals(storedPin)) {
            if (onPinVerifiedCallback != null)
                onPinVerifiedCallback.run();
        } else
            messageLabel.setText("PIN is incorrect. Try again.");

        resetPinDisplay();
    }

    private void resetPinDisplay() {
        currentPin.setLength(0);
        updatePinDisplay();
    }

    private String getStoredPin() {
        var prop = new Properties();
        try (var input = new FileInputStream("app.properties")) {
            prop.load(input);
            return prop.getProperty("PIN_CODE");
        } catch (IOException ex) {
            return null;
        }
    }

    private void setStoredPin(String pin) {
        var prop = new Properties();
        try (var output = new FileOutputStream("app.properties")) {
            prop.setProperty("PIN_CODE", pin);
            prop.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}