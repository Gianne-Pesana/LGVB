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

        // Load an image for the tray icon (optional)
        // Image image = Toolkit.getDefaultToolkit().createImage("path/to/your/icon.png");
        // For demonstration, we'll create a simple dummy image
        Image image = Toolkit.getDefaultToolkit().createImage(new byte[0]);

        TrayIcon trayIcon = new TrayIcon(image, "Java Tray Demo");
        trayIcon.setImageAutoSize(true); // Let the system resize the image if needed

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
            return;
        }

        // Display the notification
        trayIcon.displayMessage(
                "LGVB",
                message,
                MessageType.INFO
        );

        // Optional: Add an action listener to the tray icon
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Tray icon clicked!");
                // You can perform an action here, like opening a window
            }
        });
    }
}