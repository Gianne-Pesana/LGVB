/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.account.*;
import com.leshka_and_friends.lgvb.card.*;
import com.leshka_and_friends.lgvb.dashboard.*;
import com.leshka_and_friends.lgvb.transaction.*;
import com.leshka_and_friends.lgvb.user.*;
import com.leshka_and_friends.lgvb.view.*;
import com.leshka_and_friends.lgvb.core.StringUtils;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * @author giann
 */
public class AuthController {

    private User user = null;
    CustomerDTO customerdto;
    private boolean loggedIn = false;
    private final AuthService auth;
    private final CustomerService customerService;
    private final SessionService sessionService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserService userService;

    private LoginPage loginPage;

    public AuthController() {
        // DAOs are now primarily used by services, not the controller.
        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();
        CardDAO cardDAO = new CardDAO();
        TransactionDAO transactionDAO = new TransactionSQL();
        
        // Services that encapsulate business logic
        auth = new AuthService(userDAO);
        sessionService = SessionService.getInstance();
        accountService = new AccountService(accountDAO, userDAO, cardDAO);
        customerService = new CustomerService(accountService);
        transactionService = new TransactionService(transactionDAO);
        userService = new UserService(userDAO); // Updated constructor

    }

    public void start() {
        loginPage = new LoginPage();
        loginPage.setVisible(true);

        loginPage.loginBtn.addActionListener(e -> {
            handleLogin();
        });

        loginPage.registerBtn.addActionListener(e -> {
            handleRegister();
        });

    }

    private void handleLogin() {
        String email = loginPage.getInputUsername();
        char[] pwd = loginPage.getInputPassword();

        try {
            user = auth.login(email, pwd);
            sessionService.login(user);

            if (user.getRole() == Role.ADMIN) {
                JOptionPane.showMessageDialog(null, "Admin login successful. Admin dashboard not yet implemented.");
                
            } else if (user.getRole() == Role.CUSTOMER) {
                CustomerDTO customerdto = customerService.buildCustomerDTO(user);
                MainView mainView = new MainView(customerdto);
                DashboardController mainController = new DashboardController(
                        mainView, sessionService, accountService, transactionService
                );

                mainView.setVisible(true);
            }

            loginPage.dispose();
        } catch (AuthException ae) {
            JOptionPane.showMessageDialog(null, "Login failed: " + ae.getMessage());
        } finally {
            java.util.Arrays.fill(pwd, '\0');
        }
    }

    private void handleRegister() {
        // Hide login page while doing registration
        loginPage.setVisible(false);

        try {
            String email;
            while (true) {
                email = JOptionPane.showInputDialog("Email:");
                if (email == null) {
                    return; // Cancelled
                }
                try {
                    auth.isValidEmail(email);
                    break; // valid, continue
                } catch (AuthException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Email", JOptionPane.ERROR_MESSAGE);
                }
            }

            char[] pwd;
            while (true) {
                JPasswordField pf = new JPasswordField();
                int ok = JOptionPane.showConfirmDialog(null, pf, "Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (ok != JOptionPane.OK_OPTION) {
                    return; // Cancelled
                }
                pwd = pf.getPassword();
                try {
                    auth.isStrong(pwd);
                    break;
                } catch (AuthException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Weak Password", JOptionPane.ERROR_MESSAGE);
                }
            }

            String firstName;
            while (true) {
                firstName = JOptionPane.showInputDialog("First name:");
                if (firstName == null) {
                    return;
                }
                firstName = StringUtils.toProperCase(firstName.trim());
                if (!firstName.isEmpty()) {
                    break;
                }
                JOptionPane.showMessageDialog(null, "First name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }

            String lastName;
            while (true) {
                lastName = JOptionPane.showInputDialog("Last name:");
                if (lastName == null) {
                    return;
                }
                lastName = StringUtils.toProperCase(lastName.trim());
                if (!lastName.isEmpty()) {
                    break;
                }
                JOptionPane.showMessageDialog(null, "Last name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }

            String phone = JOptionPane.showInputDialog("Phone (optional):");

            LocalDate dob;
            while (true) {
                String dobStr = JOptionPane.showInputDialog("Date of birth (YYYY-MM-DD):");
                if (dobStr == null) {
                    return;
                }
                try {
                    dob = LocalDate.parse(dobStr.trim());
                    break;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use YYYY-MM-DD.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }

            auth.register(email, pwd, firstName, lastName, phone, dob);
            JOptionPane.showMessageDialog(null, "Registered successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            loginPage.setVisible(true);
        }
    }

}
