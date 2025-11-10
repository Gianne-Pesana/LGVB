package com.leshka_and_friends.lgvb.view.ui_utils;

import javax.swing.*;
import java.awt.*;

public class OutputUtils {
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(Component component, String message) {
        JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfo(Component component, String message) {
        JOptionPane.showMessageDialog(component, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmationDialog(Component component, String message, String title) {
        return JOptionPane.showConfirmDialog(component, message, title, JOptionPane.YES_NO_OPTION);
    }
}
