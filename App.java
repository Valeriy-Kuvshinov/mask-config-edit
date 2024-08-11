import javax.swing.*;
import java.awt.*;

import introduction.PinPanel;
import introduction.WelcomeProcess;

public class App extends JFrame implements WelcomeProcess {
    private PinPanel pinPanel;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
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

        add(pinPanel, BorderLayout.CENTER);
    }

    @Override
    public void onPinVerified() {
        System.out.println("PIN verified. Switching to main content...");
        // TODO: Add code to switch to main content
    }
}
