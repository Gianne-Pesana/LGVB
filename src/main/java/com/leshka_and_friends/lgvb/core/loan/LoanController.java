package com.leshka_and_friends.lgvb.core.loan;

import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.MainView;
import com.leshka_and_friends.lgvb.view.ui_utils.OutputUtils;

import java.sql.SQLException;

public class LoanController {
    AppFacade facade;
    MainView mainView;

    public LoanController(AppFacade facade, MainView mainView) {
        this.facade = facade;
        this.mainView = mainView;

        mainView.getSidebarPanel().getLoanReqItem().addActionListener(() -> {
            System.out.println("Loan Btn Clicked");
            try {
                String status = new LoanDAO().getStatusLatest(facade.getSessionManager().getCurrentSession().getWallet().getWalletId());
                if (!status.equalsIgnoreCase("closed")) {
                    OutputUtils.showInfo("Account Status: " + status);
                    return;
                }


            } catch (SQLException sq) {
                System.out.println("Error: " + sq.getMessage());
            }


        });

        mainView.getLoanPanelTest().applyButton.addActionListener(ae -> {
            try {
                double amount = Integer.parseInt(mainView.getLoanPanelTest().amountField.getText());
                String type = mainView.getLoanPanelTest().loanTypeCombo.getSelectedItem().toString();

                facade.applyForLoan(
                        facade.getSessionManager().getCurrentSession().getWallet().getWalletId(),
                        amount,
                        type
                );

            } catch (Exception ex) {
                OutputUtils.showError(ex.getMessage());
            }
        });

    }
}
