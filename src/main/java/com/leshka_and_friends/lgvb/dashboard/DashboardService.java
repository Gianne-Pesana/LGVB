/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dashboard;

import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.card.CardDAO;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.account.AccountDTO;
import com.leshka_and_friends.lgvb.card.CardDTO;
import com.leshka_and_friends.lgvb.dashboard.DashboardDTO;
import com.leshka_and_friends.lgvb.user.UserDTO;
import com.leshka_and_friends.lgvb.account.Account;
import com.leshka_and_friends.lgvb.card.CardType;
import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.auth.SessionService;
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

        userDTO.setAccounts(accountDTOs);
        
        return new DashboardDTO(userDTO);
    }

    
    
}
