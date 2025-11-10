package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedButton;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPasswordField;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedTextField;
import com.leshka_and_friends.lgvb.view.ui_utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {

    public RoundedTextField usernameField;
    public RoundedPasswordField passwordField;
    public RoundedButton loginBtn;
    public RoundedButton registerBtn;

    public LoginPanel() {
        setLayout(new BorderLayout());
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.background");

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
        add(container, BorderLayout.CENTER);
    }

    private JPanel createLoginContainer() {
        JPanel loginContainer = new JPanel();
        loginContainer.setOpaque(false);
        loginContainer.setLayout(new BoxLayout(loginContainer, BoxLayout.Y_AXIS));
        int lcBorderSize = ThemeGlobalDefaults.getScaledInt("LoginPage.loginCotainer.border");
        loginContainer.setBorder(new EmptyBorder(lcBorderSize, lcBorderSize, lcBorderSize, lcBorderSize));

        JLabel header = new JLabel("Login");
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setFont(FontLoader.getInter(ThemeGlobalDefaults.getScaledFloat("LoginPage.header.fontSize")));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text fields
        JPanel textFields = new JPanel();
        textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
        textFields.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username");
        ThemeManager.putThemeAwareProperty(usernameLabel, "foreground: $TextField.background");
        usernameLabel.setFont(FontLoader.getInter(ThemeGlobalDefaults.getScaledFloat("LoginPage.body.fontSize")));
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

        JPanel forgotPasswordPanel = new JPanel(new BorderLayout());
        forgotPasswordPanel.setOpaque(false);
        forgotPasswordPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // panel stays left-aligned
        forgotPasswordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16)); // allow expansion

        JLabel forgotPasswordLabel = new JLabel("<html><a href=''>Forgot Password?</a></html>");
        forgotPasswordLabel.setFont(FontLoader.getInter(12f));
        forgotPasswordLabel.setBorder(new EmptyBorder(0,0,0,10));
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ThemeManager.putThemeAwareProperty(forgotPasswordLabel, "foreground: $LGVB.foreground");

        forgotPasswordPanel.add(forgotPasswordLabel, BorderLayout.EAST); // label floats right


        textFields.add(usernameLabel);
        textFields.add(Box.createRigidArea(new Dimension(0, UIScale.scale(5))));
        textFields.add(usernameField);
        textFields.add(Box.createRigidArea(new Dimension(0, UIScale.scale(15))));
        textFields.add(passwordLabel);
        textFields.add(Box.createRigidArea(new Dimension(0, UIScale.scale(5))));
        textFields.add(passwordField);
        textFields.add(Box.createRigidArea(new Dimension(0, UIScale.scale(10))));
        textFields.add(forgotPasswordPanel);
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
        loginBtn.setBackground(new Color(202, 28, 82));
        loginBtn.setFont(new Font("Inter", Font.PLAIN, 14));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerBtn = new RoundedButton("Register", 30);
        registerBtn.setPreferredSize(buttonSize);
        registerBtn.setMaximumSize(buttonSize);
        registerBtn.setForeground(new Color(23, 80, 110));
        registerBtn.setBackground(new Color(245, 247, 250));
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

    public RoundedTextField getUsernameField() {
        return usernameField;
    }

    public RoundedPasswordField getPasswordField() {
        return passwordField;
    }

    public RoundedButton getLoginBtn() {
        return loginBtn;
    }

    public RoundedButton getRegisterBtn() {
        return registerBtn;
    }

    public String getUsernameInput() {
        return usernameField.getText().trim();
    }

    public char[] getPasswordInput() {
        return passwordField.getPassword();
    }

    public void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
