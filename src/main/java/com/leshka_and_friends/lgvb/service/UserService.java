/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.service;

import com.leshka_and_friends.lgvb.dao.AccountDAO;
import com.leshka_and_friends.lgvb.dao.CardDAO;
import com.leshka_and_friends.lgvb.dao.UserDAO;
import com.leshka_and_friends.lgvb.dto.AccountDTO;
import com.leshka_and_friends.lgvb.dto.CardDTO;
import com.leshka_and_friends.lgvb.dto.DashboardDTO;
import com.leshka_and_friends.lgvb.dto.UserDTO;
import com.leshka_and_friends.lgvb.model.Account;
import com.leshka_and_friends.lgvb.model.CardType;
import com.leshka_and_friends.lgvb.model.User;
import java.util.List;

/**
 *
 * @author giann
 */
public class UserService {
    UserDAO userRepo;
    AccountDAO accountRepo;
    CardDAO cardRepo;

    public UserService() {
    }

    public UserService(UserDAO userRepo, AccountDAO accoutRepo, CardDAO cardRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accoutRepo;
        this.cardRepo = cardRepo;
    }
    
    public UserDTO getUserDisplayObjects() {
        User user = SessionService.getInstance().getCurrentUser();

        List<Account> accounts = accountRepo.getAccountsByUserId(user.getUserId());

        List<AccountDTO> accountDTOs = accounts.stream()
                .map(acc -> {
                    AccountDTO a = new AccountDTO();
                    a.setAccNumberMasked(acc.getAccountNumber());
                    a.setBalance(acc.getBalance());

                    List<CardDTO> cards = cardRepo.getCardsByAccountId(acc.getAccountId()).stream()
                            .map(c -> {
                                String typeName = switch (c.getCardTypeId()) {
                                    case 1 -> "Debit";
                                    case 2 -> "Credit";
                                    case 3 -> "Prepaid";
                                    default -> "Card";
                                };
                                CardType type = new CardType(c.getCardTypeId(), typeName); 
                                return new CardDTO(type, c.getCardNumber(), user.getFullName(), c.getExpiryDate());
                            })
                            .toList();

                    a.setCards(cards);
                    return a;
                })
                .toList();

        UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName(), user.getImagePath(), accountDTOs);
        System.out.println("Service: " + userDTO.getFullName());
        userDTO.setAccounts(accountDTOs);
        return userDTO;
    }
        
}
