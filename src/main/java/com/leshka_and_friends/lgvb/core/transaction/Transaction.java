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

    private int transactionId;
    private int walletId;
    private String accountName;
    private String transactionType;
    private double amount;
    private Integer relatedWalletId;
    private String relatedAccountName;
    private Timestamp timestamp;
    private String status;

    public Transaction() {
    }

    public Transaction(int transactionId, int walletId, String transactionType, double amount,
            Integer relatedWalletId, Timestamp timestamp, String status) {
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
        return switch (transactionType.toLowerCase()) {
            case "send money" ->
                "💸";
            case "cash in" ->
                "🚀";
            case "online payment" ->
                "🛒";
            case "received" ->
                "🏦";
            case "deposit" ->
                "💰";
            case "withdrawal" ->
                "💵";
            default ->
                "❓";
        };
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getRelatedWalletId() {
        return relatedWalletId;
    }

    public void setRelatedWalletId(Integer relatedWalletId) {
        this.relatedWalletId = relatedWalletId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRelatedAccountName() {
        return relatedAccountName;
    }

    public void setRelatedAccountName(String relatedAccountName) {
        this.relatedAccountName = relatedAccountName;
    }
}
