/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.service;

import com.leshka_and_friends.lgvb.dao.TransactionDAO;

/**
 *
 * @author giann
 */
public class TransactionService {

    private final TransactionDAO repo;

    public TransactionService(TransactionDAO repo) {
        this.repo = repo;
    }
    
}
