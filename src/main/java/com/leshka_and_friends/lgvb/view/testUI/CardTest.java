/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.testUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author giann
 */
public class CardTest extends JFrame{

    public CardTest() {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        FlatSVGIcon cardIcon = new FlatSVGIcon("icons/svg/card_vectors/logo2.svg", 200, 100);
        cardIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        
        JLabel label = new JLabel();
        label.setIcon(cardIcon);
        

        add(label);
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CardTest();
        });
    }   
}