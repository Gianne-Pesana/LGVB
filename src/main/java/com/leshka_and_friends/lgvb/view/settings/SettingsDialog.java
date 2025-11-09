package com.leshka_and_friends.lgvb.view.settings;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.RoundedButton;
import com.leshka_and_friends.lgvb.view.components.switch_button.SwitchButton;
import com.leshka_and_friends.lgvb.view.themes.LGVBDark;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsDialog extends JDialog {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private SwitchButton emailToggle;
    private SwitchButton osToggle;
    private RoundedButton saveButton;

    // --- refined dark-gray palette ---
    private static final Color BG_DIALOG = new Color(18, 18, 46);        // slightly grayish dark tone
    private static final Color BG_SIDEBAR = new Color(24, 24, 60);       // lighter grayish sidebar
    private static final Color BG_SELECTED = new Color(45, 45, 95);      // highlight for selected sidebar item
    private static final Color HOVER_COLOR = new Color(38, 38, 85);      // hover color
    private static final Color BORDER_COLOR = new Color(60, 60, 110);    // soft outline
    private static final Color TEXT_PRIMARY = new Color(235, 235, 245);
    private static final Color TEXT_SECONDARY = new Color(160, 160, 180);
    private static final Color ACCENT_BLUE = new Color(0, 174, 255);

    private final List<RoundedButton> sidebarButtons = new ArrayList<>();

    public SettingsDialog(JFrame parent) {
        super(parent, "Settings", true);

        // remove the title bar (undecorated)
        setUndecorated(true);
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(BG_DIALOG);
        getRootPane().setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(180);
        splitPane.setBorder(null);
        splitPane.setBackground(BG_DIALOG);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        RoundedButton notifBtn = createSidebarButton("Notifications");
        RoundedButton aboutBtn = createSidebarButton("About");

        sidebar.add(notifBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(aboutBtn);
        sidebar.add(Box.createVerticalGlue());

        // Back to Dashboard button
        RoundedButton backBtn = new RoundedButton("Back to Dashboard");
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(32, 32, 75));
        backBtn.setForeground(TEXT_PRIMARY);
        backBtn.setFont(FontLoader.getInter(14f));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(new Color(32, 32, 75));
            }
        });
        backBtn.addActionListener(e -> dispose());
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(backBtn);

        // Content Panel
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(BG_DIALOG);

        JPanel notifPanel = createNotificationsPanel();
        JPanel aboutPanel = createAboutPanel();

        contentPanel.add(notifPanel, "notifications");
        contentPanel.add(aboutPanel, "about");

        notifBtn.addActionListener(e -> setActivePage("notifications", notifBtn));
        aboutBtn.addActionListener(e -> setActivePage("about", aboutBtn));

        // initial state
        setActivePage("notifications", notifBtn);

        splitPane.setLeftComponent(sidebar);
        splitPane.setRightComponent(contentPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private RoundedButton createSidebarButton(String text) {
        RoundedButton btn = new RoundedButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(BG_SIDEBAR);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFont(FontLoader.getInter(14f));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(160, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(BG_SELECTED))
                    btn.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(BG_SELECTED))
                    btn.setBackground(BG_SIDEBAR);
            }
        });

        sidebarButtons.add(btn);
        return btn;
    }

    private void setActivePage(String page, RoundedButton activeButton) {
        cardLayout.show(contentPanel, page);
        for (RoundedButton b : sidebarButtons) {
            if (b == activeButton) {
                b.setBackground(BG_SELECTED);
            } else {
                b.setBackground(BG_SIDEBAR);
            }
        }
    }

    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_DIALOG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("<html>Manage how you receive notifications from the app.</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(TEXT_SECONDARY);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        desc.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

        panel.add(title);
        panel.add(desc);

        JPanel emailRow = createSwitchRow("Email Notifications", "Receive transaction updates via email", true, "email");
        emailRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel osRow = createSwitchRow("OS Notifications", "Show desktop pop-ups for important alerts", false, "os");
        osRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(emailRow);
        panel.add(Box.createVerticalStrut(20));
        panel.add(osRow);
        panel.add(Box.createVerticalStrut(40));

        saveButton = new RoundedButton("Save Settings");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(ACCENT_BLUE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveButton.addActionListener(e ->
                JOptionPane.showMessageDialog(panel, "Notification settings saved successfully!", "Settings", JOptionPane.INFORMATION_MESSAGE)
        );

        panel.add(saveButton);
        return panel;
    }

    private JPanel createSwitchRow(String title, String subtitle, boolean defaultState, String type) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        row.setOpaque(false);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_PRIMARY);

        JLabel desc = new JLabel(subtitle);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(TEXT_SECONDARY);

        textPanel.add(label);
        textPanel.add(desc);

        SwitchButton toggle = new SwitchButton();
        toggle.setSpeed(0.7f);
        toggle.setPreferredSize(new Dimension(UIScale.scale(40), UIScale.scale(20)));
        toggle.setBackground(defaultState ? ACCENT_BLUE : new Color(100, 100, 130));
        toggle.setForeground(Color.WHITE);
        toggle.setSelected(defaultState);

        if ("email".equals(type)) emailToggle = toggle;
        else if ("os".equals(type)) osToggle = toggle;

        toggle.addEventSelected(selected -> {
            if (selected) toggle.setBackground(ACCENT_BLUE);
            else toggle.setBackground(new Color(100, 100, 130));
            toggle.repaint();
        });

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
        panel.setBackground(BG_DIALOG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("About");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel appInfo = new JLabel("<html><b>LGVB Application</b><br>Version 1.0.0<br><br>Developed by Leshka & Friends.</html>");
        appInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        appInfo.setForeground(TEXT_SECONDARY);
        appInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(appInfo);

        return panel;
    }

    public SwitchButton getEmailToggle() { return emailToggle; }
    public SwitchButton getOsToggle() { return osToggle; }
    public RoundedButton getSaveButton() { return saveButton; }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new LGVBDark());
            ThemeGlobalDefaults.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard");
            frame.setSize(800, 600);
            frame.getContentPane().setBackground(new Color(5, 5, 38));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            SettingsDialog dialog = new SettingsDialog(frame);
            dialog.setVisible(true);
        });
    }
}
