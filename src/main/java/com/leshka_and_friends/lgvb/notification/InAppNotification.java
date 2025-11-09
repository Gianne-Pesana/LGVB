package com.leshka_and_friends.lgvb.notification;

import javax.swing.*;

public class InAppNotification implements Observer {
    private final JPanel uiPanel;

    public InAppNotification(JPanel uiPanel) {
        this.uiPanel = uiPanel;
    }

    @Override
    public void update(String message) {
        if (message != null && message.startsWith("USER_NOTIFY:")) {
            String notification = message.substring("USER_NOTIFY:".length());
            System.out.println("[In-App Notification] " + notification);
            // In a real implementation, this would show a popup or banner in the app UI
        }
    }
}