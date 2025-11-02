package com.leshka_and_friends.lgvb.view.testUI;

import com.leshka_and_friends.lgvb.view.components.AmountField;

import javax.swing.*;
import java.awt.*;

public class TransferTestView extends JFrame {
    public JTextField emailField;
    public AmountField amountField;
    public JButton transferButton;

    public TransferTestView() {
        setTitle("Transfer Test View");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(640, 400);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel(new GridBagLayout());
        // No setBackground — FlatLaf handles the theme colors

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Title
        JLabel titleLabel = new JLabel("Transfer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0;

        // Label: To (email)
        JLabel toLabel = new JLabel("To (email):");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(toLabel, gbc);

        // TextField for email
        emailField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        panel.add(emailField, gbc);

        gbc.weightx = 0;

        // Label: Amount
        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(amountLabel, gbc);

        // TextField for amount
        amountField = new AmountField();
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        panel.add(amountField, gbc);

        // Transfer button (centered, spans both columns)
        transferButton = new JButton("Transfer");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        panel.add(transferButton, gbc);

        // Add to frame
        add(panel);
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public AmountField getAmountField() {
        return amountField;
    }

    public JButton getTransferButton() {
        return transferButton;
    }
}
