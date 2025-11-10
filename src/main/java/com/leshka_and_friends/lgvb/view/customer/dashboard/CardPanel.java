package com.leshka_and_friends.lgvb.view.customer.dashboard;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.core.card.CardDTO;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardPanel extends RoundedPanel {

    // --- Labels ---
    private JLabel cardTypeLabel;
    private JLabel visaIconLabel;
    private JLabel cardNumberLabel;
    private JLabel expiryDateLabel;
    private JLabel cardHolderNameLabel;

    private CardDTO cardDTO;

    // --- Constructors ---
    public CardPanel(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
        initComponents();
    }

    private void initComponents() {
        int baseWidth = ThemeGlobalDefaults.getScaledInt("card.width");   // 270
        int baseHeight = ThemeGlobalDefaults.getScaledInt("card.height"); // 170
        Dimension cardSize = new Dimension(baseWidth, baseHeight);

        setPreferredSize(cardSize);
        setMaximumSize(cardSize);
        setMinimumSize(cardSize);

        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.primary;");
        setBorder(new EmptyBorder(
                ThemeGlobalDefaults.getScaledInt("card.padding.top"),
                ThemeGlobalDefaults.getScaledInt("card.padding.left"),
                ThemeGlobalDefaults.getScaledInt("card.padding.bottom"),
                ThemeGlobalDefaults.getScaledInt("card.padding.right")
        ));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createHeaderPanel());
        add(Box.createVerticalStrut(15));
        add(createIconContainer());
        add(Box.createVerticalStrut(10));
        add(createCardNumberPanel());
        add(Box.createVerticalStrut(15));
        add(createCardDetailsPanel());
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        ThemeManager.putThemeAwareProperty(headerPanel, "background: $LGVB.primary;");
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                ThemeGlobalDefaults.getScaledInt("card.header.height")));

        // Logo
        JLabel logoLabel = new JLabel();
//        ImageIcon logoImage = ImageParser.loadScaled("/Logo/logo-card.png", UIScale.scale(38), UIScale.scale(16));

        FlatSVGIcon logoIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("Logo.path"),
                ThemeGlobalDefaults.getScaledInt("card.logo.width"),
                ThemeGlobalDefaults.getScaledInt("card.logo.height")
        );
//        FlatSVGIcon logoIcon = new FlatSVGIcon("/icons/svg/card_vectors/logo.svg", 28, 28);
        logoIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        logoLabel.setIcon(logoIcon);
