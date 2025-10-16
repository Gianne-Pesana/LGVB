package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.user.*;
import com.leshka_and_friends.lgvb.account.*;
import com.leshka_and_friends.lgvb.card.*;
import com.leshka_and_friends.lgvb.core.PasswordUtils;
import java.time.LocalDate;

public class RegistrationService {

    private final UserService userService;
    private final AccountService accountService;
    private final CardService cardService;

    public RegistrationService(UserService userService, AccountService accountService, CardService cardService) {
        this.userService = userService;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    public void registerCustomer(String email, char[] password,
                                 String firstName, String lastName,
                                 String phone, LocalDate dob)
            throws RegistrationException {

        try {
            String hashed = PasswordUtils.hashPassword(password);

            User user = new User();
            user.setEmail(email);
            user.setPasswordHash(hashed);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phone);
            user.setDateOfBirth(java.sql.Date.valueOf(dob));
            user.setRole(Role.CUSTOMER);
            user.setImagePath("/profile/default.jpg");

            // Create user
            user = userService.addUser(user);
            if (user == null) throw new RegistrationException("Failed to create user.");

            // Create default account
            Account acc = accountService.createDefaultAccount(user.getUserId());
            if (acc == null) throw new RegistrationException("Failed to create account.");

            // Create card for account
            Card card = cardService.createCardForAccount(acc.getAccountId());
            if (card == null) throw new RegistrationException("Failed to create card.");

            System.out.printf("Registered: UserID=%d, AccountID=%d, CardID=%d%n",
                    user.getUserId(), acc.getAccountId(), card.getCardId());

        } catch (Exception e) {
            throw new RegistrationException("Registration failed: " + e.getMessage(), e);
        }
    }
}
