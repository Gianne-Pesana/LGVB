package com.leshka_and_friends.lgvb.core.wallet;

import com.leshka_and_friends.lgvb.core.card.Card;

import java.sql.Timestamp;

public class Wallet {
    public static final String ACTIVE = "active";
    public static final String PENDING = "pending";
    public static final String CLOSED = "closed";

    private int walletId;
    private int userId;
    private String accountNumber;
    private double balance;
    private String status;
    private Card card;
    private Timestamp createdAt;


    private WalletState state;

    public Wallet() {
    }

    public Wallet(int walletId, int userId, String accountNumber, double balance, String status, Card card, Timestamp createdAt) {
        this.walletId = walletId;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.card = card;
        this.createdAt = createdAt;
        this.state = WalletStateFactory.create(status);
    }

    public void deposit(double amount) {
        state.deposit(this, amount);
    }

    public void withdraw(double amount) {
        state.withdraw(this, amount);
    }

    public void transfer(Wallet target, double amount) {
        state.transfer(this, target, amount);
    }

    public void freeze() {
        state.freeze(this);
    }

    public void close() {
        state.close(this);
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
//        this.state = WalletStateFactory.create(status);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public WalletState getState() {
        return state;
    }

    public void setState(WalletState state) {
        this.state = state;
    }

    public void setState(String status) {
        this.state = WalletStateFactory.create(status);
    }

    // Getters, setters, etc.
}


