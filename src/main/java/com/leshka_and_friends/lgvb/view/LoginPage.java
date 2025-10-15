/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.*;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author giann
 */
public class LoginPage extends JFrame {

    public RoundedTextField usernameField;
    public RoundedPasswordField passwordField;
    public RoundedButton loginBtn;
    public RoundedButton registerBtn;

    public LoginPage() {
        setSize(ThemeGlobalDefaults.getScaledInt("LoginPage.width"), ThemeGlobalDefaults.getScaledInt("LoginPage.height"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        setResizable(false);

        FlatSVGIcon svgIcon = new FlatSVGIcon("icons/svg/appIcon.svg", 32, 32);
        setIconImage(svgIcon.getImage());

        setTitle(ThemeGlobalDefaults.getString("App.title"));

        add(createLeftSide());
        add(createRightSide());

        setLocationRelativeTo(null);
    }

    private JPanel createLeftSide() {
        JPanel loginPanel = new JPanel();
//        loginPanel.setPreferredSize(new Dimension(400, 540));
        ThemeManager.putThemeAwareProperty(loginPanel, "background: $LGVB.background");
        loginPanel.setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel(new BorderLayout());
//        logoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        logoPanel.setOpaque(false);
        logoPanel.setBorder(new EmptyBorder(UIScale.scale(20), UIScale.scale(20), 0, 0));

        JLabel logoLabel = new JLabel();
        FlatSVGIcon logoIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("Logo.path"),
                ThemeGlobalDefaults.getScaledInt("LoginPage.logo.width"),
                ThemeGlobalDefaults.getScaledInt("LoginPage.logo.height")
        );
        logoIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        logoLabel.setIcon(logoIcon);

        logoPanel.add(logoLabel, BorderLayout.CENTER);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new GridBagLayout());
        int containerPadding = ThemeGlobalDefaults.getScaledInt("LoginPage.container.padding");
        container.setBorder(new EmptyBorder(0, containerPadding, 0, containerPadding));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // FILL HORIZONTALLY
        gbc.anchor = GridBagConstraints.CENTER; // center in the available space
        // add some top margin to move it up
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
        int lcBorderSize = UIScale.scale(10);
        loginContainer.setBorder(new EmptyBorder(lcBorderSize, lcBorderSize, lcBorderSize, lcBorderSize));
//        loginContainer.setPreferredSize(new Dimension(280, 300));
//        loginContainer.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));

        JLabel header = new JLabel("Login");
        header.setFont(FontLoader.getInter(ThemeGlobalDefaults.getScaledFloat("LoginPage.header.fontSize")));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---- text fields ----
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

        // ---- buttons ----
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

        // ---- add all to loginContainer ----
        loginContainer.add(header);
        loginContainer.add(Box.createRigidArea(new Dimension(0, 30))); // space between header and fields
        loginContainer.add(textFields);
        loginContainer.add(Box.createRigidArea(new Dimension(0, 30))); // space between fields and buttons
        loginContainer.add(buttonsPanel);

        return loginContainer;
    }

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

    public String getInputUsername() {
        return usernameField.getText().trim();
    }

    public char[] getInputPassword() {
        return passwordField.getPassword();
    }
}
