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
                if (status == null || status.equalsIgnoreCase("closed")) {
                    mainView.getLoanContainerPanel().showState(com.leshka_and_friends.lgvb.view.loansetup.LoanState.DEFAULT);
                    mainView.showLoanPanel();
                    return;
                }

                OutputUtils.showInfo("You have an ongoing loan application with status: " + status);

            } catch (SQLException sq) {
                System.out.println("Error: " + sq.getMessage());
            }


        });



    }
}
