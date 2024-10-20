package src.configuration.bars;

import java.awt.*;
import java.util.*;

import src.configuration.*;
import src.configuration.general.MaskSettings;
import src.utilities.*;

public class MaskEditMiddlebar extends CustPanel {
    private MaskEditManager manager;
    private Map<String, CustComponent> buttons = new LinkedHashMap<>();
    private String selectedLabel = MaskSettings.CATEGORY_ORDER.get(0); // Default selected option

    public MaskEditMiddlebar(MaskEditManager manager) {
        super(new FlowLayout(FlowLayout.CENTER, 40, 10), ColorPalette.DARK_ONE, null, null, 0, 0, 0);
        this.manager = manager;
        initUI();
    }

    private void initUI() {
        for (String label : MaskSettings.CATEGORY_ORDER) {
            CustComponent button = createButton(label);
            buttons.put(label, button);
            add(button);
        }
        updateButtonStates();
    }

    private CustComponent createButton(String label) {
        CustComponent button = new CustComponent(label, 120, 46, 20, 10,
                Component.CENTER_ALIGNMENT, ColorPalette.LIGHT_ONE, ColorPalette.DARK_TWO);
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
                button.setBackgroundColor(ColorPalette.DARK_TWO);
                button.setColor(ColorPalette.LIGHT_ONE);
                button.removeButtonBehavior();
            } else {
                button.setBackgroundColor(ColorPalette.LIGHT_ONE);
                button.setColor(ColorPalette.DARK_TWO);
                button.removeButtonBehavior(); // Remove existing behavior
                button.addButtonBehavior(() -> selectButton(label));
            }
        }
    }
}