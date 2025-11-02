package com.leshka_and_friends.lgvb.notification;

import javax.swing.*;

public class InAppNotification implements Observer {
    private final JPanel uiPanel;

    public InAppNotification(JPanel uiPanel) {
        this.uiPanel = uiPanel;
    }

    @Override
    public void update(String message) {
        System.out.println("[NOTIFICATION] " + message);
    }
}