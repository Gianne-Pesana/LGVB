
package com.leshka_and_friends.lgvb;

import com.leshka_and_friends.lgvb.auth.AuthController;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LGVB {

    public static boolean testing = false;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new LGVBDark());
            ThemeGlobalDefaults.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            AuthController authController = new AuthController();
            authController.start();

        });
    }
}