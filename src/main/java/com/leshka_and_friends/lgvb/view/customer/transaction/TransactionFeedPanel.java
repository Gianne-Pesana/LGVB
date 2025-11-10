/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.customer.transaction;

import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author giann
 */
public class TransactionFeedPanel extends JPanel {

    public TransactionFeedPanel(Map<LocalDate, List<Transaction>> groupedTransactions) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);
        updateTransactions(groupedTransactions);
    }

    public void updateTransactions(Map<LocalDate, List<Transaction>> groupedTransactions) {
        removeAll();

        // Check if there are no transactions
        if (groupedTransactions == null || groupedTransactions.isEmpty()) {
            JLabel noTransactionsLabel = new JLabel("No transactions");
            noTransactionsLabel.setFont(FontLoader.getBaloo2SemiBold(14));
            ThemeManager.putThemeAwareProperty(noTransactionsLabel, "foreground: $LGVB.header");
            noTransactionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(Box.createVerticalGlue()); // center vertically
            add(noTransactionsLabel);
            add(Box.createVerticalGlue());
            return;
        }

        groupedTransactions.entrySet().stream()
                .sorted(Map.Entry.<LocalDate, List<Transaction>>comparingByKey().reversed())
                .forEach(entry -> {
                    LocalDate date = entry.getKey();
                    List<Transaction> transactions = entry.getValue();
                    add(new TransactionGroupPanel(date, transactions));
                    add(Box.createVerticalStrut(ThemeGlobalDefaults.getScaledInt("Transaction.feed.groupSpacing")));
                });

        revalidate();
        repaint();
    }
}
