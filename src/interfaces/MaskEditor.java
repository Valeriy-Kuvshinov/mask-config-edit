package src.interfaces;

import src.configuration.general.*;

public interface MaskEditor {
    MaskSettings getLoadedSettings();

    String getMaskName();

    void showPanel(String panelName);

    CategorySettings getSettingsForCategory(String category);

    void updateSetting(String category, String key, Object value);

    void setOnPreviewAction(Runnable onPreviewAction);
}