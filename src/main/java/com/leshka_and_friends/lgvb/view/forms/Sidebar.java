/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.forms;

import com.leshka_and_friends.lgvb.view.components.buttons.ThemeToggleButton;
import com.leshka_and_friends.lgvb.view.components.buttons.MenuItemButton;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.panels.*;
import com.leshka_and_friends.lgvb.view.factories.SidebarButtonFactory;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.utils.ImageParser;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author giann
 */
public class Sidebar extends JPanel {

    private int width = UIScale.scale(238);
    private int height = UIScale.scale(720);

    private final int separation = UIScale.scale(50);
    private static boolean isDarkMode;

    private MenuItemButton[] menuItems;

    private MenuItemButton dashboardItem, walletItem, loanReqItem, cardsItem;
    private MenuItemButton accountItem, settingsItem;
    private ThemeToggleButton modeToggle;

    public Sidebar() {
        isDarkMode = (UIManager.getLookAndFeel() instanceof LGVBDark);
        setPreferredSize(new Dimension(width, height));
        putClientProperty("FlatLaf.style", "background: $LGVB.primary");
        setLayout(new BorderLayout());

        initItems();
        initComponents();
        initMenuBehavior();
    }

    private void initComponents() {
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);

    }

    private JPanel createNorthPanel() {
        JPanel northContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        northContainer.setOpaque(false);

        JLabel logo = new JLabel(ImageParser.loadScaled("/Logo/logo-white.png", 95, 95));
        northContainer.add(logo);

        return northContainer;
    }

    private JPanel createMiddlePanel() {
        JPanel middleContainer = new JPanel();
        middleContainer.setLayout(new BoxLayout(middleContainer, BoxLayout.Y_AXIS));
        middleContainer.setOpaque(false);
        middleContainer.setBorder(BorderFactory.createEmptyBorder(10, 25, 0, 20));

        JPanel menuItemContainer = new JPanel();
        menuItemContainer.setLayout(new BoxLayout(menuItemContainer, BoxLayout.Y_AXIS));
        menuItemContainer.setOpaque(false);
        menuItemContainer.add(dashboardItem);
        menuItemContainer.add(walletItem);
        menuItemContainer.add(loanReqItem);
        menuItemContainer.add(cardsItem);

        // Create a separator panel
        JPanel separatorPanel = new JPanel();
        separatorPanel.setLayout(new BorderLayout());
        separatorPanel.setOpaque(false); // keep it transparent

// Create the separator itself
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(200, 200, 200)); // or theme color
        separatorPanel.add(separator, BorderLayout.CENTER);

// Set a small fixed height for the panel
        separatorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2)); // 2 px height
        separatorPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 2));

        // Options (Account / Settings)
        JPanel optionItemContainer = new JPanel();
        optionItemContainer.setLayout(new BoxLayout(optionItemContainer, BoxLayout.Y_AXIS));
        optionItemContainer.setOpaque(false);
        optionItemContainer.add(accountItem);
        optionItemContainer.add(settingsItem);
        optionItemContainer.add(modeToggle);

        middleContainer.add(menuItemContainer);
        middleContainer.add(Box.createVerticalStrut(separation));
        middleContainer.add(separatorPanel);
        middleContainer.add(Box.createVerticalStrut(separation)); // optional spacing
        middleContainer.add(optionItemContainer);

        return middleContainer;
    }

    private void initItems() {
        dashboardItem = SidebarButtonFactory.createMenuItem("Dashboard", "icons/svg/dashboard.svg", true);
        walletItem = SidebarButtonFactory.createMenuItem("Wallet", "icons/svg/wallet.svg", true);
        loanReqItem = SidebarButtonFactory.createMenuItem("Loan", "icons/svg/loan.svg", true);
        cardsItem = SidebarButtonFactory.createMenuItem("Cards", "icons/svg/cards.svg", true);

        accountItem = SidebarButtonFactory.createMenuItem("Profile", "icons/svg/profile.svg", false);
        settingsItem = SidebarButtonFactory.createMenuItem("Settings", "icons/svg/settings.svg", false);

        modeToggle = SidebarButtonFactory.createThemeToggleButton(
                "Dark Mode", "Light Mode",
                "/icons/svg/dark.svg", "/icons/svg/light.svg"
        );
    }

    private void initMenuBehavior() {
        menuItems = new MenuItemButton[]{dashboardItem, walletItem, loanReqItem, cardsItem};
        setSelectedMenu(dashboardItem);

        for (MenuItemButton item : menuItems) {
            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    setSelectedMenu(item);
                }
            });
        }

        modeToggle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleTheme();
            }
        });
    }

    private void setSelectedMenu(MenuItemButton selectedItem) {
        for (MenuItemButton item : menuItems) {
            item.setSelected(item == selectedItem);
        }
    }

    private void toggleTheme() {
        try {
            if (isDarkMode) {
                UIManager.setLookAndFeel(new LGVBLight());
            } else {
                UIManager.setLookAndFeel(new LGVBDark());
            }
            isDarkMode = !isDarkMode;

            // Refresh all open windows (re-applies properties)
            for (Window w : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(w);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
