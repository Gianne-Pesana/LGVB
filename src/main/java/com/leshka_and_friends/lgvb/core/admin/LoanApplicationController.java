package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.core.loan.Loan;
import com.leshka_and_friends.lgvb.view.admin.AdminDashboard;
import com.leshka_and_friends.lgvb.view.admin.panels.LoanApplicationPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoanApplicationController {
    private final AppFacade facade;
    private final LoanApplicationPanel panel;
    private final AdminDashboard adminDashboard;

    private List<LoanWithUser> loanApplications; // cache of fetched loans

    public LoanApplicationController(AppFacade facade, AdminDashboard adminDashboard) {
        this.facade = facade;
        this.adminDashboard = adminDashboard;
        this.panel = adminDashboard.getLoanApplicationPanel();

        loadTable();
        handleTableClick();
        handleButtons();
    }

    // --- Load pending loan applications into the table ---
    private void loadTable() {
        loanApplications = facade.getAdminService().getAllLoanApplications();

        var model = panel.getLoanTable().getModel();
        if (model instanceof DefaultTableModel tableModel) {
            tableModel.setRowCount(0); // clear table
            for (LoanWithUser l : loanApplications) {
                tableModel.addRow(new Object[]{
                        l.loanId,
                        l.referenceNumber,
                        l.email,
                        l.principal,
                        l.loanType,
                        l.termInMonths
                });
            }
        }
    }

    // --- Handle table row selection ---
    private void handleTableClick() {
        panel.getLoanTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = panel.getLoanTable().getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < loanApplications.size()) {
                        LoanWithUser l = loanApplications.get(selectedRow);
                        panel.setLoanInfo(
                                l.firstName + " " + l.lastName,
                                l.accountNumber,
                                String.valueOf(l.principal),
                                l.loanType,
                                String.valueOf(l.interestRate),
                                String.valueOf(l.termInMonths),
                                l.referenceNumber,
                                l.createdAt.toString()
                        );
                    }
                }
            }
        });
    }

    // --- Handle Approve / Reject buttons ---
    private void handleButtons() {
        panel.getApproveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                approveSelectedLoan();
            }
        });

        panel.getRejectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rejectSelectedLoan();
            }
        });
    }

    private void approveSelectedLoan() {
        int selectedRow = panel.getLoanTable().getSelectedRow();
        if (selectedRow < 0) {
            OutputUtils.showError(adminDashboard, "Please select a loan to approve.");
            return;
        }

        LoanWithUser l = loanApplications.get(selectedRow);
        int confirm = OutputUtils.showConfirmationDialog(
                adminDashboard,
                "Are you sure you want to approve loan ID " + l.loanId + "?",
                "Confirm Approval"
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            facade.getLoanFacade().updateLoanStatus(l.loanId, Loan.ACTIVE);
            OutputUtils.showInfo(adminDashboard, "Loan ID " + l.loanId + " approved successfully.");
            loadTable(); // refresh table
        } catch (Exception ex) {
            OutputUtils.showError(adminDashboard, "Failed to approve loan: " + ex.getMessage());
        }
    }

    private void rejectSelectedLoan() {
        int selectedRow = panel.getLoanTable().getSelectedRow();
        if (selectedRow < 0) {
            OutputUtils.showError(adminDashboard, "Please select a loan to reject.");
            return;
        }

        LoanWithUser l = loanApplications.get(selectedRow);
        int confirm = OutputUtils.showConfirmationDialog(
                adminDashboard,
                "Are you sure you want to reject loan ID " + l.loanId + "?",
                "Confirm Rejection"
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            facade.getLoanFacade().updateLoanStatus(l.loanId, Loan.CLOSED);
            OutputUtils.showInfo(adminDashboard, "Loan ID " + l.loanId + " rejected successfully.");
            loadTable(); // refresh table
        } catch (Exception ex) {
            OutputUtils.showError(adminDashboard, "Failed to reject loan: " + ex.getMessage());
        }
    }
}