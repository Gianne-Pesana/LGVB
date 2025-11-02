/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.transaction;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class Transaction {
    public static final String DEPOSIT = "deposit";
    public static final String TRANSFER = "transfer";
    public static final String PAY_BILLS = "pay_bills";


    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String PENDING = "pending";

    private int transactionId;
    private int walletId;
    private TransactionType transactionType;
    private double amount;
    private int relatedWalletId = -1;
    private String relatedAccountName;
    private Timestamp timestamp;
    private TransactionStatus status;

    public Transaction() {
    }

    public Transaction(int transactionId, int walletId, TransactionType transactionType, double amount,
                       int relatedWalletId, Timestamp timestamp, TransactionStatus status) {
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.relatedWalletId = relatedWalletId;
        this.timestamp = timestamp;
        this.status = status;
    }

    public LocalDate getDate() {
        return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalTime getTime() {
        return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public String getIcon() {
        return transactionType.getIcon();
    }

    // Getters & setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
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

    public int getRelatedWalletId() {
        return relatedWalletId;
    }

    public void setRelatedWalletId(int relatedWalletId) {
        this.relatedWalletId = relatedWalletId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getRelatedAccountName() {
        return relatedAccountName;
    }

    public void setRelatedAccountName(String relatedAccountName) {
        this.relatedAccountName = relatedAccountName;
    }
}
