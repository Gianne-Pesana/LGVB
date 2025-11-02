package com.leshka_and_friends.lgvb.core.savings;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.MainView;

public class SavingsController {
    AppFacade facade;

    public SavingsController(AppFacade facade, MainView mainView) {
        this.facade = facade;
    }
}
