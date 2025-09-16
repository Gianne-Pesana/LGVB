/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.controller;

import com.leshka_and_friends.lgvb.model.User;
import com.leshka_and_friends.lgvb.service.*;
import com.leshka_and_friends.lgvb.view.MainView;

/**
 *
 * @author giann
 */
public class MainController {
    
    MainView mainView;
    SessionService session;
    AccountService accountService;
    CardService cardService;
    TransactionService transactionService;

    public MainController(MainView mainView, SessionService session, AccountService accountService, CardService cardService, TransactionService transactionService) {
        this.mainView = mainView;
        this.session = session;
        this.accountService = accountService;
        this.cardService = cardService;
        this.transactionService = transactionService;
        
    }
    
    
}
