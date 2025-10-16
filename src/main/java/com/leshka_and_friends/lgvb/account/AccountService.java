package com.leshka_and_friends.lgvb.account;

import java.security.SecureRandom;
import java.time.Instant;

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
    
    public static String generateAccountNumber() {
        long timePart = Instant.now().toEpochMilli() % 1_000_000;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        String accountNumber = sb.toString() + String.format("%06d", timePart);
        return accountNumber.substring(0, 10);
    }
    
    public Account createDefaultAccount(int userID) {
        Account account = new Account();
        account.setUserId(userID);
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(0.0);
        account.setInterestRate(0.0);
        account.setStatus("pending");

        return accountRepo.addAccount(account);
    }
}