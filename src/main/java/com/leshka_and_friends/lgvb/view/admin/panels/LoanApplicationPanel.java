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

public class LoanApplicationPanel extends JPanel {

    private JTable loanTable;
    private DefaultTableModel tableModel;

    private JLabel defaultLabel;
    private JLabel[] labelFields;
    private JLabel[] valueFields;

    private JLabel valRequestedBy;
    private JLabel valAccountNumber;
    private JLabel valAmount;
    private JLabel valLoanType;
    private JLabel valInterestRate;
    private JLabel valTerm;
    private JLabel valReferenceNumber;
    private JLabel valCreatedAt;

    private RoundedButton approveButton;
    private RoundedButton rejectButton;

    public LoanApplicationPanel() {
        setLayout(new BorderLayout());
        setBackground(ThemeGlobalDefaults.getColor("Panel.background"));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // --- Split Pane Setup ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(0);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {}
                    @Override
                    public Dimension getPreferredSize() { return new Dimension(0, 0); }
                };
            }
        });

        // --- LEFT SIDE ---
        JPanel leftPanel = new JPanel(new BorderLayout(0, 15));
        ThemeManager.putThemeAwareProperty(leftPanel, "background: $Panel.background");

        // --- Loan Info Panel ---
        RoundedPanel infoPanel = new RoundedPanel(25);
        infoPanel.setLayout(new GridBagLayout());
        ThemeManager.putThemeAwareProperty(infoPanel, "background: $LGVB.primary");
        infoPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titleLabel = new JLabel("Loan Information");
        ThemeManager.putThemeAwareProperty(titleLabel, "foreground: $Text.white");
        titleLabel.setFont(FontLoader.getBaloo2SemiBold(24f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 20, 0);
        gbc.gridwidth = 2;
        infoPanel.add(titleLabel, gbc);

        // Default label
        defaultLabel = new JLabel("Nothing Selected");
        ThemeManager.putThemeAwareProperty(defaultLabel, "foreground: $Text.white");
        defaultLabel.setFont(FontLoader.getInter(16f));
        gbc.gridy = 1;
        infoPanel.add(defaultLabel, gbc);

        // Fields
        String[] fieldLabels = {
                "Requested by:", "Account Number:", "Amount:",
                "Loan Type:", "Interest Rate:", "Term (in months):",
                "Reference Number:", "Created at:"
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
            gbc.gridy = i + 2;
            infoPanel.add(labelFields[i], gbc);
            gbc.gridx = 1;
            infoPanel.add(valueFields[i], gbc);
        }

        valRequestedBy = valueFields[0];
        valAccountNumber = valueFields[1];
        valAmount = valueFields[2];
        valLoanType = valueFields[3];
        valInterestRate = valueFields[4];
        valTerm = valueFields[5];
        valReferenceNumber = valueFields[6];
        valCreatedAt = valueFields[7];

        // --- Buttons Panel ---
        RoundedPanel buttonPanel = new RoundedPanel(25);
        buttonPanel.setLayout(new GridBagLayout());
        ThemeManager.putThemeAwareProperty(buttonPanel, "background: $Card1.background");

        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonHolder.setOpaque(false);
        buttonHolder.setBorder(new EmptyBorder(10, 0, 10, 0));

        approveButton = new RoundedButton("Approve");
        rejectButton = new RoundedButton("Reject");

        ThemeManager.putThemeAwareProperty(approveButton,
                "background: $LoanApplication.button.approve.background; foreground: $LoanApplication.button.approve.foreground");
        ThemeManager.putThemeAwareProperty(rejectButton,
                "background: $LoanApplication.button.reject.background; foreground: $LoanApplication.button.reject.foreground");

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

        JLabel tableTitle = new JLabel("Loan Applications");
        ThemeManager.putThemeAwareProperty(tableTitle, "foreground: $Text.white");
        tableTitle.setFont(FontLoader.getInter(17f));

        String[] columnNames = {
                "Loan ID", "Reference Number", "Email", "Principal", "Loan Type", "Term (Months)"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        loanTable = new JTable(tableModel);
        loanTable.setRowHeight(30);
        loanTable.getTableHeader().setReorderingAllowed(false);
        loanTable.setFont(FontLoader.getInter(14f));
        loanTable.getTableHeader().setFont(FontLoader.getInter(14f).deriveFont(Font.BOLD));

        // --- Table Colors ---
        Color tableBg = ThemeGlobalDefaults.getColor("LoanApplication.table.background");
        Color tableFg = ThemeGlobalDefaults.getColor("Table.foreground");
        Color headerBg = ThemeGlobalDefaults.getColor("LoanApplication.table.header.background");
        Color headerFg = ThemeGlobalDefaults.getColor("LoanApplication.table.header.foreground");
        Color gridColor = ThemeGlobalDefaults.getColor("LoanApplication.table.grid.color");
        Color selectionBg = ThemeGlobalDefaults.getColor("LoanApplication.table.selection.background");
        Color selectionFg = ThemeGlobalDefaults.getColor("LoanApplication.table.selection.foreground");

        loanTable.setBackground(tableBg);
        loanTable.setForeground(tableFg);
        loanTable.setGridColor(gridColor);
        loanTable.setSelectionBackground(selectionBg);
        loanTable.setSelectionForeground(selectionFg);
        loanTable.setShowVerticalLines(false);
        loanTable.setShowHorizontalLines(true);
        loanTable.setIntercellSpacing(new Dimension(0, 1));

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
        loanTable.getTableHeader().setDefaultRenderer(headerRenderer);

        JScrollPane scrollPane = new JScrollPane(loanTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(tableBg);

        tableContainer.add(tableTitle, BorderLayout.NORTH);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        // --- Wrappers with margins ---
        JPanel leftWrapper = new JPanel(new BorderLayout());
        leftWrapper.setOpaque(false);
        leftWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        leftWrapper.add(leftPanel, BorderLayout.CENTER);

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        rightWrapper.add(tableContainer, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftWrapper);
        splitPane.setRightComponent(rightWrapper);

        add(splitPane, BorderLayout.CENTER);
    }

    // --- Public Accessors ---
    public JTable getLoanTable() { return loanTable; }

    public void setLoanInfo(String requestedBy, String accountNumber, String amount,
                            String loanType, String interestRate, String term,
                            String referenceNumber, String createdAt) {
        defaultLabel.setVisible(false);
        for (JLabel lbl : labelFields) lbl.setVisible(true);
        for (JLabel val : valueFields) val.setVisible(true);

        valRequestedBy.setText(requestedBy);
        valAccountNumber.setText(accountNumber);
        valAmount.setText(amount);
        valLoanType.setText(loanType);
        valInterestRate.setText(interestRate);
        valTerm.setText(term);
        valReferenceNumber.setText(referenceNumber);
        valCreatedAt.setText(createdAt);
    }

    public RoundedButton getApproveButton() { return approveButton; }
    public RoundedButton getRejectButton() { return rejectButton; }
}
