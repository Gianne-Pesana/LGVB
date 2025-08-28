package com.leshka_and_friends.lgvb.view;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class DashboardPanel extends JPanel {

    private Font interFont;

    // colors
    private static final Color BG = new Color(245, 246, 247);
    private static final Color BRAND_BLUE = new Color(10, 10, 160); // deep blue
    private static final Color TEXT_BLUE = new Color(0, 0, 102);

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setPreferredSize(new Dimension(250, 810)); // initial size

        loadInterFont();

        // ===== NORTH: GREETING + NOTIFICATION =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(24, 28, 18, 28)); // top / left / bottom / right

        JPanel greet = new JPanel();
        greet.setOpaque(false);
        greet.setLayout(new BoxLayout(greet, BoxLayout.Y_AXIS));

        JLabel hi = new JLabel("Hi, Leshka");
        hi.setFont(interFont.deriveFont(Font.BOLD, 28f));
        hi.setForeground(TEXT_BLUE);

        JLabel date = new JLabel("Today,  August 21, 2025");
        date.setFont(interFont.deriveFont(Font.PLAIN, 12f));
        date.setForeground(TEXT_BLUE);

        greet.add(hi);
        greet.add(date);

        JLabel notif = new JLabel(new ImageIcon(getClass().getResource("/icons/notification.png")));

        topBar.add(greet, BorderLayout.WEST);
        topBar.add(notif, BorderLayout.EAST);

        // ===== CENTER (GridBagLayout) =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // --- overviewPanelGroup: 2 blue cards row ---
        JPanel cardsRow = new JPanel(new GridLayout(1, 2, 16, 0));
        cardsRow.setOpaque(false);
        cardsRow.setBorder(BorderFactory.createEmptyBorder(8, 28, 8, 28));
        JPanel leftCard = createCard("Processing...");
        JPanel rightCard = createCard(""); // placeholder
        // lock row height so it won't stretch
        lockHeight(cardsRow, 140);
        cardsRow.add(leftCard);
        cardsRow.add(rightCard);

        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        center.add(cardsRow, gbc);

        // --- overviewPanelGroup: action buttons row ---
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 42, 6));
        buttonRow.setOpaque(false);
        buttonRow.setBorder(BorderFactory.createEmptyBorder(4, 20, 10, 20));
        lockHeight(buttonRow, 80);

        buttonRow.add(createIconButton("Send", "/icons/send.png"));
        buttonRow.add(createIconButton("Receive", "/icons/receive.png"));
        buttonRow.add(createIconButton("Top Up", "/icons/topup.png"));
        buttonRow.add(createIconButton("Add More", "/icons/addmore.png"));

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        center.add(buttonRow, gbc);

        // --- transactionPanelGroup: header ---
        JPanel txHeaderRow = new JPanel(new BorderLayout());
        txHeaderRow.setOpaque(false);
        txHeaderRow.setBorder(BorderFactory.createEmptyBorder(6, 28, 6, 28));
        JLabel txHeader = new JLabel("Transactions");
        txHeader.setFont(interFont.deriveFont(Font.BOLD, 16f));
        txHeader.setForeground(TEXT_BLUE);
        txHeaderRow.add(txHeader, BorderLayout.WEST);
        lockHeight(txHeaderRow, 28);

        gbc.gridy = 2;
        gbc.insets = new Insets(8, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        center.add(txHeaderRow, gbc);

        // --- transactionPanelGroup: blue placeholder that EXPANDS ---
        JPanel txPanel = new JPanel();
        txPanel.setBackground(BRAND_BLUE);
        txPanel.setBorder(BorderFactory.createLineBorder(BRAND_BLUE, 18, true));
        JPanel txWrapper = new JPanel(new BorderLayout());
        txWrapper.setOpaque(false);
        txWrapper.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));
        txWrapper.add(txPanel, BorderLayout.CENTER);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0; // <-- ONLY this row absorbs extra height
        center.add(txWrapper, gbc);

        // ===== add to root =====
        add(topBar, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    // Helpers
    private void loadInterFont() {
        try (InputStream is = getClass().getResourceAsStream("/fonts/Inter-Regular.ttf")) {
            if (is != null) {
                interFont = Font.createFont(Font.TRUETYPE_FONT, is);
                // optional: register so UI defaults use it if needed
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(interFont);
            } else {
                interFont = new Font("SansSerif", Font.PLAIN, 12);
                System.err.println("⚠️ Could not find Inter-Regular.ttf, using fallback.");
            }
        } catch (Exception e) {
            interFont = new Font("SansSerif", Font.PLAIN, 12);
            e.printStackTrace();
        }
    }

    private JPanel createCard(String text) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(BRAND_BLUE);
        card.setBorder(BorderFactory.createLineBorder(BRAND_BLUE, 18, true));
        // keep each card about 140px tall total with the border
        card.setPreferredSize(new Dimension(200, 140));
        card.setMinimumSize(new Dimension(100, 140));

        if (text != null && !text.isEmpty()) {
            JLabel label = new JLabel(text);
            label.setFont(interFont.deriveFont(Font.BOLD, 16f));
            label.setForeground(Color.WHITE);
            card.add(label);
        }
        return card;
    }

    private JPanel createIconButton(String text, String iconPath) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel icon = new JLabel(new ImageIcon(getClass().getResource(iconPath)));
        icon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(interFont.deriveFont(Font.PLAIN, 12f));
        lbl.setForeground(TEXT_BLUE);

        p.add(icon, BorderLayout.CENTER);
        p.add(lbl, BorderLayout.SOUTH);
        p.setPreferredSize(new Dimension(110, 72)); // keeps consistent spacing
        return p;
    }

    private static void lockHeight(JComponent c, int h) {
        Dimension pref = c.getPreferredSize();
        if (pref == null) pref = new Dimension(10, h);
        c.setPreferredSize(new Dimension(Math.max(pref.width, 10), h));
        c.setMinimumSize(new Dimension(10, h));
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, h));
    }
}
