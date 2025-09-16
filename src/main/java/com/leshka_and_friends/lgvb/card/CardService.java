/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.card;

import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.card.CardDAO;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.card.Card;
import com.leshka_and_friends.lgvb.user.User;

/**
 *
 * @author giann
 */
public class CardService {

    private final CardDAO cardRepo;
    private final AccountDAO accountRepo;
    private final UserDAO userRepo;

    public CardService(CardDAO cardRepo, AccountDAO accountRepo, UserDAO userRepo) {
        this.cardRepo = cardRepo;
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    public User getUserForCard(int cardId) {
        Card card = cardRepo.getCardById(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        return userRepo.getUserById(accountRepo.getAccountById(card.getAccountId()).getUserId());
    }
}
