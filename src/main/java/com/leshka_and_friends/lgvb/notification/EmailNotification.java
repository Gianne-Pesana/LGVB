package com.leshka_and_friends.lgvb.notification;

import com.leshka_and_friends.lgvb.auth.Session;
import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.app.ServiceLocator;

public class EmailNotification implements Observer {

    public EmailNotification() {}

    @Override
    public void update(String message) {
        if (message == null || !message.startsWith("USER_NOTIFY:")) {
            return;
        }

        SessionManager sessionManager = ServiceLocator.getInstance().getService(SessionManager.class);
        Session currentSession = sessionManager.getCurrentSession();

        if (currentSession != null && currentSession.getPreferencesManager().getPreferences().isEmail()) {
            String notification = message.substring("USER_NOTIFY:".length());
            System.out.println("[Email] " + notification);
        }
    }
}
