/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.account.AccountDAO;
import com.leshka_and_friends.lgvb.account.AccountSQL;
import com.leshka_and_friends.lgvb.account.AccountService;
import com.leshka_and_friends.lgvb.card.CardDAO;
import com.leshka_and_friends.lgvb.card.CardSQL;
import com.leshka_and_friends.lgvb.card.CardService;
import com.leshka_and_friends.lgvb.dashboard.DashboardController;
import com.leshka_and_friends.lgvb.transaction.TransactionDAO;
import com.leshka_and_friends.lgvb.transaction.TransactionSQL;
import com.leshka_and_friends.lgvb.transaction.TransactionService;
import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.user.UserSQL;
import com.leshka_and_friends.lgvb.user.UserService;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.auth.AuthService;
import com.leshka_and_friends.lgvb.auth.AuthException;
import com.leshka_and_friends.lgvb.auth.SessionService;
import com.leshka_and_friends.lgvb.core.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * @author giann
 */
public class AuthController {

    private User user = null;
    private boolean loggedIn = false;
    private final AuthService auth;
    private final AccountDAO accountDAO;
    private final CardDAO cardDAO;
    private final TransactionDAO transactionDAO;
    private final UserDAO userDAO;
    private final SessionService sessionService;
    private final AccountService accountService;
    private final CardService cardService;
    private final TransactionService transactionService;
    private final UserService userService;

    public AuthController() {
        userDAO = new UserSQL();
        accountDAO = new AccountSQL();
        cardDAO = new CardSQL();
        transactionDAO = new TransactionSQL();
        auth = new AuthService(userDAO);
        sessionService = SessionService.getInstance();
        accountService = new AccountService(accountDAO, userDAO);
        cardService = new CardService(cardDAO, accountDAO, userDAO);
        transactionService = new TransactionService(transactionDAO);
        userService = new UserService(userDAO, accountDAO, cardDAO);
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
                    JOptionPane.showMessageDialog(null, "Invalid\n");
            }
        }

        if (loggedIn) {
            sessionService.login(user);

            // Main view
            MainView mainView = new MainView(userService.getUserDisplayObjects());
            System.out.println("Main View: " + mainView.getUserFullName());

            // Main controller orchestrates everything
            DashboardController mainController = new DashboardController(
                    mainView, sessionService, accountService, cardService, transactionService
            );

            mainView.setVisible(true);
        }
    }

    public void showLoginDialog() {
        String email = JOptionPane.showInputDialog("Email:");
        if (email == null) return; // User cancelled

        JPasswordField pf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(null, pf, "Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok != JOptionPane.OK_OPTION) {
            return;
        }
        char[] pwd = pf.getPassword();

        try {
            user = auth.login(email, pwd);
            loggedIn = true;
//            JOptionPane.showMessageDialog(null, "Welcome, " + user.getFullName());
        } catch (AuthException e) {
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage());
        } finally {
            java.util.Arrays.fill(pwd, '\0');
        }
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

        String firstName = StringUtils.toProperCase(JOptionPane.showInputDialog("First name:"));
        String lastName = StringUtils.toProperCase(JOptionPane.showInputDialog("Last name:"));
        String phone = JOptionPane.showInputDialog("Phone (optional):");
        String dobStr = JOptionPane.showInputDialog("Date of birth (YYYY-MM-DD):");

        try {
            LocalDate dob = LocalDate.parse(dobStr);
            auth.register(username, email, pwd, firstName, lastName, phone, dob);
            JOptionPane.showMessageDialog(null, "Registered successfully.");
        } catch (AuthException e) {
            JOptionPane.showMessageDialog(null, "Registration error: " + e.getMessage());
        } finally {
            java.util.Arrays.fill(pwd, '\0');
        }
    }

}