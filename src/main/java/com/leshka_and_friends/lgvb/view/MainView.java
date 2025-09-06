/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.leshka_and_friends.lgvb.view.forms.Sidebar;
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JSplitPane splitPane;
    private JPanel sidebarPanel;
    private JPanel mainContentPanel;

    private int width;
    private int height;
    private final double scaleFactor = 0.95;
    
    public MainView() {
        // Frame settings
        setTitle("MainView");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set size to screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) Math.floor(screenSize.getWidth() * scaleFactor);
        height = (int) Math.floor(screenSize.getHeight()* scaleFactor);
        
        setSize(width, height);

        // Sidebar panel (left)
        sidebarPanel = new Sidebar();

        // Main content panel (right)
        mainContentPanel = new JPanel();
        mainContentPanel.putClientProperty("FlatLaf.style", "background: $Panel.background");
        JLabel mainContentPlaceholder = new JLabel("Main Content Placeholder", SwingConstants.CENTER);
        mainContentPlaceholder.putClientProperty("FlatLaf.style", "foreground: $Label.foreground");
        mainContentPanel.add(mainContentPlaceholder);

        // Split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebarPanel, mainContentPanel);
        splitPane.setDividerSize(0);

        // Add split pane to frame
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
