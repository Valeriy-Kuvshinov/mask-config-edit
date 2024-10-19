package src.configuration.general;

import java.util.*;

public class CategorySettings {
    private final LinkedHashMap<String, Object> settings;
    private final ArrayList<String> keyOrder;

    public CategorySettings() {
        this.settings = new LinkedHashMap<>();
        this.keyOrder = new ArrayList<>();
    }

    public void setSetting(String key, Object value) {
        if (!settings.containsKey(key)) {
            keyOrder.add(key);
        }
        settings.put(key, value);
    }

    public Object getSetting(String key) {
        return settings.get(key);
    }

    public List<String> getSettingKeys() {
        return new ArrayList<>(keyOrder);
    }

    // Add this method to handle nested settings
    public void setNestedSetting(String serviceKey, String nestedKey, Object value) {
        CategorySettings nestedSettings = (CategorySettings) settings.get(serviceKey);
        if (nestedSettings == null) {
            nestedSettings = new CategorySettings();
            settings.put(serviceKey, nestedSettings);
            keyOrder.add(serviceKey);
        }
        nestedSettings.setSetting(nestedKey, value);
    }
}