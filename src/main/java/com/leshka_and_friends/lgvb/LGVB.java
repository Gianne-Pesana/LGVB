package com.leshka_and_friends.lgvb;
import com.leshka_and_friends.lgvb.auth.AuthController;
import com.leshka_and_friends.lgvb.core.DBConnection;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;

public class LGVB {

    public static boolean testing = true;

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
            AuthController authController = new AuthController();
            authController.start();
        });

    }

}
