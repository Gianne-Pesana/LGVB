package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.view.admin.AdminDashboard;
import com.leshka_and_friends.lgvb.view.admin.panels.WalletApplicationPanel;
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
        
        ManageWalletsController manageWalletsController = new ManageWalletsController(facade, adminDashboard);
        WalletApplicationController walletApplicationController = new WalletApplicationController(facade, adminDashboard);
    }


}
