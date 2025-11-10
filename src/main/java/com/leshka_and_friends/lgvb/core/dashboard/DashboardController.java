/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.dashboard;

import com.leshka_and_friends.lgvb.view.customer.dashboard.DepositPanel;
import com.leshka_and_friends.lgvb.view.customer.dashboard.TransferPanel;
import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import java.awt.event.ActionEvent;

/**
 * @author giann
 */
public class DashboardController {
    AppFacade facade;
    MainView mainView;
    private DepositPanel depositPanel;
    private TransferPanel transferPanel;

    public DashboardController(AppFacade facade, MainView mainView) {
        this.facade = facade;
        this.mainView = mainView;

        depositPanel = mainView.getDepositPanel();
        transferPanel = mainView.getTransferPanel();

        mainView.getDashboardPanel().getPlusButton().addActionListener((ActionEvent e) -> {
            mainView.showDepositPanel();
        });

        mainView.getDashboardPanel().getMenuItemButton("Send").addActionListener(() -> {
            mainView.showTransferPanel();
        });

        handleDeposit();
        handleTransfer();
    }

    private void handleDeposit() {
        depositPanel.getBtnConfirm().addActionListener(e -> {
            try {
                facade.deposit(facade.getSessionManager().getCurrentSession().getWallet(),
                        depositPanel.getAmountField().getAmountValue());
                OutputUtils.showInfo("Deposit Success!");
            } catch (Exception ex) {
                OutputUtils.showError("Error occured during deposit:\n" + ex.getMessage());
            }
        });
    }

    private void handleTransfer() {
        transferPanel.getBtnConfirm().addActionListener(e -> {
            try {
                String recipientEmail = transferPanel.getFieldRecipient().getText().trim();
                double transferAmount = transferPanel.getAmountField().getAmountValue();
                facade.transfer(facade.getSessionManager().getCurrentSession().getWallet(), recipientEmail, transferAmount);
                OutputUtils.showInfo("Transfer Success!");
            } catch (Exception ex) {
                OutputUtils.showError("Error occured during transfer:\n" + ex.getMessage());
            }
        });
    }
}
