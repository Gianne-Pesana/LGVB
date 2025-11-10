package com.leshka_and_friends.lgvb.view.admin.panels;

import com.leshka_and_friends.lgvb.view.components.RoundedButton;
import com.leshka_and_friends.lgvb.view.components.RoundedTextField;
import com.leshka_and_friends.lgvb.view.components.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class ManageWalletsPanel extends JPanel {

    private RoundedTextField searchField;
    private RoundedButton searchButton;
    private RoundedButton freezeButton;
    private RoundedButton closeButton;

    private JLabel lblAccountNumber;
    private JLabel lblStatus;
    private JLabel lblUserEmail;
    private JLabel lblCreatedAt;

    private JLabel valAccountNumber;
    private JLabel valStatus;
    private JLabel valUserEmail;
    private JLabel valCreatedAt;

    public ManageWalletsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(ThemeGlobalDefaults.getColor("Panel.background"));

        // Header/search area
        RoundedPanel searchPanel = new RoundedPanel();
        searchPanel.setBackground(ThemeGlobalDefaults.getColor("Panel.background"));
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        searchField = new RoundedTextField(20);
//        searchField.set("Enter Wallet ID...");
        searchButton = new RoundedButton("Search");

        JLabel searchLabel = new JLabel("Enter Wallet Account Number:");
        ThemeManager.putThemeAwareProperty(searchLabel, "foreground: $AdminDashboard.text.foreground");
        searchLabel.setFont(FontLoader.getInter(14f));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Info panel
        RoundedPanel infoPanel = new RoundedPanel();
        ThemeManager.putThemeAwareProperty(infoPanel, "background: $LGVB.background");
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        lblAccountNumber = new JLabel("Account Number:");
        lblStatus = new JLabel("Status:");
        lblUserEmail = new JLabel("User Email:");
        lblCreatedAt = new JLabel("Created At:");

        valAccountNumber = new JLabel("-");
        valStatus = new JLabel("-");
        valUserEmail = new JLabel("-");
        valCreatedAt = new JLabel("-");

        JLabel[] labels = {lblAccountNumber, lblStatus, lblUserEmail, lblCreatedAt};
        JLabel[] values = {valAccountNumber, valStatus, valUserEmail, valCreatedAt};

        for (JLabel lbl : labels) ThemeManager.putThemeAwareProperty(lbl, "foreground: $AdminDashboard.text.foreground");
        for (JLabel val : values) ThemeManager.putThemeAwareProperty(val, "foreground: $AdminDashboard.text.foreground");

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(lblAccountNumber, gbc);
        gbc.gridx = 1;
        infoPanel.add(valAccountNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(lblStatus, gbc);
        gbc.gridx = 1;
        infoPanel.add(valStatus, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(lblUserEmail, gbc);
        gbc.gridx = 1;
        infoPanel.add(valUserEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(lblCreatedAt, gbc);
        gbc.gridx = 1;
        infoPanel.add(valCreatedAt, gbc);

        add(infoPanel, BorderLayout.CENTER);

        // Action buttons
        RoundedPanel actionPanel = new RoundedPanel();
        actionPanel.setBackground(ThemeGlobalDefaults.getColor("Panel.background"));
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        freezeButton = new RoundedButton("Freeze Wallet");
        closeButton = new RoundedButton("Close Wallet");



        actionPanel.add(freezeButton);
        actionPanel.add(closeButton);

        add(actionPanel, BorderLayout.SOUTH);
    }

    // Set wallet information dynamically
    public void setWalletInfo(String accountNumber, String status, String userEmail, String createdAt) {
        valAccountNumber.setText(accountNumber);
        valStatus.setText(status);
        valUserEmail.setText(userEmail);
        valCreatedAt.setText(createdAt);
    }

    // Getters for buttons and field
    public RoundedTextField getSearchField() { return searchField; }
    public RoundedButton getSearchButton() { return searchButton; }
    public RoundedButton getFreezeButton() { return freezeButton; }
    public RoundedButton getCloseButton() { return closeButton; }


}
