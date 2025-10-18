/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author giann
 */
public class TwoFALinkFrame extends JFrame {
    private TwoFALinkPanel tfa;
    

    public TwoFALinkFrame(String otpAuthUrl) {
        setTitle("Link Authenticator App");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        tfa = new TwoFALinkPanel(otpAuthUrl);
        add(tfa, BorderLayout.CENTER);
    }

    public TwoFALinkPanel getTfa() {
        return tfa;
    }

    public void setTfa(TwoFALinkPanel tfa) {
        this.tfa = tfa;
    }
    
    
}
