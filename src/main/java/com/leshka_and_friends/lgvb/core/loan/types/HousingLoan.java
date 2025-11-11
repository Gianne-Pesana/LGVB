package com.leshka_and_friends.lgvb.core.loan.types;

import com.leshka_and_friends.lgvb.core.loan.Loan;

import java.sql.Timestamp;

public class HousingLoan extends Loan {
    private String propertyAddress;
    private String developerName;

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public HousingLoan() {
    }

    public HousingLoan(int loanId, int walletId, String referenceNumber, double principal, double remainingBalance, double interestRate, String status, Timestamp createdAt, int termInMonths, String propertyAddress, String developerName) {
        super(loanId, walletId, referenceNumber, principal, remainingBalance, interestRate, status, createdAt, termInMonths);
        this.propertyAddress = propertyAddress;
        this.developerName = developerName;
    }
}