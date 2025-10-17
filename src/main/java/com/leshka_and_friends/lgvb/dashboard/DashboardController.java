/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dashboard;

import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.auth.SessionService;
import com.leshka_and_friends.lgvb.account.AccountService;
import com.leshka_and_friends.lgvb.transaction.TransactionService;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.forms.Dashboard;

/**
 *
 * @author giann
 */
public class DashboardController {
    
    Dashboard dashboardView;
    SessionService session;
    AccountService accountService;
    TransactionService transactionService;

    public DashboardController(Dashboard dashboardView, SessionService session, AccountService accountService, TransactionService transactionService) {
        this.dashboardView = dashboardView;
        this.session = session;
        this.accountService = accountService;
        this.transactionService = transactionService;
        
    }
    
    
}
