package com.leshka_and_friends.lgvb.notification;

import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;
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
        Image image = SVGUtils.loadIconImage(ThemeGlobalDefaults.getString("App.icon"), 32, 32).getImage();
//        Image image = new ImageIcon(
//                getClass().getResource("/icons/app_icon.png")
//        ).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

//        Image image = new ImageIcon("C:/Users/giann/OneDrive - addu.edu.ph/2nd Year Files/1st Semester/OOP/Final Project/LGVB/src/main/resources/icons/app_icon.png")
//                .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        System.out.println("Image width: " + image.getWidth(null));
        System.out.println("Image height: " + image.getHeight(null));


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