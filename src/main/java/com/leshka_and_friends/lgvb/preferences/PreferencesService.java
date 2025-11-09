package com.leshka_and_friends.lgvb.preferences;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PreferencesService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Load preferences from JSON file, or create default if file doesn't exist
    public static NotificationPreferences loadPreferences(String userId) {
        File file = new File(getFilePath(userId));
        if (!file.exists()) {
            NotificationPreferences defaultPrefs = new NotificationPreferences();
            savePreferences(userId, defaultPrefs);
            return defaultPrefs;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, NotificationPreferences.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new NotificationPreferences(); // fallback to default
        }
    }

    // Save preferences to JSON file
    public static void savePreferences(String userId, NotificationPreferences prefs) {
        try (FileWriter writer = new FileWriter(getFilePath(userId))) {
            gson.toJson(prefs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFilePath(String userId) {
        return "user_" + userId + "_prefs.json";
    }
}
