/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.leshka_and_friends.lgvb;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.utils.ThemeGlobalDefaults;
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
            new MainView();
        });
    }
}
