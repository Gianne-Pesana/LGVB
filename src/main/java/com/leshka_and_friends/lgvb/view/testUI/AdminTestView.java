package com.leshka_and_friends.lgvb.view.testUI;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.core.loan.Loan;
import com.leshka_and_friends.lgvb.core.loan.LoanDAO;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminTestView extends JFrame {

    private DefaultTableModel tableModel;
    private JTable loanTable;

    // Form fields
    private JTextField loanIdField;
    private JTextField walletIdField;
    private JTextField refNumberField;
    private JTextField principalField;
    private JTextField remainingField;
    private JTextField interestField;
    private JTextField statusField;
    private JTextField loanTypeField;
    private JTextField createdAtField;

    JButton approveButton;
    JButton rejectButton;

    AppFacade facade;

    public AdminTestView(AppFacade facade) {
        this.facade = facade;

        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Manage Loans", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columnNames = {
                "Loan ID", "Wallet ID", "Reference #", "Principal",
                "Remaining Balance", "Interest Rate", "Status",
                "Loan Type", "Created At"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        loanTable = new JTable(tableModel);
        loanTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(loanTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // ===== FORM SECTION =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        loanIdField = new JTextField();
        walletIdField = new JTextField();
        refNumberField = new JTextField();
        principalField = new JTextField();
        remainingField = new JTextField();
        interestField = new JTextField();
        statusField = new JTextField();
        loanTypeField = new JTextField();
        createdAtField = new JTextField();

        row = addField(formPanel, gbc, row, "Loan ID", loanIdField);
        row = addField(formPanel, gbc, row, "Wallet ID", walletIdField);
        row = addField(formPanel, gbc, row, "Reference Number", refNumberField);
        row = addField(formPanel, gbc, row, "Principal", principalField);
        row = addField(formPanel, gbc, row, "Remaining Balance", remainingField);
        row = addField(formPanel, gbc, row, "Interest Rate", interestField);
        row = addField(formPanel, gbc, row, "Status", statusField);
        row = addField(formPanel, gbc, row, "Loan Type", loanTypeField);
        row = addField(formPanel, gbc, row, "Created At", createdAtField);

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        panel.add(formPanel, BorderLayout.SOUTH);
        add(panel);

        // Add table listener for selection
        addTableSelectionListener();

        // Load data from DAO
        loadLoans();
        onApprove();
    }

    private void addTableSelectionListener() {
        loanTable.getSelectionModel().addListSelectionListener(event -> {
            // Ignore if the table is still updating
            if (event.getValueIsAdjusting()) return;

            int selectedRow = loanTable.getSelectedRow();
            if (selectedRow != -1) {
                loanIdField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 0)));
                walletIdField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 1)));
                refNumberField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 2)));
                principalField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 3)));
                remainingField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 4)));
                interestField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 5)));
                statusField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 6)));
                loanTypeField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 7)));
                createdAtField.setText(String.valueOf(loanTable.getValueAt(selectedRow, 8)));
            }
        });
    }

    private void loadLoans() {
        LoanDAO loanDAO = new LoanDAO();
        List<Loan> loans = loanDAO.getPendingLoans(); // adjust based on your actual method

        tableModel.setRowCount(0); // Clear existing data

        for (Loan loan : loans) {
            Object[] rowData = {
                    loan.getLoanId(),
                    loan.getWalletId(),
                    loan.getReferenceNumber(),
                    loan.getPrincipal(),
                    loan.getRemainingBalance(),
                    loan.getInterestRate(),
                    loan.getStatus(),
                    Loan.PERSONAL,
                    loan.getCreatedAt()
            };
            tableModel.addRow(rowData);
        }
    }

    private int addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(labelText + ":"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        field.setPreferredSize(new Dimension(200, 24));
        panel.add(field, gbc);

        return row + 1;
    }

    public void onApprove() {
        approveButton.addActionListener(e -> {
            try {
                facade.approveLoan(Integer.parseInt(loanIdField.getText().trim()));
                OutputUtils.showInfo("Approve Success");
            } catch (Exception ex) {
                OutputUtils.showError("Approve failed");
                ex.printStackTrace();
            }

        });
    }
}
