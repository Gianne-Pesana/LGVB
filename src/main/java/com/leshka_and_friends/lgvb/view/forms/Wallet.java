/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.forms;

import com.leshka_and_friends.lgvb.view.components.panels.HeaderPanel;
import com.leshka_and_friends.lgvb.view.factories.HeaderFactory;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author vongiedyaguilar
 */
public class Wallet extends JPanel {
    
    private HeaderPanel headerPanel;
    
     public Wallet() {
        setOpaque(false);
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        add(createHeaderPanel(), BorderLayout.NORTH);
    }
    
     private JPanel createHeaderPanel() {
        headerPanel = HeaderFactory.createWalletHeader();
        return headerPanel;
    }
    
}