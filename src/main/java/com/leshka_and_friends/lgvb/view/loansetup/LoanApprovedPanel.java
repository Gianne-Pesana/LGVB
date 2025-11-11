package com.leshka_and_friends.lgvb.view.loansetup;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.core.user.CustomerDTO;
import com.leshka_and_friends.lgvb.view.factories.HeaderFactory;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.shared_components.panels.HeaderPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Consumer;

public class LoanApprovedPanel extends JPanel {

    private RoundedPanel currentBalancePanel;
    private JLabel balanceLabel;
    private CustomerDTO customerdto;

    public LoanApprovedPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Vertical container: main section + transactions
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        // Main upper section: balance + image
        mainContainer.add(createContainerPanel());
        mainContainer.add(Box.createVerticalStrut(20));

        // Separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(ThemeGlobalDefaults.getColor("Separator.color"));
        mainContainer.add(separator);
        mainContainer.add(Box.createVerticalStrut(20));

        // Transactions section
        mainContainer.add(createTransactionsPanel());

        add(mainContainer, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        HeaderPanel headerPanel = HeaderFactory.createDashboardHeader(customerdto.getFirstName());
        headerPanel.setOpaque(false);
        return headerPanel;
    }

    private JPanel createContainerPanel() {
        JPanel container = new JPanel(new BorderLayout(30, 0));
        container.setOpaque(false);

        // Wrap balance panel so it respects preferred size
        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftWrapper.setOpaque(false);
        currentBalancePanel = createCurrentBalancePanel();
        leftWrapper.add(currentBalancePanel);

        // Right: image ad that fills remaining space
        JLabel adLabel = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/loan_ad.png"));
                Image img = rawIcon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        adLabel.setOpaque(false);

        container.add(leftWrapper, BorderLayout.WEST);
        container.add(adLabel, BorderLayout.CENTER);

        return container;
    }

    private RoundedPanel createCurrentBalancePanel() {
        RoundedPanel panel = new RoundedPanel();
        panel.setBackground(new Color(240, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIScale.scale(130)));
        panel.setPreferredSize(new Dimension(UIScale.scale(400), UIScale.scale(130)));

        // Left side: balance text
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel subtextLabel = new JLabel("Current Balance");
        subtextLabel.setFont(FontLoader.getFont("inter", 18f));
        subtextLabel.setForeground(Color.DARK_GRAY);

        double balance = 0;
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        balanceLabel = new JLabel("₱ " + formatter.format(balance));
        balanceLabel.setFont(FontLoader.getFont("inter", 36f).deriveFont(Font.BOLD));
        balanceLabel.setForeground(Color.BLACK);

        textPanel.add(subtextLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(balanceLabel);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTransactionsPanel() {
        JPanel transactionSection = new JPanel(new BorderLayout());
        transactionSection.setOpaque(false);

        // Title label
        JLabel transactionLabel = new JLabel("Transactions");
        ThemeManager.putThemeAwareProperty(transactionLabel, "foreground: $LGVB.header");
        transactionLabel.setFont(FontLoader.getBaloo2Bold(ThemeGlobalDefaults.getScaledInt("Transaction.header.fontSize")));
        transactionLabel.setBorder(new EmptyBorder(5, 5, 10, 5));

        // Placeholder transaction feed
        JPanel dummyFeed = new JPanel();
        dummyFeed.setLayout(new BoxLayout(dummyFeed, BoxLayout.Y_AXIS));
        dummyFeed.setOpaque(false);
        for (int i = 1; i <= 5; i++) {
            JLabel item = new JLabel("• Transaction " + i + " - ₱" + (i * 500));
            item.setFont(FontLoader.getFont("inter", 16f));
            item.setForeground(Color.GRAY);
            item.setBorder(new EmptyBorder(5, 10, 5, 10));
            dummyFeed.add(item);
        }

        JScrollPane scrollPane = new JScrollPane(dummyFeed);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(0, UIScale.scale(200)));

        transactionSection.add(transactionLabel, BorderLayout.NORTH);
        transactionSection.add(scrollPane, BorderLayout.CENTER);

        return transactionSection;
    }

    private Consumer<LoanState> stateChangeListener;
    public void setStateChangeListener(Consumer<LoanState> listener) {
        this.stateChangeListener = listener;
    }
}
