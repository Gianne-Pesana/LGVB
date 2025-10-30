/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.LGVB;
import com.leshka_and_friends.lgvb.core.card.CardDAO;
import com.leshka_and_friends.lgvb.core.card.CardService;
import com.leshka_and_friends.lgvb.core.transaction.TransactionDAO;
import com.leshka_and_friends.lgvb.core.transaction.TransactionService;
import com.leshka_and_friends.lgvb.core.user.*;
import com.leshka_and_friends.lgvb.core.wallet.WalletDAO;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.exceptions.AuthException;
import com.leshka_and_friends.lgvb.exceptions.RegistrationException;
import com.leshka_and_friends.lgvb.view.authpage.AuthPage;
import com.leshka_and_friends.lgvb.view.authpage.LoginPanel;
import com.leshka_and_friends.lgvb.view.authpage.RegistrationPanel;
import com.leshka_and_friends.lgvb.view.authpage.TwoFALinkDialog;

import com.leshka_and_friends.lgvb.view.*;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import javax.swing.JOptionPane;
import java.time.LocalDate;

/**
 * @author giann
 */
public class AuthController {

    String testIssuer = "LGVB";
    String testUsername = "test123";
    String testSecret = "JBSWY3DPEHPK3PXP";

    private User user = null;
    CustomerDTO customerdto;
    private boolean loggedIn = false;
    private final AuthService authService;

    private final UserService userService;

    private final WalletService walletService;
    private final CardService cardService;
    private final CustomerService customerService;

    private final SessionService sessionService;
    private final TransactionService transactionService;

    private final RegistrationService registrationService;

    private final String testEmail = "test123@lgvb.com";
    private final char[] testPwd = "#Test12345678".toCharArray();

    private AuthPage authPage;

    private boolean isValidEmail;
    private boolean isStrongPassword;

    LoginPanel loginPanel;
    RegistrationPanel registrationPanel;

    public AuthController() {
        UserDAO userDAO = new UserDAO();
        WalletDAO walletDAO = new WalletDAO();
        CardDAO cardDAO = new CardDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        userService = new UserService(userDAO);
        authService = new AuthService(userService);
        sessionService = SessionService.getInstance();

        walletService = new WalletService(walletDAO);
        cardService = new CardService(cardDAO);
        customerService = new CustomerService(walletService, cardService);

        transactionService = new TransactionService(transactionDAO);

        registrationService = new RegistrationService(userService, walletService, cardService);
    }

    public void start() {
        authPage = new AuthPage();
        authPage.setVisible(true);

        loginPanel = authPage.getLoginPanel();
        registrationPanel = authPage.getRegistrationPanel();

        // ------------- LOGIN --------------
        authPage.getLoginPanel().getLoginBtn().addActionListener(e -> {
            handleLogin();
        });

        // ------------- REGISTER --------------
        authPage.getLoginPanel().getRegisterBtn().addActionListener(e -> {
            authPage.showRegisterPanel();
            registrationPanel.resetFields();
        });

        // NEXT BUTTON: email & password
        registrationPanel.getNextButton().addActionListener(nb -> {
            handleNextBtn();
        });

        // REGISTER BUTTON: personal info
        registrationPanel.getRegisterButton().addActionListener(rb -> {
            handleRegisterBtn();
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
            authService.checkActive(walletService.getWalletByUserId(user.getUserId()));
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
                        verified = authService.verifyTOTP(user.getTotpSecret(), code, 1);
                    }

                    if (!verified) {
                        JOptionPane.showMessageDialog(authPage, "Invalid 2FA code", "Authentication", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    OutputUtils.showInfo(authPage, "2FA success!");

                    // Open dashboard
                    if (user.getRole() == Role.ADMIN) {
                        OutputUtils.showInfo(authPage, "Admin login successful. Admin dashboard not yet implemented.");
                    } else if (user.getRole() == Role.CUSTOMER) {
                        CustomerDTO customerdto = customerService.buildCustomerDTO(user);
                        MainView mainView = new MainView(customerdto);
                        mainView.setVisible(true);
                    }

                    authPage.dispose();
                } catch (Exception ex) {
                    OutputUtils.showError(authPage, "Error verifying 2FA: " + ex.getMessage());
                }
            });

        } catch (AuthException ae) {
            OutputUtils.showError(authPage, "Login failed: " + ae.getMessage());
        } finally {
            java.util.Arrays.fill(pwd, '\0');
            authPage.getLoginPanel().resetFields();
        }
    }

    public void handleNextBtn() {
        try {
            String email;
            char[] password;
            char[] confirmPassword;
            
            if (LGVB.testing) {
                email = testEmail;
                password = testPwd;
                confirmPassword = testPwd;
            } else {
                email = registrationPanel.getEmail();
                password = registrationPanel.getPassword();
                confirmPassword = registrationPanel.getConfirmPassword();
            }
            
            authService.isValidEmail(email);
            authService.isStrong(password);
            authService.passwordMatches(password, confirmPassword);

            // If valid, go to next page
            registrationPanel.goToNextPage();

        } catch (AuthException e) {
            OutputUtils.showError(authPage, e.getMessage());
        }
    }

    public void handleRegisterBtn() {
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
                OutputUtils.showInfo(authPage, "You must accept terms and conditions for you to register!");
                return;
            }

            String email = registrationPanel.getEmail();
            char[] password = registrationPanel.getPassword();

            String secret = authService.generateSecret();
            String otpAuthUrl;
            if (LGVB.testing) {
                otpAuthUrl = authService.getOtpAuthUrl(testUsername, testSecret);
            } else {
                otpAuthUrl = authService.getOtpAuthUrl(firstName + "_" + lastName, secret);
            }

            boolean linked = TwoFALinkDialog.showDialog(null, otpAuthUrl);

            if (!linked) {
                System.out.println("User closed the dialog without confirming.");
            }

            registrationService.registerCustomer(email, password, firstName, lastName, phoneNum, dob, secret);
            OutputUtils.showInfo(authPage, "Registered successfully!");
            authPage.showLoginPanel();
        } catch (RegistrationException e) {
            OutputUtils.showError(authPage, e.getMessage());
        } catch (Exception e) {
            OutputUtils.showError(authPage, "Unexpected error: " + e.getMessage());
            registrationPanel.resetFields();
            e.printStackTrace();
        }

        authPage.setVisible(true);
    }

}
