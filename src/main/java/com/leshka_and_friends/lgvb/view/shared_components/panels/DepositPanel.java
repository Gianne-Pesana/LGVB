package com.leshka_and_friends.lgvb.view.shared_components.panels;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.AmountField;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedButton;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DepositPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardContainer;
    private MainView mainView;

    // Amount page components
    private RoundedPanel amountPanel;
    private JLabel labelTitle;
    private JLabel labelEnterAmount;
    private AmountField amountField;
    private RoundedButton btnCancel;
    private RoundedButton btnDeposit;

    // Confirm page components
    private RoundedPanel confirmPanel;
    private JLabel labelConfirmTitle;
    private JLabel labelConfirmAmount;
    private RoundedButton btnBack;
    private RoundedButton btnConfirm;

    public DepositPanel(MainView mainView) {
        this.mainView = mainView;
        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.setOpaque(false);
        setLayout(new GridBagLayout());
        setOpaque(false);

        showAmountPanel();
        showConfirmPage();

        add(cardContainer);
        cardLayout.show(cardContainer, "AMOUNT_PAGE");

        // Button actions
        btnDeposit.addActionListener(e -> {
            if (amountField.getText().isBlank()) {
                labelConfirmAmount.setText("₱ 0.00");
            } else {
                labelConfirmAmount.setText("₱ " + amountField.getText());
            }
            cardLayout.show(cardContainer, "CONFIRM_PAGE");
        });

        btnBack.addActionListener(e -> {
            amountField.setText("");
            mainView.showDashboard();
        });

        btnCancel.addActionListener(e -> {
            amountField.setText("");
            mainView.showDashboard();
        });

        btnConfirm.addActionListener(e -> {
            // handle actual deposit logic here
            amountField.setText(""); // clear after confirm
            cardLayout.show(cardContainer, "AMOUNT_PAGE");
        });
    }

    private void showAmountPanel() {
        amountPanel = new RoundedPanel();
        amountPanel.setLayout(new GridBagLayout());
        amountPanel.setPreferredSize(new Dimension(450, 320));
        amountPanel.setBorder(new EmptyBorder(0,30,0,30));
        ThemeManager.putThemeAwareProperty(amountPanel, "background: $LGVB.primary");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 20, 20);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        labelTitle = new JLabel("Deposit", SwingConstants.CENTER);
        labelTitle.setFont(FontLoader.getInter(28f).deriveFont(Font.BOLD));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Amount label
        labelEnterAmount = new JLabel("Enter Amount:");
        labelEnterAmount.setFont(FontLoader.getInter(16f));

        // Panel for peso + amount field
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

        btnDeposit = new RoundedButton("Deposit", UIScale.scale(30));
        btnDeposit.setPreferredSize(new Dimension(100, 35));
        btnDeposit.setBackground(ThemeGlobalDefaults.getColor("Button.confirm.background"));
        btnDeposit.setForeground(ThemeGlobalDefaults.getColor("Button.confirm.foreground"));

        buttonPanel.add(btnCancel);
        buttonPanel.add(btnDeposit);

        // Add components
        gbc.gridy = 0;
        amountPanel.add(labelTitle, gbc);

        gbc.gridy = 1;
        amountPanel.add(labelEnterAmount, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 20, 15, 20);
        amountPanel.add(amountInputPanel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(10, 20, 10, 20);
        amountPanel.add(buttonPanel, gbc);

        cardContainer.add(amountPanel, "AMOUNT_PAGE");
    }

    private void showConfirmPage() {
        confirmPanel = new RoundedPanel();
        confirmPanel.setLayout(new GridBagLayout());
        confirmPanel.setPreferredSize(new Dimension(450, 320));
        confirmPanel.setBorder(new EmptyBorder(0,30,0,30));
        ThemeManager.putThemeAwareProperty(confirmPanel, "background: $LGVB.primary");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 20, 20);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        labelConfirmTitle = new JLabel("Confirm Deposit", SwingConstants.CENTER);
        labelConfirmTitle.setFont(FontLoader.getInter(28f).deriveFont(Font.BOLD));
        labelConfirmTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Amount display
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
        confirmPanel.add(labelConfirmAmount, gbc);

        gbc.gridy = 2;
        confirmPanel.add(buttonPanel, gbc);

        cardContainer.add(confirmPanel, "CONFIRM_PAGE");
    }

    // Getters
    public RoundedButton getBtnCancel() { return btnCancel; }
    public RoundedButton getBtnDeposit() { return btnDeposit; }
    public AmountField getAmountField() { return amountField; }
    public RoundedButton getBtnBack() { return btnBack; }
    public RoundedButton getBtnConfirm() { return btnConfirm; }
}
