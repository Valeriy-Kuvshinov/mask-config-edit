package introduction;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

import utilities.RoundedComponent;

public class PinPanel extends JPanel {
    private WelcomeProcess welcomeProcess;
    private RoundedComponent[] pinDigits;
    private JPanel buttonsPanel;
    private JLabel messageLabel;
    private StringBuilder currentPin = new StringBuilder();
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);

    public PinPanel(WelcomeProcess process) {
        this.welcomeProcess = process;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(60, 120, 60, 120));

        // Check if PIN exists and set appropriate message
        var initialMessage = getStoredPin() == null ? "Create your PIN" : "Enter your PIN";

        // Create a panel for labels
        var labelsPanel = new JPanel(new GridLayout(2, 1, 0, 20));

        var welcomeLabel = new JLabel("Welcome to User Configuration!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Roboto", Font.BOLD, 24));

        messageLabel = new JLabel(initialMessage, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Roboto", Font.BOLD, 24));

        labelsPanel.add(welcomeLabel);
        labelsPanel.add(messageLabel);

        // Create a panel for PIN Display
        var pinDisplayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pinDigits = new RoundedComponent[6];
        for (int i = 0; i < 6; i++) {
            pinDigits[i] = new RoundedComponent(" ");
            pinDigits[i].setFont(new Font("Roboto", Font.BOLD, 32));
            pinDigits[i].setPreferredSize(new Dimension(50, 50));
            pinDisplayPanel.add(pinDigits[i]);
        }

        // Create a panel for the PIN buttons
        buttonsPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        for (int i = 1; i <= 9; i++) {
            addPinButton(String.valueOf(i));
        }
        addPinButton("0");
        addPinButton("-");
        addPinButton("X");

        var buttonsContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsContainerPanel.add(buttonsPanel);

        add(labelsPanel, BorderLayout.NORTH);
        add(pinDisplayPanel, BorderLayout.CENTER);
        add(buttonsContainerPanel, BorderLayout.SOUTH);

        setBackground(DARK_COLOR);
        labelsPanel.setBackground(DARK_COLOR);
        messageLabel.setForeground(LIGHT_COLOR);
        welcomeLabel.setForeground(LIGHT_COLOR);
        pinDisplayPanel.setBackground(DARK_COLOR);
        buttonsPanel.setBackground(DARK_COLOR);
        buttonsContainerPanel.setBackground(DARK_COLOR);

    }

    private void addPinButton(String label) {
        var button = new RoundedComponent(label);
        button.setFont(new Font("Roboto", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(60, 60));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleButtonClick(label);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackgroundColor(Color.LIGHT_GRAY);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackgroundColor(Color.WHITE);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        buttonsPanel.add(button);
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
            if (welcomeProcess != null)
                welcomeProcess.onPinVerified();

        } else if (enteredPin.equals(storedPin)) {
            messageLabel.setText("Access granted!");
            if (welcomeProcess != null)
                welcomeProcess.onPinVerified();
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