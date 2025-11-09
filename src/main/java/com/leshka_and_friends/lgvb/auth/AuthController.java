package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.LGVB;
import com.leshka_and_friends.lgvb.core.app.AppController;
import com.leshka_and_friends.lgvb.core.card.CardDAO;
import com.leshka_and_friends.lgvb.core.card.CardService;
import com.leshka_and_friends.lgvb.core.user.*;
import com.leshka_and_friends.lgvb.core.wallet.WalletDAO;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.exceptions.AuthException;
import com.leshka_and_friends.lgvb.exceptions.RegistrationException;
import com.leshka_and_friends.lgvb.utils.StringUtils;
import com.leshka_and_friends.lgvb.view.authpage.AuthPage;
import com.leshka_and_friends.lgvb.view.authpage.LoginPanel;
import com.leshka_and_friends.lgvb.view.authpage.RegistrationPanel;
import com.leshka_and_friends.lgvb.core.app.ServiceLocator;
import com.leshka_and_friends.lgvb.view.authpage.TwoFALinkDialog;
import com.leshka_and_friends.lgvb.view.testUI.AdminTestView;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import java.time.LocalDate;

/**
 * Handles both login and registration logic.
 * Navigation (moving to main app) is delegated to AppController.
 */
public class AuthController {
    private final AppController appController;

    private final AuthService authService;
    private final UserService userService;
    private final WalletService walletService;
    private final CardService cardService;
    private final CustomerService customerService;
    private final RegistrationService registrationService;

    private AuthPage authPage;
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;

    // Testing-only credentials
    private final String testEmail = "test123@lgvb.com";
    private final char[] testPwd = "#Test12345678".toCharArray();
    private final String testUsername = "test123";
    private final String testSecret = "JBSWY3DPEHPK3PXP";

    public AuthController(AppController appController) {
        this.appController = appController;

        // Get shared services from the locator
        walletService = ServiceLocator.getInstance().getService(WalletService.class);
        cardService = ServiceLocator.getInstance().getService(CardService.class);
        customerService = ServiceLocator.getInstance().getService(CustomerService.class);

        // Auth-specific services can be created here
        userService = new UserService(new UserDAO());
        authService = new AuthService(userService);
        registrationService = new RegistrationService(userService, walletService, cardService);
    }

    public void start() {
        authPage = new AuthPage();
        loginPanel = authPage.getLoginPanel();
        registrationPanel = authPage.getRegistrationPanel();

        setupLoginListeners();
        setupRegistrationListeners();

        authPage.setVisible(true);
    }

    private void setupLoginListeners() {
        // LOGIN button
        loginPanel.getLoginBtn().addActionListener(e -> handleLogin());

        // REGISTER button (switch to registration panel)
        loginPanel.getRegisterBtn().addActionListener(e -> {
            authPage.showRegisterPanel();
            registrationPanel.resetFields();
        });
    }

    private void setupRegistrationListeners() {
        registrationPanel.getNextButton().addActionListener(e -> handleNextBtn());
        registrationPanel.getRegisterButton().addActionListener(e -> handleRegisterBtn());
    }

    // ---------- LOGIN FLOW ----------
    private void handleLogin() {
        String email;
        char[] pwd;

        if (LGVB.testing) {
            email = testEmail;
            pwd = testPwd;
        } else {
            email = loginPanel.getUsernameInput();
            pwd = loginPanel.getPasswordInput();
        }

        try {
            User user = authService.login(email, pwd);
            if(user.isAdmin()) {
                authPage.dispose();
                appController.onLoginSuccess(user);
                return;
            }

            authService.verifyStatus(walletService.getWalletByUserId(user.getUserId()));

            // Show 2FA
            authPage.showTOTPPanel();
            authPage.getTotpPanel().getTotpVerifyButton().addActionListener(e -> {
                try {
                    String code = authPage.getTotpPanel().getTotpField().getText().trim();
                    boolean verified = LGVB.testing || authService.verifyTOTP(user.getTotpSecret(), code, 1);

                    if (!verified) {
                        OutputUtils.showError(authPage, "Invalid 2FA code.");
                        return;
                    }

                    OutputUtils.showInfo(authPage, "2FA success!");
                    authPage.dispose();
                    appController.onLoginSuccess(user); // <---- delegate to AppController

                } catch (Exception ex) {
                    OutputUtils.showError(authPage, "Error verifying 2FA: " + ex.getMessage());
                }
            });

        } catch (AuthException ae) {
            OutputUtils.showError(authPage, "Login failed: " + ae.getMessage());
        } finally {
            java.util.Arrays.fill(pwd, '\0');
            loginPanel.resetFields();
        }
    }

    // ---------- REGISTRATION FLOW ----------
    private void handleNextBtn() {
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

            registrationPanel.goToNextPage();

        } catch (AuthException e) {
            OutputUtils.showError(authPage, e.getMessage());
        }
    }

    private void handleRegisterBtn() {
        try {
            String firstName = StringUtils.toProperCase(registrationPanel.getFirstName());
            String lastName = StringUtils.toProperCase(registrationPanel.getLastName());
            String phoneNum = registrationPanel.getPhoneNumber();
            LocalDate dob = registrationPanel.getDOB();
            boolean isTermsChecked = registrationPanel.isTermsChecked();

            registrationService.validateFirstName(firstName);
            registrationService.validateLastName(lastName);
            registrationService.validatePhoneNum(phoneNum);
            registrationService.validateDateOfBirth(dob);

            if (!isTermsChecked) {
                OutputUtils.showInfo(authPage, "You must accept terms and conditions to register!");
                return;
            }

            String email = registrationPanel.getEmail();
            char[] password = registrationPanel.getPassword();

            // 2FA setup
            String secret = authService.generateSecret();
            String otpAuthUrl = LGVB.testing
                    ? authService.getOtpAuthUrl(testUsername, testSecret)
                    : authService.getOtpAuthUrl(firstName + "_" + lastName, secret);

            boolean linked = TwoFALinkDialog.showDialog(null, otpAuthUrl);

            if (!linked) {
                System.out.println("User closed the 2FA dialog without confirming.");
                return;
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
    }
}
