/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.transaction;


import com.leshka_and_friends.lgvb.transaction.Transaction;
import java.util.List;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);
    Transaction getTransactionById(int id);
    List<Transaction> getTransactionsByAccountId(int accountId);
    List<Transaction> getAllTransactions();
    void deleteTransaction(int id);
}
