package com.leshka_and_friends.lgvb.view.shared_components.dialogs;

import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutConfirmationDialog extends JDialog {

    public interface LogoutListener {
        void onConfirm();
    }

    public LogoutConfirmationDialog(JFrame parent, LogoutListener listener) {
        super(parent, "Confirm Logout", true);
        setUndecorated(true);

        // Semi-transparent overlay panel
        JPanel overlay = new JPanel(new GridBagLayout());
        overlay.setBackground(new Color(0, 0, 0, 100)); // dark transparent background

        // Dialog card
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(350, 180));
        ThemeManager.putThemeAwareProperty(card, "background: $LGVB.background");
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));

        // Header text
        JLabel titleLabel = new JLabel("Log Out", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Message text
        JLabel messageLabel = new JLabel(
                "Are you sure you want to log out?",
                SwingConstants.CENTER
        );
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Buttons
        JButton confirmButton = new JButton("Log Out");
        confirmButton.setBackground(new Color(220, 53, 69)); // red
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        confirmButton.setPreferredSize(new Dimension(100, 35));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(230, 230, 230));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cancelButton.setPreferredSize(new Dimension(100, 35));

        JPanel buttonPanel = new JPanel();
        ThemeManager.putThemeAwareProperty(buttonPanel, "background: $LGVB.background");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // Add components
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(messageLabel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        overlay.add(card);
        add(overlay);

        // Button actions
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (listener != null) listener.onConfirm();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    public static void showLogoutDialog(JFrame parent, LogoutListener listener) {
        LogoutConfirmationDialog dialog = new LogoutConfirmationDialog(parent, listener);
        dialog.setVisible(true);
    }
}
