package src;

import javax.swing.*;
import java.awt.*;

import src.interfaces.*;
import src.introduction.*;
import src.utilities.gui.*;
import src.configuration.main.*;

public class App extends JFrame {
    private CustPanel pinPanel;
    private CustPanel flashPanel;
    private CustPanel maskChoicePanel;
    private CustPanel maskEditingManager;
    private CustPanel maskPreview;
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
        flashPanel = new FlashPanel(this::navigateBackPinPanel);
        maskChoicePanel = new MaskChoicePanel(this::createMask, this::editMask, this::navigateBackPinPanel);

        switchToPanel(pinPanel);

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
        SwingUtilities.invokeLater(() -> switchToPanel(flashDriveConnected ? maskChoicePanel : flashPanel));
    }

    private void createMask(String maskName) {
        System.out.println("Creating mask: " + maskName);
        maskEditingManager = new MaskEditManager(maskName, this::navigateBackMaskChoice, this::previewMask, true);
        switchToPanel(maskEditingManager);
    }

    private void editMask(String maskName) {
        System.out.println("Editing mask: " + maskName);
        maskEditingManager = new MaskEditManager(maskName, this::navigateBackMaskChoice, this::previewMask, false);
        switchToPanel(maskEditingManager);
    }

    private void previewMask() {
        SwingUtilities.invokeLater(() -> {
            var editor = (MaskEditor) maskEditingManager;
            var previewSettings = editor.getLoadedSettings();
            maskPreview = new MaskPreview(editor.getMaskName(), previewSettings, this::navigateBackToEdit);
            switchToPanel(maskPreview);
        });
    }

    private void navigateBackPinPanel() {
        pinVerified = false;
        SwingUtilities.invokeLater(() -> switchToPanel(pinPanel));
    }

    private void navigateBackMaskChoice() {
        SwingUtilities.invokeLater(() -> switchToPanel(maskChoicePanel));
    }

    private void navigateBackToEdit() {
        SwingUtilities.invokeLater(() -> switchToPanel(maskEditingManager));
    }

    private void onFlashDriveConnected() {
        if (pinVerified) {
            SwingUtilities.invokeLater(() -> switchToPanel(maskChoicePanel));
        }
    }

    private void onFlashDriveDisconnected() {
        if (pinVerified) {
            SwingUtilities.invokeLater(() -> switchToPanel(flashPanel));
        }
    }

    private void switchToPanel(JComponent panel) {
        getContentPane().removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void shutdown() {
        if (flashDriveDetect != null) {
            flashDriveDetect.shutdown();
        }
    }
}