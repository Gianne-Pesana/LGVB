/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.account;

import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.account.Account;
import com.leshka_and_friends.lgvb.user.User;
import java.security.SecureRandom;
import java.time.Instant;

/**
 *
 * @author giann
 */
public class AccountService {

    private final AccountDAO accountRepo;
    private final UserDAO userRepo;
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public AccountService(AccountDAO accountRepo, UserDAO userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    public User getUserForAccount(int accountId) {
        Account account = accountRepo.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        return userRepo.getUserById(account.getUserId());
    }
    
    public static String generateAccountNumber() {
        // Add timestamp entropy (milliseconds since epoch)
        long timePart = Instant.now().toEpochMilli() % 1_000_000; // 6 digits max
        StringBuilder sb = new StringBuilder();

        // Add 4 random alphanumeric characters
        for (int i = 0; i < 4; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }

        // Combine random part + time part (total 10 chars)
        String accountNumber = sb.toString() + String.format("%06d", timePart);
        return accountNumber.substring(0, 10); // ensures exact 10 characters
    }

}
