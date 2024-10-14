package src.configuration.utilities;

import java.awt.*;
import java.util.stream.*;
import java.util.List;

import src.utilities.*;

// Specialized class for configuartion forms
public class InputPanelUtils {
    // Customizable Input inserted into a grid form - relevant for inputs
    public static void addInputRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int inputWidth, int maxChars, String placeholder, Color labelColor) {
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
        panel.add(input, gbc);
    }

    // Customizable Select inserted into a grid form - relevant for inputs
    public static void addSelectRow(CustPanel panel, GridBagConstraints gbc, int row, String key, Object value,
            int selectWidth, String[] options, Color labelColor) {
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
    public static String joinStringArray(Object obj) {
        if (obj instanceof String[]) {
            return String.join(", ", (String[]) obj);
        } else if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty() && list.get(0) instanceof String) {
                @SuppressWarnings("unchecked")
                List<String> stringList = (List<String>) list;
                return String.join(", ", stringList);
            }
            return list.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        } else if (obj != null) {
            return obj.toString();
        }
        return "";
    }
}