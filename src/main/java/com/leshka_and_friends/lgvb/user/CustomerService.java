/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.user;

import com.leshka_and_friends.lgvb.account.Account;
import com.leshka_and_friends.lgvb.account.AccountDTO;
import com.leshka_and_friends.lgvb.account.AccountService;
import com.leshka_and_friends.lgvb.auth.AuthException;
import com.leshka_and_friends.lgvb.card.Card;
import com.leshka_and_friends.lgvb.card.CardDTO;
import com.leshka_and_friends.lgvb.card.CardService;

/**
 *
 * @author giann
 */
public class CustomerService {

    private final AccountService accountService;
    private final CardService cardService;

    public CustomerService(AccountService accountService, CardService cardService) {
        this.accountService = accountService;
        this.cardService = cardService;
    }

    public CustomerDTO buildCustomerDTO(User user) throws AuthException {
        CustomerDTO customerdto = new CustomerDTO();
        Account account = accountService.getAccountByUserId(user.getUserId());
        if (account == null) {
            throw new AuthException("User is a customer but has no associated account.");
        }

        // Fetch the card using the service
        Card card = cardService.getCardForAccount(account.getAccountId());
        if (card == null) {
            throw new AuthException("Account has no associated card.");
        }

        account.setCard(card);

        // Now, build the DTOs with the fetched data
        CardDTO carddto = new CardDTO();
        carddto.setType(card.getCardType());
        carddto.setMaskedNumber(card.getCardLast4());
        carddto.setHolder(user.getFullName());
        carddto.setExpiryDate(card.getExpiryYear(), card.getExpiryMonth());

        AccountDTO accdto = new AccountDTO();
        accdto.setAccountNumber(account.getAccountNumber());
        accdto.setBalance(account.getBalance());
        accdto.setInterestRate(account.getInterestRate());
        accdto.setStatus(account.getStatus());
        accdto.setCard(carddto);

        customerdto = new CustomerDTO();
        customerdto.setId(user.getUserId());
        customerdto.setFirstName(user.getFirstName());
        customerdto.setLastName(user.getLastName());
        customerdto.setProfileIconPath(user.getImagePath());
        customerdto.setAccount(accdto);

        return customerdto;
    }
}
