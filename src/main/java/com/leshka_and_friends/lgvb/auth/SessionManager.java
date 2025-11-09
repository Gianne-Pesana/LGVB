package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;

public class SessionManager {
    private Session currentSession;

    public Session startSession(User user, Wallet wallet) {
        currentSession = new Session(user, wallet);
        return currentSession;
    }

    public Session getCurrentSession() {
        if (currentSession == null) {
            currentSession = new Session(new User(), new Wallet());
        }

        return currentSession;
    }

    public void endSession() {
        currentSession = null;
    }

    public void touch() {
        currentSession.touch();
    }

    public boolean isExpired() {
        return currentSession.isExpired();
    }
}
