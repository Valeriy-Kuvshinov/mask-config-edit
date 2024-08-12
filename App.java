import javax.swing.*;
import java.awt.*;

import introduction.FlashPanel;
import introduction.PinPanel;
import introduction.WelcomeProcess;

public class App extends JFrame implements WelcomeProcess {
    private PinPanel pinPanel;
    private FlashPanel flashPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.init();
            app.setVisible(true);
        });
    }

    public void init() {
        setTitle("Mask Maze");
        setSize(1024, 640);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        pinPanel = new PinPanel(this);
        flashPanel = new FlashPanel(this);

        add(pinPanel, BorderLayout.CENTER);
    }

    @Override
    public void onPinVerified() {
        System.out.println("PIN verified. Switching to flash drive panel...");
        remove(pinPanel);
        add(flashPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void onFlashDriveConnected() {
        System.out.println("Flash drive connected. Switching to main content...");
        // TODO: Add code to switch to main content
    }
}
