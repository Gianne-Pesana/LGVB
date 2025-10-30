/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.forms;

import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.view.components.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author giann
 */
public class TransactionCard extends RoundedPanel {

    int width = 0;
    int height = ThemeGlobalDefaults.getScaledInt("Transaction.card.height");
    private static final int TIME_WIDTH = 100; // fixed width for time

    public TransactionCard(Transaction t) {
        setLayout(new GridBagLayout());
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.primary");
        setBorder(new EmptyBorder(8, 10, 8, 10));
        setRadius(ThemeGlobalDefaults.getInt("Panel.arc"));
        setPreferredSize(new Dimension(width, height)); 
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 1;

        // TIME LABEL (fixed width)
        int contentFontSize = ThemeGlobalDefaults.getScaledInt("Transaction.card.content.fontSize");
        Font contentFont = FontLoader.getFont(
                ThemeGlobalDefaults.getString("Transaction.card.content.font"), 
                Font.PLAIN, 
                contentFontSize
        );
        
        JLabel timeLabel = new JLabel(t.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        timeLabel.setFont(contentFont);
        ThemeManager.putThemeAwareProperty(timeLabel, "foreground: $LGVB.foreground");
        timeLabel.setPreferredSize(new Dimension(TIME_WIDTH, ThemeGlobalDefaults.getScaledInt("Transaction.card.timeLabel.width")));
        timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(timeLabel, gbc);

        // CENTER PANEL (icon + type)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        centerPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(t.getIcon());
        ThemeManager.putThemeAwareProperty(iconLabel, "foreground: $LGVB.foreground");
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, contentFontSize));
        
        JLabel typeLabel = new JLabel(t.getTransactionType());
        ThemeManager.putThemeAwareProperty(typeLabel, "foreground: $LGVB.foreground");
        typeLabel.setFont(contentFont);

        centerPanel.add(iconLabel);
        centerPanel.add(typeLabel);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // take all remaining horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        add(centerPanel, gbc);

        // RIGHT PANEL (amount + ellipses)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints rGbc = new GridBagConstraints();
        rGbc.gridy = 0;
        rGbc.insets = new Insets(0, 5, 0, 0);

        // Amount label
        JLabel amountLabel = new JLabel(String.format("%s%.2f", t.getAmount() < 0 ? "-" : "+", Math.abs(t.getAmount())));
        amountLabel.setFont(contentFont);
        amountLabel.setForeground(t.getAmount() < 0 ? new Color(200, 0, 0) : new Color(0, 150, 0));
        rGbc.gridx = 0;
        rGbc.weightx = 0;
        rGbc.anchor = GridBagConstraints.EAST;
        rightPanel.add(amountLabel, rGbc);

        // Ellipses label
        JLabel ellipsesLabel = new JLabel("⋮");
        ellipsesLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, contentFontSize));
        ellipsesLabel.setForeground(new Color(120, 120, 120));
        rGbc.gridx = 1;
        rGbc.weightx = 0;
        rGbc.insets = new Insets(0, 15, 0, 0); // spacing from amount
        rightPanel.add(ellipsesLabel, rGbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(rightPanel, gbc);
    }
}
