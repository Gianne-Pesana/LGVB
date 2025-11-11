package com.leshka_and_friends.lgvb.view.loansetup;

import com.leshka_and_friends.lgvb.view.shared_components.panels.LoanDefaultPanel;

import javax.swing.*;
import java.util.ArrayList;

public class LoanManager {

    public static LoanState state = LoanState.DEFAULT;
    public static final ArrayList<String> ListStatus = new ArrayList<>();

    public static JPanel createLoanPanel(LoanState state) {
        switch (state) {
            case DEFAULT:
                return new LoanDefaultPanel();
            case APPLIED:
                return new LoanAppliedPanel();
            case WAITING_APPROVAL:
                return new LoanWaitingPanel();
            case APPROVED:
                return new LoanApprovedPanel();
            default:
                throw new IllegalArgumentException("Unknown LoanState: " + state);
        }
    }




    public static void setState(LoanState newState) {
        state = newState;
    }
}
