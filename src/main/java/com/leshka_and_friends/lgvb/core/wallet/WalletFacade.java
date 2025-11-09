package com.leshka_and_friends.lgvb.core.wallet;

import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.core.transaction.TransactionService;
import com.leshka_and_friends.lgvb.core.transaction.TransactionStatus;
import com.leshka_and_friends.lgvb.core.transaction.TransactionType;
import com.leshka_and_friends.lgvb.notification.NotificationManager;

public class WalletFacade {
    public WalletService walletService;
    public TransactionService transactionService;
    public NotificationManager notificationManager;

    public WalletFacade(WalletService walletService, TransactionService transactionService, NotificationManager notificationManager) {
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.notificationManager = notificationManager;
    }

    public void deposit(Wallet wallet, double amount) {
        try {
            // Delegate the entire operation to the service layer
            walletService.deposit(wallet, amount);
            notificationManager.notifyObservers("USER_NOTIFY:Deposit successful: ₱" + amount);

        } catch (IllegalArgumentException e) {
            System.err.println("Deposit aborted due to invalid input: " + e.getMessage());
            throw e;

        } catch (RuntimeException e) {
            System.err.println("Deposit failed due to system error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void transfer(Wallet sender, String recipientEmail, double amount) {
        try {
            // Delegate the entire operation to the service layer
            walletService.transfer(sender, recipientEmail, amount);
            notificationManager.notifyObservers("USER_NOTIFY:Transfer successful: ₱" + amount + " to " + recipientEmail);

        } catch (IllegalArgumentException e) {
            System.err.println("Transfer aborted due to invalid input: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            System.err.println("Transfer failed due to system error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    public void payBills(){}
    public void load() {}
}
