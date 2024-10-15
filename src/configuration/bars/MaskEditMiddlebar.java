package src.configuration.bars;

import java.awt.*;
import java.util.*;

import src.configuration.MaskEditManager;
import src.utilities.*;

public class MaskEditMiddlebar extends CustPanel {
    private MaskEditManager manager;
    private Map<String, CustComponent> buttons = new HashMap<>();
    private static final Color DARK_COLOR = new Color(30, 30, 30);
    private static final Color GRAY_COLOR = new Color(50, 50, 50);
    private static final Color LIGHT_COLOR = new Color(220, 220, 220);
    private static final String[] BUTTON_LABELS = { "System", "TOR", "VPS", "VPN", "Proxy", "Hotspot" };
    private String selectedLabel = BUTTON_LABELS[0]; // Default selected option

    public MaskEditMiddlebar(MaskEditManager manager) {
        super(new FlowLayout(FlowLayout.CENTER, 50, 10), DARK_COLOR, null, null, 0, 15, 0);
        this.manager = manager;
        initUI();
    }

    private void initUI() {
        for (String label : BUTTON_LABELS) {
            CustComponent button = createButton(label);
            buttons.put(label, button);
            add(button);
        }
        updateButtonStates();
    }

    private CustComponent createButton(String label) {
        CustComponent button = new CustComponent(label, 120, 46, 20, 10,
                Component.CENTER_ALIGNMENT, LIGHT_COLOR, GRAY_COLOR);
        button.addButtonBehavior(() -> {
            if (!label.equals(selectedLabel)) {
                selectButton(label);
            }
        });
        return button;
    }

    private void selectButton(String label) {
        selectedLabel = label;
        manager.showPanel(label);
        updateButtonStates();
    }

    private void updateButtonStates() {
        for (Map.Entry<String, CustComponent> entry : buttons.entrySet()) {
            String label = entry.getKey();
            CustComponent button = entry.getValue();
            if (label.equals(selectedLabel)) {
                button.setBackgroundColor(GRAY_COLOR);
                button.setColor(LIGHT_COLOR);
                button.removeButtonBehavior();
            } else {
                button.setBackgroundColor(LIGHT_COLOR);
                button.setColor(GRAY_COLOR);
                button.removeButtonBehavior(); // Remove existing behavior
                button.addButtonBehavior(() -> selectButton(label));
            }
        }
    }
}