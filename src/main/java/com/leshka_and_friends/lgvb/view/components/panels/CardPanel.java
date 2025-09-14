/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author giann
 */
public class CardPanel extends RoundedPanel {
    private String cardName;
    
    private int baseWidth;
    private int baseHeight;
    
    private JPanel headerPanel;
    

    public CardPanel(String cardName) {
        this.cardName = cardName;
        super();
        initComponents();
        
    }
    
    private void initComponents() {
        baseWidth = ThemeGlobalDefaults.getScaledInt("card.width");
        baseHeight = ThemeGlobalDefaults.getScaledInt("card.height");
        Dimension cardSize = new Dimension(baseWidth, baseHeight);
//        Dimension testDim = new Dimension(270, 170);
        setPreferredSize(cardSize);
        setMaximumSize(cardSize);
        
        ThemeManager.putThemeAwareProperty(this, "background: $Card1.background;");
        
        setBorder(BorderFactory.createEmptyBorder(7,21,7,21));
        
        headerPanel = createHeaderPanel();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        Dimension dim = new Dimension(
                Integer.MAX_VALUE, 
                ThemeGlobalDefaults.getScaledInt("card.header.height")
        );
        
        panel.setLayout(new BorderLayout());
        ThemeManager.putThemeAwareProperty(panel, "background: $LGVB.primary");
        
        JPanel logoPanel = new JPanel();
        ThemeManager.putThemeAwareProperty(logoPanel, "background: $LGVB.primary");
        logoPanel.setPreferredSize(new Dimension(
                ThemeGlobalDefaults.getScaledInt("card.logo.width"), 
                ThemeGlobalDefaults.getScaledInt("card.logo.size")
            )
        );
        
        
        
        JLabel logoLabel = new JLabel();
        FlatSVGIcon logoIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("cards.logo.path"), 
                ThemeGlobalDefaults.getScaledInt("card.logo.size")
        );
        logoIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        
        logoLabel.setIcon(logoIcon);
        logoLabel.setPreferredSize(new Dimension());
        
        
        return panel;
    }
}
