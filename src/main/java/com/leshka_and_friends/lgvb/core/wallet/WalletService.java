package com.leshka_and_friends.lgvb.core.wallet;

import com.leshka_and_friends.lgvb.core.admin.WalletWithUser;
import com.leshka_and_friends.lgvb.core.app.ServiceLocator;
import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.core.transaction.TransactionService;
import com.leshka_and_friends.lgvb.core.transaction.TransactionStatus;
import com.leshka_and_friends.lgvb.core.transaction.TransactionType;
import com.leshka_and_friends.lgvb.exceptions.PersistenceException;
import com.leshka_and_friends.lgvb.notification.NotificationManager;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class WalletService {

    private final double minimumDepositAmount = 50.0;
    private final double maximumDepositAmount = 500_000.00;

    private final WalletDAO walletRepo;
    private final TransactionService transactionService;
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public WalletService(WalletDAO walletRepo, TransactionService transactionService) {
        this.walletRepo = walletRepo;
        this.transactionService = transactionService;
    }

    public Wallet getWalletByUserId(int userId) {
        return walletRepo.getWalletByUserId(userId);
    }

    public static String generateAccountNumber() {
        long timePart = Instant.now().toEpochMilli() % 1_000_000;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        String accountNumber = sb.toString() + String.format("%06d", timePart);
        return accountNumber.substring(0, 10);
    }

    public Wallet createDefaultWallet(int userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAccountNumber(generateAccountNumber());
        wallet.setBalance(0.0);
        wallet.setStatus(Wallet.ACTIVE);

        return walletRepo.addWallet(wallet);
    }

    public void updateWalletBalance(int walletId, double newBalance) throws SQLException {
        walletRepo.updateWalletBalance(walletId, newBalance);
    }

    public void updateWalletStatus(int walletId, String newStatus) {
        Wallet w = walletRepo.getWalletById(walletId);
        if (w != null) {
            w.setStatus(newStatus);
            walletRepo.updateWallet(w);
        }
    }


    public void deposit(Wallet wallet, double amount)  {
        if (amount < minimumDepositAmount) {
            throw new IllegalArgumentException("Deposit amount must be at least ₱" + String.format("%,.2f", minimumDepositAmount));
        }
        if (amount > maximumDepositAmount) {
            throw new IllegalArgumentException("Deposit amount must not exceed ₱" + String.format("%,.2f", maximumDepositAmount));
        }

        try {
            wallet.deposit(amount);
            walletRepo.updateWalletBalance(wallet.getWalletId(), wallet.getBalance());

            // Record transaction
            Transaction transaction = new Transaction();
            transaction.setWalletId(wallet.getWalletId());
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setAmount(amount);
            transaction.setTimestamp(Timestamp.from(Instant.now()));
            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionService.saveTransaction(transaction);

            // Notify UI to refresh
            NotificationManager notificationManager = ServiceLocator.getInstance().getService(NotificationManager.class);
            notificationManager.notifyObservers("UI_UPDATE:TRANSACTION_COMPLETED");
        } catch (SQLException e) {
            throw new PersistenceException("Failed to update balance: " + e.getMessage(), e);
        }
    }

    public void transfer(Wallet sender, String recipientEmail, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        Wallet recipient = walletRepo.getWalletByUserEmail(recipientEmail);

        if (recipient == null) {
            throw new IllegalArgumentException("Recipient account not found.");
        }

        if (sender.getWalletId() == recipient.getWalletId()) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        try {
            sender.withdraw(amount);
            recipient.deposit(amount);
            walletRepo.updateWalletBalance(sender.getWalletId(), sender.getBalance());
            walletRepo.updateWalletBalance(recipient.getWalletId(), recipient.getBalance());

            // Record transactions
            Timestamp now = Timestamp.from(Instant.now());

            // Debit from sender (as a SENT)
            Transaction senderTx = new Transaction();
            senderTx.setWalletId(sender.getWalletId());
            senderTx.setTransactionType(TransactionType.SENT);
            senderTx.setAmount(amount);
            senderTx.setRelatedWalletId(recipient.getWalletId());
            senderTx.setTimestamp(now);
            senderTx.setStatus(TransactionStatus.SUCCESS);
            transactionService.saveTransaction(senderTx);

            // Credit to recipient (as a RECEIVED)
            Transaction recipientTx = new Transaction();
            recipientTx.setWalletId(recipient.getWalletId());
            recipientTx.setTransactionType(TransactionType.RECEIVED);
            recipientTx.setAmount(amount);
            recipientTx.setRelatedWalletId(sender.getWalletId());
            recipientTx.setTimestamp(now);
            recipientTx.setStatus(TransactionStatus.SUCCESS);
            transactionService.saveTransaction(recipientTx);

            // Notify UI to refresh
            NotificationManager notificationManager = ServiceLocator.getInstance().getService(NotificationManager.class);
            notificationManager.notifyObservers("UI_UPDATE:TRANSACTION_COMPLETED");
        } catch (SQLException e) {
            throw new PersistenceException("Failed to transfer funds: " + e.getMessage(), e);
        }
    }

    public List<WalletWithUser> getAllWalletApplications() {
        List<WalletWithUser> wallets = new ArrayList<>();
        wallets = walletRepo.getAllWalletApplications();
        return wallets;
    }
}
