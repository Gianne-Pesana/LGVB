package com.leshka_and_friends.lgvb.core.loan.types;

import com.leshka_and_friends.lgvb.core.loan.Loan;

import java.sql.Timestamp;

public class HousingLoan extends Loan {
    private String developerName;
    private String propertyAddress;

    public HousingLoan() {
    }

    public HousingLoan(int loanId, int walletId, String referenceNumber, double principal, double remainingBalance, double interestRate, String status, Timestamp createdAt, String developerName, String propertyAddress) {
        super(loanId, walletId, referenceNumber, principal, remainingBalance, interestRate, status, createdAt);
        this.developerName = developerName;
        this.propertyAddress = propertyAddress;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    // Getters/setters
}
