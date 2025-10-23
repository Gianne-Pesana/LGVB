/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.LGVB;
import com.leshka_and_friends.lgvb.view.authpage.AuthPage;
import com.leshka_and_friends.lgvb.view.authpage.RegistrationPanel;
import com.leshka_and_friends.lgvb.view.authpage.TwoFALinkDialog;
import com.leshka_and_friends.lgvb.account.*;
import com.leshka_and_friends.lgvb.card.*;
import com.leshka_and_friends.lgvb.transaction.*;
import com.leshka_and_friends.lgvb.user.*;
import com.leshka_and_friends.lgvb.view.*;

import javax.swing.JOptionPane;
import java.time.LocalDate;

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
    private final AuthService authService;


    private final UserService userService;

    private final AccountService accountService;
    private final CardService cardService;
    private final CustomerService customerService;

    private final SessionService sessionService;
    private final TransactionService transactionService;

    private final RegistrationService registrationService;

    private final String testEmail = "gianne@lgvb.com";
    private final char[] testPwd = "#Gianne123".toCharArray();

    private AuthPage authPage;

    private boolean isValidEmail;
    private boolean isStrongPassword;

    public AuthController() {
        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();
        CardDAO cardDAO = new CardDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        userService = new UserService(userDAO);
        authService = new AuthService(userService);
        sessionService = SessionService.getInstance();

        accountService = new AccountService(accountDAO);
        cardService = new CardService(cardDAO);
        customerService = new CustomerService(accountService, cardService);

        transactionService = new TransactionService(transactionDAO);

        registrationService = new RegistrationService(userService, accountService, cardService);
    }

    public void start() {
        authPage = new AuthPage();
        authPage.setVisible(true);

        authPage.getLoginPanel().getLoginBtn().addActionListener(e -> {
            handleLogin();
        });

        authPage.getLoginPanel().getRegisterBtn().addActionListener(e -> {
//            registerTest();
            handleRegister();
        });

    }

    private void handleLogin() {
        String email;
        char[] pwd;

        // Check for testing phase.
        // TODO: REMOVE IN PRODUCTION PHASE
        if (LGVB.testing) {
            email = testEmail;
            pwd = testPwd;
        } else {
            email = authPage.getLoginPanel().getUsernameInput();
            pwd = authPage.getLoginPanel().getPasswordInput();
        }

        try {
            user = authService.login(email, pwd);  // initial username/password check
            sessionService.login(user);

            // Show 2FA panel
            authPage.showTOTPPanel();

            // Handle TOTP verification
            authPage.getTotpPanel().getTotpVerifyButton().addActionListener(e -> {
                String code = authPage.getTotpPanel().getTotpField().getText().trim();
                try {
                    boolean verified;
                    // Check for testing phase.
                    // TODO: REMOVE IN PRODUCTION PHASE
                    if (LGVB.testing) {
                        verified = true;
                    } else {
                        verified = authService.verifyTOTP(secret, code, 1);
                    }
                    if (verified) {
                        JOptionPane.showMessageDialog(authPage, "2FA success!");

                        // Open dashboard
                        if (user.getRole() == Role.ADMIN) {
                            JOptionPane.showMessageDialog(null, "Admin login successful. Admin dashboard not yet implemented.");
                        } else if (user.getRole() == Role.CUSTOMER) {
                            CustomerDTO customerdto = customerService.buildCustomerDTO(user);
                            MainView mainView = new MainView(customerdto);
                            mainView.setVisible(true);
                        }

                        authPage.dispose();
                    } else {
                        JOptionPane.showMessageDialog(authPage, "Invalid 2FA code", "Authentication", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(authPage, "Error verifying 2FA: " + ex.getMessage(), "Authentication", JOptionPane.ERROR_MESSAGE);
                }
            });

        } catch (AuthException ae) {
            JOptionPane.showMessageDialog(null, "Login failed: " + ae.getMessage(), "Authentication", JOptionPane.ERROR_MESSAGE);
        } finally {
            java.util.Arrays.fill(pwd, '\0');
        }
    }

    private void registerTest() {
        authPage.showRegisterPanel();
    }

    private void handleRegister() {
        authPage.showRegisterPanel();
        RegistrationPanel registrationPanel = authPage.getRegistrationPanel();

        // --- NEXT BUTTON: email & password ---
        registrationPanel.getNextButton().addActionListener(nb -> {
            try {
                String email = registrationPanel.getEmail();
                char[] password = registrationPanel.getPassword();

                authService.isValidEmail(email);
                authService.isStrong(password);

                // If valid, go to next page
                registrationPanel.goToNextPage();

            } catch (AuthException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- REGISTER BUTTON: remaining fields ---
        registrationPanel.getRegisterButton().addActionListener(rb -> {
            try {
                String firstName = registrationPanel.getFirstName();
                String lastName = registrationPanel.getLastName();
                String phoneNum = registrationPanel.getPhoneNumber();
                LocalDate dob = registrationPanel.getDOB();
                boolean isTermsChecked = registrationPanel.isTermsChecked();

                registrationService.validateFirstName(firstName);
                registrationService.validateLastName(lastName);
                registrationService.validatePhoneNum(phoneNum);
                registrationService.validateDateOfBirth(dob);

                if (!isTermsChecked) {
                    JOptionPane.showMessageDialog(null, "You must accept terms and conditions for you to register!", "Terms and Conditions", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // You may want to reuse email/password from before; get them from panel again
                String email = registrationPanel.getEmail();
                char[] password = registrationPanel.getPassword();

                registrationService.registerCustomer(email, password, firstName, lastName, phoneNum, dob);

                // TODO: Generate secret and store it on db
                String otpAuthUrl = String.format(
                        "otpauth://totp/%s:%s?secret=%s&issuer=%s", issuer, username, secret, issuer
                );
                boolean linked = TwoFALinkDialog.showDialog(null, otpAuthUrl);

                if (linked) {
                    JOptionPane.showMessageDialog(null, "Registered successfully!");
                    authPage.showLoginPanel();
                } else {
                    System.out.println("User closed the dialog without confirming.");
                }

            } catch (RegistrationException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });

        authPage.setVisible(true);
    }
}
