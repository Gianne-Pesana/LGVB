package com.leshka_and_friends.lgvb.view.settings;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.utils.DBConnection;
import com.leshka_and_friends.lgvb.view.components.RoundedButton;
import com.leshka_and_friends.lgvb.view.components.switch_button.SwitchButton;
import com.leshka_and_friends.lgvb.view.themes.LGVBDark;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public SettingsDialog(JFrame parent) {
        super(parent, "Settings", true);
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(180);
        splitPane.setBorder(null);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(245, 245, 245));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton notifBtn = createSidebarButton("Notifications");
        JButton aboutBtn = createSidebarButton("About");

        sidebar.add(notifBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(aboutBtn);
        sidebar.add(Box.createVerticalGlue());

        // Content Panel
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.add(createNotificationsPanel(), "notifications");
        contentPanel.add(createAboutPanel(), "about");

        notifBtn.addActionListener(e -> cardLayout.show(contentPanel, "notifications"));
        aboutBtn.addActionListener(e -> cardLayout.show(contentPanel, "about"));

        splitPane.setLeftComponent(sidebar);
        splitPane.setRightComponent(contentPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        RoundedButton btn = new RoundedButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(160, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(240, 240, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });
        return btn;
    }

    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel desc = new JLabel("<html>Manage how you receive notifications from the app.</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        desc.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

        panel.add(title);
        panel.add(desc);

        // Ensure rows don't stretch oddly
        JPanel emailRow = createSwitchRow("Email Notifications", "Receive transaction updates via email", true);
        emailRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel osRow = createSwitchRow("OS Notifications", "Show desktop pop-ups for important alerts", false);
        osRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(emailRow);
        panel.add(Box.createVerticalStrut(20));
        panel.add(osRow);
        panel.add(Box.createVerticalStrut(40));

        RoundedButton saveButton = new RoundedButton("Save Settings");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(new Color(0, 174, 255));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Align neatly to the left
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveButton.addActionListener(e ->
                JOptionPane.showMessageDialog(panel, "Notification settings saved successfully!", "Settings", JOptionPane.INFORMATION_MESSAGE)
        );

        panel.add(saveButton);
        return panel;
    }


    private JPanel createSwitchRow(String title, String subtitle, boolean defaultState) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        row.setOpaque(false);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel desc = new JLabel(subtitle);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(Color.DARK_GRAY);

        textPanel.add(label);
        textPanel.add(desc);

        // --- Adjusted toggle styling ---
        SwitchButton toggle = new SwitchButton();
        toggle.setSpeed(0.7f);
        toggle.setPreferredSize(new Dimension(UIScale.scale(40), UIScale.scale(20)));   // slimmer ratio
//        toggle.setMaximumSize(new Dimension(100, 20));   // slimmer ratio
//        toggle.setMinimumSize(new Dimension(100, 20));   // slimmer ratio
        toggle.setBackground(new Color(0, 174, 255));     // active = blue
        toggle.setForeground(Color.WHITE);
        toggle.setSelected(defaultState);

        // Reverse color logic for clarity
        toggle.addEventSelected(selected -> {
            if (selected) {
                toggle.setBackground(new Color(0, 174, 255)); // on = blue
            } else {
                toggle.setBackground(new Color(180, 180, 180)); // off = gray
            }
            toggle.repaint();
        });

        // Apply initial color immediately
        if (!defaultState) {
            toggle.setBackground(new Color(180, 180, 180));
        }

        JPanel toggleWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        toggleWrapper.setOpaque(false);
        toggleWrapper.add(toggle);

        row.add(textPanel, BorderLayout.CENTER);
        row.add(toggleWrapper, BorderLayout.EAST);
        return row;
    }


    private JPanel createAboutPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("About");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel appInfo = new JLabel("<html><b>LGVB Application</b><br>Version 1.0.0<br><br>Developed by Leshka & Friends.</html>");
        appInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        appInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(appInfo);

        return panel;
    }

    // Example usage
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new LGVBDark());
            ThemeGlobalDefaults.apply();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            SettingsDialog dialog = new SettingsDialog(frame);
            dialog.setVisible(true);
        });
    }
}
