/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.leshka_and_friends.lgvb;
import com.leshka_and_friends.lgvb.controller.AuthController;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.test.AuthUITest;
import com.leshka_and_friends.lgvb.view.test.CardTest;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Gianne
 */
public class LGVB {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new LGVBDark());
            ThemeGlobalDefaults.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        SwingUtilities.invokeLater(() -> {
//            new ThemeDemo(UIManager.getLookAndFeel()).setVisible(true);
//        });

        SwingUtilities.invokeLater(() -> {
            AuthController authController = new AuthController();
            authController.start();
//            new CardTest();
//            new MainView();
//            AuthUITest test = new AuthUITest();
//            test.showRegisterDialog();
//            test.showLoginDialog();
            
        });
    }
}
