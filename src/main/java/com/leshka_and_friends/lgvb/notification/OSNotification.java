package com.leshka_and_friends.lgvb.notification;

import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;

public class OSNotification implements Observer {
    private boolean enabled;
    public OSNotification(boolean enabled) { this.enabled = enabled; }

    @Override
    public void update(String message) {
        if (!enabled) return;

        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();

        // Load your app logo here
        Image image = SVGUtils.loadIconImage("icons/lgvb_logo.png", 32, 32).getImage();

        // Create a TrayIcon with your own name and image
        TrayIcon trayIcon = new TrayIcon(image, "LGVB Wallet");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("LGVB Wallet - Secure Banking Simplified");

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
            return;
        }

        // This title is what appears at the top of the popup balloon
        trayIcon.displayMessage(
                "LGVB Wallet",     // custom title
                message,           // message body
                TrayIcon.MessageType.NONE // use NONE to avoid the Java info icon
        );

        trayIcon.addActionListener(e -> {
            System.out.println("Tray icon clicked!");
            // e.g. bring app window to front
        });
    }

}