package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.core.loan.LoanController;
import com.leshka_and_friends.lgvb.core.savings.SavingsController;
import com.leshka_and_friends.lgvb.core.dashboard.DashboardController;
import com.leshka_and_friends.lgvb.notification.NotificationManager;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.components.dialogs.LogoutConfirmationDialog;

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
        mainView.setVisible(true);
        dashboardController = new DashboardController(facade, mainView);
        loanController = new LoanController(facade, mainView);
        savingsController = new SavingsController(facade, mainView);
        logout();

        // Register dashboard to listen for updates
        NotificationManager notificationManager = ServiceLocator.getInstance().getService(NotificationManager.class);
        notificationManager.addObserver(mainView.getDashboardPanel());

//        TestTransferView transferView = new TestTransferView();
//        transferController = new TransferController(facade, transferView);
//        transferView.setVisible(true);
    }

    public void logout() {
        LogoutConfirmationDialog.showLogoutDialog(mainView, () -> {
            System.out.println("logged out");
        });
    }
}
