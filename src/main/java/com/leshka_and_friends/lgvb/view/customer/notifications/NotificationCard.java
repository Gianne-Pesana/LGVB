package com.leshka_and_friends.lgvb.view.customer.notifications;

import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotificationCard extends RoundedPanel {

    public NotificationCard(String message) {
        setLayout(new BorderLayout());
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.primary");
        setBorder(new EmptyBorder(15, 20, 15, 20));
        setRadius(ThemeGlobalDefaults.getInt("Panel.arc"));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(FontLoader.getFont("inter", 14f));
        ThemeManager.putThemeAwareProperty(messageLabel, "foreground: $LGVB.foreground");

        add(messageLabel, BorderLayout.CENTER);
    }
}
