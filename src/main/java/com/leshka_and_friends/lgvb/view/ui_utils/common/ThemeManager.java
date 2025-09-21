/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.ui_utils.common;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.factories.LookAndFeelFactory;
import com.leshka_and_friends.lgvb.view.themes.LGVBDark;
import com.leshka_and_friends.lgvb.view.themes.Theme;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author giann
 */
public class ThemeManager {
    
    private static boolean isDarkMode = (UIManager.getLookAndFeel() instanceof LGVBDark);

    // dynamic color: change upon theme change
    public static void putThemeAwareProperty(JPanel panel, String value) {
        panel.putClientProperty("FlatLaf.style", value);
    }

    public static void putThemeAwareProperty(JLabel label, String value) {
        label.putClientProperty("FlatLaf.style", value);
    }
    
    public static void toggleTheme() {
        try {
            // Switch Look and Feel
            if (isDarkMode) {
                LookAndFeelFactory.apply(Theme.LIGHT);
            } else {
                LookAndFeelFactory.apply(Theme.DARK);
            }
            isDarkMode = !isDarkMode;

            refreshAllWindows();

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
}
