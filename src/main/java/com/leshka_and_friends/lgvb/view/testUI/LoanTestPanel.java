package com.leshka_and_friends.lgvb.view.testUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoanTestPanel extends JPanel {
    public JComboBox<String> loanTypeCombo;
    public JButton applyButton;

    public LoanTestPanel() {
        // Main panel size and centering
        new Dimension(Integer.MAX_VALUE, 80);
//        setPreferredSize(new Dimension(1280, 720));
        setLayout(new GridBagLayout()); // centers the container panel in the main panel

        // Container panel (card-like) with fixed preferred size
        JPanel container = new JPanel(new GridBagLayout());
        container.setPreferredSize(new Dimension(640, 360));
        container.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        // Title (row 0) - spans two columns, centered
        JLabel titleLabel = new JLabel("Apply for loan");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        container.add(titleLabel, gbc);

        // Reset gridwidth for next rows
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        // Label: Enter amount: (row 1, col 0)
        JLabel amountLabel = new JLabel("Enter amount:");
        amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(amountLabel, gbc);

        // TextField: occupies width (row 1, col 1)
        JTextField amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(200, 32));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        container.add(amountField, gbc);

        // Label: Select Loan type: (row 2, col 0)
        JLabel typeLabel = new JLabel("Select Loan type:");
        typeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(typeLabel, gbc);

        // ComboBox: (row 2, col 1)
        loanTypeCombo = new JComboBox<>(new String[] {
                "Personal loan", "Housing loan", "Car Loan"
        });
        loanTypeCombo.setPreferredSize(new Dimension(200, 32));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        container.add(loanTypeCombo, gbc);

        // Apply button (row 3) - centered and spans two columns
        applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(120, 36));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(applyButton, gbc);

        // Add container to the center of the main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.CENTER;
        add(container, mainGbc);
    }

    public JComboBox<String> getLoanTypeCombo() {
        return loanTypeCombo;
    }

    public JButton getApplyButton() {
        return applyButton;
    }
}
