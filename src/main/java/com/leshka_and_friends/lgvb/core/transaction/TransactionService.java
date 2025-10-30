/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.transaction;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author giann
 */
public class TransactionService {

    private final TransactionDAO repo;

    public TransactionService(TransactionDAO repo) {
        this.repo = repo;
    }

    public List<Transaction> loadTransactionsForWallet(int walletId) {
        try {
            TransactionDAO dao = new TransactionDAO();
            return dao.getTransactionsByWallet(walletId);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Map<LocalDate, List<Transaction>> groupTransactionsByDate(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors
                        .groupingBy(
                                Transaction::getDate,
                                LinkedHashMap::new,
                                Collectors.toList()
                        )
                );
    }

}
