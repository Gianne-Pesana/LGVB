package com.leshka_and_friends.lgvb.core.loan;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.MainView;

public class LoanController {
    AppFacade facade;

    public LoanController(AppFacade facade, MainView mainView) {
        this.facade = facade;
    }
}
