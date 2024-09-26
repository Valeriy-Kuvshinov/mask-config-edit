package src.configuration;

import javax.swing.*;
import java.awt.*;

import src.utilities.*;

public class MaskEditingMiddlebar extends CustomPanel {
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final String[] BUTTON_LABELS = { "System", "TOR", "VPS", "VPN", "Proxy", "Hotspot" };

    public MaskEditingMiddlebar() {
        super(new FlowLayout(FlowLayout.CENTER, 50, 10), DARK_COLOR, null, null, 0, 0, 0);
        initializeUI();
    }

    private void initializeUI() {
        for (String label : BUTTON_LABELS) {
            add(createButton(label));
        }
    }

    private JComponent createButton(String label) {
        var button = new CustomComponent(label, 120, 42, 20, 10, Component.CENTER_ALIGNMENT, null, DARK_COLOR);
        button.addButtonBehavior(() -> System.out.println(label + " clicked"));
        return button;
    }
}