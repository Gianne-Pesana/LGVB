/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.controller;

import com.leshka_and_friends.lgvb.dao.AccountDAO;
import com.leshka_and_friends.lgvb.dao.*;
import com.leshka_and_friends.lgvb.dto.AccountDTO;
import com.leshka_and_friends.lgvb.dto.UserDTO;
import com.leshka_and_friends.lgvb.exceptions.AuthException;
import com.leshka_and_friends.lgvb.model.User;
import com.leshka_and_friends.lgvb.service.*;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.test.AuthUITest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author giann
 */
public class AuthController {

    private User user = null;
    private boolean loggedIn = false;
    private final AuthService auth;

    public AuthController() {
        UserDAO userDAO = new UserSQL();
        this.auth = new AuthService(userDAO);
    }

    public void start() {
        String[] opts = {"Register", "Login", "Exit"};

        boolean running = true;

        while (running) {
            int choice = JOptionPane.showOptionDialog(null, "Choose", "Auth Demo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);

            switch (choice) {
                case 0 ->
                    showRegisterDialog();
                case 1 -> {
                    showLoginDialog();
                    if (loggedIn) {
                        running = false; // exit the loop
                    }
                }
                case 2 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default ->
                    JOptionPane.showMessageDialog(null, "Invalid");
            }
        }

        if (loggedIn) {
            SessionService.getInstance().login(user);

            // DAOs
            AccountDAO accountDAO = new AccountSQL();
            CardDAO cardDAO = new CardSQL();
            TransactionDAO transactionDAO = new TransactionSQL();
            UserDAO userDAO = new UserSQL();

            // Services
            SessionService sessionService = SessionService.getInstance();
            AccountService accountService = new AccountService(accountDAO, userDAO);
            CardService cardService = new CardService(cardDAO, accountDAO, userDAO);
            TransactionService transactionService = new TransactionService(transactionDAO);

            // Main view
            UserService userService = new UserService(userDAO, accountDAO, cardDAO);
            MainView mainView = new MainView(userService.getUserDisplayObjects());
            System.out.println("Main View: " + mainView.getUserFullName());

            // Main controller orchestrates everything
            MainController mainController = new MainController(
                    mainView, sessionService, accountService, cardService, transactionService
            );

            mainView.setVisible(true);
        }
    }

    public void showLoginDialog() {
//        String email = JOptionPane.showInputDialog("Email:");
//        JPasswordField pf = new JPasswordField();
//        int ok = JOptionPane.showConfirmDialog(null, pf, "Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//        if (ok != JOptionPane.OK_OPTION) {
//            return;
//        }
//        char[] pwd = pf.getPassword();

        User mockUser = null;
        try {
//            String mockEmail = "gianne02@gmail.com";
//            char[] mockPwd = new String("#Gianne061320").toCharArray();
//            user = auth.login(mockEmail, mockPwd);
            mockUser = new User(
                    1, // userId
                    "lalcontin", // username
                    "5f4dcc3b5aa765d61d8327deb882cf99",
                    "Leshka", // firstName
                    "Friends", // lastName
                    "leshka@lgvb.com", // email
                    "+63-912-345-6789", // phoneNumber
                    new java.sql.Date(2003 - 1900, 4, 21), // dateOfBirth (May 21, 2003)
                    "ADMIN", // role
                    new java.sql.Timestamp(System.currentTimeMillis()), // createdAt
                    null // imagePath
            );
            user = mockUser;
            SessionService.getInstance().login(user);
            loggedIn = true;
            JOptionPane.showMessageDialog(null, "Welcome, " + user.getFullName());
        } catch (Exception e) {
            System.out.println("Error bhai");
        }
//        catch (AuthException e) {
//            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage());
//        } finally {
////            java.util.Arrays.fill(pwd, '\0');
//        }
    }

    public void showRegisterDialog() {
        String username = JOptionPane.showInputDialog("Username:");
        String email = JOptionPane.showInputDialog("Email:");
        JPasswordField pf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(null, pf, "Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok != JOptionPane.OK_OPTION) {
            return;
        }
        char[] pwd = pf.getPassword();

        String firstName = JOptionPane.showInputDialog("First name:");
        String lastName = JOptionPane.showInputDialog("Last name:");
        String phone = JOptionPane.showInputDialog("Phone (optional):");
        String dobStr = JOptionPane.showInputDialog("Date of birth (YYYY-MM-DD):");

        try {
            LocalDate dob = LocalDate.parse(dobStr);
            auth.register(username, email, pwd, firstName, lastName, phone, dob);
            JOptionPane.showMessageDialog(null, "Registered successfully.");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(null, "Invalid date format.");
        } catch (AuthException e) {
            JOptionPane.showMessageDialog(null, "Registration error: " + e.getMessage());
        } finally {
            java.util.Arrays.fill(pwd, '\0');
        }
    }

}
