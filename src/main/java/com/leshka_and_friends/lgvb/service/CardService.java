/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.service;

import com.leshka_and_friends.lgvb.dao.AccountDAO;
import com.leshka_and_friends.lgvb.dao.CardDAO;
import com.leshka_and_friends.lgvb.dao.UserDAO;
import com.leshka_and_friends.lgvb.dao.UserSQL;
import com.leshka_and_friends.lgvb.dto.CardDTO;
import com.leshka_and_friends.lgvb.model.Account;
import com.leshka_and_friends.lgvb.model.Card;
import com.leshka_and_friends.lgvb.model.User;
import java.util.ArrayList;
import java.util.List;

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

    // Fetch safe card DTOs for an account
    public List<CardDTO> getCardsForAccount(int accountId) {
        List<CardDTO> cardDtos = new ArrayList<>();

        Account account = accountRepo.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found!");
        }

        User user = userRepo.getUserById(account.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found for account!");
        }

        for (Card card : cardRepo.getCardsByAccountId(accountId)) {
            cardDtos.add(new CardDTO(
                    card.getCardType(),
                    card.getCardNumber(),
                    user.getFullName(), // ✅ resolve holder here
                    card.getExpiryDate()
            ));
        }

        return cardDtos;
    }

    public void createCard(int accountId, Card card) {
        // Ensure the account exists
        Account account = accountRepo.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        // Assign the card to the correct account (safety)
        card.setAccountId(accountId);

        cardRepo.addCard(card);
    }

    public void deleteCard(int cardId, int userId) {
        // Ensure card exists
        Card card = cardRepo.getCardById(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found!");
        }

        // Look up the account for this card
        Account account = accountRepo.getAccountById(card.getAccountId());
        if (account == null) {
            throw new IllegalStateException("Account not found for card");
        }

        // Validate that the account belongs to the requesting user
        if (account.getUserId() != userId) {
            throw new SecurityException("User not authorized to delete this card");
        }

        cardRepo.deleteCard(card.getCardId());
    }
}
