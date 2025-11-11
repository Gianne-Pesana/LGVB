package com.leshka_and_friends.lgvb.view.admin;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.core.admin.LoanApplicationController;
import com.leshka_and_friends.lgvb.core.app.AppFacade;
import com.leshka_and_friends.lgvb.view.admin.panels.LoanApplicationPanel;
import com.leshka_and_friends.lgvb.view.admin.panels.ManageWalletsPanel;
import com.leshka_and_friends.lgvb.view.admin.panels.WalletApplicationPanel;
import com.leshka_and_friends.lgvb.view.themes.LGVBDark;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame {

    private boolean darkMode = false;

    // Colors for light and dark mode
    private final Color LIGHT_TEXT = Color.WHITE;
    private final Color DARK_TEXT = Color.BLACK;

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JLabel titleLabel;

    ManageWalletsPanel manageWalletsPanel;
    WalletApplicationPanel walletApplicationPanel;
    LoanApplicationPanel loanApplicationPanel;

    private int width;
    private int height;
    private final double widthScaleFactor = ThemeGlobalDefaults.getDouble("AdminDashboard.width.scaleFactor");
    private final double heigthScaleFactor = ThemeGlobalDefaults.getDouble("AdminDashboard.height.scaleFactor");

    public AdminDashboard(AppFacade facade) {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Frame size scales with screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) Math.floor(screenSize.getWidth() * widthScaleFactor);
        height = (int) Math.floor(screenSize.getHeight() * heigthScaleFactor);

        setSize(width, height);

        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        initHeader();
        initTabs();
        
        new LoanApplicationController(facade, this);

//        applyTheme();
    }

    private void initHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ThemeManager.putThemeAwareProperty(headerPanel, "background: $LGVB.primary");

        // Logo placeholder
        JLabel logoLabel = new JLabel();
        FlatSVGIcon logoIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("Logo.noText.path"),
                ThemeGlobalDefaults.getScaledInt("AdminDashboard.logo.height")
        );
        logoIcon.setColorFilter(SVGUtils.createColorFilter("AdminDashboard.title.foreground"));
        logoLabel.setIcon(logoIcon);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Title
        titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(FontLoader.getBaloo2SemiBold(22f));
        ThemeManager.putThemeAwareProperty(titleLabel, "foreground: $AdminDashboard.title.foreground");

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Toggle button for dark/light mode
        JToggleButton toggleButton = new JToggleButton("\u263C"); // Sun icon placeholder
        toggleButton.setFont(new Font("Arial", Font.PLAIN, 18));
        toggleButton.addActionListener((ActionEvent e) -> {
            try {
                ThemeManager.toggleTheme();
                tabbedPane.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        headerPanel.add(toggleButton, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void initTabs() {
        tabbedPane = new JTabbedPane();
        ThemeManager.putThemeAwareProperty(tabbedPane, "background: $LGVB.background");
        tabbedPane.repaint();

        // Create panels and set their colors from ThemeGlobalDefaults
        manageWalletsPanel = new ManageWalletsPanel();
//        accountsPanel.setBackground(ThemeGlobalDefaults.getColor("LGVB.background"));
//        accountsPanel.add(new JLabel("Accounts placeholder") {{
//            setForeground(ThemeGlobalDefaults.getColor("Label.foreground"));
//        }});

        walletApplicationPanel = new WalletApplicationPanel();

        loanApplicationPanel = new LoanApplicationPanel();

        // Add tabs
        tabbedPane.addTab("Manage Wallets", manageWalletsPanel);
        tabbedPane.addTab("Wallet Applications", walletApplicationPanel);
        tabbedPane.addTab("Loan Applications", loanApplicationPanel);

        // Set tab colors
        tabbedPane.setBackground(ThemeGlobalDefaults.getColor("Card1.background")); // tab bar background
        tabbedPane.setForeground(ThemeGlobalDefaults.getColor("Label.foreground"));   // tab text

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }


    public ManageWalletsPanel getManageWalletsPanel() {
        return manageWalletsPanel;
    }

    public WalletApplicationPanel getWalletApplicationPanel() {
        return walletApplicationPanel;
    }

    public LoanApplicationPanel getLoanApplicationPanel() {
        return loanApplicationPanel;
    }
}
