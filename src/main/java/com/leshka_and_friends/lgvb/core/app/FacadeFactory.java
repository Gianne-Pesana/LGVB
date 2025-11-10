package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.admin.AdminService;
import com.leshka_and_friends.lgvb.core.loan.LoanFacade;
import com.leshka_and_friends.lgvb.core.loan.LoanService;
import com.leshka_and_friends.lgvb.core.loan.LoanServiceProxy;
import com.leshka_and_friends.lgvb.core.savings.SavingFacade;
import com.leshka_and_friends.lgvb.core.savings.SavingService;
import com.leshka_and_friends.lgvb.core.transaction.TransactionService;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.WalletFacade;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.notification.NotificationManager;

public class FacadeFactory {
    public static AppFacade createAppFacade(User user) {
        return new AppFacade(
            createWalletFacade(),
            createLoanFacade(user),
            createSavingFacade(),
            ServiceLocator.getInstance().getService(SessionManager.class),
            ServiceLocator.getInstance().getService(NotificationManager.class),
            ServiceLocator.getInstance().getService(AdminService.class)
        );
    }

    public static WalletFacade createWalletFacade() {
        return new WalletFacade(
            ServiceLocator.getInstance().getService(WalletService.class),
            ServiceLocator.getInstance().getService(TransactionService.class),
            ServiceLocator.getInstance().getService(NotificationManager.class)
        );
    }

    public static LoanFacade createLoanFacade(User user) {
        LoanService loanService = new LoanServiceProxy(ServiceLocator.getInstance().getService(LoanService.class), user);
        return new LoanFacade(loanService, ServiceLocator.getInstance().getService(TransactionService.class));
    }

    public static SavingFacade createSavingFacade() {
        return new SavingFacade(
            ServiceLocator.getInstance().getService(SavingService.class),
            ServiceLocator.getInstance().getService(TransactionService.class)
        );
    }
}
