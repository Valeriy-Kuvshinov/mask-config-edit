import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

public class PinPanel extends JPanel {
    private JTextField pinField;
    private JPanel buttonPanel;
    private JLabel messageLabel;
    private StringBuilder currentPin = new StringBuilder();
    private static final String CONFIG_FILE = "config.properties";
    private App parentApp;

    public PinPanel(App app) {
        this.parentApp = app;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Check if PIN exists and set appropriate message
        String initialMessage = getStoredPin() == null ? "Create your PIN" : "Enter your PIN";

        // Message Label
        messageLabel = new JLabel(initialMessage, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(messageLabel, BorderLayout.NORTH);

        // PIN Field
        pinField = new JTextField(6);
        pinField.setEditable(false);
        pinField.setHorizontalAlignment(JTextField.CENTER);
        pinField.setFont(new Font("Roboto", Font.BOLD, 32));
        add(pinField, BorderLayout.CENTER);

        // Button Panel
        buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        for (int i = 1; i <= 9; i++) {
            addPinButton(String.valueOf(i));
        }
        addPinButton("0");
        addPinButton("-");
        addPinButton("X");
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addPinButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Roboto", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(80, 80));
        button.addActionListener(new PinButtonListener());
        buttonPanel.add(button);
    }

    private class PinButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("X".equals(command)) {
                currentPin.setLength(0);
                pinField.setText("");
            } else if ("-".equals(command)) {
                if (currentPin.length() > 0) {
                    currentPin.deleteCharAt(currentPin.length() - 1);
                    pinField.setText("*".repeat(currentPin.length()));
                }
            } else if (currentPin.length() < 6) {
                currentPin.append(command);
                pinField.setText("*".repeat(currentPin.length()));
                if (currentPin.length() == 6) {
                    verifyPin();
                }
            }
        }
    }

    private void verifyPin() {
        String enteredPin = currentPin.toString();
        String storedPin = getStoredPin();

        if (storedPin == null) {
            setStoredPin(enteredPin);
            messageLabel.setText("New PIN created successfully!");
            parentApp.onPinVerified();
        } else if (enteredPin.equals(storedPin)) {
            messageLabel.setText("Welcome user!");
            parentApp.onPinVerified();
        } else {
            messageLabel.setText("PIN is incorrect. Try again.");
        }

        currentPin.setLength(0);
        pinField.setText("");
    }

    private String getStoredPin() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            prop.load(input);
            return prop.getProperty("PIN_CODE");
        } catch (IOException ex) {
            return null;
        }
    }

    private void setStoredPin(String pin) {
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            prop.setProperty("PIN_CODE", pin);
            prop.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}