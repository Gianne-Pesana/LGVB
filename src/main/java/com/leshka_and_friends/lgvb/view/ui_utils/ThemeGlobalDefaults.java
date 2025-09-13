/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.ui_utils;

import com.formdev.flatlaf.util.UIScale;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Properties;

public class ThemeGlobalDefaults {

    private static Properties config;

    // Load config only once
    private static void loadConfig() {
        if (config != null) {
            return;
        }
        try (InputStream is = ThemeGlobalDefaults.class.getResourceAsStream(
                "/com/leshka_and_friends/lgvb/view/themes/LGVB_config.properties")) {
            config = new Properties();
            config.load(is);
        } catch (Exception e) {
            e.printStackTrace();
            config = new Properties();
        }
    }

    /**
     * Apply all config values into UIManager with smart type conversion.
     */
    public static void apply() {
        loadConfig();

        for (String key : config.stringPropertyNames()) {
            String raw = config.getProperty(key).trim();
            Object parsed = parseValue(raw);
            UIManager.put(key, parsed);
        }

        // Program-defined defaults (not in config file)
        FontLoader.loadFonts();
        UIManager.put("defaultFont", FontLoader.getInter(14f));
    }

    /**
     * Parse a config value into Integer, Double, Color, Boolean, or keep as
     * String.
     */
    private static Object parseValue(String raw) {
        // Try integer
        if (raw.matches("-?\\d+")) {
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException ignored) {
            }
        }

        // Try double/float
        if (raw.matches("-?\\d*\\.\\d+")) {
            try {
                return Double.parseDouble(raw);
            } catch (NumberFormatException ignored) {
            }
        }

        // Try boolean
        if (raw.equalsIgnoreCase("true") || raw.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(raw);
        }

        // Try hex color (#RRGGBB or #AARRGGBB)
        if (raw.matches("#[0-9a-fA-F]{6}") || raw.matches("#[0-9a-fA-F]{8}")) {
            try {
                return Color.decode(raw);
            } catch (NumberFormatException ignored) {
            }
        }

        // Fallback: return raw string
        return raw;
    }

    // Convenience getters (now just wrappers around UIManager)
    public static int getInt(String key) {
        Object val = UIManager.get(key);
        return (val instanceof Number) ? ((Number) val).intValue() : 0;
    }

    public static double getDouble(String key) {
        Object val = UIManager.get(key);
        return (val instanceof Number) ? ((Number) val).doubleValue() : 0.0;
    }

    public static boolean getBoolean(String key) {
        Object val = UIManager.get(key);
        return (val instanceof Boolean) ? (Boolean) val : false;
    }

    public static Color getColor(String key) {
        Object val = UIManager.get(key);
        return (val instanceof Color) ? (Color) val : null;
    }

    public static String getString(String key) {
        Object val = UIManager.get(key);
        return (val != null) ? val.toString() : "";
    }

    // Scaled getters
    public static int getScaledInt(String key) {
        return UIScale.scale(getInt(key));
    }

    public static double getScaledDouble(String key) {
        return (double) UIScale.scale((float) getDouble(key));
    }
    
    
    

}
