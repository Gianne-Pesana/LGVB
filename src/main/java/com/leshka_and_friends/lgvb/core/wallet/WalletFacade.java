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
        // Prepare a Transaction object that will be updated in either case
        Transaction t = new Transaction();
        t.setWalletId(wallet.getWalletId());
        t.setTransactionType(TransactionType.DEPOSIT);
        t.setAmount(amount);

        try {
            // 1. ATTEMPT DEPOSIT
            // If this line throws a RuntimeException, the flow immediately jumps to the catch blocks.
            walletService.deposit(wallet, amount);

            // 2. LOG SUCCESS
            // This code runs ONLY if the deposit operation successfully completes.
            t.setStatus(TransactionStatus.SUCCESS);
            transactionService.saveTransaction(t);

            notificationManager.notifyObservers("Deposit successful: ₱" + amount);

        } catch (IllegalArgumentException e) {
            // 3. HANDLE VALIDATION FAILURE (No Transaction Log)
            // If this is an invalid input error (e.g., amount < min, amount is zero),
            // we stop the transaction but DO NOT log it to the database as a FAILED attempt.
            System.err.println("Deposit aborted due to invalid input: " + e.getMessage());


            // Re-throw the exception to signal the caller that the operation failed due to bad data.
            throw e;

        } catch (RuntimeException e) {

            // 4. HANDLE SYSTEM/PERSISTENCE FAILURE (Log FAILED Transaction)
            // This runs for all other unchecked exceptions (e.g., system limit, DB failure, network error).
            System.err.println("Deposit failed due to system error: " + e.getMessage());
            t.setStatus(TransactionStatus.FAILED);
            transactionService.saveTransaction(t); // LOG FAILED TRANSACTION
            e.printStackTrace();
            // Re-throw the original exception to propagate the error up the call stack.
            throw e;
        }
    }

    public void transfer(Wallet sender, String recipientEmail, double amount) {
        Transaction t = new Transaction();
        t.setWalletId(sender.getWalletId());
        t.setTransactionType(TransactionType.TRANSFER);
        t.setAmount(amount);

        try {
            walletService.transfer(sender, recipientEmail, amount);

            t.setStatus(TransactionStatus.SUCCESS);
            transactionService.saveTransaction(t);

            notificationManager.notifyObservers("Transfer successful: ₱" + amount + " to " + recipientEmail);

        } catch (IllegalArgumentException e) {
            System.err.println("Transfer aborted due to invalid input: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            System.err.println("Transfer failed due to system error: " + e.getMessage());
            t.setStatus(TransactionStatus.FAILED);
            transactionService.saveTransaction(t);
            e.printStackTrace();
            throw e;
        }
    }
    public void payBills(){}
    public void load() {}
}
