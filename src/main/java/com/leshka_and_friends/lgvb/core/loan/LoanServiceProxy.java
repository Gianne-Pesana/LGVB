package com.leshka_and_friends.lgvb.core.loan;

import com.leshka_and_friends.lgvb.core.user.Role;
import com.leshka_and_friends.lgvb.core.user.User;

public class LoanServiceProxy implements LoanService {
    private final LoanService realService;
    private final User user;

    public LoanServiceProxy(LoanService realService, User user) {
        this.realService = realService;
        this.user = user;
    }

    @Override
    public void applyForLoan(int walletId, String loanType, double amountRequested, int termInMonths, String purpose) {
        if (isAdmin()) throw new SecurityException("Admins cannot apply for loan");
        realService.applyForLoan(walletId, loanType, amountRequested, termInMonths, purpose);
    }

    @Override
    public void approveLoan(int loanId) {
        if (!isAdmin()) {
            throw new SecurityException("Access denied: only admins can approve loans!");
        }

        realService.approveLoan(loanId);
    }

    @Override
    public void rejectLoan(int loanId, String reason) {
        if (!isAdmin()) {
            throw new SecurityException("Access denied: only admins can reject loans!");
        }

        realService.rejectLoan(loanId, reason);
    }

    @Override
    public void updateLoanStatus(int loanId, String status) {
        if (!isAdmin()) {
            throw new SecurityException("Access denied: only admins can update loan status!");
        }
        realService.updateLoanStatus(loanId, status);
    }

    private boolean isAdmin() {
        return user.getRole() == Role.ADMIN;
    }
}
