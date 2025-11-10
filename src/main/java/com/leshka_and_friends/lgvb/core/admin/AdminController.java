package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.view.admin.AdminDashboard;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import javax.swing.*;

public class AdminController {
    AppFacade facade;
    AdminDashboard adminDashboard;
    private User searchedUser;
    private Wallet wallet;

    public AdminController(AppFacade facade, AdminDashboard adminDashboard) {
        this.facade = facade;
        this.adminDashboard = adminDashboard;
        
        handleManageWallets();
    }

    private void handleManageWallets() {
        adminDashboard.getManageWalletsPanel().getSearchButton().addActionListener(e -> {
            String email = adminDashboard.getManageWalletsPanel().getSearchField().getText();
            if (email.isEmpty()) {
                OutputUtils.showInfo(adminDashboard, "Please enter an email to search.");
                return;
            }

            searchedUser = facade.getAdminService().searchUserByEmail(email);
            wallet = facade.getWalletFacade().walletService.getWalletByUserId(searchedUser.getUserId());
            if (searchedUser == null) {
                OutputUtils.showInfo(adminDashboard, "User not found.");
                return;
            }

            displayWalletInfo();
        });

        adminDashboard.getManageWalletsPanel().getFreezeButton().addActionListener(e -> {
            if (searchedUser == null) {
                OutputUtils.showInfo(adminDashboard, "Please search for a user first.");
                return;
            }

            int confirmation = OutputUtils.showConfirmationDialog(adminDashboard, "Are you sure you want to freeze this account?", "Freeze Account");
            if (confirmation == JOptionPane.YES_OPTION) {
                facade.getAdminService().updateUserWalletStatus(wallet, Wallet.FROZEN);
                OutputUtils.showInfo(adminDashboard, "Account has been frozen.");
                displayWalletInfo();
            }
        });

        adminDashboard.getManageWalletsPanel().getCloseButton().addActionListener(e -> {
            if (searchedUser == null) {
                OutputUtils.showInfo(adminDashboard, "Please search for a user first.");
                return;
            }

            int confirmation = OutputUtils.showConfirmationDialog(adminDashboard, "Are you sure you want to close this account?", "Close Account");
            if (confirmation == JOptionPane.YES_OPTION) {
                facade.getAdminService().updateUserWalletStatus(wallet, Wallet.CLOSED);
                OutputUtils.showInfo(adminDashboard, "Account has been closed.");
                displayWalletInfo();
            }
        });
    }

    private void displayWalletInfo() {
        if (searchedUser == null) return;

        adminDashboard.getManageWalletsPanel().setWalletInfo(
                wallet.getAccountNumber(),
                wallet.getStatus(),
                searchedUser.getEmail(),
                wallet.getCreatedAt().toString()
        );
    }
}
