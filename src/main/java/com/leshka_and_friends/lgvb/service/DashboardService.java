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
import com.leshka_and_friends.lgvb.model.User;
import java.util.List;

/**
 *
 * @author giann
 */
public class DashboardService {

    UserDAO userRepo;
    AccountDAO accountRepo;
    CardDAO cardRepo;

    public DashboardService() {
    }

    public DashboardService(UserDAO userRepo, AccountDAO accoutRepo, CardDAO cardRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accoutRepo;
        this.cardRepo = cardRepo;
    }

    public DashboardDTO getDashboardData() {
        User user = SessionService.getInstance().getCurrentUser();

        List<Account> accounts = accountRepo.getAccountsByUserId(user.getUserId());
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        List<AccountDTO> accountDTOs = accounts.stream()
                .map(acc -> {
                    AccountDTO a = new AccountDTO();
                    a.setAccNumberMasked(acc.getAccountNumber());
                    a.setBalance(acc.getBalance());

                    List<CardDTO> cards = cardRepo.getCardsByAccountId(acc.getAccountId()).stream()
                            .map(c -> new CardDTO(c.getCardType(), c.getCardNumber(), user.getFullName(), c.getExpiryDate()))
                            .toList();

                    a.setCards(cards);
                    return a;
                })
                .toList();

        userDTO.setAccounts(accountDTOs);
        
        return new DashboardDTO(userDTO);
    }

    
    
}
