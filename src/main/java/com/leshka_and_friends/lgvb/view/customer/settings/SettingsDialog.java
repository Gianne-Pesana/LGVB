package com.leshka_and_friends.lgvb.view.customer.settings;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedButton;
import com.leshka_and_friends.lgvb.view.shared_components.switch_button.SwitchButton;
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

    private final List<RoundedButton> sidebarButtons = new ArrayList<>();

    public SettingsDialog(JFrame parent) {
        super(parent, "Settings", true);

        // load colors from theme
        Color bgDialog = ThemeGlobalDefaults.getColor("settings.bg.dialog");
        Color bgSidebar = ThemeGlobalDefaults.getColor("settings.bg.sidebar");
        Color bgSelected = ThemeGlobalDefaults.getColor("settings.bg.sidebar.selected");
        Color hoverColor = ThemeGlobalDefaults.getColor("settings.bg.sidebar.hover");
        Color borderColor = ThemeGlobalDefaults.getColor("settings.border.color");
        Color textPrimary = ThemeGlobalDefaults.getColor("settings.text.primary");
        Color textSecondary = ThemeGlobalDefaults.getColor("settings.text.secondary");
        Color accentBlue = ThemeGlobalDefaults.getColor("settings.accent.blue");
        Color switchInactive = ThemeGlobalDefaults.getColor("settings.switch.inactive");
        Color backBg = ThemeGlobalDefaults.getColor("settings.bg.back");

        // Dialog setup
        setUndecorated(true);
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(bgDialog);
        getRootPane().setBorder(BorderFactory.createLineBorder(borderColor, 1));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(180);
        splitPane.setBorder(null);
        splitPane.setBackground(bgDialog);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(bgSidebar);
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        RoundedButton notifBtn = createSidebarButton("Notifications", bgSidebar, textPrimary, hoverColor, borderColor, bgSelected);
        RoundedButton aboutBtn = createSidebarButton("About", bgSidebar, textPrimary, hoverColor, borderColor, bgSelected);

        sidebar.add(notifBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(aboutBtn);
        sidebar.add(Box.createVerticalGlue());

        // Back to Dashboard button (bottom)
        RoundedButton backBtn = new RoundedButton("Back to Dashboard");
        backBtn.setFocusPainted(false);
        backBtn.setBackground(backBg);
        backBtn.setForeground(textPrimary);
        backBtn.setFont(FontLoader.getInter(14f));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(backBg);
            }
        });
        backBtn.addActionListener(e -> dispose());
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(backBtn);

        // Content Panel
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(bgDialog);

        JPanel notifPanel = createNotificationsPanel(bgDialog, textPrimary, textSecondary, accentBlue, switchInactive);
        JPanel aboutPanel = createAboutPanel(bgDialog, textPrimary, textSecondary);

        contentPanel.add(notifPanel, "notifications");
        contentPanel.add(aboutPanel, "about");

        notifBtn.addActionListener(e -> setActivePage("notifications", notifBtn, bgSidebar, bgSelected));
        aboutBtn.addActionListener(e -> setActivePage("about", aboutBtn, bgSidebar, bgSelected));

        setActivePage("notifications", notifBtn, bgSidebar, bgSelected);

        splitPane.setLeftComponent(sidebar);
        splitPane.setRightComponent(contentPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private RoundedButton createSidebarButton(String text, Color bgSidebar, Color textPrimary, Color hoverColor, Color borderColor, Color bgSelected) {
        RoundedButton btn = new RoundedButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(bgSidebar);
        btn.setForeground(textPrimary);
        btn.setFont(FontLoader.getInter(14f));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(160, 45));
        btn.setPreferredSize(new Dimension(160, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(bgSelected))
                    btn.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(bgSelected))
                    btn.setBackground(bgSidebar);
            }
        });

        sidebarButtons.add(btn);
        return btn;
    }

    private void setActivePage(String page, RoundedButton activeButton, Color bgSidebar, Color bgSelected) {
        cardLayout.show(contentPanel, page);
        for (RoundedButton b : sidebarButtons) {
            b.setBackground(b == activeButton ? bgSelected : bgSidebar);
            b.repaint();
        }
    }

    private JPanel createNotificationsPanel(Color bgDialog, Color textPrimary, Color textSecondary, Color accentBlue, Color switchInactive) {
        // Keep BoxLayout vertical and preserve width; ensure children use LEFT_ALIGNMENT and fixed max widths where needed
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgDialog);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Title (left aligned)
        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(textPrimary);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);

        // Description
        JLabel desc = new JLabel("<html>Manage how you receive notifications from the app.</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(textSecondary);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        desc.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));
        panel.add(desc);

        // Rows
        JPanel emailRow = createSwitchRow("Email Notifications", "Receive transaction updates via email", true, "email", textPrimary, textSecondary, accentBlue, switchInactive);
        emailRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(emailRow);

        panel.add(Box.createVerticalStrut(20));

        JPanel osRow = createSwitchRow("OS Notifications", "Show desktop pop-ups for important alerts", false, "os", textPrimary, textSecondary, accentBlue, switchInactive);
        osRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(osRow);

        panel.add(Box.createVerticalStrut(40));

        // Save button left aligned but in its own small container so it doesn't affect widths
        saveButton = new RoundedButton("Save Settings");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(accentBlue);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel saveWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        saveWrap.setOpaque(false);
        saveWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveWrap.add(saveButton);

        saveButton.addActionListener(e ->
                JOptionPane.showMessageDialog(panel, "Notification settings saved successfully!", "Settings", JOptionPane.INFORMATION_MESSAGE)
        );

        panel.add(saveWrap);

        return panel;
    }

    private JPanel createSwitchRow(String title, String subtitle, boolean defaultState, String type,
                                   Color textPrimary, Color textSecondary, Color accentBlue, Color switchInactive) {
        // Use a container with fixed max height and full width, but inner text panel uses BoxLayout for vertical
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        row.setOpaque(false);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(textPrimary);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel(subtitle);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(textSecondary);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(label);
        textPanel.add(desc);

        SwitchButton toggle = new SwitchButton();
        toggle.setSpeed(0.7f);
        toggle.setPreferredSize(new Dimension(UIScale.scale(40), UIScale.scale(20)));
        toggle.setBackground(defaultState ? accentBlue : switchInactive);
        toggle.setForeground(Color.WHITE);
        toggle.setSelected(defaultState);

        if ("email".equals(type)) emailToggle = toggle;
        else if ("os".equals(type)) osToggle = toggle;

        toggle.addEventSelected(selected -> {
            toggle.setBackground(selected ? accentBlue : switchInactive);
            toggle.repaint();
        });

        JPanel toggleWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        toggleWrapper.setOpaque(false);
        toggleWrapper.add(toggle);

        row.add(textPanel, BorderLayout.CENTER);
        row.add(toggleWrapper, BorderLayout.EAST);
        return row;
    }

    private JPanel createAboutPanel(Color bgDialog, Color textPrimary, Color textSecondary) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgDialog);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("About");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(textPrimary);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(10));

        JLabel appInfo = new JLabel("<html><b>LGVB Application</b><br>Version 1.0.0<br><br>Developed by Leshka & Friends.</html>");
        appInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        appInfo.setForeground(textSecondary);
        appInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
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
