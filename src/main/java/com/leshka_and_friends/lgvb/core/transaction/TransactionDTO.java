package com.leshka_and_friends.lgvb.core.transaction;

import java.sql.Timestamp;

public class TransactionDTO {
    private int transactionId; // as reference number
    private String userFullName;
    private String accountNumber;
    private TransactionType transactionType;
    private double amount;
    private String relatedAccountNumber; // for transfers only
    private Timestamp timestamp;

    public TransactionDTO(int transactionId, String userFullName, String accountNumber, TransactionType transactionType, double amount, String relatedAccountNumber, Timestamp timestamp) {
        this.transactionId = transactionId;
        this.userFullName = userFullName;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.relatedAccountNumber = relatedAccountNumber;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRelatedAccountNumber() {
        return relatedAccountNumber;
    }

    public void setRelatedAccountNumber(String relatedAccountNumber) {
        this.relatedAccountNumber = relatedAccountNumber;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
