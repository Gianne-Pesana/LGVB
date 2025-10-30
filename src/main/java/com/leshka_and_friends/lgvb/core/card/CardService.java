/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.card;

/**
 *
 * @author giann
 */
public class CardService {
    CardDAO cardRepo;

    public CardService(CardDAO cardRepo) {
        this.cardRepo = cardRepo;
    }
    
    public Card getCardForWallet(int walletId) {
        return cardRepo.getCardByWalletId(walletId);
    }
    
    public Card createCardForWallet(int walletId) {
        return cardRepo.createCardForWallet(walletId);
    }
}

