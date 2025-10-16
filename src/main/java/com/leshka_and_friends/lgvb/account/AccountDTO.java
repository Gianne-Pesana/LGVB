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
    private String accNumberMasked;
    private double balance;
    private CardDTO card;

    public AccountDTO() {
    }

    public AccountDTO(String accNumberMasked, double balance, CardDTO cards) {
        this.accNumberMasked = accNumberMasked;
        this.balance = balance;
        this.card = card;
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

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
    }

}
