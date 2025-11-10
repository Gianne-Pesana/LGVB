package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.admin.AdminDashboard;

public class AdminController {
    AppFacade facade;
    AdminDashboard adminDashboard;

    public AdminController(AppFacade facade, AdminDashboard adminDashboard) {
        this.facade = facade;
        this.adminDashboard = adminDashboard;
        
        handleManageWallets();
    }

    private void handleManageWallets() {
        adminDashboard.getManageWalletsPanel().getSearchButton().addActionListener(e -> {
            // get input from admin dashboard
            // pass it to service then service -> dao
        });

        adminDashboard.getManageWalletsPanel().getFreezeButton().addActionListener(e -> {
            // service -> dao
        });

        adminDashboard.getManageWalletsPanel().getCloseButton().addActionListener(e -> {
            // service -> dao
        });
    }
}
