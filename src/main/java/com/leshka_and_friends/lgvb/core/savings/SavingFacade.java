package com.leshka_and_friends.lgvb.core.savings;

import com.leshka_and_friends.lgvb.core.transaction.TransactionService;

public class SavingFacade {
    SavingService savingService;
    TransactionService transactionService;

    public SavingFacade(SavingService savingService, TransactionService transactionService) {
        this.savingService = savingService;
        this.transactionService = transactionService;
    }
}
