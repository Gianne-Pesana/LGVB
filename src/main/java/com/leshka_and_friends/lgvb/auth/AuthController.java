/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.view.authpage.AuthPage;
import com.leshka_and_friends.lgvb.view.authpage.TwoFALinkDialog;
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

    String issuer = "LGVB";
    String username = "test_user1";
    String secret = "JBSWY3DPEHPK3PXP";

    private User user = null;
    CustomerDTO customerdto;
    private boolean loggedIn = false;
    private final AuthService auth;

    private final UserService userService;

    private final AccountService accountService;
    private final CardService cardService;
    private final CustomerService customerService;

    private final SessionService sessionService;
    private final TransactionService transactionService;

    private final RegistrationService registrationService;

    private final String testEmail = "gianne@lgvb.com";
    private final char[] testPwd = "#Gianne123".toCharArray();

    private AuthPage loginPage;

    public AuthController() {
        // DAOs are now primarily used by services, not the controller.
        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();
        CardDAO cardDAO = new CardDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        // Services that encapsulate business logic
        userService = new UserService(userDAO);
        auth = new AuthService(userService);
        sessionService = SessionService.getInstance();

        accountService = new AccountService(accountDAO);
        cardService = new CardService(cardDAO);
        customerService = new CustomerService(accountService, cardService);

        transactionService = new TransactionService(transactionDAO);

        registrationService = new RegistrationService(userService, accountService, cardService);
    }

    public void start() {
        loginPage = new AuthPage();
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
//        email = testEmail;
//        pwd = testPwd;

        try {
            user = auth.login(email, pwd);  // initial username/password check
            sessionService.login(user);

            // Show 2FA panel
            loginPage.showTOTPPanel();

            // Handle TOTP verification
            loginPage.getTotpVerifyButton().addActionListener(e -> {
                String code = loginPage.getTotpField().getText().trim();
                try {
                    boolean verified;
                    verified = auth.verifyTOTP(secret, code, 1);
//                    verified = true;
                    if (verified) {
                        JOptionPane.showMessageDialog(loginPage, "2FA success!");

                        // Open dashboard
                        if (user.getRole() == Role.ADMIN) {
                            JOptionPane.showMessageDialog(null, "Admin login successful. Admin dashboard not yet implemented.");
                        } else if (user.getRole() == Role.CUSTOMER) {
                            CustomerDTO customerdto = customerService.buildCustomerDTO(user);
                            MainView mainView = new MainView(customerdto);
                            mainView.setVisible(true);
                        }

                        loginPage.dispose();
                    } else {
                        JOptionPane.showMessageDialog(loginPage, "Invalid 2FA code", "Authentication", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(loginPage, "Error verifying 2FA: " + ex.getMessage(), "Authentication", JOptionPane.ERROR_MESSAGE);
                }
            });

        } catch (AuthException ae) {
            JOptionPane.showMessageDialog(null, "Login failed: " + ae.getMessage(), "Authentication", JOptionPane.ERROR_MESSAGE);
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

            registrationService.registerCustomer(email, pwd, firstName, lastName, phone, dob);

            String otpAuthUrl = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", issuer, username, secret, issuer);

            // This line will block until user closes or presses confirm
            boolean linked = TwoFALinkDialog.showDialog(null, otpAuthUrl);

            if (linked) {
                System.out.println("User confirmed they linked their account!");
                // Continue registration flow...
            } else {
                System.out.println("User closed the dialog without confirming.");
            }

            JOptionPane.showMessageDialog(null, "Registered successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            loginPage.setVisible(true);
        }
    }

}
