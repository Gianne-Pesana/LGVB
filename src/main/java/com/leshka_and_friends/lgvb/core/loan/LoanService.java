package com.leshka_and_friends.lgvb.core.loan;

public interface LoanService {
    void applyForLoan(int walletId, double amountRequested, String purpose);
    void approveLoan(int loanId);
    void rejectLoan(int loanId, String reason);
}
