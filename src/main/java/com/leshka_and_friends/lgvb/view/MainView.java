/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.leshka_and_friends.lgvb.core.user.CustomerDTO;
import com.leshka_and_friends.lgvb.view.components.panels.TitlePanel;
import com.leshka_and_friends.lgvb.view.forms.Dashboard;
import com.leshka_and_friends.lgvb.view.forms.Sidebar;
import com.leshka_and_friends.lgvb.view.forms.Wallet;
import com.leshka_and_friends.lgvb.view.test.LoanTestPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JSplitPane splitPane;
//    private JPanel sidebarPanel;
    private Sidebar sidebar;
    private JPanel mainContentPanel;
    private CardLayout contentLayout;

    // Main content panels
    private Dashboard dashboardPanel;
    private Wallet walletPanel;
    private TitlePanel loanPanel;
    private LoanTestPanel loanPanelTest;
    private TitlePanel cardsPanel;

    private int width;
    private int height;

    private final double widthScaleFactor = ThemeGlobalDefaults.getDouble("MainView.width.scaleFactor");
    private final double heigthScaleFactor = ThemeGlobalDefaults.getDouble("MainView.height.scaleFactor");

    private CustomerDTO dto;

    public MainView(CustomerDTO dto) {
        this.dto = dto;
        initializeFrame();
        initializeSidebar();
        createContentPanels();
        createMainContent();

        // Add split pane to frame
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeFrame() {
        FontLoader.loadFonts();

        // Frame settings
        setTitle(ThemeGlobalDefaults.getString("App.title"));
        Image image = new ImageIcon(getClass().getResource("/icons/app_icon.png")).getImage();
        setIconImage(image);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set size to screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) Math.floor(screenSize.getWidth() * widthScaleFactor);
        height = (int) Math.floor(screenSize.getHeight() * heigthScaleFactor);

        setSize(width, height);
        setMinimumSize(
                new Dimension(
                        width - ThemeGlobalDefaults.getScaledInt("MainView.minimumWidth.difference"),
                        height - ThemeGlobalDefaults.getScaledInt("MainView.minimumHeight.difference")
                )
        );
    }

    private void initializeSidebar() {
        // Sidebar panel (left)
        System.out.println("Inside Main View: " + dto.getFullName());
        sidebar = new Sidebar(this.dto);
        sidebar.setSelectionListener(new Sidebar.SelectionListener() {
            @Override
            public void onSelectDashboard() {
                contentLayout.show(mainContentPanel, "DASHBOARD");
            }

            @Override
            public void onSelectWallet() {
                contentLayout.show(mainContentPanel, "WALLET");
            }

            @Override
            public void onSelectLoan() {
                contentLayout.show(mainContentPanel, "LOAN");
            }

            @Override
            public void onSelectCards() {
                contentLayout.show(mainContentPanel, "CARDS");
            }
        });
    }

    /**
     * Creates all main content panels.
     */
    private void createContentPanels() {
        // Create dashboard panel with sample data
        String dashboardTitle = ThemeGlobalDefaults.getString("Panel.Dashboard.title");
        dashboardPanel = new Dashboard(this.dto);

        // Create other panels
        String walletTitle = ThemeGlobalDefaults.getString("Panel.Wallet.title");
        walletPanel = new Wallet();

        String loanTitle = ThemeGlobalDefaults.getString("Panel.Loan.title");
//        loanPanel = new TitlePanel(loanTitle.isEmpty() ? "LOAN" : loanTitle);
        loanPanelTest = new LoanTestPanel();

        String cardsTitle = ThemeGlobalDefaults.getString("Panel.Cards.title");
        cardsPanel = new TitlePanel(cardsTitle.isEmpty() ? "CARDS" : cardsTitle);
    }

    public Sidebar getSidebarPanel() {
        return sidebar;
    }

    public String getUserFullName() {
        return dto.getFullName();
    }

    private void createMainContent() {
        // Main content panel (right) with CardLayout
        contentLayout = new CardLayout();
        mainContentPanel = new JPanel(contentLayout);
        ThemeManager.putThemeAwareProperty(mainContentPanel, "background: $Panel.background");

        // Add panels to card layout
        mainContentPanel.add(dashboardPanel, "DASHBOARD");
        mainContentPanel.add(walletPanel, "WALLET");
//        mainContentPanel.add(loanPanel, "LOAN");
        mainContentPanel.add(loanPanelTest, "LOAN");
        mainContentPanel.add(cardsPanel, "CARDS");

        // Show dashboard by default
        contentLayout.show(mainContentPanel, "DASHBOARD");

        // Split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainContentPanel);
        splitPane.setDividerSize(0);
    }

    public Dashboard getDashboardPanel() {
        return dashboardPanel;
    }

    public Wallet getWalletPanel() {
        return walletPanel;
    }

    public TitlePanel getLoanPanel() {
        return loanPanel;
    }

    public TitlePanel getCardsPanel() {
        return cardsPanel;
    }
}
