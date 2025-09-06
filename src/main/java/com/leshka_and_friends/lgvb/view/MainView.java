/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.leshka_and_friends.lgvb.view.forms.Sidebar;
import java.awt.FlowLayout;
import javax.swing.JFrame;

/**
 *
 * @author giann
 */
public class MainView extends JFrame {

    public MainView() {
        setSize(1200, 1200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Sidebar sidebar = new Sidebar();
        add(sidebar);
        setVisible(true);
    }
    
    
}
