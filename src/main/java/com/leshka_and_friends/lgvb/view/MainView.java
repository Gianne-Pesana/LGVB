/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.leshka_and_friends.lgvb.view.forms.Dashboard;
import com.leshka_and_friends.lgvb.view.forms.Sidebar;
import com.leshka_and_friends.lgvb.view.forms.Wallet;
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JSplitPane splitPane;
    private JPanel sidebarPanel;
    private JPanel dashboardPanel;
    private JPanel walletPanel;
    private JPanel mainContentPanel;

    private int width;
    private int height;
    private final double scaleFactor = 0.80;
    private final int margin = 400; 
    
    public MainView() {
        // Frame settings
        setTitle("MainView");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set size to screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) Math.floor(screenSize.getWidth() - margin);
        height = (int) Math.floor(screenSize.getHeight()* scaleFactor);
        
        setSize(width, height);
        
        //CardLayout Panels
        dashboardPanel = new Dashboard();
        walletPanel = new Wallet();

        // Main content panel (right)
        mainContentPanel = new JPanel(new CardLayout());
        mainContentPanel.putClientProperty("FlatLaf.style", "background: $Panel.background");
        JLabel mainContentPlaceholder = new JLabel("Main Content Placeholder", SwingConstants.CENTER);
        mainContentPlaceholder.putClientProperty("FlatLaf.style", "foreground: $Label.foreground");
        mainContentPanel.add(dashboardPanel, "dashboard");
        mainContentPanel.add(walletPanel, "wallet");
        
         // Sidebar panel (left)
        sidebarPanel = new Sidebar(mainContentPanel);
        
        //conten manager
        ContentManager contentManager = new ContentManager(mainContentPanel);
        contentManager.show("dashboard");
        
        // Split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebarPanel, mainContentPanel);
        splitPane.setDividerSize(0);

        // Add split pane to frame
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
