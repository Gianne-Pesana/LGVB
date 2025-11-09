package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.card.CardDAO;
import com.leshka_and_friends.lgvb.core.card.CardService;
import com.leshka_and_friends.lgvb.core.loan.LoanDAO;
import com.leshka_and_friends.lgvb.core.loan.LoanService;
import com.leshka_and_friends.lgvb.core.loan.LoanServiceImpl;
import com.leshka_and_friends.lgvb.core.savings.SavingService;
import com.leshka_and_friends.lgvb.core.transaction.TransactionDAO;
import com.leshka_and_friends.lgvb.core.transaction.TransactionService;
import com.leshka_and_friends.lgvb.core.user.CustomerService;
import com.leshka_and_friends.lgvb.core.wallet.WalletDAO;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.notification.EmailNotification;
import com.leshka_and_friends.lgvb.notification.InAppNotification;
import com.leshka_and_friends.lgvb.notification.NotificationManager;
import com.leshka_and_friends.lgvb.notification.OSNotification;

import javax.swing.*;

public class ServiceRegistry {
    public static void registerServices() {
        // DAOs
        ServiceLocator.getInstance().registerService(WalletDAO.class, new WalletDAO());
        ServiceLocator.getInstance().registerService(TransactionDAO.class, new TransactionDAO());
        ServiceLocator.getInstance().registerService(CardDAO.class, new CardDAO());
        ServiceLocator.getInstance().registerService(LoanDAO.class, new LoanDAO());

        // Services
        ServiceLocator.getInstance().registerService(TransactionService.class, new TransactionService(ServiceLocator.getInstance().getService(TransactionDAO.class)));
        ServiceLocator.getInstance().registerService(WalletService.class, new WalletService(ServiceLocator.getInstance().getService(WalletDAO.class), ServiceLocator.getInstance().getService(TransactionService.class)));
        ServiceLocator.getInstance().registerService(CardService.class, new CardService(ServiceLocator.getInstance().getService(CardDAO.class)));
        ServiceLocator.getInstance().registerService(LoanService.class, new LoanServiceImpl(ServiceLocator.getInstance().getService(LoanDAO.class), ServiceLocator.getInstance().getService(WalletService.class)));
        ServiceLocator.getInstance().registerService(SavingService.class, new SavingService());
        ServiceLocator.getInstance().registerService(CustomerService.class, new CustomerService(ServiceLocator.getInstance().getService(WalletService.class), ServiceLocator.getInstance().getService(CardService.class)));

        // Session Manager
        ServiceLocator.getInstance().registerService(SessionManager.class, new SessionManager());

        // Notification Manager
        NotificationManager notificationManager = new NotificationManager();
        notificationManager.addObserver(new InAppNotification(new JPanel()));
        notificationManager.addObserver(new EmailNotification());
        notificationManager.addObserver(new OSNotification());

        ServiceLocator.getInstance().registerService(NotificationManager.class, notificationManager);
    }
}
