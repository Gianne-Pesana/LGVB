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
            String status = facade.getLoanFacade().getLatestLoanStatus(facade.getSessionManager().getCurrentSession().getWallet().getWalletId());
            System.out.println("Loan Status: " + status); // Logging the status
            if (status == null || status.equalsIgnoreCase("closed")) {
                mainView.getLoanContainerPanel().showState(com.leshka_and_friends.lgvb.view.loansetup.LoanState.DEFAULT);
            } else {
                switch (status.toLowerCase()) {
                    case "pending":
                        mainView.getLoanContainerPanel().showState(com.leshka_and_friends.lgvb.view.loansetup.LoanState.WAITING_APPROVAL);
                        break;
                    case "active":
                        mainView.getLoanContainerPanel().showState(com.leshka_and_friends.lgvb.view.loansetup.LoanState.APPROVED);
                        break;
                    default:
                        mainView.getLoanContainerPanel().showState(com.leshka_and_friends.lgvb.view.loansetup.LoanState.DEFAULT);
                        break;
                }
            }
            mainView.showLoanPanel();
        });

        mainView.getLoanContainerPanel().getLoanAppliedPanel().getSubmitButton().setClickListener(() -> {
            String loanAmountStr = mainView.getLoanContainerPanel().getLoanAppliedPanel().getLoanAmount();
            String loanType = mainView.getLoanContainerPanel().getLoanAppliedPanel().getLoanType();
            String installmentPlanStr = mainView.getLoanContainerPanel().getLoanAppliedPanel().getInstallmentPlan();

            // Parse "X months" string to get integer value
            int termInMonths = Integer.parseInt(installmentPlanStr.replaceAll("[^0-9]", ""));

            double loanAmount = Double.parseDouble(loanAmountStr);
            int walletId = facade.getSessionManager().getCurrentSession().getWallet().getWalletId();

            facade.applyForLoan(walletId, loanType, loanAmount, termInMonths, ""); // purpose is empty for now
        });
    }
}
