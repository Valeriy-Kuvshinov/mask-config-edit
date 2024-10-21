package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.json.*;

import src.configuration.*;
import src.configuration.general.*;
import src.utilities.gui.*;

// Specialized class for configuartion forms
public class InputPanelUtils {
    // Method to handle common initializeUI functionality
    public static void initCommonUI(CustPanel mainPanel, CustPanel inputsPanel) {
        // Create a new panel to center the inputsPanel horizontally
        var centerPanel = new CustPanel(new GridBagLayout(), new Color(30, 30, 30), null, null, 0, 0, 0);
        var centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(inputsPanel, centerGbc);

        mainPanel.add(centerPanel, BorderLayout.NORTH);
        mainPanel.add(Box.createVerticalGlue(), BorderLayout.CENTER);
    }

    // Customizable Input inserted into a grid form - relevant for inputs
    // Inputs created by this method are tied to a form with listeners
    public static void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int inputWidth, int maxChars, String placeholder, Color labelColor, MaskEditManager manager,
            String category, CategorySettings currentSettings) {
        gbc.insets = new Insets(10, 15, 10, 15);

        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        var label = new CustLabel(key, labelColor, 16, Component.RIGHT_ALIGNMENT);
        panel.add(label, gbc);

        // Input
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 20, 10, 15);

        var input = new CustInput(inputWidth, 54, maxChars, placeholder, Component.LEFT_ALIGNMENT);
        input.setText(value != null ? value.toString() : "");
        input.addTextChangeListener(newText -> updateSetting(manager, category, key, newText));
        panel.add(input, gbc);
    }

    // Customizable Select inserted into a grid form - relevant for inputs
    // Selects created by this method are tied to a form with listeners
    public static void addSelectRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int selectWidth, String[] options, Color labelColor, MaskEditManager manager, String category,
            CategorySettings currentSettings) {
        gbc.insets = new Insets(10, 15, 10, 15);

        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        var label = new CustLabel(key, labelColor, 16, Component.RIGHT_ALIGNMENT);
        panel.add(label, gbc);

        // Select
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 20, 10, 15);

        var select = new CustSelect(selectWidth, 54, options, value != null ? value.toString() : options[0],
                Component.LEFT_ALIGNMENT);
        select.addActionListener(e -> updateSetting(manager, category, key, select.getSelectedItem()));
        panel.add(select, gbc);
    }

    // Customizable Label inserted into a grid form - relevant for visual clarity
    public static void addSectionHeader(CustPanel panel, GridBagConstraints gbc, int row, String headerText,
            Color headerColor) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 15, 10, 15);

        var header = new CustLabel(headerText, headerColor, 24, Component.LEFT_ALIGNMENT);
        panel.add(header, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 15, 10, 15);
    }

    // Method to join String arrays, Lists, or convert objects to strings
    public static String jsonArrayToString(Object obj) {
        if (obj instanceof JSONArray) {
            var jsonArray = (JSONArray) obj;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
            return String.join(", ", list);
        } else if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            List<String> stringList = new ArrayList<>();
            for (Object item : list) {
                stringList.add(item != null ? item.toString() : "");
            }
            return String.join(", ", stringList);
        } else if (obj instanceof String[]) {
            return String.join(", ", (String[]) obj);
        }
        return obj != null ? obj.toString() : "";
    }

    // Method to update previewSettings displayed to the user while editing mask
    public static void updateSetting(MaskEditManager manager, String category, String key, Object value) {
        manager.updateSetting(category, key, value);
    }
}