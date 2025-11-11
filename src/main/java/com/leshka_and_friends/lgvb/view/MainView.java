/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.leshka_and_friends.lgvb.core.user.CustomerDTO;
import com.leshka_and_friends.lgvb.preferences.SettingsController;
import com.leshka_and_friends.lgvb.view.customer.dashboard.Dashboard;
import com.leshka_and_friends.lgvb.view.customer.dashboard.DepositPanel;
import com.leshka_and_friends.lgvb.view.customer.dashboard.TransferPanel;
import com.leshka_and_friends.lgvb.view.customer.sidebar.Sidebar;
import com.leshka_and_friends.lgvb.view.loansetup.LoanContainerPanel;
import com.leshka_and_friends.lgvb.view.loansetup.LoanState;
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
    private LoanContainerPanel loanContainerPanel;
    private DepositPanel depositPanel;
    private TransferPanel transferPanel;


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
                showDashboard();
            }

            @Override
            public void onSelectLoan() {
                contentLayout.show(mainContentPanel, "LOAN");
            }

            @Override
            public void onSelectSettings() {
                new SettingsController().showView();
            }
        });
    }

    /**
     * Creates all main content panels.
     */
    private void createContentPanels() {
        // Create dashboard panel with sample data
        dashboardPanel = new Dashboard(this, this.dto);
        loanContainerPanel = new LoanContainerPanel();
        depositPanel = new DepositPanel(this);
        transferPanel = new TransferPanel(this);
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
        mainContentPanel.add(loanContainerPanel, "LOAN");
        mainContentPanel.add(depositPanel, "DEPOSIT");
        mainContentPanel.add(transferPanel, "TRANSFER");

        // Show dashboard by default
        contentLayout.show(mainContentPanel, "DASHBOARD");

        // Split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainContentPanel);
        splitPane.setDividerSize(0);
    }

    public void showDashboard() {
        contentLayout.show(mainContentPanel, "DASHBOARD");
    }

    public void showDepositPanel() {
        contentLayout.show(mainContentPanel, "DEPOSIT");
    }

    public void showTransferPanel() {
        contentLayout.show(mainContentPanel, "TRANSFER");
    }

    public void showLoanPanel() {
        contentLayout.show(mainContentPanel, "LOAN");
    }

    public Dashboard getDashboardPanel() {
        return dashboardPanel;
    }

    public LoanContainerPanel getLoanContainerPanel() {
        return loanContainerPanel;
    }

    public DepositPanel getDepositPanel() {
        return depositPanel;
    }

    public TransferPanel getTransferPanel() {
        return transferPanel;
    }

    public void addLogoutListener(Runnable action) {
        sidebar.addLogoutListener(action);
    }
}

