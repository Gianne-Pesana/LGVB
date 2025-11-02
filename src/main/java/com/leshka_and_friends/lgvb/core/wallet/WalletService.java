package com.leshka_and_friends.lgvb.core.wallet;

import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.core.transaction.TransactionType;
import com.leshka_and_friends.lgvb.exceptions.PersistenceException;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.Instant;

public class WalletService {

    private final double minimumDepositAmount = 50.0;
    private final double maximumDepositAmount = 500_000.00;

    private final WalletDAO walletRepo;
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public WalletService(WalletDAO walletRepo) {
        this.walletRepo = walletRepo;
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

    public void deposit(Wallet wallet, double amount)  {
        if (amount < minimumDepositAmount) {
            throw new IllegalArgumentException("Deposit amount must be at least ₱" + String.format("%,.2f", minimumDepositAmount));
        }
        if (amount > maximumDepositAmount) {
            throw new IllegalArgumentException("Deposit amount must not exceed ₱" + String.format("%,.2f", maximumDepositAmount));
        }

        try {
            wallet.deposit(amount);
//            wallet.addToBalance(amount);
            walletRepo.updateWalletBalance(wallet.getWalletId(), wallet.getBalance());
        } catch (SQLException e) {
            throw new PersistenceException("Failed to update balance: " + e.getMessage(), e);
        }
    }

    public void transfer(Wallet sender, Wallet receipient, double amount) {
        try {
            if (!walletRepo.walletExists(receipient.getWalletId())) {
                throw new IllegalArgumentException("Could not find account!");
            }

            if (sender.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient balance!");
            }

//            sender.d

        } catch (SQLException e) {
            throw new PersistenceException("Failed to transfer: " + e.getMessage(), e);
        }
    }
}
