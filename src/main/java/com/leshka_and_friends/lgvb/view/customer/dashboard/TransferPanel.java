package com.leshka_and_friends.lgvb.view.customer.dashboard;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.AmountField;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedButton;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedTextField;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TransferPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardContainer;
    private MainView mainView;

    // Transfer page components
    private RoundedPanel transferPanel;
    private JLabel labelTitle;
    private JLabel labelTransferTo;
    private JLabel labelAmount;
    private RoundedTextField fieldRecipient;
    private AmountField amountField;
    private RoundedButton btnCancel;
    private RoundedButton btnTransfer;

    // Confirm page components
    private RoundedPanel confirmPanel;
    private JLabel labelConfirmTitle;
    private JLabel labelConfirmRecipient;
    private JLabel labelConfirmAmount;
    private RoundedButton btnBack;
    private RoundedButton btnConfirm;

    public TransferPanel(MainView mainView) {
        this.mainView = mainView;
        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.setOpaque(false);
        setLayout(new GridBagLayout());
        setOpaque(false);

        showTransferPage();
        showConfirmPage();

        add(cardContainer);
        cardLayout.show(cardContainer, "TRANSFER_PAGE");

        // Button actions
        btnTransfer.addActionListener(e -> {
            String email = fieldRecipient.getText().trim();
            String amountText = amountField.getText().isBlank() ? "0.00" : amountField.getText();

            labelConfirmRecipient.setText("To: " + (email.isEmpty() ? "[No recipient]" : email));
            labelConfirmAmount.setText("₱ " + amountText);

            cardLayout.show(cardContainer, "CONFIRM_PAGE");
        });

        btnBack.addActionListener(e -> {
            fieldRecipient.setText("");
            amountField.setText("");
            mainView.showDashboard();
        });

        btnCancel.addActionListener(e -> {
            fieldRecipient.setText("");
            amountField.setText("");
            mainView.showDashboard();
        });

        btnConfirm.addActionListener(e -> {
            // handle transfer logic here
            fieldRecipient.setText("");
            amountField.setText("");
            cardLayout.show(cardContainer, "TRANSFER_PAGE");
        });
    }

    private void showTransferPage() {
        transferPanel = new RoundedPanel();
        transferPanel.setLayout(new GridBagLayout());
        transferPanel.setPreferredSize(new Dimension(480, 380));
        transferPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        ThemeManager.putThemeAwareProperty(transferPanel, "background: $LGVB.primary");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 20, 20);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        labelTitle = new JLabel("Transfer", SwingConstants.CENTER);
        labelTitle.setFont(FontLoader.getInter(28f).deriveFont(Font.BOLD));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Transfer To
        labelTransferTo = new JLabel("Transfer To (email):");
        labelTransferTo.setFont(FontLoader.getInter(16f));

        fieldRecipient = new RoundedTextField();
        fieldRecipient.setFont(FontLoader.getInter(15f));
        fieldRecipient.setPreferredSize(new Dimension(200, 40));

        // Amount
        labelAmount = new JLabel("Amount:");
        labelAmount.setFont(FontLoader.getInter(16f));

        JPanel amountInputPanel = new JPanel(new BorderLayout(10, 0));
        amountInputPanel.setOpaque(false);

        JLabel pesoLabel = new JLabel("₱");
        pesoLabel.setFont(FontLoader.getInter(18f).deriveFont(Font.BOLD));
        pesoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        amountField = new AmountField();
        amountField.setRadius(UIScale.scale(30));
        amountField.setFont(FontLoader.getInter(16f));
        amountField.setPreferredSize(new Dimension(200, 40));

        amountInputPanel.add(pesoLabel, BorderLayout.WEST);
        amountInputPanel.add(amountField, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        btnCancel = new RoundedButton("Cancel", UIScale.scale(30));
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.setBackground(ThemeGlobalDefaults.getColor("Button.cancel.background"));
        btnCancel.setForeground(ThemeGlobalDefaults.getColor("Button.cancel.foreground"));

        btnTransfer = new RoundedButton("Transfer", UIScale.scale(30));
        btnTransfer.setPreferredSize(new Dimension(100, 35));
        btnTransfer.setBackground(ThemeGlobalDefaults.getColor("Button.confirm.background"));
        btnTransfer.setForeground(ThemeGlobalDefaults.getColor("Button.confirm.foreground"));

        buttonPanel.add(btnCancel);
        buttonPanel.add(btnTransfer);

        // Add components
        gbc.gridy = 0;
        transferPanel.add(labelTitle, gbc);

        gbc.gridy = 1;
        transferPanel.add(labelTransferTo, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 20, 10, 20);
        transferPanel.add(fieldRecipient, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(10, 20, 10, 20);
        transferPanel.add(labelAmount, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(5, 20, 15, 20);
        transferPanel.add(amountInputPanel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(10, 20, 10, 20);
        transferPanel.add(buttonPanel, gbc);

        cardContainer.add(transferPanel, "TRANSFER_PAGE");
    }

    private void showConfirmPage() {
        confirmPanel = new RoundedPanel();
        confirmPanel.setLayout(new GridBagLayout());
        confirmPanel.setPreferredSize(new Dimension(480, 380));
        confirmPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        ThemeManager.putThemeAwareProperty(confirmPanel, "background: $LGVB.primary");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 20, 20);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        labelConfirmTitle = new JLabel("Confirm Transfer", SwingConstants.CENTER);
        labelConfirmTitle.setFont(FontLoader.getInter(28f).deriveFont(Font.BOLD));
        labelConfirmTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Confirmation details
        labelConfirmRecipient = new JLabel("To: [No recipient]", SwingConstants.CENTER);
        labelConfirmRecipient.setFont(FontLoader.getInter(16f));

        labelConfirmAmount = new JLabel("₱ 0.00", SwingConstants.CENTER);
        labelConfirmAmount.setFont(FontLoader.getInter(24f));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        btnBack = new RoundedButton("Back", UIScale.scale(30));
        btnBack.setPreferredSize(new Dimension(100, 35));
        btnBack.setBackground(ThemeGlobalDefaults.getColor("Button.cancel.background"));
        btnBack.setForeground(ThemeGlobalDefaults.getColor("Button.cancel.foreground"));

        btnConfirm = new RoundedButton("Confirm", UIScale.scale(30));
        btnConfirm.setPreferredSize(new Dimension(100, 35));
        btnConfirm.setBackground(ThemeGlobalDefaults.getColor("Button.confirm.background"));
        btnConfirm.setForeground(ThemeGlobalDefaults.getColor("Button.confirm.foreground"));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnConfirm);

        // Add components
        gbc.gridy = 0;
        confirmPanel.add(labelConfirmTitle, gbc);

        gbc.gridy = 1;
        confirmPanel.add(labelConfirmRecipient, gbc);

        gbc.gridy = 2;
        confirmPanel.add(labelConfirmAmount, gbc);

        gbc.gridy = 3;
        confirmPanel.add(buttonPanel, gbc);

        cardContainer.add(confirmPanel, "CONFIRM_PAGE");
    }

    // Getters
    public RoundedButton getBtnCancel() { return btnCancel; }
    public RoundedButton getBtnTransfer() { return btnTransfer; }
    public RoundedButton getBtnBack() { return btnBack; }
    public RoundedButton getBtnConfirm() { return btnConfirm; }
    public RoundedTextField getFieldRecipient() { return fieldRecipient; }
    public AmountField getAmountField() { return amountField; }
}
