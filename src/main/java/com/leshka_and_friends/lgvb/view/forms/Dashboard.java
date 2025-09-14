/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.forms;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.buttons.MenuItemButtonDashboard;
import com.leshka_and_friends.lgvb.view.components.panels.HeaderPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.leshka_and_friends.lgvb.view.components.panels.BalancePanel;
import com.leshka_and_friends.lgvb.view.components.panels.CardPanel;
import com.leshka_and_friends.lgvb.view.factories.HeaderFactory;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author vongiedyaguilar
 */
public class Dashboard extends JPanel {
    
    private int width = UIScale.scale(380);
    private int height = UIScale.scale(100);
    
    private HeaderPanel headerPanel;
    private BalancePanel balancePanel;
    private CardPanel cardLayout;

    // Store your buttons here
    private List<MenuItemButtonDashboard> menuItems = new ArrayList<>();
    
    public Dashboard() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        initComponents();
    }
    
    private void initComponents() {
        add(createHeaderPanel());
        add(createMiddlePanel());
        add(createSouthPanel());
    }
    
    private JPanel createHeaderPanel() {
        headerPanel = HeaderFactory.createDashboardHeader();
        return headerPanel;
    }
    
    private JPanel createMiddlePanel() {
        JPanel middlePanel = new JPanel();
        middlePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 170));middlePanel.setBackground(new Color(1, 1, 1));
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.setMaximumSize(middlePanel.getPreferredSize());
        middlePanel.setOpaque(false);
        
        JPanel middleWestPanel = new JPanel();
        middleWestPanel.setLayout(new BoxLayout(middleWestPanel, BoxLayout.Y_AXIS));
        middleWestPanel.setPreferredSize(new Dimension(width, height));
        
        // menuBarDashboard container
        JPanel menuBarDashboard = new JPanel();
        menuBarDashboard.setPreferredSize(new Dimension(380, 65));
        menuBarDashboard.setLayout(new BoxLayout(menuBarDashboard, BoxLayout.X_AXIS));
        menuBarDashboard.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        //separator panel
//        JPanel separatorPanel = new JPanel(new BorderLayout());
//        separatorPanel.setOpaque(false);
//
//        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
//        separator.setForeground(new Color(200, 200, 200));
//
//        separatorPanel.add(separator, BorderLayout.CENTER);
//        separatorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
//        separatorPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 2));
        
        // Insert menu items here
        initMenuItems(menuBarDashboard);
        
        //Menubar west container
        JPanel mbWestContainer = new JPanel(new BorderLayout());
        mbWestContainer.add(menuBarDashboard);
        mbWestContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        // add other panels
        cardLayout = new CardPanel("1", "2", "3", "4");
        balancePanel = new BalancePanel();
        
        middleWestPanel.add(balancePanel);
//        middleWestPanel.add(Box.createHorizontalStrut(10));
        middleWestPanel.add(mbWestContainer);
        
        middlePanel.add(middleWestPanel, BorderLayout.WEST);
        middlePanel.add(cardLayout, BorderLayout.EAST);
       
        return middlePanel;
    }
    
    private JPanel createSouthPanel(){
        JPanel southContainer = new JPanel();
        southContainer.setPreferredSize(new Dimension(Integer.MAX_VALUE,350));
        southContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        
        
        return southContainer;
    }
    
private void initMenuItems(JPanel menuBarDashboard) {
    // SVG paths
    String[] svgPaths = {
        "icons/svg/send.svg",
        "icons/svg/receive.svg",
        "icons/svg/topup.svg",
        "icons/svg/addmore.svg"
    };

    // Labels for each button
    String[] labels = {
        "Send",
        "Receive",
        "Top Up",
        "Add More"
    };

    menuItems = new ArrayList<>();

    Dimension buttonSize = new Dimension(60, 70); // width + height (adjusted for icon + text)

    for (int i = 0; i < svgPaths.length; i++) {
        MenuItemButtonDashboard btn = new MenuItemButtonDashboard(svgPaths[i], labels[i], true);
        btn.setFocusable(false);

        // Lock button sizing
        btn.setPreferredSize(buttonSize);
        btn.setMaximumSize(buttonSize);
        btn.setMinimumSize(buttonSize);

        menuItems.add(btn);
        menuBarDashboard.add(btn);
        menuBarDashboard.add(Box.createHorizontalStrut(10)); // spacing
    }
}

    
    public void setHeaderTitle(String title){
        headerPanel.setTitle(title);
    }

    public String getHeaderTitle() {
        return headerPanel.getTitle();
    }
}