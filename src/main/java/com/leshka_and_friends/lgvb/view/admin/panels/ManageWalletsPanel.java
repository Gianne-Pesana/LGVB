package com.leshka_and_friends.lgvb.view.admin.panels;

import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedButton;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedTextField;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
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
    private RoundedButton activateButton;

    private JLabel lblAccountNumber;
    private JLabel lblStatus;
    private JLabel lblUserEmail;
    private JLabel lblCreatedAt;

    private JLabel valAccountNumber;
    private JLabel valStatus;
    private JLabel valUserEmail;
    private JLabel valCreatedAt;

    public ManageWalletsPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(ThemeGlobalDefaults.getColor("Panel.background"));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // SEARCH SECTION
        RoundedPanel searchPanel = new RoundedPanel(20);
        ThemeManager.putThemeAwareProperty(searchPanel, "background: $Card1.background");
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 10, 5, 10);
        gbcSearch.anchor = GridBagConstraints.WEST;

        JLabel searchLabel = new JLabel("Enter email:");
        ThemeManager.putThemeAwareProperty(searchLabel, "foreground: $Text.white");
        searchLabel.setFont(FontLoader.getInter(15f));

        searchField = new RoundedTextField(25);
        searchButton = new RoundedButton("Search");
        ThemeManager.putThemeAwareProperty(searchButton, "background: $ManageWallets.button.search.background; foreground: $ManageWallets.button.search.foreground");

        gbcSearch.gridx = 0;
        gbcSearch.gridy = 0;
        searchPanel.add(searchLabel, gbcSearch);

        gbcSearch.gridy = 1;
        searchPanel.add(searchField, gbcSearch);

        gbcSearch.gridx = 1;
        searchPanel.add(searchButton, gbcSearch);

        add(searchPanel, BorderLayout.NORTH);

        // INFO SECTION
        RoundedPanel infoPanel = new RoundedPanel(25);
        ThemeManager.putThemeAwareProperty(infoPanel, "background: $Card1.background");
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 25, 10, 25);
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

        for (JLabel lbl : labels) {
            ThemeManager.putThemeAwareProperty(lbl, "foreground: $Text.white");
            lbl.setFont(FontLoader.getInter(15f));
        }
        for (JLabel val : values) {
            ThemeManager.putThemeAwareProperty(val, "foreground: $Text.white");
            val.setFont(FontLoader.getInter(15f));
        }

        gbc.gridx = 0; gbc.gridy = 0; infoPanel.add(lblAccountNumber, gbc);
        gbc.gridx = 1; infoPanel.add(valAccountNumber, gbc);

        gbc.gridx = 0; gbc.gridy++; infoPanel.add(lblStatus, gbc);
        gbc.gridx = 1; infoPanel.add(valStatus, gbc);

        gbc.gridx = 0; gbc.gridy++; infoPanel.add(lblUserEmail, gbc);
        gbc.gridx = 1; infoPanel.add(valUserEmail, gbc);

        gbc.gridx = 0; gbc.gridy++; infoPanel.add(lblCreatedAt, gbc);
        gbc.gridx = 1; infoPanel.add(valCreatedAt, gbc);

        add(infoPanel, BorderLayout.CENTER);

        // ACTION BUTTONS
        RoundedPanel actionPanel = new RoundedPanel(20);
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        ThemeManager.putThemeAwareProperty(actionPanel, "background: $Panel.background");

        freezeButton = new RoundedButton("Freeze Wallet");
        ThemeManager.putThemeAwareProperty(freezeButton, "background: $ManageWallets.button.freeze.background; foreground: $ManageWallets.button.freeze.foreground");

        closeButton = new RoundedButton("Close Wallet");
        ThemeManager.putThemeAwareProperty(closeButton, "background: $ManageWallets.button.close.background; foreground: $ManageWallets.button.close.foreground");

        activateButton = new RoundedButton("Activate Wallet");
        ThemeManager.putThemeAwareProperty(activateButton, "background: $ManageWallets.button.activate.background; foreground: $ManageWallets.button.activate.foreground");

        freezeButton.setFont(FontLoader.getInter(14f));
        closeButton.setFont(FontLoader.getInter(14f));
        activateButton.setFont(FontLoader.getInter(14f));

        actionPanel.add(activateButton);
        actionPanel.add(freezeButton);
        actionPanel.add(closeButton);

        add(actionPanel, BorderLayout.SOUTH);
    }

    public void setWalletInfo(String accountNumber, String status, String userEmail, String createdAt) {
        valAccountNumber.setText(accountNumber);
        valStatus.setText(status);
        valUserEmail.setText(userEmail);
        valCreatedAt.setText(createdAt);
    }

    public RoundedTextField getSearchField() { return searchField; }
    public RoundedButton getSearchButton() { return searchButton; }
    public RoundedButton getFreezeButton() { return freezeButton; }
    public RoundedButton getCloseButton() { return closeButton; }
    public RoundedButton getActivateButton() { return activateButton; }
}
