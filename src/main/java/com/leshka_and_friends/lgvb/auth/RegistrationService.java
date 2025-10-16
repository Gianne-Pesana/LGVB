package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.account.Account;
import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.card.CardDAO;
import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.user.UserDAO;

public class RegistrationService {

    private final UserDAO userDAO = new UserDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final CardDAO cardDAO = new CardDAO();

}
