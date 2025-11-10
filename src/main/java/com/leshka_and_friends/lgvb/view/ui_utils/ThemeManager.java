/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.ui_utils;

import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.TransparentScrollbar;
import com.leshka_and_friends.lgvb.view.themes.LGVBDark;
import com.leshka_and_friends.lgvb.view.themes.LGVBLight;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author giann
 */
public class ThemeManager {

    private static boolean isDarkMode = (UIManager.getLookAndFeel() instanceof LGVBDark);

    // dynamic color: change upon theme change
//    public static void putThemeAwareProperty(JPanel panel, String value) {
//        panel.putClientProperty("FlatLaf.style", value);
//    }
//
//    public static void putThemeAwareProperty(JLabel label, String value) {
//        label.putClientProperty("FlatLaf.style", value);
//    }

    public static void putThemeAwareProperty(JComponent component, String value) {
        component.putClientProperty("FlatLaf.style", value);
    }


    public static void toggleTheme() {
        try {
            if (isDarkMode) {
                UIManager.setLookAndFeel(new LGVBLight());
            } else {
                UIManager.setLookAndFeel(new LGVBDark());
            }
            isDarkMode = !isDarkMode;

            refreshAllWindows();
            reapplyThemeToAllWindows();
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void refreshAllWindows() {
        // Refresh all open windows
        for (Window w : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(w);
            w.invalidate();   // force layout to recalc
            w.validate();
            w.repaint();      // force repaint
        }
    }

    public static void reapplyThemeToAllWindows() {
        for (Window window : Window.getWindows()) {
            if (window.isDisplayable()) {
                reapplyTheme(window);
            }
        }
    }

    private static void reapplyTheme(Component c) {
        // Step 1: Apply your theme-aware colors
        if (c instanceof JComponent jc) {
            applyThemeToComponent(jc);
        }

        // Step 2: Recurse through children
        if (c instanceof Container container) {
            for (Component child : container.getComponents()) {
                reapplyTheme(child);
            }
        }

        // Optional: repaint so the colors actually show
        c.repaint();
    }

    private static void applyThemeToComponent(JComponent c) {
        Object key = c.getClientProperty("ThemeAware");

        if (key instanceof String themeKey) {
            // Re-fetch color or other theme property from your config
            Color color = ThemeGlobalDefaults.getColor(themeKey);
            c.setForeground(color);
        }

        // Example: handle scrollbars or buttons with special properties
        if (Boolean.TRUE.equals(c.getClientProperty("CustomScrollbarUI")) && c instanceof JScrollBar bar) {
            bar.setUI(new TransparentScrollbar());
        }
    }

}
