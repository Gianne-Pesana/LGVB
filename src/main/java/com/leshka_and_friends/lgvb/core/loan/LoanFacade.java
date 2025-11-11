package com.leshka_and_friends.lgvb.core.loan;

import com.leshka_and_friends.lgvb.core.transaction.TransactionService;

public class LoanFacade {
    LoanService loanService;
    TransactionService transactionService;

    public LoanFacade(LoanService loanService, TransactionService transactionService) {
        this.loanService = loanService;
        this.transactionService = transactionService;
    }


    public void applyForLoan(int walletId, String loanType, double amountRequested, int termInMonths, String purpose) {
        loanService.applyForLoan(walletId, loanType, amountRequested, termInMonths, purpose);
    }

    public void approveLoan(int loanId) {
        loanService.approveLoan(loanId);
    }

    public void updateLoanStatus(int loanId, String status) {
        loanService.updateLoanStatus(loanId, status);
    }

    public String getLatestLoanStatus(int walletId) {
        return loanService.getLatestLoanStatus(walletId);
    }

    public void rejectLoan() {

    }

    public LoanService getLoanService() {
        return loanService;
    }
}
