package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.preferences.PreferencesManager;
import com.leshka_and_friends.lgvb.utils.AppConfig;
import com.leshka_and_friends.lgvb.view.MainView;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Data model for a user session (a POJO).
 * All management logic is deferred to SessionService.
 */
public class Session {
    private final User user;
    private Wallet wallet;
    private PreferencesManager preferencesManager;
    private MainView mainView;

    private final Instant creationTime;
    private Instant lastAccessTime;

    private static final Duration IDLE_TIMEOUT = Duration.ofMinutes(AppConfig.getInt("security.session.inactive.minutes"));
    private static final Duration SESSION_TTL = Duration.ofMinutes(AppConfig.getInt("security.session.timeout.minutes"));

    public Session(User user, Wallet wallet) {
        this.user = user;
        this.wallet = wallet;
        this.creationTime = Instant.now();
        this.lastAccessTime = Instant.now();
    }

    public User getUser() { return user; }

    public Wallet getWallet() { return wallet; }
    public void setWallet(Wallet wallet) { this.wallet = wallet; }

    public PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public void setPreferencesManager(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    // Reset idle timer whenever the user does something
    public void touch() {
        this.lastAccessTime = Instant.now();
    }

    // Checks if session is expired (idle or max lifetime)
    public boolean isExpired() {
        Instant now = Instant.now();
        if (creationTime.plus(SESSION_TTL).isBefore(now)) return true;
        if (lastAccessTime.plus(IDLE_TIMEOUT).isBefore(now)) return true;
        return false;
    }

}