/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.account;

import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.account.Account;
import com.leshka_and_friends.lgvb.user.User;

/**
 *
 * @author giann
 */
public class AccountService {

    private final AccountDAO accountRepo;
    private final UserDAO userRepo;

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
}
