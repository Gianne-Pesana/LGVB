package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.core.card.Card;
import com.leshka_and_friends.lgvb.core.card.CardService;
import com.leshka_and_friends.lgvb.core.user.Role;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.user.UserService;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.exceptions.RegistrationException;

import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.utils.PasswordUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class RegistrationService {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(09|\\+639)\\d{9}$");

    private final UserService userService;
    private final WalletService walletService;
    private final CardService cardService;

    public RegistrationService(UserService userService, WalletService walletService, CardService cardService) {
        this.userService = userService;
        this.walletService = walletService;
        this.cardService = cardService;
    }

    public void validateFirstName(String firstName) throws RegistrationException {
        if (firstName == null || firstName.isBlank()) {
            throw new RegistrationException("First name cannot be empty!");
        }
        if (!firstName.matches("[a-zA-Z\\-']+")) {
            throw new RegistrationException("First name contains invalid characters!");
        }
    }

    public boolean validateLastName(String lastName) throws RegistrationException {
        if (lastName == null || lastName.isBlank()) {
            throw new RegistrationException("Last name cannot be empty!");
        }
        if (!lastName.matches("[a-zA-Z\\-']+")) {
            throw new RegistrationException("Last name contains invalid characters!");
        }

        return true;
    }

    public void validatePhoneNum(String phoneNum) throws RegistrationException {
        if (phoneNum == null || phoneNum.isBlank()) {
            throw new RegistrationException("Phone number cannot be empty!");
        }
        if (!PHONE_PATTERN.matcher(phoneNum).matches()) {
            throw new RegistrationException("Phone number is invalid! Must be 09XXXXXXXXX or +639XXXXXXXXX");
        }
    }

    public void validateDateOfBirth(LocalDate dob) throws RegistrationException {
        if (dob == null) {
            throw new RegistrationException("Date of birth cannot be empty!");
        }

        LocalDate today = LocalDate.now();
        if (dob.isAfter(today)) {
            throw new RegistrationException("Date of birth cannot be in the future!");
        }

        int age = Period.between(dob, today).getYears();
        if (age < 18) {
            throw new RegistrationException("You must be at least 18 years old!");
        }
    }

    public void registerCustomer(String email, char[] password,
                                 String firstName, String lastName,
                                 String phone, LocalDate dob, String secret)
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
            user.setTotpSecret(secret);

            // Create user
            user = userService.addUser(user);
            if (user == null) throw new RegistrationException("Failed to create user.");

            // Create default wallet
            Wallet wallet = walletService.createDefaultWallet(user.getUserId());
            if (wallet == null) throw new RegistrationException("Failed to create wallet.");

            // Create card for wallet
            Card card = cardService.createCardForWallet(wallet.getWalletId());
            if (card == null) throw new RegistrationException("Failed to create card.");

            System.out.printf("Registered: UserID=%d, WalletID=%d, CardID=%d%n",
                    user.getUserId(), wallet.getWalletId(), card.getCardId());

        } catch (Exception e) {
            throw new RegistrationException("Registration failed: " + e.getMessage(), e);
        }
    }
}
