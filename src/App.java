package src;

import javax.swing.*;
import java.awt.*;

import src.introduction.FlashPanel;
import src.introduction.PinPanel;

public class App extends JFrame {
    private PinPanel pinPanel;
    private FlashPanel flashPanel;
    private FlashDriveDetect flashDriveDetect;
    private boolean flashDriveConnected = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.init();
            app.setVisible(true);

            Runtime.getRuntime().addShutdownHook(new Thread(app::shutdown));
        });
    }

    public void init() {
        setTitle("Mask Maze");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        pinPanel = new PinPanel(this::onPinVerified);
        flashPanel = new FlashPanel(this::createMask, this::editMask);

        add(pinPanel, BorderLayout.CENTER);

        flashDriveDetect = new FlashDriveDetect(this::handleFlashDriveConnection);
        flashDriveDetect.startDetection();
    }

    private void handleFlashDriveConnection(boolean isConnected) {
        if (isConnected && !flashDriveConnected) {
            flashDriveConnected = true;
            SwingUtilities.invokeLater(this::onFlashDriveConnected);
        } else if (!isConnected && flashDriveConnected) {
            flashDriveConnected = false;
            SwingUtilities.invokeLater(this::onFlashDriveDisconnected);
        }
    }

    private void onPinVerified() {
        System.out.println("PIN verified. Switching to flash drive panel...");
        SwingUtilities.invokeLater(() -> {
            remove(pinPanel);
            add(flashPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }

    private void onFlashDriveConnected() {
        System.out.println("Flash drive connected. Switching to main content...");
        SwingUtilities.invokeLater(() -> {
            if (flashPanel != null) {
                flashPanel.onFlashDriveConnected();
                revalidate();
                repaint();
            }
        });
    }

    private void onFlashDriveDisconnected() {
        System.out.println("Flash drive disconnected.");
        SwingUtilities.invokeLater(() -> {
            if (flashPanel != null) {
                flashPanel.onFlashDriveDisconnected();
                revalidate();
                repaint();
            }
        });
    }

    private void createMask(String maskName) {
        System.out.println("Creating mask: " + maskName);
        // TODO: Implement actual mask creation logic
    }

    private void editMask(String maskName) {
        System.out.println("Editing mask: " + maskName);
        // TODO: Implement actual mask editing logic
    }

    public void shutdown() {
        if (flashDriveDetect != null) {
            flashDriveDetect.shutdown();
        }
    }
}