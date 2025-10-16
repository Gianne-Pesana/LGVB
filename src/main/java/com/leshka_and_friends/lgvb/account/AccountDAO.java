/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.account;


import com.leshka_and_friends.lgvb.account.Account;
import java.util.List;

public interface AccountDAO {
    int addAccount(Account account);
    Account getAccountById(int id);
    List<Account> getAllAccounts();
    void updateAccount(Account account);
    void deleteAccount(int id);
    int createDefaultAccount(int userID);
    Account getAccountByNumber(String accNum);
}

