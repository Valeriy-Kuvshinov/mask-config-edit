import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class App extends JFrame {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        SwingUtilities.invokeLater(() -> {
            App myFrame = new App();
            myFrame.init();
        });
    }

    public void init() {
        setTitle("Mask User Config Editor");
        setSize(600, 480);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
