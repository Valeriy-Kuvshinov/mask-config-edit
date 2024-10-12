package src.configuration.bars;

import javax.swing.*;
import java.awt.*;

import src.configuration.MaskEditManager;
import src.utilities.*;

public class MaskEditMiddlebar extends CustPanel {
    private MaskEditManager manager;
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color GRAY_COLOR = new Color(50, 50, 50);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);
    private static final String[] BUTTON_LABELS = { "System", "TOR", "VPS", "VPN", "Proxy", "Hotspot" };

    public MaskEditMiddlebar(MaskEditManager manager) {
        super(new FlowLayout(FlowLayout.CENTER, 50, 10), DARK_COLOR, null, null, 0, 15, 0);
        this.manager = manager;
        initializeUI();
    }

    private void initializeUI() {
        for (String label : BUTTON_LABELS) {
            add(createButton(label));
        }
    }

    private JComponent createButton(String label) {
        var button = new CustComponent(label, 120, 46, 20, 10,
                Component.CENTER_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);
        button.addButtonBehavior(() -> {
            manager.showPanel(label);
            System.out.println(label + " settings selected");
        });
        return button;
    }
}