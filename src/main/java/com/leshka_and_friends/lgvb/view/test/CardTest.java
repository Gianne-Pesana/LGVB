/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author giann
 */
public class CardTest extends JFrame{

    public CardTest() {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        
        FlatSVGIcon cardIcon = SVGUtils.loadCard(
                "/cards/Card1.svg",
                200,
                "Gianne",
                "12/29",
                "1234   5678   9876   5432"
        );

        JLabel label = new JLabel();
        label.setIcon(cardIcon);

        add(label);
//        pack();
        setVisible(true);
    }
}
