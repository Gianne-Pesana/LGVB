package com.leshka_and_friends.lgvb.core.loan.types;

import com.leshka_and_friends.lgvb.core.loan.Loan;

import java.sql.Timestamp;

public class PersonalLoan extends Loan {
    private String purpose;

    // Getters/setters
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public PersonalLoan() {
    }

    public PersonalLoan(int loanId, int walletId, String referenceNumber, double principal, double remainingBalance, double interestRate, String status, Timestamp createdAt, String purpose) {
        super(loanId, walletId, referenceNumber, principal, remainingBalance, interestRate, status, createdAt);
        this.purpose = purpose;
    }
}

