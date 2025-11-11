package com.leshka_and_friends.lgvb.core.loan;

public interface LoanService {
    void applyForLoan(int walletId, String loanType, double amountRequested, int termInMonths, String purpose);
    void approveLoan(int loanId);
    void rejectLoan(int loanId, String reason);
    void updateLoanStatus(int loanId, String status);
    String getLatestLoanStatus(int walletId);
}
