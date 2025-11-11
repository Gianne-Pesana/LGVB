package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.admin.AdminDashboard;

public class LoanApplicationController {
    AppFacade facade;
    AdminDashboard view;

    public LoanApplicationController(AppFacade facade, AdminDashboard view) {
        this.facade = facade;
        this.view = view;
    }
}
