package com.leshka_and_friends.lgvb.core.app;

import com.leshka_and_friends.lgvb.auth.AuthController;
import com.leshka_and_friends.lgvb.auth.Session;
import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.auth.SessionWatcher;
import com.leshka_and_friends.lgvb.core.admin.AdminController;
import com.leshka_and_friends.lgvb.core.user.CustomerService;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.notification.NotificationManager;
import com.leshka_and_friends.lgvb.preferences.PreferencesManager;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.admin.AdminDashboard;
import com.leshka_and_friends.lgvb.view.testUI.AdminTestView;
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

        // Load/create user preferences and store in session
        PreferencesManager prefsManager = new PreferencesManager(String.valueOf(user.getUserId()));
        session.setPreferencesManager(prefsManager);

        try {
            // Build all the services & facade
            AppFacade facade = FacadeFactory.createAppFacade(user);
            CustomerService customerService = ServiceLocator.getInstance().getService(CustomerService.class);

            MainView mainView = new MainView(customerService.buildCustomerDTO(user));
            session.setMainView(mainView); // Store main view in session
            mainView.addLogoutListener(this::logout); // Add logout action
            
            if (user.isAdmin()) {
//                AdminTestView av = new AdminTestView(facade);
//                av.setVisible(true);
                AdminDashboard adminDashboard = new AdminDashboard();
                AdminController adminController = new AdminController(facade, adminDashboard);
                adminDashboard.setVisible(true);
            } else {
                MainController mainController = new MainController(facade, mainView);
                // Show the main view
                mainView.setVisible(true);
            }

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

    public void logout() {
        Session session = sessionManager.getCurrentSession();
        if (session != null && session.getMainView() != null) {
            session.getMainView().dispose();
        }
        sessionManager.endSession();
        showLogin();
        OutputUtils.showInfo("You have been logged out.");
    }
}
