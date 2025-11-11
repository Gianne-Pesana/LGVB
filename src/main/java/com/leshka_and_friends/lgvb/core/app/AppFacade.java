package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.admin.AdminService;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.core.wallet.WalletFacade;
import com.leshka_and_friends.lgvb.core.loan.LoanFacade;
import com.leshka_and_friends.lgvb.core.savings.SavingFacade;
import com.leshka_and_friends.lgvb.notification.NotificationManager;

public class AppFacade {

    private final WalletFacade walletFacade;
    private final LoanFacade loanFacade;
    private final SavingFacade savingFacade;
    private final SessionManager sessionManager;
    private final NotificationManager notificationManager;
    private final AdminService adminService;

    public AppFacade(WalletFacade walletFacade,
                     LoanFacade loanFacade,
                     SavingFacade savingFacade,
                     SessionManager sessionManager,
                     NotificationManager notificationManager,
                     AdminService adminService) {
        this.walletFacade = walletFacade;
        this.loanFacade = loanFacade;
        this.savingFacade = savingFacade;
        this.sessionManager = sessionManager;
        this.notificationManager = notificationManager;
        this.adminService = adminService;
    }

    // Example of a session touch (keep-alive)
    public void touchSession() {
        sessionManager.touch();
    }

    // Delegate domain operations
    public void deposit(Wallet userWallet, double amount) {
        ensureSession();
        walletFacade.deposit(userWallet, amount);
    }

    public void transfer(Wallet sender, String recipientEmail, double amount) {
        ensureSession();
        walletFacade.transfer(sender, recipientEmail, amount);
    }

    public void applyForLoan(int walletId, String loanType, double amountRequested, int termInMonths, String purpose) {
        ensureSession();
        loanFacade.applyForLoan(walletId, loanType, amountRequested, termInMonths, purpose);
    }

    public void approveLoan(int loanId) {
        ensureSession();
        loanFacade.approveLoan(loanId);
    }

    private void ensureSession() {
        if (sessionManager.isExpired()) {
            throw new IllegalStateException("Session expired");
        }
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void logout() {
        sessionManager.endSession();
        notificationManager.removeAllObserver();

    }

    // Getters (optional)
    public WalletFacade getWalletFacade() { return walletFacade; }
    public LoanFacade getLoanFacade() { return loanFacade; }
    public SavingFacade getSavingFacade() { return savingFacade; }
    public AdminService getAdminService() { return adminService; }
}
