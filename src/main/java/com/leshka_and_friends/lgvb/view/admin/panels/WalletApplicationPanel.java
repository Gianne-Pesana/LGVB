package com.leshka_and_friends.lgvb.view.admin.panels;

import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedButton;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WalletApplicationPanel extends JPanel {

    private JTable walletTable;
    private DefaultTableModel tableModel;

    private JLabel valWalletId;
    private JLabel valAccountNumber;
    private JLabel valEmail;
    private JLabel valFirstName;
    private JLabel valLastName;
    private JLabel valStatus;
    private JLabel valCreatedAt;

    private JLabel defaultLabel;
    private JLabel[] labelFields;
    private JLabel[] valueFields;

    private RoundedButton approveButton;
    private RoundedButton rejectButton;

    public WalletApplicationPanel() {
        setLayout(new BorderLayout());
        setBackground(ThemeGlobalDefaults.getColor("Panel.background"));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // --- Split Pane Setup ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(0); // make divider invisible
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        // force transparent divider
        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {
                        // do nothing (completely invisible divider)
                    }

                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }
        });

        // --- LEFT SIDE ---
        JPanel leftPanel = new JPanel(new BorderLayout(0, 15));
        ThemeManager.putThemeAwareProperty(leftPanel, "background: $Panel.background");

        // --- Wallet Info Panel ---
        RoundedPanel infoPanel = new RoundedPanel(25);
        infoPanel.setLayout(new GridBagLayout());
        ThemeManager.putThemeAwareProperty(infoPanel, "background: $LGVB.primary");
        infoPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("Wallet Information");
        ThemeManager.putThemeAwareProperty(titleLabel, "foreground: $Text.white");
        titleLabel.setFont(FontLoader.getBaloo2SemiBold(24f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 20, 0);
        gbc.gridwidth = 2;
        infoPanel.add(titleLabel, gbc);

// Default "Nothing Selected" label
        defaultLabel = new JLabel("Nothing Selected");
        ThemeManager.putThemeAwareProperty(defaultLabel, "foreground: $Text.white");
        defaultLabel.setFont(FontLoader.getInter(16f));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        infoPanel.add(defaultLabel, gbc);

// Original field labels and value labels (hidden initially)
        String[] fieldLabels = {
                "Wallet ID:", "Account Number:", "Email:",
                "First Name:", "Last Name:", "Status:", "Created At:"
        };
        labelFields = new JLabel[fieldLabels.length];
        valueFields = new JLabel[fieldLabels.length];

        for (int i = 0; i < fieldLabels.length; i++) {
            labelFields[i] = new JLabel(fieldLabels[i]);
            ThemeManager.putThemeAwareProperty(labelFields[i], "foreground: $Text.white");
            labelFields[i].setFont(FontLoader.getInter(15f));

            valueFields[i] = new JLabel("-");
            ThemeManager.putThemeAwareProperty(valueFields[i], "foreground: $Text.white");
            valueFields[i].setFont(FontLoader.getInter(15f));
            labelFields[i].setVisible(false);
            valueFields[i].setVisible(false);

            gbc.gridwidth = 1;
            gbc.insets = new Insets(8, 0, 8, 10);

            gbc.gridx = 0;
            gbc.gridy = i + 1; // start after title
            infoPanel.add(labelFields[i], gbc);
            gbc.gridx = 1;
            infoPanel.add(valueFields[i], gbc);
        }

        valWalletId = valueFields[0];
        valAccountNumber = valueFields[1];
        valEmail = valueFields[2];
        valFirstName = valueFields[3];
        valLastName = valueFields[4];
        valStatus = valueFields[5];
        valCreatedAt = valueFields[6];


        // --- Buttons Panel ---
        RoundedPanel buttonPanel = new RoundedPanel(25);
        buttonPanel.setLayout(new GridBagLayout());
        ThemeManager.putThemeAwareProperty(buttonPanel, "background: $Card1.background");

        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonHolder.setOpaque(false);
        buttonHolder.setBorder(new EmptyBorder(10,0,10,0));

        approveButton = new RoundedButton("Approve");
        rejectButton = new RoundedButton("Reject");

        ThemeManager.putThemeAwareProperty(approveButton,
                "background: $WalletApplication.button.approve.background; foreground: $WalletApplication.button.approve.foreground");
        ThemeManager.putThemeAwareProperty(rejectButton,
                "background: $WalletApplication.button.reject.background; foreground: $WalletApplication.button.reject.foreground");

        approveButton.setFont(FontLoader.getInter(14f));
        rejectButton.setFont(FontLoader.getInter(14f));

        buttonHolder.add(approveButton);
        buttonHolder.add(rejectButton);

        buttonPanel.add(buttonHolder, new GridBagConstraints());

        leftPanel.add(infoPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // --- RIGHT SIDE (TABLE) ---
        RoundedPanel tableContainer = new RoundedPanel(25);
        ThemeManager.putThemeAwareProperty(tableContainer, "background: $Card1.background");
        tableContainer.setLayout(new BorderLayout(0, 10));
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel tableTitle = new JLabel("Wallet Applications");
        ThemeManager.putThemeAwareProperty(tableTitle, "foreground: $Text.white");
        tableTitle.setFont(FontLoader.getInter(17f));

        String[] columnNames = {"Wallet ID", "Account Number", "Email", "Created At"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        walletTable = new JTable(tableModel);
        walletTable.setRowHeight(30);
        walletTable.getTableHeader().setReorderingAllowed(false);
        walletTable.setFont(FontLoader.getInter(14f));
        walletTable.getTableHeader().setFont(FontLoader.getInter(14f).deriveFont(Font.BOLD));

        // --- Table Colors ---
        Color tableBg = ThemeGlobalDefaults.getColor("Table.background");
        Color tableFg = ThemeGlobalDefaults.getColor("Table.foreground");
        Color headerBg = ThemeGlobalDefaults.getColor("Table.header.background");
        Color headerFg = ThemeGlobalDefaults.getColor("Table.header.foreground");
        Color gridColor = ThemeGlobalDefaults.getColor("Table.grid.color");
        Color selectionBg = ThemeGlobalDefaults.getColor("Table.selection.background");
        Color selectionFg = ThemeGlobalDefaults.getColor("Table.selection.foreground");

        walletTable.setBackground(tableBg);
        walletTable.setForeground(tableFg);
        walletTable.setGridColor(gridColor);
        walletTable.setSelectionBackground(selectionBg);
        walletTable.setSelectionForeground(selectionFg);
        walletTable.setShowVerticalLines(false);
        walletTable.setShowHorizontalLines(true);
        walletTable.setIntercellSpacing(new Dimension(0, 1));

        // --- Custom Header Renderer ---
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(headerBg);
                lbl.setForeground(headerFg);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);
                lbl.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
                return lbl;
            }
        };
        walletTable.getTableHeader().setDefaultRenderer(headerRenderer);

        JScrollPane scrollPane = new JScrollPane(walletTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(tableBg);

        tableContainer.add(tableTitle, BorderLayout.NORTH);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        // LEFT SIDE wrapper with right margin
        JPanel leftWrapper = new JPanel(new BorderLayout());
        leftWrapper.setOpaque(false);
        leftWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15)); // 15px margin to the right
        leftWrapper.add(leftPanel, BorderLayout.CENTER);

// RIGHT SIDE wrapper with left margin
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // 15px margin to the left
        rightWrapper.add(tableContainer, BorderLayout.CENTER);

// Add to split pane
        splitPane.setLeftComponent(leftWrapper);
        splitPane.setRightComponent(rightWrapper);

        add(splitPane, BorderLayout.CENTER);
    }

    // --- Public accessors ---
    public JTable getWalletTable() { return walletTable; }

    public void setWalletInfo(String walletId, String accountNumber, String email,
                              String firstName, String lastName, String status, String createdAt) {
        // Hide default message
        defaultLabel.setVisible(false);

        // Show actual fields
        for (JLabel lbl : labelFields) lbl.setVisible(true);
        for (JLabel val : valueFields) val.setVisible(true);

        valWalletId.setText(walletId);
        valAccountNumber.setText(accountNumber);
        valEmail.setText(email);
        valFirstName.setText(firstName);
        valLastName.setText(lastName);
        valStatus.setText(status);
        valCreatedAt.setText(createdAt);
    }


    public RoundedButton getApproveButton() { return approveButton; }
    public RoundedButton getRejectButton() { return rejectButton; }
}
