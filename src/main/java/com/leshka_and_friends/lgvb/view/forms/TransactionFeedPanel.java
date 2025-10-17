/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.forms;

import com.leshka_and_friends.lgvb.transaction.Transaction;
import com.leshka_and_friends.lgvb.view.components.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import java.awt.Color;
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
        
        groupedTransactions.entrySet().stream()
                .sorted(Map.Entry.<LocalDate, List<Transaction>>comparingByKey().reversed())
                .forEach(entry -> {
                    LocalDate date = entry.getKey();
                    List<Transaction> transactions = entry.getValue();
                    add(new TransactionGroupPanel(date, transactions));
                    add(Box.createVerticalStrut(ThemeGlobalDefaults.getScaledInt("Transaction.feed.groupSpacing")));
                });
    }
}
