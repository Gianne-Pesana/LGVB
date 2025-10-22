package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.ui_utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AuthPage extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JPanel mainPanel;
    private JPanel rightImagePanel;
    private JPanel logoPanel;
    private JPanel contentContainer;

    private TOTPpanel totpPanel;   // TOTP separate from card layout
    private RegistrationPanel registrationPanel;
    private LoginPanel loginPanel;  // refactored login

    public AuthPage() {
        setSize(ThemeGlobalDefaults.getScaledInt("LoginPage.width"), ThemeGlobalDefaults.getScaledInt("LoginPage.height"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(ThemeGlobalDefaults.getString("App.title"));
        setIconImage(new FlatSVGIcon("icons/svg/appIcon.svg", 32, 32).getImage());

        registrationPanel = new RegistrationPanel();
        registrationPanel.setOnSwitchToLogin(() -> showLoginPanel());

        loginPanel = new LoginPanel(); // new separate class

        // Create card panel for LOGIN + REGISTER
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(loginPanel, "LOGIN");
        cardPanel.add(registrationPanel, "REGISTER");

        // Create global panels
        logoPanel = createLogoPanel();
        rightImagePanel = createRightSide();
        totpPanel = new TOTPpanel(); // separate — not in card layout

        // Container holds logo + card layout vertically
        contentContainer = new JPanel(new BorderLayout());
        contentContainer.add(logoPanel, BorderLayout.NORTH);
        contentContainer.add(cardPanel, BorderLayout.CENTER);
        ThemeManager.putThemeAwareProperty(contentContainer, "background: $LGVB.background");

        // Main layout — split horizontally: left content + right image
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentContainer, BorderLayout.CENTER);
        mainPanel.add(rightImagePanel, BorderLayout.EAST);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    /**
     * ----------------- Logo Panel -----------------
     */
    private JPanel createLogoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(UIScale.scale(20), UIScale.scale(20), 0, 0));

        JLabel logoLabel = new JLabel();
        FlatSVGIcon logoIcon = SVGUtils.loadIconAutoAspect(
                ThemeGlobalDefaults.getString("Logo.path"),
                ThemeGlobalDefaults.getScaledInt("LoginPage.logo.height")
        );
        logoIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        logoLabel.setIcon(logoIcon);
        panel.add(logoLabel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * ----------------- Right Image -----------------
     */
    private JPanel createRightSide() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel();

        Image scaledImage = new ImageIcon(getClass().getResource("/images/log_in_focal.png"))
                .getImage()
                .getScaledInstance(
                        ThemeGlobalDefaults.getScaledInt("LoginPage.focalPhoto.width"),
                        ThemeGlobalDefaults.getScaledInt("LoginPage.focalPhoto.height"),
                        Image.SCALE_SMOOTH
                );

        label.setIcon(new ImageIcon(scaledImage));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    /**
     * ----------------- Switching -----------------
     */
    public void showTOTPPanel() {
        mainPanel.removeAll();
        mainPanel.add(totpPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showLoginPanel() {
        resetMainLayout();
        cardLayout.show(cardPanel, "LOGIN");
    }

    public void showRegisterPanel() {
        resetMainLayout();
        cardLayout.show(cardPanel, "REGISTER");
    }

    private void resetMainLayout() {
        mainPanel.removeAll();
        mainPanel.add(contentContainer, BorderLayout.CENTER);
        mainPanel.add(rightImagePanel, BorderLayout.EAST);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public RegistrationPanel getRegistrationPanel() {
        return registrationPanel;
    }

    public TOTPpanel getTotpPanel() {
        return totpPanel;
    }
}
