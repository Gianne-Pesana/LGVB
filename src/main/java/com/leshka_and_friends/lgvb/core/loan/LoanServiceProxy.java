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
    public void applyForLoan() {
        realService.applyForLoan();
    }

    @Override
    public void approveLoan() {
        if (!isAdmin()) {
            throw new SecurityException("Access denied: only admins can approve loans!");
        }

        realService.approveLoan();
    }

    @Override
    public void rejectLoan() {
        if (!isAdmin()) {
            throw new SecurityException("Access denied: only admins can reject loans!");
        }

        realService.rejectLoan();
    }

    private boolean isAdmin() {
        return user.getRole() == Role.ADMIN;
    }
}
