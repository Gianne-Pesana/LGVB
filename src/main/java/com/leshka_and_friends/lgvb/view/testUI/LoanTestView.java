package com.leshka_and_friends.lgvb.view.testUI;

import javax.swing.*;

public class LoanTestView extends JFrame {
    public LoanTestPanel panel;

    public LoanTestView() {
        setTitle("Loan");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        panel = new LoanTestPanel();
        setContentPane(new LoanTestPanel());
        pack();
    }
}
