package src.configuration.bars;

import javax.swing.*;
import java.awt.*;

import src.configuration.MaskEditingManager;
import src.utilities.*;

public class MaskEditingMiddlebar extends CustomPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);
    private static final String[] BUTTON_LABELS = { "System", "TOR", "VPS", "VPN", "Proxy", "Hotspot" };
    private MaskEditingManager manager;

    public MaskEditingMiddlebar(MaskEditingManager manager) {
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
        var button = new CustomComponent(label, 120, 42, 20, 10, Component.CENTER_ALIGNMENT, LIGHT_COLOR, DARK_COLOR);
        button.addButtonBehavior(() -> {
            manager.showPanel(label);
            System.out.println(label + " settings selected");
        });
        return button;
    }
}