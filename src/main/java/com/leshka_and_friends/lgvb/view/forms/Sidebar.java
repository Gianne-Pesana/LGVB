/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.forms;

import com.leshka_and_friends.lgvb.view.components.buttons.ThemeToggleButton;
import com.leshka_and_friends.lgvb.view.components.buttons.MenuItemButton;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.buttons.SidebarButtonPanel;
import com.leshka_and_friends.lgvb.view.components.buttons.UserProfile;
import com.leshka_and_friends.lgvb.view.factories.SidebarButtonFactory;
import com.leshka_and_friends.lgvb.view.themes.*;
import com.leshka_and_friends.lgvb.view.utils.ImageParser;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    
    private JPanel mainContentPanel; // the panel that holds Dashboard/Wallet/etc
    private CardLayout cardLayout;  

    private MenuItemButton[] menuItems;

    private MenuItemButton dashboardItem, walletItem, loanReqItem, cardsItem;
    private MenuItemButton accountItem, settingsItem;
    private ThemeToggleButton modeToggle;

    public Sidebar(JPanel mainContentPanel) {
        this.mainContentPanel = mainContentPanel;
        this.cardLayout = (CardLayout) mainContentPanel.getLayout();
        isDarkMode = (UIManager.getLookAndFeel() instanceof LGVBDark);
        setPreferredSize(new Dimension(width, height));
        putClientProperty("FlatLaf.style", "background: $LGVB.primary");
        setLayout(new BorderLayout());
        
        MenuItemButton dashboardBtn = new MenuItemButton("Dashboard", "icons/svg/dashboard.svg", true, mainContentPanel);
        add(dashboardBtn);

        initItems();
        initComponents();
        initMenuBehavior();
    }

    private void initComponents() {
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);
        add(createSouthPanel(), BorderLayout.SOUTH);
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
        middleContainer.setPreferredSize(new Dimension(width, UIScale.scale(550)));
        middleContainer.setMaximumSize(new Dimension(width, UIScale.scale(550)));

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

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(200, 200, 200));
        separatorPanel.add(separator, BorderLayout.CENTER);

        separatorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
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

    private JPanel createSouthPanel() {
        JPanel southContainer = new JPanel();
        southContainer.setLayout(new BoxLayout(southContainer, BoxLayout.Y_AXIS));
        southContainer.setOpaque(false);

        UserProfile userProfileItem = new UserProfile();
        userProfileItem.setUserProfile("Gianne Pesana", "/profile/pesana.jpg");

        southContainer.add(userProfileItem);

        // Ensure SOUTH panel has a proper preferred height
        southContainer.setPreferredSize(new Dimension(width, UIScale.scale(80)));
        southContainer.setMaximumSize(new Dimension(width, UIScale.scale(80)));

        return southContainer;
    }

    private void initItems() {
    dashboardItem = SidebarButtonFactory.createMenuItem("Dashboard", "icons/svg/dashboard.svg", true, mainContentPanel);
    walletItem = SidebarButtonFactory.createMenuItem("Wallet", "icons/svg/wallet.svg", true, mainContentPanel);
    loanReqItem = SidebarButtonFactory.createMenuItem("Loan", "icons/svg/loan.svg", true, mainContentPanel);
    cardsItem = SidebarButtonFactory.createMenuItem("Cards", "icons/svg/cards.svg", true, mainContentPanel);

    accountItem = SidebarButtonFactory.createMenuItem("Profile", "icons/svg/profile.svg", false, mainContentPanel);
    settingsItem = SidebarButtonFactory.createMenuItem("Settings", "icons/svg/settings.svg", false, mainContentPanel);

    modeToggle = SidebarButtonFactory.createThemeToggleButton(
            "Dark Mode", "Light Mode",
            "icons/svg/dark.svg", "icons/svg/light.svg"
    );
}


    private void initMenuBehavior() {
        menuItems = new MenuItemButton[]{dashboardItem, walletItem, loanReqItem, cardsItem};
        setSelectedMenu(dashboardItem);

        for (MenuItemButton item : menuItems) {
    item.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            setSelectedMenu(item); // highlight button

            String panelName = (String) item.getClientProperty("panelName");
            if (panelName != null) {
                cardLayout.show(mainContentPanel, panelName);
            }
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
            // Switch Look and Feel
            if (isDarkMode) {
                UIManager.setLookAndFeel(new LGVBLight());
            } else {
                UIManager.setLookAndFeel(new LGVBDark());
            }
            isDarkMode = !isDarkMode;

            // Refresh all open windows
            for (Window w : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(w);
                w.invalidate();   // force layout to recalc
                w.validate();
                w.repaint();      // force repaint
            }

            // Reapply custom styles for menu items
            for (MenuItemButton item : menuItems) {
                item.applyCurrentStyle();
                item.revalidate();
                item.repaint();    // force redraw of hovered/selected state
            }
            
            accountItem.applyCurrentStyle();
            settingsItem.applyCurrentStyle();
            modeToggle.applyCurrentStyle();
            modeToggle.revalidate();
            modeToggle.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
