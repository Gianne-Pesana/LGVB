package com.leshka_and_friends.lgvb.core.loan;

import java.sql.Timestamp;

public abstract class Loan {
    public static final String ACTIVE = "active";
    public static final String PENDING = "pending";
    public static final String OVERDUE = "overdue";
    public static final String CLOSED = "closed";

    public static final String PERSONAL = "personal";
    public static final String HOUSING = "housing";
    public static final String CAR = "car";

    protected int loanId;
    protected int walletId;
    protected String referenceNumber;
    protected double principal;
    protected double remainingBalance;
    protected double interestRate;
    protected String status;
    protected Timestamp createdAt;
    protected int termInMonths;

    public Loan() {
    }

    public Loan(int loanId, int walletId, String referenceNumber, double principal, double remainingBalance, double interestRate, String status, Timestamp createdAt, int termInMonths) {
        this.loanId = loanId;
        this.walletId = walletId;
        this.referenceNumber = referenceNumber;
        this.principal = principal;
        this.remainingBalance = remainingBalance;
        this.interestRate = interestRate;
        this.status = status;
        this.createdAt = createdAt;
        this.termInMonths = termInMonths;
    }

    // Common behavior (getters, setters, maybe computed fields)
    public double getOutstandingInterest() {
        return remainingBalance * (interestRate / 100);
    }

    // Getters and setters omitted for brevity

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getTermInMonths() {
        return termInMonths;
    }

    public void setTermInMonths(int termInMonths) {
        this.termInMonths = termInMonths;
    }
}
