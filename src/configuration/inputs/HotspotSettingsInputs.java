package src.configuration.inputs;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import src.configuration.*;
import src.utilities.*;

public class HotspotSettingsInputs extends CustomPanel {
        private static final Color DARK_COLOR = new Color(30, 30, 30);
        private static final Color LIGHT_COLOR = new Color(220, 220, 220);

        public HotspotSettingsInputs() {
                super(new BorderLayout(), DARK_COLOR, null, null, 0, 15, 15);
                initializeUI();
        }

        private void initializeUI() {
                Map<String, Object> hotspotSettings = MaskEditingManager.getSettingsForCategory("Hotspot");
                var combinedPanel = createInputsPanel(hotspotSettings);

                // Create a new panel to center the combinedPanel horizontally
                var centerPanel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                var centerGbc = new GridBagConstraints();
                centerGbc.gridx = 0;
                centerGbc.gridy = 0;
                centerGbc.anchor = GridBagConstraints.CENTER;
                centerPanel.add(combinedPanel, centerGbc);

                add(centerPanel, BorderLayout.NORTH);
                add(Box.createVerticalGlue(), BorderLayout.CENTER);
        }

        private CustomPanel createInputsPanel(Map<String, Object> hotspotSettings) {
                var panel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0, 0, 0);
                var gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 15, 10, 15);

                addSelectRow(panel, gbc, 0, "inputInterface_hotspot_hidden",
                                hotspotSettings.get("inputInterface_hotspot_hidden"), 64, new String[] { "0", "1" });
                addInputRow(panel, gbc, 1, "inputInterface_hotspot_ssid_length",
                                hotspotSettings.get("inputInterface_hotspot_ssid_length"), 46, 1, "9");
                addInputRow(panel, gbc, 2, "inputInterface_hotspot_password_length",
                                hotspotSettings.get("inputInterface_hotspot_password_length"), 46, 1, "9");
                addInputRow(panel, gbc, 3, "inputInterface_hotspot_ssid",
                                hotspotSettings.get("inputInterface_hotspot_ssid"), 330, 32, "SSID name");
                addInputRow(panel, gbc, 4, "inputInterface_hotspot_password",
                                hotspotSettings.get("inputInterface_hotspot_password"), 330, 32, "Password");
                addSelectRow(panel, gbc, 5, "inputInterface_hotspot_ssid_policy",
                                hotspotSettings.get("inputInterface_hotspot_ssid_policy"), 330,
                                new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" });
                addSelectRow(panel, gbc, 6, "inputInterface_hotspot_password_policy",
                                hotspotSettings.get("inputInterface_hotspot_password_policy"), 330,
                                new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols" });

                return panel;
        }

        private void addInputRow(CustomPanel panel, GridBagConstraints gbc, int row, String key, Object value,
                        int inputWidth, int maxChars, String placeholder) {
                // Reset insets for each new row
                gbc.insets = new Insets(10, 15, 10, 15);

                // Label
                gbc.gridx = 0;
                gbc.gridy = row;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                var label = new CustomLabel(key, LIGHT_COLOR, 16, Component.RIGHT_ALIGNMENT);
                panel.add(label, gbc);

                // Input
                gbc.gridx = 1;
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.NONE;
                gbc.insets = new Insets(10, 20, 10, 15);

                var input = new CustomInput(inputWidth, 54, maxChars, placeholder, Component.LEFT_ALIGNMENT);
                input.setText(value != null ? value.toString() : "");
                panel.add(input, gbc);
        }

        private void addSelectRow(CustomPanel panel, GridBagConstraints gbc, int row, String key, Object value,
                        int selectWidth, String[] options) {
                // Reset insets for each new row
                gbc.insets = new Insets(10, 15, 10, 15);

                // Label
                gbc.gridx = 0;
                gbc.gridy = row;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                var label = new CustomLabel(key, LIGHT_COLOR, 16, Component.RIGHT_ALIGNMENT);
                panel.add(label, gbc);

                // Select
                gbc.gridx = 1;
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.NONE;
                gbc.insets = new Insets(10, 20, 10, 15);

                var select = new CustomSelect(selectWidth, 54, options, value != null ? value.toString() : options[0],
                                Component.LEFT_ALIGNMENT);
                panel.add(select, gbc);
        }

        // private CustomPanel createInputsPanel(Map<String, Object> hotspotSettings) {
        // var panel = new CustomPanel(new GridBagLayout(), DARK_COLOR, null, null, 0,
        // 0, 0);
        // var gbc = new GridBagConstraints();
        // gbc.insets = new Insets(10, 15, 10, 15);

        // addSelectRow(panel, gbc, 0, 2, "inputInterface_hotspot_hidden",
        // hotspotSettings.get("inputInterface_hotspot_hidden"), 64, new String[] { "0",
        // "1" });
        // addInputRow(panel, gbc, 0, 0, "inputInterface_hotspot_ssid",
        // hotspotSettings.get("inputInterface_hotspot_ssid"), 330, 32, "SSID name");
        // addInputRow(panel, gbc, 1, 2, "inputInterface_hotspot_ssid_length",
        // hotspotSettings.get("inputInterface_hotspot_ssid_length"), 46, 1, "9");
        // addSelectRow(panel, gbc, 1, 0, "inputInterface_hotspot_ssid_policy",
        // hotspotSettings.get("inputInterface_hotspot_ssid_policy"), 330,
        // new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols"
        // });
        // addInputRow(panel, gbc, 2, 0, "inputInterface_hotspot_password",
        // hotspotSettings.get("inputInterface_hotspot_password"), 330, 32, "Password");
        // addInputRow(panel, gbc, 2, 2, "inputInterface_hotspot_password_length",
        // hotspotSettings.get("inputInterface_hotspot_password_length"), 46, 1, "9");
        // addSelectRow(panel, gbc, 3, 0, "inputInterface_hotspot_password_policy",
        // hotspotSettings.get("inputInterface_hotspot_password_policy"), 330,
        // new String[] { "alpha", "num", "alphanum", "alphanum-_", "alphanumSymbols"
        // });

        // return panel;
        // }

        // private void addInputRow(CustomPanel panel, GridBagConstraints gbc, int row,
        // int col, String key, Object value,
        // int inputWidth, int maxChars, String placeholder) {
        // // Reset insets for each new row
        // gbc.insets = new Insets(10, 15, 10, 15);

        // // Label
        // gbc.gridx = col;
        // gbc.gridy = row;
        // gbc.weightx = 0;
        // gbc.anchor = GridBagConstraints.EAST;
        // gbc.fill = GridBagConstraints.HORIZONTAL;

        // var label = new CustomLabel(key, LIGHT_COLOR, 16, Component.RIGHT_ALIGNMENT);
        // panel.add(label, gbc);

        // // Input
        // gbc.gridx = col + 1;
        // gbc.weightx = 1;
        // gbc.anchor = GridBagConstraints.WEST;
        // gbc.fill = GridBagConstraints.NONE;
        // gbc.insets = new Insets(10, 20, 10, 15);

        // var input = new CustomInput(inputWidth, 54, maxChars, placeholder,
        // Component.LEFT_ALIGNMENT);
        // input.setText(value != null ? value.toString() : "");
        // panel.add(input, gbc);
        // }

        // private void addSelectRow(CustomPanel panel, GridBagConstraints gbc, int row,
        // int col, String key, Object value,
        // int selectWidth, String[] options) {
        // // Reset insets for each new row
        // gbc.insets = new Insets(10, 15, 10, 15);

        // // Label
        // gbc.gridx = col;
        // gbc.gridy = row;
        // gbc.weightx = 0;
        // gbc.anchor = GridBagConstraints.EAST;
        // gbc.fill = GridBagConstraints.HORIZONTAL;

        // var label = new CustomLabel(key, LIGHT_COLOR, 16, Component.RIGHT_ALIGNMENT);
        // panel.add(label, gbc);

        // // Select
        // gbc.gridx = col + 1;
        // gbc.weightx = 1;
        // gbc.anchor = GridBagConstraints.WEST;
        // gbc.fill = GridBagConstraints.NONE;
        // gbc.insets = new Insets(10, 20, 10, 15);

        // var select = new CustomSelect(selectWidth, 54, options, value != null ?
        // value.toString() : options[0],
        // Component.LEFT_ALIGNMENT);
        // panel.add(select, gbc);
        // }
}