//        logoLabel.setIcon(logoImage);
        headerPanel.add(logoLabel, BorderLayout.LINE_START);

        // Card type + Visa icon
        JPanel headerEast = new JPanel(new GridLayout(1, 2, 20, 0));
        headerEast.setOpaque(false);

        cardTypeLabel = new JLabel(cardDTO.getType());
        cardTypeLabel.setFont(FontLoader.getFont("ibmplexmono-semibold",
                ThemeGlobalDefaults.getScaledInt("card.font.small")));
        ThemeManager.putThemeAwareProperty(cardTypeLabel, "foreground: $LGVB.foreground;");
        headerEast.add(cardTypeLabel);

        visaIconLabel = new JLabel();
        visaIconLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        FlatSVGIcon visaIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("card.visa.path"),
                ThemeGlobalDefaults.getScaledInt("card.visa.width"),
                ThemeGlobalDefaults.getScaledInt("card.visa.height")
        );
        visaIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        visaIconLabel.setIcon(visaIcon);
        headerEast.add(visaIconLabel);

        headerPanel.add(headerEast, BorderLayout.LINE_END);

        return headerPanel;
    }

    private JPanel createIconContainer() {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT,
                ThemeGlobalDefaults.getScaledInt("card.icon.hgap"),
                ThemeGlobalDefaults.getScaledInt("card.icon.vgap")));
        container.setOpaque(false);

        JLabel chipIcon = new JLabel();
        FlatSVGIcon chip = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("card.chip.path"),
                ThemeGlobalDefaults.getScaledInt("card.chip.width"),
                ThemeGlobalDefaults.getScaledInt("card.chip.height")
        );
        chip.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        chipIcon.setIcon(chip);
        container.add(chipIcon);

        return container;
    }

    private JPanel createCardNumberPanel() {
        JPanel cardNumberContainer = new JPanel(new BorderLayout());
        cardNumberContainer.setOpaque(false);
        cardNumberContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                ThemeGlobalDefaults.getScaledInt("card.number.height")));

        cardNumberLabel = new JLabel(formatCardNumber(cardDTO.getMaskedNumber()), SwingConstants.CENTER);
        cardNumberLabel.setFont(FontLoader.getFont("ibmplexmono-semibold",
                ThemeGlobalDefaults.getScaledInt("card.font.large")));
        ThemeManager.putThemeAwareProperty(cardNumberLabel, "foreground: $LGVB.foreground;");
        cardNumberContainer.add(cardNumberLabel, BorderLayout.CENTER);

        return cardNumberContainer;
    }

    private JPanel createCardDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setOpaque(false);

        // Expiry container
        JPanel expiryContainer = new JPanel();
        expiryContainer.setLayout(new BoxLayout(expiryContainer, BoxLayout.Y_AXIS));
        expiryContainer.setBorder(new EmptyBorder(
                ThemeGlobalDefaults.getScaledInt("card.expiry.top"), 1, 1, 1));
        expiryContainer.setOpaque(false);

        JLabel expiryLabel = new JLabel("EXPIRES");
        expiryLabel.setFont(FontLoader.getFont("ibmplexmono-regular",
                ThemeGlobalDefaults.getScaledInt("card.font.tiny")));
        ThemeManager.putThemeAwareProperty(expiryLabel, "foreground: $LGVB.foreground;");
        expiryLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("MM/yy");
        expiryDateLabel = new JLabel(cardDTO.getExpiryDate().format(formatter));
        expiryDateLabel.setFont(FontLoader.getFont("ibmplexmono-medium",
                ThemeGlobalDefaults.getScaledInt("card.font.small")));
        ThemeManager.putThemeAwareProperty(expiryDateLabel, "foreground: $LGVB.foreground;");
        expiryDateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        expiryContainer.add(expiryLabel);
        expiryContainer.add(expiryDateLabel);

        detailsPanel.add(expiryContainer, BorderLayout.LINE_END);

        // Card holder container
        JPanel holderContainer = new JPanel();
        holderContainer.setLayout(new BoxLayout(holderContainer, BoxLayout.Y_AXIS));
        holderContainer.setBorder(new EmptyBorder(
                ThemeGlobalDefaults.getScaledInt("card.holder.top"), 1, 1, 1));
        holderContainer.setOpaque(false);

        JLabel holderLabel = new JLabel("CARD HOLDER");
        holderLabel.setFont(FontLoader.getFont("ibmplexmono-regular",
                ThemeGlobalDefaults.getScaledInt("card.font.tiny")));
        ThemeManager.putThemeAwareProperty(holderLabel, "foreground: $LGVB.foreground;");

        cardHolderNameLabel = new JLabel(cardDTO.getHolder());
        cardHolderNameLabel.setFont(FontLoader.getFont("ibmplexmono-medium",
                ThemeGlobalDefaults.getScaledInt("card.font.small")));
        ThemeManager.putThemeAwareProperty(cardHolderNameLabel, "foreground: $LGVB.foreground;");

        holderContainer.add(holderLabel);
        holderContainer.add(cardHolderNameLabel);

        detailsPanel.add(holderContainer, BorderLayout.CENTER);

        return detailsPanel;
    }

    // --- Helper: format card number ---
    private String formatCardNumber(String rawNumber) {
        if (rawNumber == null || rawNumber.length() != 16) {
            return rawNumber;
        }
        return rawNumber.replaceAll("(.{4})(?!$)", "$1   "); // groups of 4 with 3 spaces
    }

    // --- Getters for labels (for external UI tweaks if needed) ---
    public JLabel getCardTypeLabel() {
        return cardTypeLabel;
    }

    public JLabel getVisaIconLabel() {
        return visaIconLabel;
    }

    public JLabel getCardNumberLabel() {
        return cardNumberLabel;
    }

    public JLabel getExpiryDateLabel() {
        return expiryDateLabel;
    }

    public JLabel getCardHolderNameLabel() {
        return cardHolderNameLabel;
    }
}