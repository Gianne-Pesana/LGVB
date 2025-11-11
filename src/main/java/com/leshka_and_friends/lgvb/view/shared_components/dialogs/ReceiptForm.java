package com.leshka_and_friends.lgvb.view.shared_components.dialogs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.core.transaction.TransactionDTO;
import com.leshka_and_friends.lgvb.core.transaction.TransactionType;
import com.leshka_and_friends.lgvb.utils.DBConnection;
import com.leshka_and_friends.lgvb.view.themes.LGVBDark;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ReceiptForm extends JFrame {

    private TransactionDTO transaction;

    public ReceiptForm(TransactionDTO transaction) {
        this.transaction = transaction;
        setTitle("Transaction Receipt");
        setSize(380, 520); // slightly bigger to accommodate larger fonts
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        // Wrapper panel with margin
        JPanel mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setBackground(Color.WHITE);
        mainWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Main panel with dashed border
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.LIGHT_GRAY);
                float[] dash = {2f, 4f};
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 1.0f, dash, 0f));
                g2d.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
            }
        };
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // --- Logo ---
        JLabel logoLabel = new JLabel();
        logoLabel.setFont(FontLoader.getBaloo2Bold(26f));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        FlatSVGIcon logoIcon = SVGUtils.loadIconAutoAspect(
                ThemeGlobalDefaults.getString("Logo.path"),
                ThemeGlobalDefaults.getScaledInt("Receipt.logo.height")
        );

        logoIcon.setColorFilter(SVGUtils.createColorFilter("Text.black"));
        logoLabel.setIcon(logoIcon);

        mainPanel.add(logoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));


        // --- Company Name ---
        JLabel companyLabel = new JLabel("Leshka & Friends Inc.");
        companyLabel.setFont(FontLoader.getBaloo2SemiBold(18f)); // +2pt
        companyLabel.setForeground(Color.BLACK);
        companyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(companyLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createSeparator());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Details Panel using GridBagLayout ---
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 4, 4, 10);
        gbc.gridy = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        addDetail(detailsPanel, gbc, "Ref #:", String.valueOf(transaction.getTransactionId()));
        addDetail(detailsPanel, gbc, "Date/Time:", sdf.format(transaction.getTimestamp()));
        addDetail(detailsPanel, gbc, "Customer:", transaction.getUserFullName());
        addDetail(detailsPanel, gbc, "Account #:", transaction.getAccountNumber());
        addDetail(detailsPanel, gbc, "Type:", transaction.getTransactionType().toString());
        addDetail(detailsPanel, gbc, "Amount:", String.format("$%.2f", transaction.getAmount()));

        if (transaction.getTransactionType() == TransactionType.SENT) {
            addDetail(detailsPanel, gbc, "Related Acct:", transaction.getRelatedAccountNumber());
        }

        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createSeparator());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Thank You & Note ---
        JLabel thankYouLabel = new JLabel("Thank you for your business!");
        thankYouLabel.setFont(FontLoader.getInter(14f).deriveFont(Font.ITALIC)); // +2pt
        thankYouLabel.setForeground(Color.BLACK);
        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(thankYouLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel noteLabel = new JLabel("Visit us again!");
        noteLabel.setFont(FontLoader.getInter(14f)); // +2pt
        noteLabel.setForeground(Color.BLACK);
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(noteLabel);

        mainWrapper.add(mainPanel, BorderLayout.CENTER);
        add(mainWrapper);
    }

    // Add detail using GridBagLayout
    private void addDetail(JPanel panel, GridBagConstraints gbc, String key, String value) {
        JLabel keyLabel = new JLabel(key);
        keyLabel.setFont(FontLoader.getIbmPlexMonoRegular(13f)); // +2pt
        keyLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        panel.add(keyLabel, gbc);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(FontLoader.getIbmPlexMonoRegular(13f).deriveFont(Font.BOLD)); // +2pt
        valueLabel.setForeground(Color.BLACK);
        gbc.gridx = 1;
        panel.add(valueLabel, gbc);

        gbc.gridy++; // next row
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Color.LIGHT_GRAY);
        return sep;
    }
}
