/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dashboard;

import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.auth.SessionService;
import com.leshka_and_friends.lgvb.account.AccountService;
import com.leshka_and_friends.lgvb.card.CardService;
import com.leshka_and_friends.lgvb.transaction.TransactionService;
import com.leshka_and_friends.lgvb.view.MainView;

/**
 *
 * @author giann
 */
public class DashboardController {
    
    MainView mainView;
    SessionService session;
    AccountService accountService;
    CardService cardService;
    TransactionService transactionService;

    public DashboardController(MainView mainView, SessionService session, AccountService accountService, CardService cardService, TransactionService transactionService) {
        this.mainView = mainView;
        this.session = session;
        this.accountService = accountService;
        this.cardService = cardService;
        this.transactionService = transactionService;
        
    }
    
    
}
