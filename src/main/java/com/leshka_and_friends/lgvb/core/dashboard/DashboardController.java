/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.dashboard;

import com.leshka_and_friends.lgvb.view.components.panels.DepositPanel;
import com.leshka_and_friends.lgvb.view.components.panels.TransferPanel;
import com.leshka_and_friends.lgvb.view.testUI.DepositTestView;
import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.testUI.TransferTestView;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author giann
 */
public class DashboardController {
    AppFacade facade;
    MainView mainView;

    public DashboardController(AppFacade facade, MainView mainView) {
        this.facade = facade;
        this.mainView = mainView;

        testDeposit();
        tranferTest();


//        mainView.getDashboardPanel().getPlusButton().addActionListener(e -> {
//            JFrame frame = new JFrame();
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            frame.setSize(1920, 1080);
//            frame.add(new DepositPanel());
//            frame.pack();
//            frame.setVisible(true);
//        });
//
//        mainView.getDashboardPanel().getMenuItemButton("Send").addActionListener(() -> {
//            JFrame frame = new JFrame();
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            frame.setSize(1920, 1080);
//            frame.add(new TransferPanel());
//            frame.pack();
//            frame.setVisible(true);
//        });
    }

    private void testDeposit() {
        mainView.getDashboardPanel().getPlusButton().addActionListener(e -> {
            DepositTestView tv = new DepositTestView();
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

    public void tranferTest() {
        mainView.getDashboardPanel().getMenuItemButton("Send").addActionListener(() -> {
            System.out.println("Send Btn Clicked");
            TransferTestView view = new TransferTestView();
            view.setVisible(true);
            view.getTransferButton().addActionListener(ae -> {
                try {
                    String recipientEmail = view.getEmailField().getText().trim();
                    double amount = Double.parseDouble(view.getAmountField().getText().trim());
                    facade.transfer(facade.getSessionManager().getCurrentSession().getWallet(), recipientEmail, amount);
                    OutputUtils.showInfo("Transfer successful!");
                } catch (Exception e) {
                    OutputUtils.showError(e.getMessage());
                }
            });
        });
    }
}
