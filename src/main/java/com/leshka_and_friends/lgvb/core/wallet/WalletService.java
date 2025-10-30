package com.leshka_and_friends.lgvb.core.wallet;

import java.security.SecureRandom;
import java.time.Instant;

public class WalletService {

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

    public void updateWalletBalance(int walletId, double newBalance) {
        walletRepo.updateWalletBalance(walletId, newBalance);
    }
}
