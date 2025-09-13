/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.service;

import com.leshka_and_friends.lgvb.dao.UserDAO;
import com.leshka_and_friends.lgvb.exceptions.AuthException;
import com.leshka_and_friends.lgvb.model.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AuthService {

    private final UserDAO userDAO;
    private static final Pattern EMAIL_RE = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void register(String username, String email, char[] password,
            String firstName, String lastName, String phone, LocalDate dob) throws AuthException {
        // Validate
        if (username == null || username.isBlank()) {
            throw new AuthException("Username is required");
        }
        if (email == null || !EMAIL_RE.matcher(email).matches()) {
            throw new AuthException("Invalid email");
        }
        if (!isStrong(password)) {
            throw new AuthException("Weak password: must contain upper, lower, digit, special char");
        }
        if (dob == null) {
            throw new AuthException("Date of birth required");
        }

        // Check duplicates
        try {
            if (userDAO.getUserByUsername(username) != null) {
                throw new AuthException("Username already taken");
            }
            // If you add getUserByEmail() in DAO, check email too
        } catch (Exception e) {
            throw new AuthException("Database error", e);
        }

        // Hash password
        String hashed = PasswordUtils.hashPassword(password);

        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(hashed);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPhoneNumber(phone);
        u.setDateOfBirth(java.sql.Date.valueOf(dob));
        u.setRole("customer");

        try {
            userDAO.addUser(u);
        } catch (Exception e) {
            throw new AuthException("Failed to register", e);
        }
    }

    public User login(String email, char[] password) throws AuthException {
        // You might want to implement getUserByEmail in your DAO
        User user = null;
        for (User u : userDAO.getAllUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            throw new AuthException("Invalid credentials");
        }
        boolean ok = PasswordUtils.verifyPassword(password, user.getPasswordHash());
        if (!ok) {
            throw new AuthException("Invalid credentials");
        }
        return user;
    }

    private boolean isStrong(char[] pwd) {
        if (pwd == null || pwd.length < 10) {
            return false;
        }
        String s = new String(pwd);
        boolean lower = s.chars().anyMatch(Character::isLowerCase);
        boolean upper = s.chars().anyMatch(Character::isUpperCase);
        boolean digit = s.chars().anyMatch(Character::isDigit);
        boolean special = s.chars().anyMatch(c -> "!@#$%^&*()_+[]{}<>?/|".indexOf(c) >= 0);
        return lower && upper && digit && special;
    }
}
