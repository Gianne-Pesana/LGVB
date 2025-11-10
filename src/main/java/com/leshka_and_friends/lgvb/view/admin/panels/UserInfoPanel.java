package com.leshka_and_friends.lgvb.view.admin.panels;

import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class UserInfoPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel balanceLabel;
    private JLabel statusLabel;

    public UserInfoPanel() {
        setLayout(new GridLayout(5, 2, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.background");

        Font labelFont = FontLoader.getFont("Inter", 14f);
        Font valueFont = FontLoader.getFont("Inter", Font.BOLD, 14f);

        add(createLabel("Name:", labelFont));
        nameLabel = createValueLabel(valueFont);
        add(nameLabel);

        add(createLabel("Email:", labelFont));
        emailLabel = createValueLabel(valueFont);
        add(emailLabel);

        add(createLabel("Phone:", labelFont));
        phoneLabel = createValueLabel(valueFont);
        add(phoneLabel);

        add(createLabel("Balance:", labelFont));
        balanceLabel = createValueLabel(valueFont);
        add(balanceLabel);

        add(createLabel("Status:", labelFont));
        statusLabel = createValueLabel(valueFont);
        add(statusLabel);
    }

    public void displayUserInfo(User user, Wallet wallet) {
        if (user != null) {
            nameLabel.setText(user.getFullName());
            emailLabel.setText(user.getEmail());
            phoneLabel.setText(user.getPhoneNumber());
        }
        if (wallet != null) {
            balanceLabel.setText(String.format("%,.2f", wallet.getBalance()));
            statusLabel.setText(wallet.getStatus());
        }
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        ThemeManager.putThemeAwareProperty(label, "foreground: $Label.foreground");
        return label;
    }

    private JLabel createValueLabel(Font font) {
        JLabel label = new JLabel();
        label.setFont(font);
        ThemeManager.putThemeAwareProperty(label, "foreground: $Label.foreground");
        return label;
    }
}
