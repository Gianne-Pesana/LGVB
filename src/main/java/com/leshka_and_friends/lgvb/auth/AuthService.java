/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.account.Account;
import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.auth.AuthException;
import com.leshka_and_friends.lgvb.auth.RegistrationException;
import com.leshka_and_friends.lgvb.card.Card;
import com.leshka_and_friends.lgvb.card.CardDAO;
import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.core.PasswordUtils;
import com.leshka_and_friends.lgvb.user.Role;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AuthService {

    private final UserDAO userDAO;
    private final AccountDAO accountDAO = new AccountDAO();
    private final CardDAO cardDAO = new CardDAO();
    private static final Pattern EMAIL_RE = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean isValidEmail(String email) throws AuthException {
        if (email == null || !EMAIL_RE.matcher(email).matches()) {
            throw new AuthException("Invalid email");
        }

        if (userDAO.getUserByEmail(email) != null) {
            throw new AuthException("Email already linked to an account, please log in.");
        }

        return true;
    }

    public void register(String email, char[] password,
            String firstName, String lastName, String phone, LocalDate dob) throws AuthException, RegistrationException {

        if (dob == null) {
            throw new AuthException("Date of birth required");
        }

        // Hash password
        String hashed = PasswordUtils.hashPassword(password);

        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(hashed);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPhoneNumber(phone);
        u.setDateOfBirth(java.sql.Date.valueOf(dob));
        u.setRole(Role.CUSTOMER);
        u.setImagePath("/profile/default.jpg");

        try {
            u = userDAO.addUser(u);
            if (u == null) {
                throw new RegistrationException("Failed to create user.");
            }
            System.out.println("UserID: " + u.getUserId());

            // Create default account
            Account acc = accountDAO.createDefaultAccount(u.getUserId());
            if (acc == null) {
                throw new RegistrationException("Failed to create account.");
            }
            System.out.println("AccountID: " + acc.getAccountId());

            // Create card and link to account
            Card card = cardDAO.createCardForAccount(acc.getAccountId());
            acc.setCard(card);
            System.out.println("CardID: " + card.getCardId());

            System.out.println("Registration completed successfully.");

        } catch (RegistrationException e) {
            throw e;

        } catch (Exception e) {
            throw new RegistrationException("Unexpected error during registration: " + e.getMessage(), e);
        }

    }

    public User login(String email, char[] password) throws AuthException {
        User user = userDAO.getUserByEmail(email); 
        if (user == null || !PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
            throw new AuthException("Invalid credentials");
        }

        return user;
    }

    public boolean isStrong(char[] pwd) throws AuthException {
        if (pwd == null || pwd.length == 0) {
            throw new AuthException("Please enter a password.");
        }

        if (pwd.length < 10) {
            throw new AuthException("Your password must be at least 10 characters long.");
        }

        String s = new String(pwd);
        boolean hasLower = s.chars().anyMatch(Character::isLowerCase);
        boolean hasUpper = s.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = s.chars().anyMatch(Character::isDigit);
        boolean hasSymbol = s.chars().anyMatch(c -> "!@#$%^&*()_+[]{}<>?/|".indexOf(c) >= 0);

        if (!(hasLower && hasUpper && hasDigit && hasSymbol)) {
            StringBuilder msg = new StringBuilder("Your password must include:\n");
            msg.append("• at least one lowercase letter\n");
            msg.append("• at least one uppercase letter\n");
            msg.append("• at least one number\n");
            msg.append("• at least one special character (!@#$%^&* etc.)");
            throw new AuthException(msg.toString().trim());
        }

        return true;
    }

}
