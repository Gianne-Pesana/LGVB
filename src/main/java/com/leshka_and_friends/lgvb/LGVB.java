/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.leshka_and_friends.lgvb;

import com.leshka_and_friends.lgvb.controller.AuthController;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.components.panels.CardPanel;
import com.leshka_and_friends.lgvb.view.test.AuthUITest;
import com.leshka_and_friends.lgvb.view.test.CardTest;
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

//        SwingUtilities.invokeLater(() -> {
//            new ThemeDemo(UIManager.getLookAndFeel()).setVisible(true);
//        });
        SwingUtilities.invokeLater(() -> {
//            cardTest();
            AuthController authController = new AuthController();
            authController.start();
//            new CardTest();
//            new MainView();
//            AuthUITest test = new AuthUITest();
//            test.showRegisterDialog();
//            test.showLoginDialog();

        });
    }

//    private static void cardTest() {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("CardPanel Test");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(500, 500);
//            frame.setLayout(new FlowLayout());
//
//            // Create a test CardPanel with dummy data
//            CardPanel testCard = new CardPanel(
//                    "Visa", // card type
//                    "1234567812345678", // card number (will auto-format as XXXX XXXX XXXX XXXX)
//                    "12/29", // expiry
//                    "John Doe" // card holder
//            );
//
//            frame.add(testCard);
//
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}
