import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;

public class App extends JFrame {
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
    
    public void onPinVerified() {
        // This method will be called when the PIN is verified
        // You can use it to switch to the main content of your app
        System.out.println("PIN verified. Switching to main content...");
        // TODO: Add code to switch to main content
    }
}
