/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.customer.dashboard;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.auth.Session;
import com.leshka_and_friends.lgvb.auth.SessionManager;
import com.leshka_and_friends.lgvb.core.app.ServiceLocator;
import com.leshka_and_friends.lgvb.core.transaction.Transaction;
import com.leshka_and_friends.lgvb.core.transaction.TransactionService;
import com.leshka_and_friends.lgvb.core.user.CustomerDTO;
import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.core.wallet.WalletService;
import com.leshka_and_friends.lgvb.notification.Observer;
import com.leshka_and_friends.lgvb.view.shared_components.panels.HeaderPanel;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.TransparentScrollbar;
import com.leshka_and_friends.lgvb.view.factories.HeaderFactory;
import com.leshka_and_friends.lgvb.view.customer.transaction.TransactionFeedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import com.leshka_and_friends.lgvb.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.border.EmptyBorder;

public class Dashboard extends JPanel implements Observer {


    private String username;
    private double balance;
    private CustomerDTO customerdto;
    private MainView mainView;

    private HeaderPanel headerPanel;
    private CardPanel cardPanel;
    private RoundedPanel currentBalancePanel;
    private JPanel actionContainer;
    private JPanel transactionsPanel;
    private JLabel balanceLabel;
    private TransactionFeedPanel feedPanel;

    private JButton plusButton;

    private List<MenuItemButtonDashboard> menuItems = new ArrayList<>();

