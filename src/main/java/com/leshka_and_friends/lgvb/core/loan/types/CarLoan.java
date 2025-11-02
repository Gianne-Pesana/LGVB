
package com.leshka_and_friends.lgvb.core.loan.types;

import com.leshka_and_friends.lgvb.core.loan.Loan;

import java.sql.Timestamp;

public class CarLoan extends Loan {
    private String dealershipName;
    private String vehicleModel;
    private int vehicleYear;

    public CarLoan() {
    }

    public CarLoan(int loanId, int walletId, String referenceNumber, double principal, double remainingBalance, double interestRate, String status, Timestamp createdAt, String dealershipName, String vehicleModel, int vehicleYear) {
        super(loanId, walletId, referenceNumber, principal, remainingBalance, interestRate, status, createdAt);
        this.dealershipName = dealershipName;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
    }

    public String getDealershipName() {
        return dealershipName;
    }

    public void setDealershipName(String dealershipName) {
        this.dealershipName = dealershipName;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public int getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(int vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    // Getters/setters
}
