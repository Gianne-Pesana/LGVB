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
import com.leshka_and_friends.lgvb.view.factories.LookAndFeelFactory;
import com.leshka_and_friends.lgvb.view.ui_utils.ImageParser;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author giann
 */
public class Sidebar extends JPanel {

    private int width = UIScale.scale(ThemeGlobalDefaults.getInt("Sidebar.width"));
    private int height = UIScale.scale(ThemeGlobalDefaults.getInt("Sidebar.height"));

    private final int separation = UIScale.scale(ThemeGlobalDefaults.getInt("Sidebar.separation"));
    private static boolean isDarkMode;

    private MenuItemButton[] menuItems;
    private SidebarButtonPanel[] buttonItems;

    private MenuItemButton dashboardItem, walletItem, loanReqItem, cardsItem;
    private MenuItemButton accountItem, settingsItem;
    private ThemeToggleButton modeToggle;

    private UserProfile userProfileItem;

    public interface SelectionListener {

        void onSelectDashboard();

        void onSelectWallet();

        void onSelectLoan();

        void onSelectCards();
    }

    private SelectionListener selectionListener;

    public Sidebar() {
        setPreferredSize(new Dimension(width, height));
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.primary");
        setLayout(new BorderLayout());

        initItems();
        initComponents();
        initMenuBehavior();
    }

    public void setSelectionListener(SelectionListener listener) {
        this.selectionListener = listener;
    }

    private void initComponents() {
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);
        add(createSouthPanel(), BorderLayout.SOUTH);
    }

    private JPanel createNorthPanel() {
        JPanel northContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        northContainer.setOpaque(false);

        JLabel logo = new JLabel(ImageParser.loadScaled(
                ThemeGlobalDefaults.getString("Sidebar.logo.path"),
                ThemeGlobalDefaults.getInt("Sidebar.logo.size"),
                ThemeGlobalDefaults.getInt("Sidebar.logo.size")
        ));
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

        userProfileItem = new UserProfile();
        userProfileItem.setUserProfile(
                ThemeGlobalDefaults.getString("UserProfileDefault.name"),
                ThemeGlobalDefaults.getString("UserProfileDefault.image")
        );

        southContainer.add(userProfileItem);

        // Ensure SOUTH panel has a proper preferred height
        southContainer.setPreferredSize(new Dimension(width, UIScale.scale(80)));
        southContainer.setMaximumSize(new Dimension(width, UIScale.scale(80)));

        return southContainer;
    }

    public void updateUserProfile(String name, String imagePath) {
        if (userProfileItem != null) {
            userProfileItem.setUserProfile(name, imagePath);
            userProfileItem.revalidate();
            userProfileItem.repaint();
        }
    }

    private void initItems() {
        dashboardItem = SidebarButtonFactory.createMenuItem(
                ThemeGlobalDefaults.getString("Menu.Dashboard.text"),
                ThemeGlobalDefaults.getString("Menu.Dashboard.icon"), true);
        walletItem = SidebarButtonFactory.createMenuItem(
                ThemeGlobalDefaults.getString("Menu.Wallet.text"),
                ThemeGlobalDefaults.getString("Menu.Wallet.icon"), true);
        loanReqItem = SidebarButtonFactory.createMenuItem(
                ThemeGlobalDefaults.getString("Menu.Loan.text"),
                ThemeGlobalDefaults.getString("Menu.Loan.icon"), true);
        cardsItem = SidebarButtonFactory.createMenuItem(
                ThemeGlobalDefaults.getString("Menu.Cards.text"),
                ThemeGlobalDefaults.getString("Menu.Cards.icon"), true);

        accountItem = SidebarButtonFactory.createMenuItem(
                ThemeGlobalDefaults.getString("Menu.Profile.text"),
                ThemeGlobalDefaults.getString("Menu.Profile.icon"), false);
        settingsItem = SidebarButtonFactory.createMenuItem(
                ThemeGlobalDefaults.getString("Menu.Settings.text"),
                ThemeGlobalDefaults.getString("Menu.Settings.icon"), false);

        modeToggle = SidebarButtonFactory.createThemeToggleButton(
                ThemeGlobalDefaults.getString("Toggle.Dark.text"),
                ThemeGlobalDefaults.getString("Toggle.Light.text"),
                ThemeGlobalDefaults.getString("Toggle.Dark.icon"),
                ThemeGlobalDefaults.getString("Toggle.Light.icon")
        );
        
        buttonItems = new SidebarButtonPanel[]{
            dashboardItem, walletItem, loanReqItem, 
            cardsItem, accountItem, settingsItem, modeToggle
        };

    }

    private void initMenuBehavior() {
        menuItems = new MenuItemButton[]{dashboardItem, walletItem, loanReqItem, cardsItem};
        setSelectedMenu(dashboardItem);

        for (MenuItemButton item : menuItems) {
            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    setSelectedMenu(item);
                    if (selectionListener != null) {
                        if (item == dashboardItem) {
                            selectionListener.onSelectDashboard();
                        } else if (item == walletItem) {
                            selectionListener.onSelectWallet();
                        } else if (item == loanReqItem) {
                            selectionListener.onSelectLoan();
                        } else if (item == cardsItem) {
                            selectionListener.onSelectCards();
                        }
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
            ThemeManager.toggleTheme();

            // Reapply custom styles for menu items
            for (SidebarButtonPanel item : buttonItems) {
                item.applyCurrentStyle();
                item.revalidate();
                item.repaint();    // force redraw of hovered/selected state
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
