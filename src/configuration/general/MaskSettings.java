package src.configuration.general;

import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import org.json.*;

public class MaskSettings {
    private final LinkedHashMap<String, CategorySettings> categories;
    private static final String DEFAULT_SETTINGS_PATH = "/resources/json/default_settings.json";
    public static final List<String> CATEGORY_ORDER = List.of("System", "Tor", "VPN", "VPS", "Proxy", "Hotspot");
    public static final MaskSettings DEFAULT_SETTINGS = loadDefaultSettings();

    public MaskSettings() {
        this.categories = new LinkedHashMap<>();
    }

    public void addCategory(String categoryName, CategorySettings categorySettings) {
        categories.put(categoryName, categorySettings);
    }

    public CategorySettings getCategory(String categoryName) {
        return categories.get(categoryName);
    }

    public List<String> getCategoryNames() {
        return new ArrayList<>(categories.keySet());
    }

    private static MaskSettings loadDefaultSettings() {
        var settings = new MaskSettings();
        try {
            var content = new String(Files.readAllBytes(Paths.get(
                    MaskSettings.class.getResource(DEFAULT_SETTINGS_PATH).toURI())));
            var jsonObject = new JSONObject(content);

            for (var category : CATEGORY_ORDER) {
                if (jsonObject.has(category)) {
                    var categoryObject = jsonObject.getJSONObject(category);
                    var categorySettings = new CategorySettings();

                    for (var key : categoryObject.keySet()) {
                        categorySettings.setSetting(key, categoryObject.get(key));
                    }
                    settings.addCategory(category, categorySettings);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading default settings: " +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return settings;
    }

    // Create a deep copy of default_settings to edit freely when creating new mask
    public MaskSettings copy() {
        var newSettings = new MaskSettings();
        for (var category : this.getCategoryNames()) {
            var originalCategorySettings = this.getCategory(category);
            var newCategorySettings = new CategorySettings();
            for (var key : originalCategorySettings.getSettingKeys()) {
                var value = originalCategorySettings.getSetting(key);
                // Create a deep copy of arrays
                if (value instanceof JSONArray) {
                    var originalArray = (JSONArray) value;
                    var newArray = new JSONArray();
                    for (var i = 0; i < originalArray.length(); i++) {
                        newArray.put(originalArray.get(i));
                    }
                    value = newArray;
                }
                newCategorySettings.setSetting(key, value);
            }
            newSettings.addCategory(category, newCategorySettings);
        }
        return newSettings;
    }
}