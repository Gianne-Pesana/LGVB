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

        mainView.getDashboardPanel().getPlusButton().addActionListener(e -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1920, 1080);
            DepositPanel depositPanel = new DepositPanel();
            frame.add(depositPanel);
            frame.pack();
            frame.setVisible(true);

            depositPanel.getBtnConfirm().addActionListener(ca -> {
                try {
                    facade.deposit(facade.getSessionManager().getCurrentSession().getWallet(),
                            depositPanel.getAmountField().getAmountValue()
                    );
                } catch (Exception ex) {
                    OutputUtils.showError("Error occured during deposit:\n" + ex.getMessage());
                }
            });
        });

        mainView.getDashboardPanel().getMenuItemButton("Send").addActionListener(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1920, 1080);
            TransferPanel transferPanel = new TransferPanel();
            frame.add(transferPanel);
            frame.pack();
            frame.setVisible(true);

            transferPanel.getBtnConfirm().addActionListener(ca -> {
                try {
                    String recipientEmail = transferPanel.getFieldRecipient().getText().trim();
                    double transferAmount = transferPanel.getAmountField().getAmountValue();

                    facade.transfer(facade.getSessionManager().getCurrentSession().getWallet(), recipientEmail, transferAmount);
                } catch (Exception ex) {
                    OutputUtils.showError("Error occured during transfer:\n" + ex.getMessage());
                }
            });
        });
    }
}
