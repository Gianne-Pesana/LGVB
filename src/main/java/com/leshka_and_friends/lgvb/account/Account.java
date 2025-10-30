/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.account;

import com.leshka_and_friends.lgvb.core.card.Card;
import com.leshka_and_friends.lgvb.loan.Loan;
import com.leshka_and_friends.lgvb.core.transaction.Transaction;

import java.sql.Timestamp;
import java.util.List;

public class Account {
    public static final String ACTIVE = "active";
    public static final String PENDING = "pending";
    public static final String CLOSED = "closed";

    private int accountId;
    private int userId;
    private String accountNumber;
    private double balance;
    private double interestRate;
    private String status;
    private Timestamp createdAt;

    // One-to-one
    private Card card;

    // One-to-many
    private List<Transaction> transactions;
    private List<Loan> loans;

    public Account() {
    }


    public Account(int accountId, int userId, String accountNumber, double balance, double interestRate, String status, Timestamp createdAt, Card card, List<Transaction> transactions, List<Loan> loans) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.status = status;
        this.createdAt = createdAt;
        this.card = card;
        this.transactions = transactions;
        this.loans = loans;
    }

    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public List<Loan> getLoans() {
        return loans;
    }
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }


}
