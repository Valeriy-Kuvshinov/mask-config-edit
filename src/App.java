package src;

import javax.swing.*;
import java.awt.*;

import src.introduction.*;
import src.configuration.*;

public class App extends JFrame {
    private PinPanel pinPanel;
    private FlashPanel flashPanel;
    private MaskEditingManager maskEditingManager;
    private MaskChoicePanel maskChoicePanel;
    private FlashDriveDetect flashDriveDetect;
    private boolean flashDriveConnected = false;
    private boolean pinVerified = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var app = new App();
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
        flashPanel = new FlashPanel(this::navigateBackToPinPanel);
        maskChoicePanel = new MaskChoicePanel(this::createMask, this::editMask, this::navigateBackToPinPanel);

        add(pinPanel, BorderLayout.CENTER);

        // Start the detection of an USB
        flashDriveDetect = new FlashDriveDetect(this::handleFlashDriveConnection);
        flashDriveDetect.startDetection();
    }

    private void handleFlashDriveConnection(boolean isConnected) {
        if (isConnected && !flashDriveConnected) {
            flashDriveConnected = true;
            if (pinVerified) {
                SwingUtilities.invokeLater(this::onFlashDriveConnected);
            }
        } else if (!isConnected && flashDriveConnected) {
            flashDriveConnected = false;
            if (pinVerified) {
                SwingUtilities.invokeLater(this::onFlashDriveDisconnected);
            }
        }
    }

    private void onPinVerified() {
        pinVerified = true;

        SwingUtilities.invokeLater(() -> {
            remove(pinPanel);
            add(flashDriveConnected ? maskChoicePanel : flashPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }

    private void createMask(String maskName) {
        System.out.println("Creating mask: " + maskName);
        maskEditingManager = new MaskEditingManager(maskName, this::navigateBackToMaskChoice);

        remove(maskChoicePanel);
        add(maskEditingManager, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void editMask(String maskName) {
        System.out.println("Editing mask: " + maskName);
        maskEditingManager = new MaskEditingManager(maskName, this::navigateBackToMaskChoice);

        remove(maskChoicePanel);
        add(maskEditingManager, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void navigateBackToPinPanel() {
        pinVerified = false;

        SwingUtilities.invokeLater(() -> {
            getContentPane().removeAll();
            add(pinPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }

    private void navigateBackToMaskChoice() {
        SwingUtilities.invokeLater(() -> {
            getContentPane().removeAll();
            add(maskChoicePanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }

    private void onFlashDriveConnected() {
        System.out.println("Flash drive connected. Switching to main content...");

        SwingUtilities.invokeLater(() -> {
            if (pinVerified) {
                remove(flashPanel);
                add(maskChoicePanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }

    private void onFlashDriveDisconnected() {
        System.out.println("Flash drive disconnected.");

        SwingUtilities.invokeLater(() -> {
            if (pinVerified) {
                getContentPane().removeAll();
                add(flashPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }

    public void shutdown() {
        if (flashDriveDetect != null) {
            flashDriveDetect.shutdown();
        }
    }
}