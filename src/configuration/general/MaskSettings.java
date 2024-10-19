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

            for (String category : CATEGORY_ORDER) {
                if (jsonObject.has(category)) {
                    var categoryObject = jsonObject.getJSONObject(category);
                    var categorySettings = new CategorySettings();

                    for (String key : categoryObject.keySet()) {
                        var value = categoryObject.get(key);
                        if (value instanceof JSONObject) {
                            // Handle nested objects (VPN and Proxy services)
                            var nestedSettings = new CategorySettings();
                            var nestedObject = (JSONObject) value;
                            for (String nestedKey : nestedObject.keySet()) {
                                nestedSettings.setSetting(nestedKey, nestedObject.get(nestedKey));
                            }
                            categorySettings.setSetting(key, nestedSettings);
                        } else {
                            categorySettings.setSetting(key, value);
                        }
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
        for (String category : this.getCategoryNames()) {
            var originalCategorySettings = this.getCategory(category);
            var newCategorySettings = new CategorySettings();
            for (String key : originalCategorySettings.getSettingKeys()) {
                Object value = originalCategorySettings.getSetting(key);
                if (value instanceof CategorySettings) {
                    var nestedOriginal = (CategorySettings) value;
                    var nestedNew = new CategorySettings();
                    for (String nestedKey : nestedOriginal.getSettingKeys()) {
                        nestedNew.setSetting(nestedKey, nestedOriginal.getSetting(nestedKey));
                    }
                    newCategorySettings.setSetting(key, nestedNew);
                } else {
                    newCategorySettings.setSetting(key, value);
                }
            }
            newSettings.addCategory(category, newCategorySettings);
        }
        return newSettings;
    }
}