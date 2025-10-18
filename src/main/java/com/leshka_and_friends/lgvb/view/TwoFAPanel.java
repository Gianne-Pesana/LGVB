/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;


import javax.swing.*;
import java.awt.*;


public class TwoFAPanel extends JPanel {
    private JTextField codeField;
    private JButton verifyBtn;

    public TwoFAPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Enter 2FA code:");
        codeField = new JTextField(6);
        verifyBtn = new JButton("Verify");

        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(codeField);
        inputPanel.add(verifyBtn);

        add(inputPanel, BorderLayout.CENTER);
    }

    public String getCode() {
        return codeField.getText().trim();
    }

    public JButton getVerifyButton() {
        return verifyBtn;
    }
}
