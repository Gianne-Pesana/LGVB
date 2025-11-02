package com.leshka_and_friends.lgvb.notification;

public class EmailNotification implements Observer {
    private boolean enabled;
    public EmailNotification(boolean enabled) { this.enabled = enabled; }

    @Override
    public void update(String message) {
        if (enabled) System.out.println("[Email] " + message);
    }
}
