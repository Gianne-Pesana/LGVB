package com.leshka_and_friends.lgvb.view.components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.notification.NotificationManager;
import com.leshka_and_friends.lgvb.notification.Observer;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class NotificationsPanel extends JPanel implements Observer {

    private MainView mainView;
    private NotificationManager notificationManager;
    private JPanel notificationsListPanel;
    private JScrollPane scrollPane;
    private JButton btnBack;

    public NotificationsPanel(MainView mainView, NotificationManager notificationManager) {
        this.mainView = mainView;
        this.notificationManager = notificationManager;
        this.notificationManager.addObserver(this); // Register as observer
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Add some padding

        initComponents();
        // Removed addDummyNotifications();
    }

    private void initComponents() {
        // Top Panel for Title and Back Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Notifications");
        titleLabel.setFont(FontLoader.getFont(
                ThemeGlobalDefaults.getString("Panel.Notifications.title.fontName"),
                ThemeGlobalDefaults.getFloat("Panel.Notifications.title.fontSize")
        ));
        ThemeManager.putThemeAwareProperty(titleLabel, "foreground: $LGVB.header");
        topPanel.add(titleLabel, BorderLayout.WEST);

        FlatSVGIcon backIcon = SVGUtils.loadIcon(
                "icons/svg/back.svg", // Assuming a back icon exists here
                20, 20
        );
        backIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.header"));

        btnBack = new JButton(backIcon);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setOpaque(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener((ActionEvent e) -> {
            mainView.showDashboard();
        });
        topPanel.add(btnBack, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Notifications List Panel
        notificationsListPanel = new JPanel();
        notificationsListPanel.setLayout(new BoxLayout(notificationsListPanel, BoxLayout.Y_AXIS));
        notificationsListPanel.setOpaque(false);
        notificationsListPanel.setBorder(new EmptyBorder(10, 0, 0, 0)); // Padding from title

        scrollPane = new JScrollPane(notificationsListPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        ThemeManager.putThemeAwareProperty(scrollPane.getVerticalScrollBar(), "background: $Panel.background");

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void update(String message) {
        addNotification(message);
    }

    public void addNotification(String message) {
        JPanel notificationEntry = new JPanel(new BorderLayout());
        notificationEntry.setOpaque(false);
        notificationEntry.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeGlobalDefaults.getColor("Separator.color")), // Bottom border
                new EmptyBorder(10, 5, 10, 5) // Padding
        ));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(FontLoader.getFont(
                ThemeGlobalDefaults.getString("Panel.Notifications.message.fontName"),
                ThemeGlobalDefaults.getFloat("Panel.Notifications.message.fontSize")
        ));
        ThemeManager.putThemeAwareProperty(messageLabel, "foreground: $Label.foreground");
        notificationEntry.add(messageLabel, BorderLayout.CENTER);

        notificationsListPanel.add(notificationEntry);
        notificationsListPanel.revalidate();
        notificationsListPanel.repaint();
    }
}
