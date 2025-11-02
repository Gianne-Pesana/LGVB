/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.dashboard;

import com.leshka_and_friends.lgvb.TestView;
import com.leshka_and_friends.lgvb.auth.Session;
import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import java.awt.*;

/**
 *
 * @author giann
 */
public class DashboardController {
    AppFacade facade;
    MainView mainView;

    public DashboardController(AppFacade facade, MainView mainView) {
        this.facade = facade;
        this.mainView = mainView;

        mainView.getDashboardPanel().getPlusButton().addActionListener(e -> {
            TestView tv = new TestView();
            tv.setVisible(true);

            tv.depositButton.addActionListener(tve -> {
                try {
                    facade.deposit(facade.getSessionManager().getCurrentSession().getWallet(), Double.parseDouble(tv.amountField.getText()));
                    tv.statusLabel.setText("✅ Deposited: $" + String.format("%.2f", tv.depositAmount));
                    tv.statusLabel.setForeground(Color.BLUE);

                } catch (Exception ex) {
                    OutputUtils.showError("Error occured during deposit:\n" + ex.getMessage());
                    tv.statusLabel.setText("Could not deposit!");
                    tv.statusLabel.setForeground(Color.RED);
                }
            });
        });



    }
    
    
}
