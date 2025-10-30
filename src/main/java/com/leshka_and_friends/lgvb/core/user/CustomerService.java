/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.user;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.core.wallet.WalletDTO;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.exceptions.AuthException;
import com.leshka_and_friends.lgvb.core.card.Card;
import com.leshka_and_friends.lgvb.core.card.CardDTO;
import com.leshka_and_friends.lgvb.core.card.CardService;

/**
 *
 * @author giann
 */
public class CustomerService {

    private final WalletService walletService;
    private final CardService cardService;

    public CustomerService(WalletService walletService, CardService cardService) {
        this.walletService = walletService;
        this.cardService = cardService;
    }

    public CustomerDTO buildCustomerDTO(User user) throws AuthException {
        CustomerDTO customerdto = new CustomerDTO();
        Wallet wallet = walletService.getWalletByUserId(user.getUserId());
        if (wallet == null) {
            throw new AuthException("User is a customer but has no associated wallet.");
        }

        // Fetch the card using the service
        Card card = cardService.createCardForWallet(wallet.getWalletId());
        if (card == null) {
            throw new AuthException("Wallet has no associated card.");
        }

        wallet.setCard(card);

        // Now, build the DTOs with the fetched data
        CardDTO carddto = new CardDTO();
        carddto.setType(card.getCardType());
        carddto.setMaskedNumber(card.getCardLast4());
        carddto.setHolder(user.getFullName());
        carddto.setExpiryDate(card.getExpiryYear(), card.getExpiryMonth());

        WalletDTO accdto = new WalletDTO();
        accdto.setWalletId(wallet.getWalletId());
        accdto.setAccountNumber(wallet.getAccountNumber());
        accdto.setBalance(wallet.getBalance());
        accdto.setStatus(wallet.getStatus());
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
