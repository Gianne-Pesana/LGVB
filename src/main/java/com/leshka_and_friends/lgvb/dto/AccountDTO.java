/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dto;

import java.util.List;

/**
 *
 * @author giann
 */
public class AccountDTO {
    private String accNumberMasked;
    private double balance;
    private List<CardDTO> cards;

    public AccountDTO() {
    }

    public AccountDTO(String accNumberMasked, double balance, List<CardDTO> cards) {
        this.accNumberMasked = accNumberMasked;
        this.balance = balance;
        this.cards = cards;
    }

    public String getAccNumberMasked() {
        return accNumberMasked;
    }

    public void setAccNumberMasked(String accNumberMasked) {
        this.accNumberMasked = accNumberMasked;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

}
