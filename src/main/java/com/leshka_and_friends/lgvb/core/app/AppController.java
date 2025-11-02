package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.auth.AuthController;
import com.leshka_and_friends.lgvb.auth.Session;
import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.auth.SessionWatcher;
import com.leshka_and_friends.lgvb.core.user.CustomerService;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

public class AppController {

    private SessionManager sessionManager;

    public void start() {
        ServiceRegistry.registerServices();
        sessionManager = ServiceLocator.getInstance().getService(SessionManager.class);
        showLogin();
    }

    private void showLogin() {
        AuthController authController = new AuthController(this);
        authController.start();
    }

    public void onLoginSuccess(User user) {
        // Create the session once login is successful
        WalletService walletService = ServiceLocator.getInstance().getService(WalletService.class);
        Session session = sessionManager.startSession(user, walletService.getWalletByUserId(user.getUserId()));
        System.out.println("Session: " + session.getUser().getEmail() + " Wallet: " + session.getWallet().getAccountNumber());

        try {
            // Build all the services & facade
            AppFacade facade = FacadeFactory.createAppFacade(user);
            CustomerService customerService = ServiceLocator.getInstance().getService(CustomerService.class);
            MainView mainView = new MainView(customerService.buildCustomerDTO(user));
            MainController mainController = new MainController(facade, mainView);


            // Show the main view
            mainView.setVisible(true);

            // Handle session expiration
            SessionWatcher watcher = new SessionWatcher(sessionManager, () -> {
                System.out.println("[AppController] Session expired. Returning to login...");
                mainView.dispose();
                showLogin();
                OutputUtils.showInfo("Session expired. Please log in again.");
            });
            watcher.start();
        } catch (Exception ex) {
            OutputUtils.showError("[App] Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
