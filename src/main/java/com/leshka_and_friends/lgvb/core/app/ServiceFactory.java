package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.card.CardDAO;
import com.leshka_and_friends.lgvb.core.card.CardService;
import com.leshka_and_friends.lgvb.core.loan.LoanFacade;
import com.leshka_and_friends.lgvb.core.loan.LoanService;
import com.leshka_and_friends.lgvb.core.loan.LoanServiceImpl;
import com.leshka_and_friends.lgvb.core.loan.LoanServiceProxy;
import com.leshka_and_friends.lgvb.core.savings.SavingFacade;
import com.leshka_and_friends.lgvb.core.savings.SavingService;
import com.leshka_and_friends.lgvb.core.transaction.TransactionDAO;
import com.leshka_and_friends.lgvb.core.transaction.TransactionService;
import com.leshka_and_friends.lgvb.core.user.CustomerService;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.WalletDAO;
import com.leshka_and_friends.lgvb.core.wallet.WalletFacade;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.notification.EmailNotification;
import com.leshka_and_friends.lgvb.notification.InAppNotification;
import com.leshka_and_friends.lgvb.notification.NotificationManager;
import com.leshka_and_friends.lgvb.notification.OSNotification;

import javax.swing.*;

public class ServiceFactory {

    private static final SessionManager sessionManager = new SessionManager();

    private static final WalletDAO walletDAO = new WalletDAO();
    private static final TransactionDAO transactionDAO = new TransactionDAO();
    private static final CardDAO cardDAO = new CardDAO();

    // shared services (singletons)
    private static final WalletService walletService = new WalletService(walletDAO);
    private static final TransactionService transactionService = new TransactionService(transactionDAO);
    private static final CardService cardService = new CardService(cardDAO);

    private static NotificationManager notificationManager = new NotificationManager();


    public static CustomerService createCustomerService() {
        return new CustomerService(walletService, cardService);
    }

    // Factories for domain facades
    public static WalletFacade createWalletFacade() {
        return new WalletFacade(walletService, transactionService, notificationManager);
    }

    public static LoanFacade createLoanFacade(User user) {
        LoanService loanService = new LoanServiceProxy(new LoanServiceImpl(), user);
        return new LoanFacade(loanService, transactionService);
    }

    public static SavingFacade createSavingFacade() {
        return new SavingFacade(new SavingService(), transactionService);
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static NotificationManager getNotificationManager() {
        notificationManager.addObserver(new InAppNotification(new JPanel()));
        notificationManager.addObserver(new EmailNotification(true));
        notificationManager.addObserver(new OSNotification(true));
        return notificationManager;
    }

    public static WalletService getWalletService() {
        return walletService;
    }
}
