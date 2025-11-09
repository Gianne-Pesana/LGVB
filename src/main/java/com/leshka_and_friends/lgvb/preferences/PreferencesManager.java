package com.leshka_and_friends.lgvb.preferences;

public class PreferencesManager {

    private NotificationPreferences preferences;
    private final String userId;

    public PreferencesManager(String userId) {
        this.userId = userId;
        this.preferences = PreferencesService.loadPreferences(userId);
    }

    public NotificationPreferences getPreferences() {
        return preferences;
    }

    public void setEmail(boolean enabled) {
        preferences.setEmail(enabled);
        PreferencesService.savePreferences(userId, preferences);
    }

    public void setSms(boolean enabled) {
        preferences.setSms(enabled);
        PreferencesService.savePreferences(userId, preferences);
    }

    public void setPush(boolean enabled) {
        preferences.setPush(enabled);
        PreferencesService.savePreferences(userId, preferences);
    }

    public void save() {
        PreferencesService.savePreferences(userId, preferences);
    }
}
