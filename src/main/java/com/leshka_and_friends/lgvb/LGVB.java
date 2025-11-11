package com.leshka_and_friends.lgvb;
import com.leshka_and_friends.lgvb.core.app.AppController;
import com.leshka_and_friends.lgvb.utils.DBConnection;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;

public class LGVB {

    public static boolean testing = true;
    public static String testEmail = "leshka@lgvb.com";
    public static char[] testPass = "#Leshka12345678".toCharArray();
//    public static String testEmail = "gianne@lgvb.com";
//    public static char[] testPass = "#Gianne12345678".toCharArray();

    public static void main(String[] args) {
        try {
            DBConnection.testConnection();
            UIManager.setLookAndFeel(new LGVBDark());
            ThemeGlobalDefaults.apply();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            AppController appController = new AppController();
            appController.start();
        });
    }
}
