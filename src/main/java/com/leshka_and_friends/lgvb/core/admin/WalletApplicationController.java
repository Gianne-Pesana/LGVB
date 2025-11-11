package com.leshka_and_friends.lgvb.core.admin;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.view.admin.AdminDashboard;
import com.leshka_and_friends.lgvb.view.admin.panels.WalletApplicationPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WalletApplicationController {
    private final AppFacade facade;
    private final WalletApplicationPanel panel;
    private final AdminDashboard adminDashboard;

    private List<WalletWithUser> walletApplications; // cache of fetched wallets

    public WalletApplicationController(AppFacade facade, AdminDashboard adminDashboard) {
        this.facade = facade;
        this.adminDashboard = adminDashboard;
        this.panel = adminDashboard.getWalletApplicationPanel();

        loadTable();
        handleTableClick();
        handleButtons();
    }

    // --- Load pending wallet applications into the table ---
    private void loadTable() {
        walletApplications = facade.getWalletFacade().walletService.getAllWalletApplications();

        var model = panel.getWalletTable().getModel();
        if (model instanceof DefaultTableModel tableModel) {
            tableModel.setRowCount(0); // clear table
            for (WalletWithUser w : walletApplications) {
                tableModel.addRow(new Object[]{
                        w.walletId,
                        w.accountNumber,
                        w.email,
                        w.createdAt.toString()
                });
            }
        }
    }

    // --- Handle table row selection ---
    private void handleTableClick() {
        panel.getWalletTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = panel.getWalletTable().getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < walletApplications.size()) {
                        WalletWithUser w = walletApplications.get(selectedRow);
                        panel.setWalletInfo(
                                String.valueOf(w.walletId),
                                w.accountNumber,
                                w.email,
                                w.firstName,
                                w.lastName,
                                w.status,
                                w.createdAt.toString()
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
                approveSelectedWallet();
            }
        });

        panel.getRejectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rejectSelectedWallet();
            }
        });
    }

    private void approveSelectedWallet() {
        int selectedRow = panel.getWalletTable().getSelectedRow();
        if (selectedRow < 0) {
            OutputUtils.showError(adminDashboard, "Please select a wallet to approve.");
            return;
        }

        WalletWithUser w = walletApplications.get(selectedRow);
        int confirm = OutputUtils.showConfirmationDialog(
                adminDashboard,
                "Are you sure you want to approve wallet ID " + w.walletId + "?",
                "Confirm Approval"
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            w.status = Wallet.ACTIVE;
            facade.getWalletFacade().walletService.updateWalletStatus(w.walletId, Wallet.ACTIVE);
            OutputUtils.showInfo(adminDashboard, "Wallet ID " + w.walletId + " approved successfully.");
            loadTable(); // refresh table
        } catch (Exception ex) {
            OutputUtils.showError(adminDashboard, "Failed to approve wallet: " + ex.getMessage());
        }
    }

    private void rejectSelectedWallet() {
        int selectedRow = panel.getWalletTable().getSelectedRow();
        if (selectedRow < 0) {
            OutputUtils.showError(adminDashboard, "Please select a wallet to reject.");
            return;
        }

        WalletWithUser w = walletApplications.get(selectedRow);
        int confirm = OutputUtils.showConfirmationDialog(
                adminDashboard,
                "Are you sure you want to reject wallet ID " + w.walletId + "?",
                "Confirm Rejection"
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            w.status = "closed";
            facade.getWalletFacade().walletService.updateWalletStatus(w.walletId, "closed");
            OutputUtils.showInfo(adminDashboard, "Wallet ID " + w.walletId + " rejected successfully.");
            loadTable(); // refresh table
        } catch (Exception ex) {
            OutputUtils.showError(adminDashboard, "Failed to reject wallet: " + ex.getMessage());
        }
    }

}
