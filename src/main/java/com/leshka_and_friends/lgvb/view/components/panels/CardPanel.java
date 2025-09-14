/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.panels;

import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author giann
 */
public class CardPanel extends RoundedPanel {
    private String cardName;

    public CardPanel(String cardName) {
        this.cardName = cardName;
        super();
        initComponents();
        
    }
    
    private void initComponents() {
        
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setSize(500, 500);
            CardPanel cp = new CardPanel("Card1");
            frame.add(cp);
            frame.setVisible(true);
        });
        
    }
}
