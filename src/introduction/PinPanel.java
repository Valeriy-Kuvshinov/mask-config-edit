package src.introduction;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

import src.utilities.CustomComponent;
import src.utilities.CustomLabel;
import src.utilities.CustomPanel;

public class PinPanel extends JPanel {
    private CustomComponent[] pinDigits;
    private CustomPanel buttonsPanel;
    private CustomLabel messageLabel;
    private Runnable onPinVerifiedCallback;
    private StringBuilder currentPin = new StringBuilder();
    private static final Color DARK_COLOR = new Color(30, 30, 30);

    public PinPanel(Runnable onPinVerifiedCallback) {
        this.onPinVerifiedCallback = onPinVerifiedCallback;

        setLayout(new BorderLayout());
        setBackground(DARK_COLOR);

        // Create a main panel to hold all components
        var mainPanel = new CustomPanel(null, DARK_COLOR, null, null);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // Create a wrapper panel to center the main panel
        var wrapperPanel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null);
        wrapperPanel.add(mainPanel);

        // Check if PIN exists and set appropriate message
        var initialMessage = getStoredPin() == null ? "Create your PIN" : "Enter your PIN";

        // Create labels
        var welcomeLabel = new CustomLabel("Welcome to User Configuration!", null, null, Component.CENTER_ALIGNMENT);
        messageLabel = new CustomLabel(initialMessage, null, null, Component.CENTER_ALIGNMENT);

        // Create PIN Display
        var pinDisplayPanel = new CustomPanel(new FlowLayout(FlowLayout.CENTER, 10, 0), DARK_COLOR, null,
                Component.CENTER_ALIGNMENT);
        pinDigits = new CustomComponent[6];
        for (int i = 0; i < 6; i++) {
            pinDigits[i] = new CustomComponent(" ", null, null, 50, 50, null);
            pinDisplayPanel.add(pinDigits[i]);
        }

        createPinButtons(); // Create PIN Buttons

        // Add components to main panel
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(pinDisplayPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(wrapperPanel, BorderLayout.CENTER);
    }

    private void createPinButtons() {
        buttonsPanel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] buttonLabels = {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "0", "-", "X"
        };
        for (int i = 0; i < buttonLabels.length; i++) {
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            addPinButton(buttonLabels[i], gbc);
        }
    }

    private void addPinButton(String label, GridBagConstraints gbc) {
        var button = new CustomComponent(label, null, null, 60, 60, null);
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
        for (int i = 0; i < 6; i++) {
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
            messageLabel.setText("Access granted!");
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
        try (var input = new FileInputStream("config.properties")) {
            prop.load(input);
            return prop.getProperty("PIN_CODE");
        } catch (IOException ex) {
            return null;
        }
    }

    private void setStoredPin(String pin) {
        var prop = new Properties();
        try (var output = new FileOutputStream("config.properties")) {
            prop.setProperty("PIN_CODE", pin);
            prop.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}