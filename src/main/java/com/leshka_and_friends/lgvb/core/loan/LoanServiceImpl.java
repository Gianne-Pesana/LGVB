package com.leshka_and_friends.lgvb.core.loan;

import com.leshka_and_friends.lgvb.core.app.ServiceLocator;
import com.leshka_and_friends.lgvb.core.loan.types.*;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.exceptions.PersistenceException;
import com.leshka_and_friends.lgvb.notification.NotificationManager;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class LoanServiceImpl implements LoanService {

    LoanDAO loanDAO;
    WalletService walletService;

    public LoanServiceImpl(LoanDAO loanDAO, WalletService walletService) {
        this.loanDAO = loanDAO;
        this.walletService = walletService;
    }

    @Override
    public void applyForLoan(int walletId, String loanType, double amountRequested, int termInMonths, String purpose) {

        if (!canApplyForLoan(walletId)) {
            throw new RuntimeException("User already has an ongoing loan.");
        }

        // Basic validation rules
        if (amountRequested <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        // Compute interest rate based on business logic
        double interestRate = calculateInterestRate(loanType, amountRequested);

        Loan loan;
        switch (loanType) {
            case "Personal Loan":
                PersonalLoan personalLoan = new PersonalLoan();
                personalLoan.setPurpose(purpose);
                loan = personalLoan;
                break;
            case "Housing Loan":
                loan = new HousingLoan();
                // Set housing-specific properties if any
                break;
            case "Car Loan":
                loan = new CarLoan();
                // Set car-specific properties if any
                break;
            default:
                throw new IllegalArgumentException("Invalid loan type: " + loanType);
        }

        loan.setWalletId(walletId);
        loan.setPrincipal(amountRequested);
        loan.setRemainingBalance(amountRequested);
        loan.setInterestRate(interestRate);
        loan.setStatus(Loan.PENDING);
        loan.setCreatedAt(Timestamp.from(Instant.now()));
        loan.setReferenceNumber(generateReferenceNumber());
        loan.setTermInMonths(termInMonths);

        // Insert into database
        loanDAO.insertLoan(loan);

        OutputUtils.showInfo(loanType + " application submitted. Reference: " + loan.getReferenceNumber());
    }

    @Override
    public void approveLoan(int loanId) {
        Loan loan = loanDAO.getById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("Loan not found!");
        }

        if (!Loan.PENDING.equalsIgnoreCase(loan.getStatus())) {
            throw new IllegalArgumentException("Loan is not pending.");
        }

        // Mark as active
        boolean updated = loanDAO.updateStatus(loanId, Loan.ACTIVE);
        if (!updated) throw new RuntimeException("Failed to update!");

        // Deposit to user's wallet
        Wallet wallet = walletService.getWalletByUserId(loan.getWalletId());
        if (wallet != null) {
            double newBalance = wallet.getBalance() + loan.getPrincipal();
            try {
                walletService.updateWalletBalance(wallet.getWalletId(), newBalance);
            } catch (SQLException e) {
                throw new PersistenceException("Failed to update balance", e);
            }
        }

        // Notify UI to refresh
        NotificationManager notificationManager = ServiceLocator.getInstance().getService(NotificationManager.class);
        notificationManager.notifyObservers("UI_UPDATE:TRANSACTION_COMPLETED");

        OutputUtils.showInfo("Loan " + loan.getReferenceNumber() + " approved and funds released.");
    }

    @Override
    public void updateLoanStatus(int loanId, String status) {
        Loan loan = loanDAO.getById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("Loan not found!");
        }

        if (!Loan.PENDING.equalsIgnoreCase(loan.getStatus())) {
            throw new IllegalArgumentException("Loan is not pending.");
        }

        boolean updated = loanDAO.updateStatus(loanId, status);
        if (!updated) {
            throw new RuntimeException("Failed to update loan status!");
        }

        if (Loan.ACTIVE.equalsIgnoreCase(status)) {
            // Deposit to user's wallet
            Wallet wallet = walletService.getWalletByUserId(loan.getWalletId());
            if (wallet != null) {
                double newBalance = wallet.getBalance() + loan.getPrincipal();
                try {
                    walletService.updateWalletBalance(wallet.getWalletId(), newBalance);
                } catch (SQLException e) {
                    throw new PersistenceException("Failed to update balance", e);
                }
            }
        }

        // Notify UI to refresh
        NotificationManager notificationManager = ServiceLocator.getInstance().getService(NotificationManager.class);
        notificationManager.notifyObservers("UI_UPDATE:TRANSACTION_COMPLETED");

        OutputUtils.showInfo("Loan " + loan.getReferenceNumber() + " status updated to " + status);
    }

    @Override
    public void rejectLoan(int loanId, String reason) {
        Loan loan = loanDAO.getById(loanId);
        if (loan == null) {
            System.err.println("Loan not found.");
        }

        if (!Loan.PENDING.equalsIgnoreCase(loan.getStatus())) {
            System.err.println("Loan is not pending.");
        }

        boolean updated = loanDAO.updateStatus(loanId, Loan.CLOSED);
        if (updated) {
            System.out.println("Loan " + loan.getReferenceNumber() + " rejected. Reason: " + reason);
        }
    }

    // =============================
    // BUSINESS LOGIC HELPERS
    // =============================

    private boolean canApplyForLoan(int walletId) {
        try {
            String status = loanDAO.getStatusLatest(walletId);
            if(status == null || status.equalsIgnoreCase("closed")) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        List<Loan> loans = loanDAO.getByWalletId(walletId);
//        return loans.stream()
//                .noneMatch(l -> Loan.ACTIVE.equalsIgnoreCase(l.getStatus()) ||
//                        Loan.PENDING.equalsIgnoreCase(l.getStatus()));
        return false;
    }


    @Override
    public String getLatestLoanStatus(int walletId) {
        try {
            return loanDAO.getStatusLatest(walletId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a unique reference number for each loan.
     * Format example: LN-2025-ABCDE1234
     */
    private String generateReferenceNumber() {
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "LN-" + java.time.Year.now() + "-" + random;
    }

    /**
     * Basic logic for determining interest rate.
     * Example rule: smaller loans → higher rates, larger → lower.
     */
    private double calculateInterestRate(String loanType, double amount) {
        return switch (loanType) {
            case "Personal Loan" -> 8.5;
            case "Housing Loan" -> 5.0;
            case "Car Loan" -> 6.5;
            default -> 10.0; // Default rate
        };
    }

    /**
     * Calculates total repayment amount (principal + interest).
     */
    public double calculateTotalRepayment(Loan loan) {
        double interest = loan.getPrincipal() * (loan.getInterestRate() / 100);
        return loan.getPrincipal() + interest;
    }

    // =============================
    // FUTURE EXTENSION POINTS
    // =============================
    // - calculateAmortizationSchedule()
    // - applyCarLoan()
    // - applyHousingLoan()
    // - addLoanRepayment()
}

