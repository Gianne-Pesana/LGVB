package com.leshka_and_friends.lgvb;
import com.leshka_and_friends.lgvb.core.app.AppController;
import com.leshka_and_friends.lgvb.utils.DBConnection;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;

public class LGVB {

    public static boolean testing = true;
    public static String[] emails = {"leshka@lgvb.com", "gianne@lgvb.com", "test123@lgvb.com"};
    public static String[] passwords = {"#Leshka12345678", "#Gianne12345678", "#Test12345678"};
//    public static String testEmail = "leshka@lgvb.com";
//    public static char[] testPass = "#Leshka12345678".toCharArray();
    static int select = 2;
    public static String testEmail;
    public static char[] testPass;

    public static void main(String[] args) {
        testEmail = emails[select];
        testPass = passwords[select].toCharArray();

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
