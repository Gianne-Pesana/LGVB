/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.customer.transaction;

import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author giann
 */
public class TransactionGroupPanel extends JPanel {
    public TransactionGroupPanel(LocalDate date, List<Transaction> transactions) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        JPanel datePanel = new JPanel(new BorderLayout());
        datePanel.setOpaque(false);
        datePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        datePanel.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel dateLabel = new JLabel(formatDate(date));
        dateLabel.setFont(FontLoader.getBaloo2SemiBold(ThemeGlobalDefaults.getScaledInt("Transaction.group.date.fontSize")));
        ThemeManager.putThemeAwareProperty(dateLabel, "foreground: $LGVB.header ");
        datePanel.add(dateLabel, BorderLayout.WEST); // snap left
        add(datePanel);
        add(Box.createVerticalStrut(ThemeGlobalDefaults.getScaledInt("Transaction.date_card.spacing")));

        for (Transaction t : transactions) {
            TransactionCard card = new TransactionCard(t);
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(card);
            add(Box.createVerticalStrut(ThemeGlobalDefaults.getScaledInt("Transaction.card.spacing")));
        }
    }

    private String formatDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        if (date.equals(today)) return "Today";
        if (date.equals(today.minusDays(1))) return "Yesterday";
        return date.format(DateTimeFormatter.ofPattern("MMM. d, yyyy"));
    }
}
