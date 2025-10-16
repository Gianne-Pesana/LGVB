package com.leshka_and_friends.lgvb.account;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

public class AccountService {

    private final AccountDAO accountRepo;
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public AccountService(AccountDAO accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account getAccountByUserId(int userId) {
        return accountRepo.getAccountByUserId(userId);
    }

    public Account getAccountById(int accountId) {
        return accountRepo.getAccountById(accountId);
    }

    public Account createDefaultAccount(int userId) {
        Account acc = new Account();
        acc.setUserId(userId);
        acc.setAccountNumber(generateAccountNumber());
        acc.setBalance(0.0);
        acc.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return accountRepo.addAccount(acc);
    }

    private static String generateAccountNumber() {
        long timePart = Instant.now().toEpochMilli() % 1_000_000;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return (sb.toString() + String.format("%06d", timePart)).substring(0, 10);
    }
}
