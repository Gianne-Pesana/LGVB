package com.leshka_and_friends.lgvb.core.loan;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.test.LoanTestPanel;
import com.leshka_and_friends.lgvb.view.test.LoanTestView;

public class LoanController {
    AppFacade facade;
    MainView mainView;

    public LoanController(AppFacade facade, MainView mainView) {
        this.facade = facade;
        this.mainView = mainView;

        mainView.getSidebarPanel().getLoanReqItem().addActionListener(() -> {

        });
    }
}
