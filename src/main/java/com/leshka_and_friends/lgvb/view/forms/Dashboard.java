/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.forms;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.dashboard.DashboardDTO;
import com.leshka_and_friends.lgvb.view.components.buttons.MenuItemButtonDashboard;
import com.leshka_and_friends.lgvb.view.components.panels.CardPanel;
import com.leshka_and_friends.lgvb.view.components.panels.HeaderPanel;
import com.leshka_and_friends.lgvb.view.components.panels.RoundedPanel;
import com.leshka_and_friends.lgvb.view.factories.HeaderFactory;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JPanel {

    private String username;
    private double balance;
    private DashboardDTO dashboardDTO;

    private HeaderPanel headerPanel;
    private CardPanel cardPanel;
    private RoundedPanel currentBalancePanel;
    private JPanel actionContainer;
    private JPanel transactionsPanel;

    private List<MenuItemButtonDashboard> menuItems = new ArrayList<>();

    public Dashboard(DashboardDTO dashboardDTO) {
        this.dashboardDTO = dashboardDTO;
        setOpaque(false);
        // Set a border similar to DHB.java
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Top container for header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Middle panel for main content
        JPanel middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(createInfoSection());
        middlePanel.add(createTransactionsPanel());

        add(middlePanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        // Reusing the existing HeaderFactory
        headerPanel = HeaderFactory.createDashboardHeader(dashboardDTO.getUserDTO().getFirstName());

        // DHB's header has a different text, but for now, we use the factory.
        // To match DHB exactly, we might need to change the title.
        // headerPanel.setTitle("Hi, Leshka");
        return headerPanel;
    }

    private JPanel createInfoSection() {
        JPanel infoSection = new JPanel(new BorderLayout(10, 10));
        infoSection.setOpaque(false);

        // Card Panel on the East
        cardPanel = new CardPanel(dashboardDTO.
                getUserDTO().
                getAccounts().
                getFirst().
                getCards().
                getFirst()
        );

        JPanel cardWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        cardWrapper.setOpaque(false);
        cardWrapper.add(cardPanel);
        infoSection.add(cardWrapper, BorderLayout.EAST);

        // Info Container on the Center
        JPanel infoContainer = new JPanel();
        infoContainer.setOpaque(false);
        infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.Y_AXIS));

        infoContainer.add(createCurrentBalancePanel());
        infoContainer.add(createActionContainer());

        infoSection.add(infoContainer, BorderLayout.CENTER);

        return infoSection;
    }

    private RoundedPanel createCurrentBalancePanel() {
        currentBalancePanel = new RoundedPanel();
        currentBalancePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ThemeManager.putThemeAwareProperty(currentBalancePanel, "background: $LGVB.primary");
        currentBalancePanel.setLayout(new BoxLayout(currentBalancePanel, BoxLayout.Y_AXIS));
        currentBalancePanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 1, 1));
        currentBalancePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        currentBalancePanel.setPreferredSize(new Dimension(getPreferredSize().width, 120));

        JLabel balanceLabel = new JLabel("₱ " +
                dashboardDTO.getUserDTO().getAccounts().getFirst().getBalance()
        );
        balanceLabel.setFont(FontLoader.getFont("inter", 36f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(balanceLabel, "foreground: $LGVB.foreground");

        JLabel subtextLabel = new JLabel("Current Balance");
        subtextLabel.setFont(FontLoader.getFont("inter", 18f));
        ThemeManager.putThemeAwareProperty(subtextLabel, "foreground: $LGVB.foreground");

        currentBalancePanel.add(balanceLabel);
        currentBalancePanel.add(subtextLabel);

        return currentBalancePanel;
    }

    private JPanel createActionContainer() {
        actionContainer = new JPanel();
        actionContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionContainer.setOpaque(false);
        actionContainer.setLayout(new BoxLayout(actionContainer, BoxLayout.X_AXIS));
        actionContainer.setBorder(BorderFactory.createEmptyBorder(1, 30, 1, 1));
        actionContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        initMenuItems(actionContainer);

        return actionContainer;
    }

    private void initMenuItems(JPanel menuBarDashboard) {
        String[] svgPaths = {
            "icons/svg/send.svg",
            "icons/svg/receive.svg",
            "icons/svg/topup.svg",
            "icons/svg/addmore.svg"
        };

        String[] labels = {
            "Send",
            "Receive",
            // The reference DHB has "Deposit" and "Withdraw", but the original Dashboard has these.
            "Top Up",
            "Add More"
        };

        menuItems = new ArrayList<>();

        for (int i = 0; i < svgPaths.length; i++) {
            MenuItemButtonDashboard btn = new MenuItemButtonDashboard(svgPaths[i], labels[i], true);
            btn.setFocusable(false);

            // In DHB, the buttons are JPanels with JLabels. Here we use the existing button class.
            // To match the size, we can set preferred/max sizes.
            Dimension buttonSize = new Dimension(71, 71);
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);
            btn.setMinimumSize(buttonSize);

            menuItems.add(btn);
            menuBarDashboard.add(btn);
            if (i < svgPaths.length - 1) {
                menuBarDashboard.add(Box.createHorizontalStrut(10));
            }
        }
    }

    private JPanel createTransactionsPanel() {
        transactionsPanel = new JPanel();
        transactionsPanel.setOpaque(false);
        transactionsPanel.setLayout(new BorderLayout()); // Allows content to expand
        ThemeManager.putThemeAwareProperty(transactionsPanel, "background: $Panel.background");
        transactionsPanel.setBorder(BorderFactory.createTitledBorder("Recent Transactions"));

        // Make it expand in Y-axis inside BoxLayout
        transactionsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        transactionsPanel.setPreferredSize(new Dimension(0, 300)); // starting height

        return transactionsPanel;
    }

    public void setHeaderTitle(String title) {
        headerPanel.setTitle(title);
    }

    public String getHeaderTitle() {
        return headerPanel.getTitle();
    }

    public void setUsername() {

    }
}