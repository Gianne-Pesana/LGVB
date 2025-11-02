package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.core.loan.LoanController;
import com.leshka_and_friends.lgvb.core.savings.SavingsController;
import com.leshka_and_friends.lgvb.core.dashboard.DashboardController;
import com.leshka_and_friends.lgvb.view.MainView;

public class MainController {
    AppFacade facade;
    private final DashboardController dashboardController;
    private final LoanController loanController;
    private final SavingsController savingsController;
//    private final TransferController transferController;
    private final MainView mainView; // contains sidebar + main content panel

    public MainController(AppFacade facade, MainView mainView) {
        this.facade = facade;
        this.mainView = mainView;
        dashboardController = new DashboardController(facade, mainView);
        loanController = new LoanController(facade, mainView);
        savingsController = new SavingsController(facade, mainView);

//        TestTransferView transferView = new TestTransferView();
//        transferController = new TransferController(facade, transferView);
//        transferView.setVisible(true);
    }
}
