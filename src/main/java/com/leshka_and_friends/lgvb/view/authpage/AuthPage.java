package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.*;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.TextFieldUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AuthPage extends JFrame {

    public RoundedTextField usernameField;
    public RoundedPasswordField passwordField;
    public RoundedButton loginBtn;
    public RoundedButton registerBtn;

    private RoundedTextField totpField;
    private RoundedButton totpVerifyBtn;

    private JPanel cardPanel;        // holds login and TOTP panels
    private CardLayout cardLayout;

    private JPanel mainPanel;        // main container for swapping panels
    private JPanel rightImagePanel;  // right-side company image

    public AuthPage() {
        setSize(ThemeGlobalDefaults.getScaledInt("LoginPage.width"), ThemeGlobalDefaults.getScaledInt("LoginPage.height"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        FlatSVGIcon svgIcon = new FlatSVGIcon("icons/svg/appIcon.svg", 32, 32);
        setIconImage(svgIcon.getImage());
        setTitle(ThemeGlobalDefaults.getString("App.title"));

        // Card layout for login / TOTP
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createTOTPPanel(), "TOTP");

        // Right image panel
        rightImagePanel = createRightSide();

        // Main container using BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);     // login panel initially
        mainPanel.add(rightImagePanel, BorderLayout.EAST); // only for login

        add(mainPanel);

        setLocationRelativeTo(null);
    }

    /**
     * ----------------- Login Panel ----------------- *
     */
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        ThemeManager.putThemeAwareProperty(loginPanel, "background: $LGVB.background");
        loginPanel.setLayout(new BorderLayout());

        // Logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false);
        logoPanel.setBorder(new EmptyBorder(UIScale.scale(20), UIScale.scale(20), 0, 0));

        JLabel logoLabel = new JLabel();
        FlatSVGIcon logoIcon = SVGUtils.loadIconAutoAspect(
                ThemeGlobalDefaults.getString("Logo.path"),
                ThemeGlobalDefaults.getScaledInt("LoginPage.logo.height")
        );
        
        logoIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        logoLabel.setIcon(logoIcon);

        logoPanel.add(logoLabel, BorderLayout.CENTER);

        // Container
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);
        int containerPadding = ThemeGlobalDefaults.getScaledInt("LoginPage.container.padding");
        container.setBorder(new EmptyBorder(0, containerPadding, 0, containerPadding));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(ThemeGlobalDefaults.getScaledInt("LoginPage.container.top.margin"), 0, 0, 0);

        container.add(createLoginContainer(), gbc);

        loginPanel.add(logoPanel, BorderLayout.NORTH);
        loginPanel.add(container, BorderLayout.CENTER);

        return loginPanel;
    }

    private JPanel createLoginContainer() {
        JPanel loginContainer = new JPanel();
        loginContainer.setOpaque(false);
        loginContainer.setLayout(new BoxLayout(loginContainer, BoxLayout.Y_AXIS));
        int lcBorderSize = ThemeGlobalDefaults.getScaledInt("LoginPage.loginCotainer.border");
        loginContainer.setBorder(new EmptyBorder(lcBorderSize, lcBorderSize, lcBorderSize, lcBorderSize));

        JLabel header = new JLabel("Login");
        header.setFont(FontLoader.getInter(ThemeGlobalDefaults.getScaledFloat("LoginPage.header.fontSize")));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text fields
        JPanel textFields = new JPanel();
        textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
        textFields.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(FontLoader.getInter(ThemeGlobalDefaults.getScaledFloat("LoginPage.body.fontSize")));
        ThemeManager.putThemeAwareProperty(usernameLabel, "foreground: $TextField.background");
        usernameLabel.setAlignmentX(LEFT_ALIGNMENT);

        int textFieldCornerRadius = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.arc");
        int textFieldHeight = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.height");

        usernameField = new RoundedTextField(textFieldCornerRadius);
        usernameField.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFieldHeight));
        usernameField.setFont(FontLoader.getBaloo2Regular(ThemeGlobalDefaults.getScaledFloat("LoginPage.textField.fontSize")));
        usernameField.setAlignmentX(LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(FontLoader.getInter(ThemeGlobalDefaults.getScaledFloat("LoginPage.body.fontSize")));
        ThemeManager.putThemeAwareProperty(passwordLabel, "foreground: $TextField.background");
        passwordLabel.setAlignmentX(LEFT_ALIGNMENT);

        passwordField = new RoundedPasswordField(textFieldCornerRadius);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFieldHeight));
        passwordField.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        passwordField.setFont(FontLoader.getBaloo2Regular(ThemeGlobalDefaults.getScaledFloat("LoginPage.textField.fontSize")));
        passwordField.setAlignmentX(LEFT_ALIGNMENT);

        textFields.add(usernameLabel);
        textFields.add(Box.createRigidArea(new Dimension(0, UIScale.scale(5))));
        textFields.add(usernameField);
        textFields.add(Box.createRigidArea(new Dimension(0, UIScale.scale(15))));
        textFields.add(passwordLabel);
        textFields.add(Box.createRigidArea(new Dimension(0, UIScale.scale(5))));
        textFields.add(passwordField);
        textFields.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        int bpBorder = ThemeGlobalDefaults.getScaledInt("LoginPage.buttonsPanel.border");
        buttonsPanel.setBorder(new EmptyBorder(0, bpBorder, 0, bpBorder));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(Integer.MAX_VALUE, ThemeGlobalDefaults.getScaledInt("LoginPage.buttonSize"));

        loginBtn = new RoundedButton("Login", ThemeGlobalDefaults.getScaledInt("LoginPage.buttonArc"));
        loginBtn.setPreferredSize(buttonSize);
        loginBtn.setMaximumSize(buttonSize);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBaseColor(new Color(202, 28, 82));
        loginBtn.setFont(new Font("Inter", Font.PLAIN, 14));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerBtn = new RoundedButton("Register", 30);
        registerBtn.setPreferredSize(buttonSize);
        registerBtn.setMaximumSize(buttonSize);
        registerBtn.setForeground(new Color(23, 80, 110));
        registerBtn.setBaseColor(new Color(245, 247, 250));
        registerBtn.setFont(new Font("Inter", Font.PLAIN, 14));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsPanel.add(loginBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(registerBtn);

        loginContainer.add(header);
        loginContainer.add(Box.createRigidArea(new Dimension(0, 30)));
        loginContainer.add(textFields);
        loginContainer.add(Box.createRigidArea(new Dimension(0, 30)));
        loginContainer.add(buttonsPanel);

        return loginContainer;
    }

    /**
     * ----------------- TOTP Panel ----------------- *
     */
    private JPanel createTOTPPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout()); // centers vertically + horizontally
        wrapper.setOpaque(false);

        JPanel totpPanel = new JPanel();
        totpPanel.setOpaque(false);
        totpPanel.setLayout(new BoxLayout(totpPanel, BoxLayout.Y_AXIS));
        totpPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel logoLabel = new JLabel();
        FlatSVGIcon logoIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("Logo.noText.path"),
                ThemeGlobalDefaults.getScaledInt("LoginPage.2fa.logo.height")
        );
        logoIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        logoLabel.setIcon(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel header = new JLabel("Two-Factor Authentication");
        header.setFont(FontLoader.getInter(24f).deriveFont(Font.BOLD));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel body = new JLabel("Enter the 6-digit code from your Authenticator app");
        body.setFont(FontLoader.getInter(14f));
        body.setAlignmentX(Component.CENTER_ALIGNMENT);

        totpField = new RoundedTextField(ThemeGlobalDefaults.getInt("LoginPage.textField.arc"));
        totpField.setMaximumSize(new Dimension(200, 45));
        totpField.setAlignmentX(Component.CENTER_ALIGNMENT);
        totpField.setFont(FontLoader.getBaloo2SemiBold(18f));
        totpField.setHorizontalAlignment(JTextField.CENTER);
        totpField.setCaretColor(ThemeGlobalDefaults.getColor("Caret.color"));
        TextFieldUtils.restrictToDigits(totpField, 6);

        totpVerifyBtn = new RoundedButton("Verify", ThemeGlobalDefaults.getInt("LoginPage.2fa.button.arc"));
        totpVerifyBtn.setContentAreaFilled(true);
        totpVerifyBtn.setOpaque(true);
        ThemeManager.putThemeAwareProperty(totpVerifyBtn, "background: $LoginPage.2fa.button.background");
//        totpVerifyBtn.setBackground(ThemeGlobalDefaults.getColor("AuthPage.2fa.button.background"));
//        totpVerifyBtn.setBackground(new Color(0x238636));
        totpVerifyBtn.setForeground(ThemeGlobalDefaults.getColor("LoginPage.2fa.button.foreground"));
        totpVerifyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        int totpVerifyBtnHeight = ThemeGlobalDefaults.getScaledInt("LoginPage.2fa.button.height");
        int totpVerifyBtnWidth = ThemeGlobalDefaults.getScaledInt("LoginPage.2fa.button.width");
        totpVerifyBtn.setPreferredSize(new Dimension(totpVerifyBtnWidth, totpVerifyBtnHeight));
        totpVerifyBtn.setMaximumSize(new Dimension(totpVerifyBtnWidth, totpVerifyBtnHeight));

        totpPanel.add(logoLabel);
        totpPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        totpPanel.add(header);
        totpPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        totpPanel.add(body);
        totpPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        totpPanel.add(totpField);
        totpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        totpPanel.add(totpVerifyBtn);

        wrapper.add(totpPanel); // wrapper centers totpPanel
        return wrapper;
    }

    /**
     * ----------------- Right Image ----------------- *
     */
    private JPanel createRightSide() {
        JPanel imageContainer = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        Image scaledImage = new ImageIcon(
                getClass().getResource("/images/log_in_focal.png"))
                .getImage()
                .getScaledInstance(
                        ThemeGlobalDefaults.getScaledInt("LoginPage.focalPhoto.width"),
                        ThemeGlobalDefaults.getScaledInt("LoginPage.focalPhoto.height"),
                        Image.SCALE_SMOOTH
                );

        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageContainer.add(imageLabel);
        return imageContainer;
    }

    /**
     * ----------------- Helpers ----------------- *
     */
    public String getInputUsername() {
        return usernameField.getText().trim();
    }

    public char[] getInputPassword() {
        return passwordField.getPassword();
    }

    public void showTOTPPanel() {
        // Remove the right-side image
        mainPanel.remove(rightImagePanel);
        mainPanel.revalidate();
        mainPanel.repaint();

        cardLayout.show(cardPanel, "TOTP");
    }

    public RoundedTextField getTotpField() {
        return totpField;
    }

    public RoundedButton getTotpVerifyButton() {
        return totpVerifyBtn;
    }
}
