package com.leshka_and_friends.lgvb.view.loansetup;

import javax.swing.*;
import java.awt.*;

public class LoanContainerPanel extends JPanel {

    private CardLayout cardLayout;
    private LoanDefault loanDefaultPanel;
    private LoanAppliedPanel loanAppliedPanel;
    private LoanWaitingPanel loanWaitingPanel;
    private LoanApprovedPanel loanApprovedPanel;

    public LoanContainerPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create instances of all possible loan state panels
        loanDefaultPanel = new LoanDefault();
        loanAppliedPanel = new LoanAppliedPanel();
        loanWaitingPanel = new LoanWaitingPanel();
        loanApprovedPanel = new LoanApprovedPanel();

        // Add panels to the card layout
        add(loanDefaultPanel, LoanState.DEFAULT.name());
        add(loanAppliedPanel, LoanState.APPLIED.name());
        add(loanWaitingPanel, LoanState.WAITING_APPROVAL.name());
        add(loanApprovedPanel, LoanState.APPROVED.name());

        // Set up listeners to transition between states
        loanDefaultPanel.setStateChangeListener(newState -> showState(newState));
        loanAppliedPanel.setOnStateChangeListener(newState -> showState(newState));
    }

    public void showState(LoanState state) {
        cardLayout.show(this, state.name());
    }

    public LoanAppliedPanel getLoanAppliedPanel() {
        return loanAppliedPanel;
    }
}
