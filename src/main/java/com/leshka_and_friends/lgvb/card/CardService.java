/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.card;

import com.leshka_and_friends.lgvb.auth.AuthException;

/**
 *
 * @author giann
 */
public class CardService {
    CardDAO cardRepo;

    public CardService(CardDAO cardRepo) {
        this.cardRepo = cardRepo;
    }
    
    public Card getCardForAccount(int accountId) {
        return cardRepo.getCardByAccountId(accountId);
    }
    
    public Card createCardForAccount(int accountId) {
        return cardRepo.createCardForAccount(accountId);
    }
}