    public Dashboard(MainView mainView, CustomerDTO customerdto) {
        this.mainView = mainView;
        this.customerdto = customerdto;
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

        // Add info section
        middlePanel.add(createInfoSection());

        // Add a separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // full width, 1px height
        separator.setForeground(ThemeGlobalDefaults.getColor("Separator.color")); // optional color
        middlePanel.add(Box.createRigidArea(new Dimension(0, 10))); // optional spacing before
        middlePanel.add(separator);
        middlePanel.add(Box.createRigidArea(new Dimension(0, 10))); // optional spacing after

        // Add transactions panel
        middlePanel.add(createTransactionsPanel());

        add(middlePanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        // Reusing the existing HeaderFactory
        headerPanel = HeaderFactory.createDashboardHeader(customerdto.getFirstName());
        return headerPanel;
    }

    private JPanel createInfoSection() {
        JPanel infoSection = new JPanel(new BorderLayout(10, 10));
        infoSection.setOpaque(false);

        // Card Panel on the East
        cardPanel = new CardPanel(customerdto.getAccount().getCard());

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
        currentBalancePanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30)); // even padding top/bottom
        currentBalancePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIScale.scale(120)));
        currentBalancePanel.setPreferredSize(new Dimension(getPreferredSize().width, UIScale.scale(120)));

        // Use GridBagLayout for full control
        GridBagLayout layout = new GridBagLayout();
        currentBalancePanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // === LEFT SECTION (balance + subtext) ===
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        double balance = customerdto.getAccount().getBalance();
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String formattedBalance = formatter.format(balance);

        balanceLabel = new JLabel("₱ " + formattedBalance);
        balanceLabel.setFont(FontLoader.getFont("inter", 36f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(balanceLabel, "foreground: $LGVB.foreground");
        balanceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


        JLabel subtextLabel = new JLabel("Current Balance");
        subtextLabel.setFont(FontLoader.getFont("inter", 18f));
        ThemeManager.putThemeAwareProperty(subtextLabel, "foreground: $LGVB.foreground");
        subtextLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(subtextLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(balanceLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        currentBalancePanel.add(leftPanel, gbc);

        // === RIGHT SECTION (plus icon) ===
        JPanel plusPanel = new JPanel(new GridBagLayout());
        plusPanel.setOpaque(false);

        plusButton = new JButton();
        FlatSVGIcon plusIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("Dashboard.balancePanel.plusIcon.path"),
                ThemeGlobalDefaults.getScaledInt("Dashboard.balancePanel.plusIcon.height")
        );
        plusIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        plusButton.setIcon(plusIcon);

        // Make it look like a plain icon (transparent background, no border, no text)
        plusButton.setContentAreaFilled(false);
        plusButton.setBorderPainted(false);
        plusButton.setFocusPainted(false);
        plusButton.setOpaque(false);
        plusButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        plusButton.addActionListener((ActionEvent e) -> mainView.showDepositPanel());


        plusPanel.add(plusButton, new GridBagConstraints());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        currentBalancePanel.add(plusPanel, gbc);

        return currentBalancePanel;
    }


    private JPanel createActionContainer() {
        actionContainer = new JPanel();
        actionContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionContainer.setOpaque(false);
        actionContainer.setLayout(new BoxLayout(actionContainer, BoxLayout.X_AXIS));
        actionContainer.setBorder(BorderFactory.createEmptyBorder(1, 30, 1, 1));
        actionContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIScale.scale(80)));

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

            int buttonSize = ThemeGlobalDefaults.getScaledInt("DashboardMenuItems.button.size");
            Dimension buttonDim = new Dimension(buttonSize, buttonSize);
            btn.setPreferredSize(buttonDim);
            btn.setMaximumSize(buttonDim);
            btn.setMinimumSize(buttonDim);

            menuItems.add(btn);
            menuBarDashboard.add(btn);
            if (i < svgPaths.length - 1) {
                menuBarDashboard.add(Box.createHorizontalStrut(10));
            }
        }

        MenuItemButtonDashboard sendButton = getMenuItemButton("Send");
        if (sendButton != null) {
            sendButton.addActionListener(() -> mainView.showTransferPanel());
        }

        MenuItemButtonDashboard addMoreButton = getMenuItemButton("Add More");
        if (addMoreButton != null) {
            addMoreButton.addActionListener(() -> mainView.showDepositPanel());
        }
    }

    private JPanel createTransactionsPanel() {
        TransactionService ts = ServiceLocator.getInstance().getService(TransactionService.class);

        List<Transaction> transactions = null;
        try {
            transactions = ts.loadTransactionsForWallet(customerdto.getAccount().getWalletId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<LocalDate, List<Transaction>> grouped = ts.groupTransactionsByDate(transactions);

        this.feedPanel = new TransactionFeedPanel(grouped);
        JScrollPane scrollPane = createStyledScrollPane(feedPanel);

        JLabel transactionLabel = createTransactionHeaderLabel();

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(transactionLabel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.setPreferredSize(new Dimension(0, ThemeGlobalDefaults.getScaledInt("transactionPanel.height")));
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        return container;
    }

    private JScrollPane createStyledScrollPane(JComponent content) {
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        content.setOpaque(false);

        JScrollBar vBar = new JScrollBar(JScrollBar.VERTICAL);
        vBar.setOpaque(false);
        vBar.setUI(new TransparentScrollbar());
        vBar.putClientProperty("CustomScrollbarUI", Boolean.TRUE);

        vBar.setUnitIncrement(16);

        scrollPane.setVerticalScrollBar(vBar);
        scrollPane.getVerticalScrollBar().setOpaque(false);

        return scrollPane;
    }

    private JLabel createTransactionHeaderLabel() {
        JLabel label = new JLabel("Transactions");
        ThemeManager.putThemeAwareProperty(label, "foreground: $LGVB.header");
        label.setFont(FontLoader.getBaloo2Bold(ThemeGlobalDefaults.getScaledInt("Transaction.header.fontSize")));
        label.setBorder(new EmptyBorder(5, 5, 10, 5));
        return label;
    }

    @Override
    public void update(String message) {
        if ("UI_UPDATE:TRANSACTION_COMPLETED".equals(message)) {
            SwingUtilities.invokeLater(this::refreshData);
        }
    }

    private void refreshData() {
        System.out.println("[Dashboard] Refreshing data...");
        // 1. Refresh Balance
        Session session = ServiceLocator.getInstance().getService(SessionManager.class).getCurrentSession();
        WalletService walletService = ServiceLocator.getInstance().getService(WalletService.class);
        Wallet updatedWallet = walletService.getWalletByUserId(session.getUser().getUserId());
        session.setWallet(updatedWallet);

        double balance = updatedWallet.getBalance();
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String formattedBalance = formatter.format(balance);
        balanceLabel.setText("₱ " + formattedBalance);

        // 2. Refresh Transactions
        TransactionService ts = ServiceLocator.getInstance().getService(TransactionService.class);
        List<Transaction> transactions = null;
        try {
            transactions = ts.loadTransactionsForWallet(customerdto.getAccount().getWalletId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<LocalDate, List<Transaction>> grouped = ts.groupTransactionsByDate(transactions);
        feedPanel.updateTransactions(grouped);
    }

    public void setHeaderTitle(String title) {
        headerPanel.setTitle(title);
    }

    public String getHeaderTitle() {
        return headerPanel.getTitle();
    }

    public void setUsername() {

    }


    // buttons getters
    public JButton getPlusButton() {
        return plusButton;
    }

    public MenuItemButtonDashboard getMenuItemButton(String btnName) {
        for (MenuItemButtonDashboard btn : menuItems) {
            if (btn.getTextLabel().getText().equalsIgnoreCase(btnName)) {
                return btn;
            }
        }
        return null; // not found
    }

}
