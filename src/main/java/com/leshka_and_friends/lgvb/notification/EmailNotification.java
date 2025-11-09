package com.leshka_and_friends.lgvb.notification;

import com.leshka_and_friends.lgvb.auth.Session;
import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.app.ServiceLocator;

public class EmailNotification implements Observer {

    public EmailNotification() {}

    @Override
    public void update(String message) {
        SessionManager sessionManager = ServiceLocator.getInstance().getService(SessionManager.class);
        Session currentSession = sessionManager.getCurrentSession();

        if (currentSession != null && currentSession.getPreferencesManager().getPreferences().isEmail()) {
            System.out.println("[Email] " + message);
        }
    }
}
