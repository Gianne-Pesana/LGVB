package com.leshka_and_friends.lgvb.core.loan.types;

import com.leshka_and_friends.lgvb.core.loan.Loan;

import java.sql.Timestamp;

public class CarLoan extends Loan {
    private String carModel;
    private int carYear;
    private String dealershipName;

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public String getDealershipName() {
        return dealershipName;
    }

    public void setDealershipName(String dealershipName) {
        this.dealershipName = dealershipName;
    }

    public CarLoan() {
    }

    public CarLoan(int loanId, int walletId, String referenceNumber, double principal, double remainingBalance, double interestRate, String status, Timestamp createdAt, int termInMonths, String carModel, int carYear, String dealershipName) {
        super(loanId, walletId, referenceNumber, principal, remainingBalance, interestRate, status, createdAt, termInMonths);
        this.carModel = carModel;
        this.carYear = carYear;
        this.dealershipName = dealershipName;
    }
}