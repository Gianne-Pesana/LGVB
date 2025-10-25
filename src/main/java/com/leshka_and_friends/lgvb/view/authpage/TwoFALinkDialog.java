/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.authpage;


import javax.swing.*;
import java.awt.*;

public class TwoFALinkDialog extends JDialog {

    private boolean confirmed = false;

    public TwoFALinkDialog(Frame parent, String otpAuthUrl) {
        super(parent, "Link Authenticator App", true); // <— 'true' makes it modal
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(480, 680);
        setLocationRelativeTo(parent);
        setResizable(false);

        TwoFALinkPanel panel = new TwoFALinkPanel(otpAuthUrl);
        panel.getConfirmButton().addActionListener(e -> {
            confirmed = true;
            dispose(); // close dialog
        });

        add(panel, BorderLayout.CENTER);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public static boolean showDialog(Frame parent, String otpAuthUrl) {
        TwoFALinkDialog dialog = new TwoFALinkDialog(parent, otpAuthUrl);
        dialog.setVisible(true); // <— blocks here until dispose()
        return dialog.isConfirmed();
    }
}
