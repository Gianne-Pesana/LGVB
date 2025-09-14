/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.utils.SVGUtils;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author vongiedyaguilar
 */
public class CardPanel extends RoundedPanel {

    
//    private JLabel svgLabel;

       public CardPanel() {
        // Set panel size
        setPreferredSize(new Dimension(350, 250));
        setLayout(new BorderLayout());
        setOpaque(false);

        // Load SVG icon (adjust size to fit panel)
        int iconWidth = 200;
        int iconHeight = 140;
        FlatSVGIcon cardIcon = SVGUtils.loadIcon("/icons/svg/card.svg", iconWidth, "LGVB.primary");

        JLabel cardLabel = new JLabel(cardIcon);
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setVerticalAlignment(SwingConstants.CENTER);

        add(cardLabel, BorderLayout.CENTER);
    }
}
