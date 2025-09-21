/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.leshka_and_friends.lgvb;

import com.leshka_and_friends.lgvb.auth.AuthController;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.components.panels.CardPanel;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

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

        SwingUtilities.invokeLater(() -> {
            AuthController authController = new AuthController();
            authController.start();

        });
    }
}