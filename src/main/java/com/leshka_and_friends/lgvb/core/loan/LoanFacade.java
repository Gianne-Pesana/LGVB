package com.leshka_and_friends.lgvb.core.loan;

import com.leshka_and_friends.lgvb.core.transaction.TransactionService;

public class LoanFacade {
    LoanService loanService;
    TransactionService transactionService;

    public LoanFacade(LoanService loanService, TransactionService transactionService) {
        this.loanService = loanService;
        this.transactionService = transactionService;
    }


    public void applyForLoan(int walletId, double amountRequested, String purpose) {
        loanService.applyForLoan(walletId, amountRequested, purpose);
    }

    public void approveLoan(int loanId) {
        loanService.approveLoan(loanId);
    }
    public void rejectLoan() {

    }
}
