package com.leshka_and_friends.lgvb.view.loansetup;

import javax.swing.*;
import java.awt.*;

public class LoanContainerPanel extends JPanel {

    private final CardLayout cardLayout;

    // Panels for each loan state
    private final LoanDefault loanDefaultPanel;
    private final LoanAppliedPanel loanAppliedPanel;
    private final LoanWaitingPanel loanWaitingPanel;
    private final LoanApprovedPanel loanApprovedPanel;

    public LoanContainerPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create each panel
        loanDefaultPanel = new LoanDefault();
        loanAppliedPanel = new LoanAppliedPanel();
        loanWaitingPanel = new LoanWaitingPanel();
        loanApprovedPanel = new LoanApprovedPanel();

        // Add to CardLayout using enum names
        add(loanDefaultPanel, LoanState.DEFAULT.name());
        add(loanAppliedPanel, LoanState.APPLIED.name());
        add(loanWaitingPanel, LoanState.WAITING_APPROVAL.name());
        add(loanApprovedPanel, LoanState.APPROVED.name());

        // Link all panels to transition function
        loanDefaultPanel.setStateChangeListener(this::showState);
        loanAppliedPanel.setOnStateChangeListener(this::showState);
        loanWaitingPanel.setStateChangeListener(this::showState);
        loanApprovedPanel.setStateChangeListener(this::showState);

        // Start at the default state
        showState(LoanState.DEFAULT);
    }

    public void showState(LoanState state) {
        LoanManager.setState(state);
        cardLayout.show(this, state.name());
    }
}
