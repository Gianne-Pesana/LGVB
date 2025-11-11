/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.loansetup;

import com.leshka_and_friends.lgvb.view.shared_components.panels.HeaderPanel;
import com.leshka_and_friends.lgvb.view.shared_components.panels.LoanDefaultPanel;
import com.leshka_and_friends.lgvb.view.shared_components.panels.LoanHeaderPanel;
import com.leshka_and_friends.lgvb.view.factories.HeaderFactory;


import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author vongiedyaguilar
 */

public class LoanDefault extends JPanel {

    private LoanDefaultPanel loanDefaultPanel;
    private HeaderPanel headerPanel;

    // Callback interface
    public interface LoanStateChangeListener {
        void onStateChange(LoanState newState); // <- use top-level enum
    }


    private LoanStateChangeListener stateListener;

    public void setStateChangeListener(LoanStateChangeListener listener) {
        this.stateListener = listener;
    }

    public LoanDefault() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        initComponents();
    }

    private void initComponents() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMidPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        headerPanel = HeaderFactory.createWalletHeader();
        return headerPanel;
    }

    private JPanel createMidPanel() {
        loanDefaultPanel = new LoanDefaultPanel();

        // Link the button click to the state change
        loanDefaultPanel.setOnApplyListener(() -> {
            if (stateListener != null) {
                stateListener.onStateChange(LoanState.APPLIED);
            }
        });

        return loanDefaultPanel;
    }


}
