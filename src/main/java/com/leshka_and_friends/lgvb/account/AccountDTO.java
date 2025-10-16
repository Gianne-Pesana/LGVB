/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.account;

import com.leshka_and_friends.lgvb.card.CardDTO;
import java.util.List;

/**
 *
 * @author giann
 */
public class AccountDTO {
    private String accountNumber;
    private double balance;
    private double interestRate;
    private String status;
    
    private CardDTO card;

    public AccountDTO() {
    }

    public AccountDTO(String accNumberMasked, double balance, CardDTO cards) {
        this.accountNumber = accNumberMasked;
        this.balance = balance;
        this.card = card;
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

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
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
    
    

}
